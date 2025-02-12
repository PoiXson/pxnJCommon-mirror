package com.poixson.threadpool;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.ContinueException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.task.xThreadPoolTask;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.threadpool.worker.xThreadPoolWorker;
import com.poixson.tools.Keeper;
import com.poixson.tools.xTime;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.startstop.xStartStop;


public abstract class xThreadPool implements xStartStop, Runnable {
	public static final boolean DEBUG_EXTRA = false;

	public static final int  HARD_MAX_WORKERS = 100;
	public static final long WORKER_START_TIMEOUT = 500L;
	public static final int  DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY;

	protected static final ConcurrentHashMap<String, xThreadPool> pools = new ConcurrentHashMap<String, xThreadPool>(3);
	protected final AtomicBoolean keepOneAlive = new AtomicBoolean(true);

	public final String pool_name;

	protected final ThreadGroup thread_group;
	protected final AtomicInteger threadPriority = new AtomicInteger(DEFAULT_THREAD_PRIORITY);

	// run now queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueNow   = new LinkedBlockingDeque<xThreadPoolTask>();
	// run later queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueLater = new LinkedBlockingDeque<xThreadPoolTask>();
	// run lazy queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueLazy  = new LinkedBlockingDeque<xThreadPoolTask>();

	// pool state
	protected        final AtomicBoolean running     = new AtomicBoolean(false);
	protected        final AtomicBoolean stopping    = new AtomicBoolean(false);
	protected static final AtomicBoolean StoppingAll = new AtomicBoolean(false);

	// stats
	protected final AtomicLong count_now   = new AtomicLong(0L);
	protected final AtomicLong count_later = new AtomicLong(0L);
	protected final AtomicLong count_lazy  = new AtomicLong(0L);
	protected final AtomicLong count_idle_loops = new AtomicLong(0);
	protected final AtomicLong count_task_index = new AtomicLong(0L);



	public static xThreadPool get(final String pool_name) {
		return pools.get(pool_name);
	}



	protected xThreadPool(final String pool_name) {
		if (IsEmpty(pool_name)) throw new RequiredArgumentException("pool_name");
		if (StoppingAll.get())
			throw new IllegalStateException("Cannot create new thread pool, already stopping all!");
		this.pool_name = pool_name;
		this.thread_group = new ThreadGroup(pool_name);
	}



	// -------------------------------------------------------------------------------
	// start/stop pool



	@Override
	public boolean start() {
		if (this.okStart()) {
			this.startNewWorkerIfNeededAndAble();
			Keeper.Add(this);
			return true;
		}
		return false;
	}

	@Override
	public abstract void run();

	protected boolean okStart() {
		if (StoppingAll.get()) return false;
		if (this.isRunning())  return false;
		this.stopping.set(false);
		this.queueStartupTask();
		return true;
	}

	protected void queueStartupTask() {
		final xThreadPoolTask task =
			new xThreadPoolTask(this, "Pool-Startup") {
				@Override
				public void run() {
					xThreadPool.this.log()
						.finer("Thread queue is running..");
				}
			};
		this.queueLater.addFirst(task);
	}

	@Override
	public boolean stop() {
		Keeper.Remove(this);
		if (this.stopping.compareAndSet(false, true)) {
			this.stopWorkers();
			return true;
		}
		return false;
	}
	public static void StopAll() {
		StoppingAll.set(true);
		final Iterator<xThreadPool> it = pools.values().iterator();
		LOOP_WORKERS:
		while (it.hasNext()) {
			final xThreadPool pool = it.next();
			if (pool.isMainPool())     continue LOOP_WORKERS;
			if (pool.isGraphicsPool()) continue LOOP_WORKERS;
			pool.stop();
		} // end LOOP_WORKERS
	}



	// -------------------------------------------------------------------------------
	// workers



	public abstract xThreadPoolWorker[] getWorkers();
	protected abstract void stopWorkers();

	protected abstract void startNewWorkerIfNeededAndAble();



	public void join(final xTime time) {
		if (time == null) this.join(0L);
		else              this.join(time.ms());
	}
	public void join() {
		this.join(0L);
	}

	public abstract void join(final long timeout);



