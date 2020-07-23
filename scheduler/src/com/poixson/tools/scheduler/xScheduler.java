package com.poixson.tools.scheduler;

import static com.poixson.logger.xLog.XLog;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.abstractions.xStartable;
import com.poixson.logger.xLog;
import com.poixson.tools.Keeper;
import com.poixson.tools.xClock;
import com.poixson.tools.xTime;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xScheduler implements xStartable, Runnable {

	private static final String LOG_NAME        = "xSched";
	private static final String MAIN_SCHED_NAME = "main";

	private static final ConcurrentMap<String, xScheduler> instances =
			new ConcurrentHashMap<String, xScheduler>();

	private final String schedName;
	private final Set<xSchedulerTask> tasks = new CopyOnWriteArraySet<xSchedulerTask>();

	// manager thread
	private final Thread thread;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private final AtomicBoolean stopping = new AtomicBoolean(false);

	// manager thread sleep
	private final xTime threadSleepTime = xTime.getNew("5s");
	private final double threadSleepInhibitPercent = 0.95;
	private final AtomicBoolean sleeping = new AtomicBoolean(false);
	private final AtomicBoolean changes  = new AtomicBoolean(false);



	public static xScheduler getMainSched() {
		return get(MAIN_SCHED_NAME);
	}
	public static xScheduler get(final String schedName) {
		final String name = (
			Utils.isBlank(schedName)
			? MAIN_SCHED_NAME
			: schedName
		);
		// existing scheduler
		{
			final xScheduler sched = instances.get(name);
			if (sched != null)
				return sched;
		}
		// new scheduler instance
		{
			final xScheduler sched = new xScheduler(name);
			final xScheduler existing =
				instances.putIfAbsent(name, sched);
			if (existing != null) {
				Keeper.remove(sched);
				return existing;
			}
			return sched;
		}
	}



	private xScheduler(final String schedName) {
		if (Utils.isBlank(schedName)) throw new IllegalArgumentException("shedName");
		this.schedName = schedName;
		this.thread = new Thread(this);
		this.thread.setDaemon(false);
		this.thread.setName(LOG_NAME);
		Keeper.add(this);
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}



	@Override
	public void start() {
		if (this.stopping.get()) throw new RuntimeException("Scheduler already stopping");
		if (this.running.get())  throw new RuntimeException("Scheduler already running");
		this.thread.start();
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		this.wakeManager();
	}



//TODO: get list of tasks to run and trigger in proper order
	// manager loop
	@Override
	public void run() {
		if (this.stopping.get())
			return;
		if ( ! this.running.compareAndSet(false, true) )
			return;
		this.log().fine("Starting schedule manager..");
		final long threadSleep = this.threadSleepTime.getMS();
		final Set<xSchedulerTask> finishedTasks = new HashSet<xSchedulerTask>();
		OUTER_LOOP:
		while (true) {
			if ( this.stopping.get() || ! this.running.get() )
				break OUTER_LOOP;
			long sleep = threadSleep;
			finishedTasks.clear();
			// check task triggers
			{
				final Iterator<xSchedulerTask> it = this.tasks.iterator();
				this.changes.set(false);
				final long now = getClockMillis();
				INNER_LOOP:
				while (it.hasNext()) {
					final xSchedulerTask task = it.next();
					final long untilNext = task.untilSoonestTrigger(now);
					// disabled
					if (untilNext == Long.MIN_VALUE)
						continue INNER_LOOP;
					// trigger now
					if (untilNext <= 0L) {
						// clear thread interrupt
						Thread.interrupted();
						task.doTrigger();
						// mark for removal
						if (task.notRepeating()) {
							finishedTasks.add(task);
						}
						// running again soon
						if (task.untilSoonestTrigger(now) < 0L) {
							this.changes.set(true);
							continue INNER_LOOP;
						}
					}
					if (untilNext < sleep) {
						sleep = untilNext;
					}
				}
			}
			// remove finished tasks
			if (!finishedTasks.isEmpty()) {
				final Iterator<xSchedulerTask> it = finishedTasks.iterator();
				//REMOVE_LOOP:
				while (it.hasNext()) {
					final xSchedulerTask task = it.next();
					task.unregister();
					this.tasks.remove(task);
				} // end REMOVE_LOOP
			}
			// no sleep needed
			if (this.changes.get() || sleep <= 0L)
				continue OUTER_LOOP;
			// calculate sleep
			final long sleepLess = (
				sleep <= threadSleep
				? (long) Math.ceil( ((double) sleep) * this.threadSleepInhibitPercent )
				: threadSleep
			);
			// no sleep needed
			if (this.changes.get() || sleep <= 0L)
				continue OUTER_LOOP;
			// log sleep time
			if (this.isDetailedLogging()) {
				final double sleepLessSec = ((double)sleepLess) / 1000.0;
				log().finest(
					"Sleeping.. {} s",
					NumberUtils.FormatDecimal("0.000", sleepLessSec)
				);
			}
			// sleep until next check
			this.sleeping.set(true);
			if ( ! this.changes.get() ) {
				ThreadUtils.Sleep(sleepLess);
			}
			this.sleeping.set(false);
		} // end OUTER_LOOP
		finishedTasks.clear();
		log().fine("Stopped scheduler manager thread");
		this.stopping.set(true);
		this.running.set(false);
		Keeper.remove(this);
	}
	public void wakeManager() {
		this.changes.set(true);
		if (this.sleeping.get()) {
			try {
				this.thread.interrupt();
			} catch (Exception ignore) {}
		}
	}



	@Override
	public boolean isRunning() {
		if (this.stopping.get())
			return false;
		return this.running.get();
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	// ------------------------------------------------------------------------------- //
	// scheduler config



	// scheduler name
	public String getName() {
		return this.schedName;
	}



	// tasks
	public void add(final xSchedulerTask task) {
		this.tasks.add(task);
		this.wakeManager();
	}
//TODO:
/*
	public boolean hasTask(final String taskName) {
		if (Utils.isBlank(taskName))
			return false;
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final String name = it.next().getTaskName();
			if (taskName.equals(name))
				return true;
			if (it.next().taskNameEquals(taskName)) {
				return true;
			}
		}
		return false;
	}
*/



	public boolean cancel(final String taskName) {
		if (Utils.isBlank(taskName))
			return false;
		boolean found = false;
		final Iterator<xSchedulerTask> it = this.tasks.iterator();
		while (it.hasNext()) {
			final String name = it.next().getTaskName();
			if (taskName.equals(name)) {
				it.remove();
				found = true;
			}
		}
		return found;
	}
	public boolean cancel(final xSchedulerTask task) {
		if (task == null)
			return false;
		if (this.tasks.contains(task)) {
			task.unregister();
			return this.tasks.remove(task);
		}
		return false;
	}



	// ------------------------------------------------------------------------------- //
	// clock



	private static final AtomicReference<SoftReference<xClock>> _clock =
			new AtomicReference<SoftReference<xClock>>(null);

	public static long getClockMillis() {
		return getClock().millis();
	}
	public static xClock getClock() {
		// cached clock
		final SoftReference<xClock> ref = _clock.get();
		if (ref != null) {
			final xClock clock = ref.get();
			if (clock != null)
				return clock;
		}
		// get clock
		{
			final xClock clock = xClock.get(false);
			_clock.set(
				new SoftReference<xClock>(
					clock
				)
			);
			return clock;
		}
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<SoftReference<xLog>> _log =
			new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached logger
		final SoftReference<xLog> ref = this._log.get();
		if (ref != null) {
			final xLog log = ref.get();
			if (log != null)
				return log;
		}
		// get logger
		{
			final xLog log = this._log();
			this._log.set(
				new SoftReference<xLog>(
					log
				)
			);
			return log;
		}
	}
	protected xLog _log() {
		return
			xLogRoot.Get(LOG_NAME)
				.getWeak(this.getName());
	}



	// cached log level
	private final AtomicReference<SoftReference<Boolean>> _detail =
			new AtomicReference<SoftReference<Boolean>>(null);

	public boolean isDetailedLogging() {
		// cached value
		final SoftReference<Boolean> ref = this._detail.get();
		if (ref != null) {
			final Boolean detail = ref.get();
			if (detail != null)
				return detail.booleanValue();
		}
		// get log level
		{
			final boolean detail =
				this.log().isDetailLoggable();
			this._detail.set(
				new SoftReference<Boolean>(
					Boolean.valueOf( detail )
				)
			);
			return detail;
		}
	}



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
		return XLog(LOG_NAME);
	}



}
