package com.poixson.threadpool;

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
import com.poixson.logger.xLogRoot;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public abstract class xThreadPool implements xStartable {

	public static final int  HARD_MAX_WORKERS = 100;
	public static final int  DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY;

	public enum TaskPriority {
		NOW,
		LATER,
		LAZY
	};

	protected static final ConcurrentHashMap<String, xThreadPool> pools = new ConcurrentHashMap<String, xThreadPool>(3);

	public final String poolName;

	// pool state
	protected        final AtomicBoolean running     = new AtomicBoolean(false);
	protected        final AtomicBoolean stopping    = new AtomicBoolean(false);
	protected static final AtomicBoolean StoppingAll = new AtomicBoolean(false);

	protected final AtomicBoolean keepOneAlive = new AtomicBoolean(true);

	protected final ThreadGroup threadGroup;
	protected final AtomicInteger threadPriority = new AtomicInteger(DEFAULT_THREAD_PRIORITY);

	// run now queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueNow   = new LinkedBlockingDeque  <xThreadPoolTask>();
	// run later queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueLater = new LinkedBlockingDeque  <xThreadPoolTask>();
	// run lazy queue
	protected final LinkedBlockingDeque<xThreadPoolTask> queueLazy  = new LinkedBlockingDeque  <xThreadPoolTask>();

	// stats
	protected final AtomicLong countNow   = new AtomicLong(0L);
	protected final AtomicLong countLater = new AtomicLong(0L);
	protected final AtomicLong countLazy  = new AtomicLong(0L);
	protected final AtomicLong idleLoopCount  = new AtomicLong(0);
	protected final AtomicLong taskIndexCount = new AtomicLong(0L);



	public static xThreadPool get(final String poolName) {
		return pools.get(poolName);
	}



	protected xThreadPool(final String poolName) {
		if (Utils.isEmpty(poolName)) throw new RequiredArgumentException("poolName");
		if (StoppingAll.get())
			throw new IllegalStateException("Cannot create new thread pool, already stopping all!");
		this.poolName = poolName;
		this.threadGroup = new ThreadGroup(poolName);
		Keeper.add(this);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop pool



	@Override
	public void start() {
		if (!this.okStart()) return;
		this.startNewWorkerIfNeededAndAble();
		Keeper.add(this);
	}

	// start with current thread
	public abstract void go();

	@Override
	public void run() {
		this.go();
	}

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
						.fine("Thread queue is running..");
				}
			};
		this.queueLater.addFirst(task);
	}



	@Override
	public void stop() {
		if (this.stopping.compareAndSet(false, true)) {
			this.stopWorkers();
		}
		Keeper.remove(this);
	}
	public static void StopAll() {
		StoppingAll.set(true);
		final Iterator<xThreadPool> it = pools.values().iterator();
		WORKERS_LOOP:
		while (it.hasNext()) {
			final xThreadPool pool = it.next();
			if ( pool.isMainPool() || pool.isGraphicsPool() )
				continue WORKERS_LOOP;
			pool.stop();
		} // end WORKERS_LOOP
	}



	// ------------------------------------------------------------------------------- //
	// workers



	public abstract xThreadPoolWorker[] getWorkers();
	protected abstract void stopWorkers();

	protected abstract void startNewWorkerIfNeededAndAble();



	public abstract void joinWorkers(final long timeout);

	public void joinWorkers() {
		this.joinWorkers(0L);
	}



	public abstract void unregisterWorker(final xThreadPoolWorker worker);



	// ------------------------------------------------------------------------------- //
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
		return this.taskIndexCount
				.incrementAndGet();
	}



	public abstract xThreadPoolWorker getCurrentThreadWorker();
	public abstract boolean isCurrentThread();



	/**
	 * Are task queues empty.
	 * @return true if all task queues are empty.
	 */
	public boolean isEmpty() {
		if ( ! this.queueLazy.isEmpty()  ) return false;
		if ( ! this.queueLater.isEmpty() ) return false;
		if ( ! this.queueNow.isEmpty()   ) return false;
		return true;
	}



	// ------------------------------------------------------------------------------- //
	// queue task



	public xThreadPoolTask grabNextTask() throws InterruptedException {
		// loop a few times
		//PRIORITY_LOOP:
		while (true) {
			if (this.isStopping())
				return null;
			// high priority tasks
			{
				final xThreadPoolTask task = this.queueNow.poll();
				if (task != null) {
					this.countNow.incrementAndGet();
					return task;
				}
			}
			// normal priority tasks
			{
				final xThreadPoolTask task = this.queueLater.poll();
				if (task != null) {
					this.countLater.incrementAndGet();
					return task;
				}
			}
			// low priority tasks (before waiting on high)
			{
				final int index = (int) (this.idleLoopCount.incrementAndGet() % ((long)this.getLowLoopCount()));
				if (index == 0) {
					final xThreadPoolTask task = this.queueLazy.poll();
					if (task != null) {
						this.idleLoopCount.set(-1);
						this.countLazy.incrementAndGet();
						return task;
					}
				}
			}
			// wait for high priority tasks
			{
				final xThreadPoolTask task;
				task = this.queueNow.poll(
					this.getHighLoopWait(),
					TimeUnit.MILLISECONDS
				);
				if (task != null) {
					this.countNow.incrementAndGet();
					return task;
				}
			}
		} // end PRIORITY_LOOP
	}



	public xThreadPoolTask addTask(final TaskPriority priority,
			final Runnable run) {
		return this.addTask(priority, null, run);
	}
	public xThreadPoolTask addTask(final TaskPriority priority,
			final String taskName, final Runnable run) {
		if (run instanceof xThreadPoolTask) {
			final xThreadPoolTask task = (xThreadPoolTask) run;
			if (Utils.notEmpty(taskName))
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
	public void addTask(final TaskPriority priority,
			final xThreadPoolTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		// pass to main thread pool
		if (this.getMaxWorkers() == 0) {
			xThreadPool_Main.Get()
				.addTask(priority, task);
			return;
		}
		final TaskPriority pri = (priority == null ? TaskPriority.LATER : priority);
//TODO: remove this
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
			QUEUE_LOOP:
			for (int i=0; i<maxAddAttempts; i++) {
				success = queue.offer(task, addTimeout, TimeUnit.MILLISECONDS);
				if (success)
					break QUEUE_LOOP;
			} // end QUEUE_LOOP
			if (this.isRunning()) {
				this.startNewWorkerIfNeededAndAble();
			}
			// timeout adding to queue
			if (!success) {
				this.log().warning("Thread queue {} jammed!", pri.name());
				// try a lower priority
				switch (priority) {
				case NOW:
					this.log().warning("Thread queue jammed, trying a lower priority.. (high->norm)", task.getTaskName());
					this.addTask(TaskPriority.LATER, task);
					return;
				case LATER:
					this.log().warning("Thread queue jammed, trying a lower priority.. (norm->low)", task.getTaskName());
					this.addTask(TaskPriority.LAZY, task);
					return;
				default: break;
				}
				throw new RuntimeException("Timeout queueing task: "+task.getTaskName());
			}
		} catch (InterruptedException ignore) {
			throw new RuntimeException("Interrupted queueing task: "+task.getTaskName());
		}
	}
	protected LinkedBlockingDeque<xThreadPoolTask> getQueueByPriority(final TaskPriority priority) {
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
		this.addTask( TaskPriority.NOW, run );
	}
	public void runTaskNow(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.NOW, taskName, run );
	}
	public void runTaskNow(final xThreadPoolTask task) {
		this.addTask( TaskPriority.NOW, task );
	}



	// later
	public void runTaskLater(final Runnable run) {
		this.addTask( TaskPriority.LATER, run );
	}
	public void runTaskLater(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.LATER, taskName, run );
	}
	public void runTaskLater(final xThreadPoolTask task) {
		this.addTask( TaskPriority.LATER, task );
	}



	// lazy
	public void runTaskLazy(final Runnable run) {
		this.addTask( TaskPriority.LAZY, run );
	}
	public void runTaskLazy(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.LAZY, taskName, run );
	}
	public void runTaskLazy(final xThreadPoolTask task) {
		this.addTask( TaskPriority.LAZY, task );
	}



	// ------------------------------------------------------------------------------- //
	// force thread



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
			final TaskPriority priority, final Object...args) {
		if (callingFrom == null)       throw new RequiredArgumentException("callingFrom");
		if (Utils.isEmpty(methodName)) throw new RequiredArgumentException("methodName");
		if (priority == null)          throw new RequiredArgumentException("priority");
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
		return this.proper(callingFrom, methodName, TaskPriority.LATER, args);
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
			final TaskPriority priority, final Object...args)
			throws ContinueException {
		if (callingFrom == null)       throw new RequiredArgumentException("callingFrom");
		if (Utils.isEmpty(methodName)) throw new RequiredArgumentException("methodName");
		if (priority == null)          throw new RequiredArgumentException("priority");
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
		return this.properResult(callingFrom, methodName, TaskPriority.NOW, args);
	}



	// throw exception if not in expected thread
	public <E extends Exception> void threadOrException(final E e) throws E {
		if ( ! this.isCurrentThread() )
			throw e;
	}
	public void threadOrException() throws RuntimeException {
		this.threadOrException(new RuntimeException());
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getPoolName() {
		return this.poolName;
	}



	public abstract int getMaxWorkers();
	public abstract void setMaxWorkers(final int maxWorkers);



	public int getThreadPriority() {
		return this.threadPriority.get();
	}
	public void setThreadPriority(final int priority) {
		this.threadPriority.set(priority);
		this.threadGroup.setMaxPriority(priority);
	}



	public ThreadGroup getThreadGroup() {
		return this.threadGroup;
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



	// ------------------------------------------------------------------------------- //
	// stats



	public long getQueueCountNow() {
		return this.countNow.get();
	}
	public long getQueueCountLater() {
		return this.countLater.get();
	}
	public long getQueueCountLazy() {
		return this.countLazy.get();
	}
	public long getQueueCount(final TaskPriority priority) {
		switch (priority) {
		case NOW:
			return this.countNow.get();
		case LATER:
			return this.countLater.get();
		case LAZY:
			return this.countLazy.get();
		default: break;
		}
		return -1L;
	}
	public long getTaskCountTotal() {
		long count = 0L;
		count += this.countLazy.get();
		count += this.countLater.get();
		count += this.countNow.get();
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
		return this.idleLoopCount.get();
	}



	public int getQueueCount() {
		int count = this.queueLazy.size();
		count += this.queueLater.size();
		count += this.queueNow.size();
		return count;
	}



	public String getStatsDisplay() {
		return
			StringUtils.ReplaceTags(
				"Queued: {}  Threads: {}[{}]  Active/Free: {}/{}  Finished: {}",
				this.getQueueCount(),
				this.getWorkerCount(),
				this.getMaxWorkers(),
				this.getActiveWorkerCount(),
				this.getInactiveWorkerCount(),
				this.getRunCount()
			);
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
				new SoftReference<xLog>( log )
			);
			return log;
		}
	}
	protected xLog _log() {
		return xLogRoot.Get( "thpool-"+this.getPoolName() );
	}



}