	public abstract void unregisterWorker(final xThreadPoolWorker worker);



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isRunning() {
		return this.running.get();
	}
	@Override
	public boolean isStopping() {
		if (StoppingAll.get())
			return true;
		return this.stopping.get();
	}
	public static boolean isStoppingAll() {
		return StoppingAll.get();
	}



	/**
	 * Are task queues empty.
	 * @return true if all task queues are empty.
	 */
	public boolean isEmpty() {
		if (!this.queueLazy.isEmpty() ) return false;
		if (!this.queueLater.isEmpty()) return false;
		if (!this.queueNow.isEmpty()  ) return false;
		return true;
	}



	public boolean isMainPool() {
		return false;
	}
	public boolean isGraphicsPool() {
		return false;
	}



	// active workers
	public abstract int getActiveCount();



	// next worker index
	public abstract long getNextWorkerIndex();

	// next task index
	public long getNextTaskIndex() {
		return this.count_task_index
				.incrementAndGet();
	}



	public abstract xThreadPoolWorker getCurrentThreadWorker();
	public abstract boolean isCurrentThread();



	// -------------------------------------------------------------------------------
	// queue task



	public xThreadPoolTask grabNextTask() throws InterruptedException {
		// loop a few times
		LOOP_PRIORITY:
		while (true) {
			// high priority tasks
			{
				final xThreadPoolTask task = this.queueNow.poll();
				if (task != null) {
					this.count_now.incrementAndGet();
					return task;
				}
			}
			// normal priority tasks
			{
				final xThreadPoolTask task = this.queueLater.poll();
				if (task != null) {
					this.count_later.incrementAndGet();
					return task;
				}
			}
			// low priority tasks (before waiting on high)
			{
				final int mod_low = (int) (this.count_idle_loops.incrementAndGet() % ((long)this.getLowLoopCount()));
				if (mod_low == 0) {
					final xThreadPoolTask task = this.queueLazy.poll();
					if (task != null) {
						this.count_idle_loops.set(-1);
						this.count_lazy.incrementAndGet();
						return task;
					}
				}
			}
			if (this.isStopping()
			&&  this.isEmpty())
				break LOOP_PRIORITY;
			// wait for high priority tasks
			{
				final xThreadPoolTask task;
				task = this.queueNow.poll(
					this.getHighLoopWait(),
					TimeUnit.MILLISECONDS
				);
				if (task != null) {
					this.count_now.incrementAndGet();
					return task;
				}
			}
		} // end LOOP_PRIORITY
		return null;
	}



	public xThreadPoolTask addTask(final xThreadTaskPriority priority,
			final Runnable run) {
		return this.addTask(priority, null, run);
	}
	public xThreadPoolTask addTask(final xThreadTaskPriority priority,
			final String taskName, final Runnable run) {
		if (run instanceof xThreadPoolTask) {
			final xThreadPoolTask task = (xThreadPoolTask) run;
			if (!IsEmpty(taskName))
				task.setTaskName(taskName);
			this.addTask(priority, task);
			return task;
		} else {
			final xThreadPoolTask task =
				new xThreadPoolTask(
					this,
					taskName,
					run
				);
			this.addTask(priority, task);
			return task;
		}
	}
	public void addTask(final xThreadTaskPriority priority,
			final xThreadPoolTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		// pass to main thread pool
		if (this.getMaxWorkers() == 0) {
			xThreadPool_Main.Get()
				.addTask(priority, task);
			return;
		}
		final xThreadTaskPriority pri = (priority == null ? xThreadTaskPriority.LATER : priority);
//TODO: remove this?
//		// run now as current thread
//		if (TaskPriority.NOW.equals(pri)) {
//			final xThreadPoolWorker worker = this.getCurrentThreadWorker();
//			if (worker != null)
//				return;
//		}
		// get priority queue
		final LinkedBlockingDeque<xThreadPoolTask> queue = this.getQueueByPriority(pri);
		// add task to queue
		final int maxAddAttempts = this.getMaxAddAttempts();
		final long addTimeout    = this.getAddTimeout();
		try {
			boolean success = false;
			LOOP_QUEUE:
			for (int i=0; i<maxAddAttempts; i++) {
				success = queue.offer(task, addTimeout, TimeUnit.MILLISECONDS);
				if (success)
					break LOOP_QUEUE;
			} // end LOOP_QUEUE
			if (this.isRunning())
				this.startNewWorkerIfNeededAndAble();
			// timeout adding to queue
			if (!success) {
				this.log().warning("Thread queue %s jammed!", pri.name());
				// try a lower priority
				switch (priority) {
				case NOW:
					this.log().warning("Thread queue jammed, trying a lower priority.. (high->norm)", task.getTaskName());
					this.addTask(xThreadTaskPriority.LATER, task);
					return;
				case LATER:
					this.log().warning("Thread queue jammed, trying a lower priority.. (norm->low)", task.getTaskName());
					this.addTask(xThreadTaskPriority.LAZY, task);
					return;
				default: break;
				}
				throw new RuntimeException("Timeout queueing task: "+task.getTaskName());
			}
		} catch (InterruptedException ignore) {
			throw new RuntimeException("Interrupted queueing task: "+task.getTaskName());
		}
	}
	protected LinkedBlockingDeque<xThreadPoolTask> getQueueByPriority(final xThreadTaskPriority priority) {
		if (priority == null) throw new RequiredArgumentException("priority");
		switch (priority) {
		case NOW:   return this.queueNow;
		case LATER: return this.queueLater;
		case LAZY:  return this.queueLazy;
		default: break;
		}
		throw new UnsupportedOperationException("Unknown task priority: "+priority.toString());
	}



