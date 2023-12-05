package com.poixson.tools.scheduler;

import static com.poixson.utils.Utils.GetMS;
import static com.poixson.utils.Utils.IsEmpty;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.xTime;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.ThreadUtils;


public class xScheduler implements xStartable, Runnable {
	public static final boolean DEBUG = false;
	public static final String LOG_NAME = "sched";

	protected final String name;

	protected final CopyOnWriteArraySet<xSchedulerTask> tasks = new CopyOnWriteArraySet<xSchedulerTask>();

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);
	protected final AtomicReference<xThreadPool> pool = new AtomicReference<xThreadPool>(null);

	protected final AtomicBoolean stopping = new AtomicBoolean(false);
	protected final AtomicBoolean sleeping = new AtomicBoolean(false);

	protected final xTime max_sleep_time = new xTime("10s");
	protected final double threadSleepInhibitPercent = 0.9999;



	public xScheduler() {
		this(null);
	}
	public xScheduler(final String name) {
		this.name = name;
	}



	@Override
	public void start() {
		if (this.thread.get() == null) {
			this.stopping.set(false);
			final Thread thread = new Thread(this);
			if (this.thread.compareAndSet(null, thread)) {
				thread.setDaemon(true);
				thread.setName(String.format("%s:%s", LOG_NAME, this.name));
				thread.start();
			}
		}
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		this.wake();
	}



	@Override
	public void run() {
		if (this.stopping.get()) {
			this.thread.set(null);
			return;
		}
		this.log().fine("Starting schedule manager..");
		if (this.thread.get() == null)
			this.thread.compareAndSet(null, Thread.currentThread());
		final Set<xSchedulerTask> tasks_finished = new HashSet<xSchedulerTask>();
		final long sleep_time = this.max_sleep_time.ms();
		RUN_LOOP:
		while (true) {
			if (this.stopping.get()) break RUN_LOOP;
			final long now = getCurrentMillis();
			long sleep = sleep_time;
			// check task triggers
			final Iterator<xSchedulerTask> it = this.tasks.iterator();
			TASK_LOOP:
			while (it.hasNext()) {
				final xSchedulerTask task = it.next();
				final long until_next = task.getUntilNext(now);
				// disabled
				if (until_next == Long.MIN_VALUE)
					continue TASK_LOOP;
				// trigger now
				if (until_next <= 0L) {
					task.doTrigger();
					// mark for removal
					if (task.isRepeat()) {
						sleep = 0L;
					} else {
						task.setEnabled(false);
						tasks_finished.add(task);
					}
				} else
				if (sleep > until_next) {
					sleep = until_next;
				}
			} // end TASK_LOOP
			// finished tasks
			if (!tasks_finished.isEmpty()) {
				this.tasks.removeAll(tasks_finished);
				tasks_finished.clear();
			}
			if (sleep <= 0L) continue RUN_LOOP;
			if (sleep > sleep_time) sleep = sleep_time;
			else                    sleep = (long)Math.floor( ((double)sleep) * this.threadSleepInhibitPercent );
			if (sleep <= 0L) continue RUN_LOOP;
			// log sleep time
			final double sleepSec = ((double)sleep) / 1000.0;
			if (DEBUG)
				this.log().finest("Sleeping.. %s sec", NumberUtils.FormatDecimal("0.000", sleepSec));
			// sleep until next check
			this.sleeping.set(true);
			ThreadUtils.Sleep(sleep);
			this.sleeping.set(false);
		} // end RUN_LOOP
		this.stopping.set(true);
		this.thread.set(null);
		this.log().fine("Stopped scheduler manager");
	}



	protected void wake() {
		final Thread thread = this.thread.get();
		if (thread != null) {
			if (this.sleeping.get()) {
				try {
					thread.interrupt();
				} catch (Exception ignore) {}
			}
		}
	}



	public static long getCurrentMillis() {
		return GetMS();
	}



	public xThreadPool getThreadPool() {
		final xThreadPool pool = this.pool.get();
		return (pool == null ? xThreadPool_Main.Get() : pool);
	}



	// -------------------------------------------------------------------------------
	// tasks



	public void add(final xSchedulerTask task) {
		task.setScheduler(this);
		this.tasks.add(task);
		this.wake();
	}
	public boolean cancel(final xSchedulerTask task) {
		final boolean result = this.tasks.remove(task);
		task.unsetScheduler(this);
		return result;
	}
	public boolean cancel(final String taskName) {
		if (IsEmpty(taskName)) throw new RequiredArgumentException("taskName");
		boolean found = false;
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final xSchedulerTask task = it.next();
			if (task.equalsTaskName(taskName)) {
				this.cancel(task);
				found = true;
			}
		}
		return found;
	}
	public void cancelAllTasks() {
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final xSchedulerTask task = it.next();
			this.cancel(task);
		}
	}
	public boolean hasTask(final String taskName) {
		if (IsEmpty(taskName)) throw new RequiredArgumentException("taskName");
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final xSchedulerTask task = it.next();
			if (task.equalsTaskName(taskName))
				return true;
		}
		return false;
	}



	// -------------------------------------------------------------------------------



	@Override
	public boolean isRunning() {
		if (this.stopping.get()) return false;
		return (this.thread.get() != null);
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	public String getName() {
		return this.name;
	}
	public boolean isMain() {
		return (this.name == null);
	}



	// -------------------------------------------------------------------------------
	// logger



	protected final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);
	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log == null) this._log.set(null);
				else             return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get(LOG_NAME)
				.get(this.getName());
	}



}
