package com.poixson.tools.scheduler;

import static com.poixson.logger.xLog.XLog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.Keeper;
import com.poixson.tools.xTime;
import com.poixson.tools.abstractions.xRunnable;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.tools.scheduler.trigger.Trigger;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xScheduler implements xStartable, Runnable {
	private static final boolean DEBUG = false;
	private static final String LOG_NAME        = "xSched";
	private static final String MAIN_SCHED_NAME = "main";

	private static final ConcurrentHashMap<String, xScheduler> instances =
			new ConcurrentHashMap<String, xScheduler>();

	public final String name;

	protected final CopyOnWriteArraySet<xSchedulerTask> tasks = new CopyOnWriteArraySet<xSchedulerTask>();

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);
	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);
	protected final AtomicBoolean sleeping = new AtomicBoolean(false);

	private final xTime threadSleepTime = new xTime("10s");
	private final double threadSleepInhibitPercent = 0.99;



	public static xScheduler Get() {
		return Get(null);
	}
	public static xScheduler Get(final String name) {
		// main scheduler
		if (Utils.isEmpty(name))
			return Get(MAIN_SCHED_NAME);
		// existing instance
		if (instances.containsKey(name)) {
			final xScheduler sched = instances.get(name);
			if (sched != null)
				return sched;
		}
		// load main first
		if (instances.isEmpty()) {
			if (!MAIN_SCHED_NAME.equals(name))
				Get(MAIN_SCHED_NAME);
		}
		// new instance
		{
			final xScheduler sched = new xScheduler(name);
			if (instances.putIfAbsent(name, sched) == null)
				return sched;
		}
		return Get(name);
	}



	public xScheduler(final String name) {
		if (Utils.isEmpty(name)) throw new IllegalArgumentException("name");
		this.name = name;
		if (this.isMain())
			Keeper.add(this);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop



	@Override
	public void start() {
		if (this.stopping.get())       return;
		if (this.thread.get() != null) return;
		final Thread thread = new Thread(this);
		if (this.thread.compareAndSet(null, thread)) {
			thread.setDaemon(true);
			thread.setName(LOG_NAME);
			thread.start();
		}
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		this.wake();
	}



	public static void StopAll() {
		final Iterator<xScheduler> it = instances.values().iterator();
		while (it.hasNext()) {
			final xScheduler sched = it.next();
			if (sched.isMain()) continue;
			sched.stop();
		}
		Get().stop();
	}



	// ------------------------------------------------------------------------------- //
	// manager loop



	@Override
	public void run() {
		if (this.stopping.get()) {
			this.thread.set(null);
			return;
		}
		if (!this.running.compareAndSet(false, true))
			throw new RuntimeException("xScheduler already running");
		if (this.thread.get() == null)
			this.thread.compareAndSet(null, Thread.currentThread());
		this.log().fine("Starting schedule manager..");
		final long sleepTime = this.threadSleepTime.ms();
		final Set<xSchedulerTask> finishedTasks = new HashSet<xSchedulerTask>();
		OUTER_LOOP:
		while (true) {
			if (this.stopping.get()) break OUTER_LOOP;
			final long now = getCurrentMillis();
			long sleep = sleepTime;
			// check task triggers
			{
				final Iterator<xSchedulerTask> it = this.tasks.iterator();
				TASKS_LOOP:
				while (it.hasNext()) {
					final xSchedulerTask task = it.next();
					final long untilNext = task.untilNext(now);
					// disabled
					if (untilNext == Long.MIN_VALUE)
						continue TASKS_LOOP;
					// trigger now
					if (untilNext <= 0L) {
						task.doTrigger();
						// mark for removal
						if (task.isRepeat()) {
							sleep = 0L;
						} else {
							task.setEnabled(false);
							finishedTasks.add(task);
						}
					} else
					if (sleep > untilNext) {
						sleep = untilNext;
					}
				} // end TASKS_LOOP
			}
			// finished tasks
			if (!finishedTasks.isEmpty()) {
				this.tasks.removeAll(finishedTasks);
				finishedTasks.clear();
			}
			if (sleep <= 0L) continue OUTER_LOOP;
			if (sleep > sleepTime) {
				sleep = sleepTime;
			} else {
				sleep = (long)Math.floor( ((double)sleep) * this.threadSleepInhibitPercent );
			}
			if (sleep <= 0L) continue OUTER_LOOP;
			// log sleep time
			final double sleepSec = ((double)sleep) / 1000.0;
			if (DEBUG)
				log().finest("Sleeping.. {} sec", NumberUtils.FormatDecimal("0.000", sleepSec));
			// sleep until next check
			this.sleeping.set(true);
			ThreadUtils.Sleep(sleep);
			this.sleeping.set(false);
		} // end OUTER_LOOP
		this.stopping.set(true);
		this.running.set(false);
		this.thread.set(null);
		this.log().fine("Stopped scheduler manager");
	}



	protected void wake() {
		final Thread thread = this.thread.get();
		if (thread == null) return;
		if (this.sleeping.get()) {
			try {
				thread.interrupt();
			} catch (Exception ignore) {}
		}
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		if (this.thread.get() == null)
			return false;
		return this.running.get();
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	public static long getCurrentMillis() {
		return Utils.getSystemMillis();
	}



	// ------------------------------------------------------------------------------- //
	// configs



	public boolean isMain() {
		return MAIN_SCHED_NAME.equals(this.name);
	}



	public String getName() {
		return this.name;
	}



	// ------------------------------------------------------------------------------- //
	// tasks



	public void add(final xSchedulerTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		this.tasks.add(task);
		this.wake();
	}
	public void add(final xRunnable run, final Trigger trigger) {
		if (run     == null) throw new RequiredArgumentException("run");
		if (trigger == null) throw new RequiredArgumentException("trigger");
		final xSchedulerTask task = new xSchedulerTask(run);
		task.setScheduler(this);
		task.add(trigger);
		this.add(task);
	}
	public void add(final String name, final Runnable run, final Trigger trigger) {
		if (Utils.isEmpty(name)) throw new RequiredArgumentException("name");
		if (run     == null)     throw new RequiredArgumentException("run");
		if (trigger == null)     throw new RequiredArgumentException("trigger");
		final xSchedulerTask task = new xSchedulerTask(name, run);
		task.setScheduler(this);
		task.add(trigger);
		this.add(task);
	}



	public boolean cancel(final xSchedulerTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		task.setScheduler(null);
		return this.tasks.remove(task);
	}
	public boolean cancel(final String taskName) {
		if (Utils.isEmpty(taskName)) throw new RequiredArgumentException("taskName");
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



	public boolean hasTask(final String taskName) {
		if (Utils.isEmpty(taskName)) throw new RequiredArgumentException("taskName");
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final xSchedulerTask task = it.next();
			if (task.equalsTaskName(taskName))
				return true;
		}
		return false;
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<xLog> _log = new AtomicReference<xLog>(null);

	public xLog log() {
		if (this._log.get() == null) {
			final xLog log = this._log();
			if (this._log.compareAndSet(null, log))
				return log;
		}
		return this._log.get();
	}
	private xLog _log() {
		return XLog(LOG_NAME)
				.get(this.getName());
	}



}