	// now
	public void runTaskNow(final Runnable run) {
		this.addTask( xThreadTaskPriority.NOW, run );
	}
	public void runTaskNow(final String taskName, final Runnable run) {
		this.addTask( xThreadTaskPriority.NOW, taskName, run );
	}
	public void runTaskNow(final xThreadPoolTask task) {
		this.addTask( xThreadTaskPriority.NOW, task );
	}



	// later
	public void runTaskLater(final Runnable run) {
		this.addTask( xThreadTaskPriority.LATER, run );
	}
	public void runTaskLater(final String taskName, final Runnable run) {
		this.addTask( xThreadTaskPriority.LATER, taskName, run );
	}
	public void runTaskLater(final xThreadPoolTask task) {
		this.addTask( xThreadTaskPriority.LATER, task );
	}



	// lazy
	public void runTaskLazy(final Runnable run) {
		this.addTask( xThreadTaskPriority.LAZY, run );
	}
	public void runTaskLazy(final String taskName, final Runnable run) {
		this.addTask( xThreadTaskPriority.LAZY, taskName, run );
	}
	public void runTaskLazy(final xThreadPoolTask task) {
		this.addTask( xThreadTaskPriority.LAZY, task );
	}



	// -------------------------------------------------------------------------------
	// force proper thread



	/**
	 * Forces a method to be called from the correct thread.
	 * @param callingFrom Class object which contains the method.
	 * @param methodName The method which is being called.
	 * @param priority
	 * @param args Arguments being passed to the method.
	 * @return false if already in the correct thread;
	 *   true if calling from some other thread. this will queue
	 *   a task to call the method in the correct thread and return
	 *   true to signal bypassing the method following.
	 * Example:
	 * public void getSomething() {
	 *     final xThreadPool pool = xThreadPool_Main.get();
	 *     if (pool.proper(this, "getSomething", false))
	 *         return;
	 *     // do something here
	 * }
	 */
	public boolean proper(
			final Object callingFrom, final String methodName,
			final xThreadTaskPriority priority, final Object...args) {
		if (callingFrom == null) throw new RequiredArgumentException("callingFrom");
		if (IsEmpty(methodName)) throw new RequiredArgumentException("methodName");
		if (priority == null)    throw new RequiredArgumentException("priority");
		// already running in correct thread
		if (this.isCurrentThread())
			return false;
		// queue to run in correct thread
		final RunnableMethod<Object> run = new RunnableMethod<Object>(callingFrom, methodName, args);
		this.addTask(priority, run);
		return true;
	}
	public boolean proper(
			final Object callingFrom, final String methodName,
			final Object...args) {
		return this.proper(callingFrom, methodName, xThreadTaskPriority.LATER, args);
	}



	/**
	 * Forces a method to be called from the correct thread.
	 * @param callingFrom Class object which contains the method.
	 * @param methodName The method which is being called.
	 * @param priority
	 * @param args Arguments being passed to the method.
	 * @return resulting return value if not in the correct thread.
	 *   this will queue a task to run in the correct thread.
	 *   if already in the correct thread, ContinueException is
	 *   throws to signal to continue running the method following.
	 * Example:
	 * public boolean getSomething() {
	 *     try {
	 *         return xThreadPool_Main.get()
	 *             .properResult(this, "getSomething");
	 *     } catch (ContinueException ignore) {}
	 *     // do something here
	 *     return result;
	 * }
	 */
	public <V> V properResult(
			final Object callingFrom, final String methodName,
			final xThreadTaskPriority priority, final Object...args)
			throws ContinueException {
		if (callingFrom == null) throw new RequiredArgumentException("callingFrom");
		if (IsEmpty(methodName)) throw new RequiredArgumentException("methodName");
		if (priority == null)    throw new RequiredArgumentException("priority");
		// already running in correct thread
		if (this.isCurrentThread())
			throw new ContinueException();
		// queue to run in correct thread
		final RunnableMethod<V> run = new RunnableMethod<V>(callingFrom, methodName, args);
		this.addTask(priority, run);
		return run.getResult();
	}
	public <V> V properResult(
			final Object callingFrom, final String methodName,
			final Object...args) throws ContinueException {
		return this.properResult(callingFrom, methodName, xThreadTaskPriority.NOW, args);
	}



	// throw exception if not in expected thread
	public <E extends Exception> void threadOrException(final E e) throws E {
		if ( ! this.isCurrentThread() )
			throw e;
	}
	public void threadOrException() throws RuntimeException {
		this.threadOrException(new RuntimeException());
	}



	// -------------------------------------------------------------------------------
	// config



	public String getPoolName() {
		return this.pool_name;
	}



	public abstract int getMaxWorkers();
	public abstract void setMaxWorkers(final int maxWorkers);



	public int getThreadPriority() {
		return this.threadPriority.get();
	}
	public void setThreadPriority(final int priority) {
		this.threadPriority.set(priority);
		this.thread_group.setMaxPriority(priority);
	}



	public ThreadGroup getThreadGroup() {
		return this.thread_group;
	}



	public long getHighLoopWait() {
		return 25L;
	}
	public int getLowLoopCount() {
		return 5;
	}
	public long getAddTimeout() {
		return 100L;
	}
	public int getMaxAddAttempts() {
		return 5;
	}



	// -------------------------------------------------------------------------------
	// stats



	public long getQueueCountNow() {
		return this.count_now.get();
	}
	public long getQueueCountLater() {
		return this.count_later.get();
	}
	public long getQueueCountLazy() {
		return this.count_lazy.get();
	}
	public long getQueueCount(final xThreadTaskPriority priority) {
		switch (priority) {
		case NOW:
			return this.count_now.get();
		case LATER:
			return this.count_later.get();
		case LAZY:
			return this.count_lazy.get();
		default: break;
		}
		return -1L;
	}
	public long getTaskCountTotal() {
		long count = 0L;
		count += this.count_lazy.get();
		count += this.count_later.get();
		count += this.count_now.get();
		return count;
	}



	public long getRunCount() {
		final xThreadPoolWorker[] workers = this.getWorkers();
		if (workers == null)
			return 0L;
		long count = 0L;
		for (final xThreadPoolWorker worker : workers) {
			count += worker.getRunCount();
		}
		return count;
	}



	public abstract int getWorkerCount();
	public abstract int getActiveWorkerCount();
	public abstract int getInactiveWorkerCount();



	public long getIdleLoops() {
		return this.count_idle_loops.get();
	}



	public int getQueueCount() {
		int count = this.queueLazy.size();
		count += this.queueLater.size();
		count += this.queueNow.size();
		return count;
	}



	public String getStatsDisplay() {
		return
			String.format(
				"Queued: %d  Threads: %d[%d]  Active/Free: %d/%d  Finished: %d",
				Integer.valueOf( this.getQueueCount()          ),
				Integer.valueOf( this.getWorkerCount()         ),
				Integer.valueOf( this.getMaxWorkers()          ),
				Integer.valueOf( this.getActiveWorkerCount()   ),
				Integer.valueOf( this.getInactiveWorkerCount() ),
				Long.valueOf(    this.getRunCount()            )
			);
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);

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
		final StringBuilder name = new StringBuilder();
		name.append("threadpool-").append(this.getPoolName());
		return xLog.Get(name.toString());
	}



}
