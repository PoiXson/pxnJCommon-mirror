package com.poixson.threadpool;

import java.lang.ref.SoftReference;
import java.rmi.UnexpectedException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.abstractions.xStartable;
import com.poixson.exceptions.ContinueException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.exceptions.UnknownThreadPoolException;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;
import com.poixson.tools.remapped.RemappedMethod;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public abstract class xThreadPool implements xStartable {

	public static final int DEFAULT_MAX_WORKERS =
			Runtime.getRuntime().availableProcessors() + 1;
	public static final int  GLOBAL_MAX_WORKERS             = 50;
	public static final long DEFAULT_MAX_LOOP_WAIT          = 25L;
	public static final int  DEFAULT_MAX_LOOP_COUNT         = 5;
	public static final int  DEFAULT_ADD_QUEUE_MAX_ATTEMPTS = 5;
	public static final long DEFAULT_ADD_QUEUE_TIMEOUT      = 250L;
	public static final int  DEFAULT_THREAD_PRIORITY        = Thread.NORM_PRIORITY;

	protected static final ConcurrentHashMap<String, xThreadPool> pools =
			new ConcurrentHashMap<String, xThreadPool>(3);

	protected final String poolName;
	protected final AtomicBoolean keepOneAlive    = new AtomicBoolean(true);
	protected final AtomicBoolean imposeMain      = new AtomicBoolean(false);
	protected final AtomicBoolean allowNewThreads = new AtomicBoolean(false);

	// pool state
	protected final        AtomicBoolean running     = new AtomicBoolean(false);
	protected final        AtomicBoolean stopping    = new AtomicBoolean(false);
	protected static final AtomicBoolean stoppingAll = new AtomicBoolean(false);

	protected final ThreadGroup threadGroup;
	protected final AtomicInteger threadPriority =
			new AtomicInteger(DEFAULT_THREAD_PRIORITY);

	public enum TaskPriority {
		NOW,
		LATER,
		LAZY
	};

	// run now queue
	private final LinkedBlockingQueue<xThreadPoolTask> queueNow =
			new LinkedBlockingQueue  <xThreadPoolTask>();
	// run later queue
	private final LinkedBlockingQueue<xThreadPoolTask> queueLater =
			new LinkedBlockingQueue  <xThreadPoolTask>();
	// run lazy queue
	private final LinkedBlockingQueue<xThreadPoolTask> queueLazy =
			new LinkedBlockingQueue  <xThreadPoolTask>();

	// stats
	protected final AtomicLong countNow   = new AtomicLong(0L);
	protected final AtomicLong countLater = new AtomicLong(0L);
	protected final AtomicLong countLazy  = new AtomicLong(0L);
	protected final AtomicInteger idleLoopCount = new AtomicInteger(0);
	protected final AtomicLong taskIndexCount   = new AtomicLong(0L);



	public static xThreadPool get(final String poolName) {
		final xThreadPool pool = pools.get(poolName);
		if (pool == null) throw new UnknownThreadPoolException(poolName);
		return pool;
	}



	protected xThreadPool(final String poolName) {
		if (stoppingAll.get())
			throw new IllegalStateException("Cannot create new thread pool, already stopping all!");
		if (Utils.isEmpty(poolName)) throw new RequiredArgumentException("poolName");
		this.poolName = poolName;
		this.threadGroup = new ThreadGroup( this.getPoolName() );
		// just to prevent gc
		Keeper.add(this);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop pool



	@Override
	public void start() {
		if (stoppingAll.get()) return;
		this.stopping.set(false);
		if ( ! this.running.compareAndSet(false, true) )
			throw new RuntimeException("Thread pool already running");
		// initial task (creates the first thread)
		final xThreadPoolTask task =
			new xThreadPoolTask(
				"Pool-Startup",
				this,
				new Runnable() {
					@Override
					public void run() {
						xThreadPool.this.log()
							.fine("Thread queue is running..");
					}
				}
			);
		this.addTask(TaskPriority.NOW, task);
	}



	@Override
	public void stop() {
		this.stopping.set(true);
		ThreadUtils.Sleep(10L);
	}
	public static void StopAll() {
		stoppingAll.set(true);
		final Iterator<xThreadPool> it = pools.values().iterator();
		WORKER_LOOP:
		while (it.hasNext()) {
			final xThreadPool pool = it.next();
			if ( pool.isMainPool() || pool.isEventDispatchPool() )
				continue WORKER_LOOP;
			pool.stop();
		} // end WORKER_LOOP
	}



	// override this to allow manual workers
	@Override
	public void run() {
		throw new UnsupportedOperationException("Not supported");
	}



	protected abstract void startNewWorkerIfNeededAndAble();

	public abstract void registerWorker(final xThreadPoolWorker worker);
	public abstract void unregisterWorker(final xThreadPoolWorker worker);

	public abstract void joinWorkers(final long timeout);
	public abstract void joinWorkers();



	// ------------------------------------------------------------------------------- //
	// queue



	public xThreadPoolTask grabNextTask() throws InterruptedException {
		// loop a few times
		//PRIORITY_LOOP:
		while (true) {
			if (this.stopping.get())
				return null;
			// check for high priority tasks
			{
				final xThreadPoolTask task = this.queueNow.poll();
				if (task != null) {
					this.countNow.incrementAndGet();
					return task;
				}
			}
			// check for normal priority tasks
			{
				final xThreadPoolTask task = this.queueLater.poll();
				if (task != null) {
					this.countLater.incrementAndGet();
					return task;
				}
			}
			// check for low priority tasks (before waiting on high)
			{
				final int index = this.idleLoopCount.incrementAndGet() % DEFAULT_MAX_LOOP_COUNT;
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
					DEFAULT_MAX_LOOP_WAIT,
					TimeUnit.MILLISECONDS
				);
				if (task != null) {
					this.countNow.incrementAndGet();
					return task;
				}
			}
		} // end PRIORITY_LOOP
	}



	public xThreadPoolTask addTask(final TaskPriority priority, final Runnable run) {
		return this.addTask(priority, null, run);
	}
	public xThreadPoolTask addTask(final TaskPriority priority, final String taskName, final Runnable run) {
		final xThreadPoolTask task;
		if (run instanceof xThreadPoolTask) {
			task = (xThreadPoolTask) run;
		} else {
			task = new xThreadPoolTask(taskName, this, run);
		}
		this.addTask(
			priority,
			task
		);
		return task;
	}
	public void addTask(final TaskPriority priority, final xThreadPoolTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		// pass to main thread pool
		if (this.isImposeMainPool()) {
			xThreadPool_Main.get()
				.addTask(priority, task);
			return;
		}
		// default priority
		final TaskPriority pr = (
			priority == null
			? TaskPriority.LATER
			: priority
		);
		// run now as current thread
		if (TaskPriority.NOW.equals(pr)) {
			// delay this if manual workers only
			if (this.isAllowNewThreads()) {
				final xThreadPoolWorker worker = this.getCurrentWorker();
				if (worker != null) {
					task.setWorker(worker);
					task.run();
					return;
				}
			}
		}
		// get task queue (default to normal/later)
		final LinkedBlockingQueue<xThreadPoolTask> queue =
			this.getQueueByPriority(pr);
		// add task to queue
		//QUEUE_LOOP:
		for (int i=0; i<DEFAULT_ADD_QUEUE_MAX_ATTEMPTS; i++) {
			try {
				final boolean result =
					queue.offer(
						task,
						DEFAULT_ADD_QUEUE_TIMEOUT,
						TimeUnit.MILLISECONDS
					);
				if (result) {
					// new worker if needed
					this.startNewWorkerIfNeededAndAble();
					return;
				}
			} catch (InterruptedException ignore) {}
		} // end QUEUE_LOOP
		// failed to queue task
		this.log().warning("Thread queue jammed!");
		// try a lower priority
		switch (priority) {
		case NOW:
			this.log().warning("Thread queue jammed, trying a lower priority.. (high->norm)");
			this.addTask(TaskPriority.LATER, task);
			return;
		case LATER:
			this.log().warning("Thread queue jammed, trying a lower priority.. (norm->low)");
			this.addTask(TaskPriority.LAZY, task);
			return;
		default:
		}
		throw new RuntimeException("Thread queue jammed! Failed to queue task: "+task.getTaskName());
	}



	protected LinkedBlockingQueue<xThreadPoolTask> getQueueByPriority(
			final TaskPriority priority) {
		if (priority == null) throw new RequiredArgumentException("priority");
		switch (priority) {
		case NOW: // now
			return this.queueNow;
		case LATER: // later
			return this.queueLater;
		case LAZY:  // lazy
			return this.queueLazy;
		}
		throw new UnsupportedOperationException("Unknown task priority: "+priority.toString());
	}



	// now (task)
	public void runTaskNow(final Runnable run) {
		this.addTask( TaskPriority.NOW, run );
	}
	public void runTaskNow(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.NOW, taskName, run );
	}
	public void runTaskNow(final xThreadPoolTask task) {
		this.addTask( TaskPriority.NOW, task );
	}
	public void runTaskNow(final String taskName, final xThreadPoolTask task) {
		if (Utils.notEmpty(taskName))
			task.setTaskName(taskName);
		this.addTask( TaskPriority.NOW, task );
	}



	// later (task)
	public void runTaskLater(final Runnable run) {
		this.addTask( TaskPriority.LATER, run );
	}
	public void runTaskLater(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.LATER, taskName, run );
	}
	public void runTaskLater(final xThreadPoolTask task) {
		this.addTask( TaskPriority.LATER, task );
	}
	public void runTaskLater(final String taskName, final xThreadPoolTask task) {
		if (Utils.notEmpty(taskName))
			task.setTaskName(taskName);
		this.addTask( TaskPriority.LATER, task );
	}



	// lazy (task)
	public void runTaskLazy(final Runnable run) {
		this.addTask( TaskPriority.LAZY, run );
	}
	public void runTaskLazy(final String taskName, final Runnable run) {
		this.addTask( TaskPriority.LAZY, taskName, run );
	}
	public void runTaskLazy(final xThreadPoolTask task) {
		this.addTask( TaskPriority.LAZY, task );
	}
	public void runTaskLazy(final String taskName, final xThreadPoolTask task) {
		if (Utils.notEmpty(taskName))
			task.setTaskName(taskName);
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
	 *     if (xThreadPool_Main.get()
	 *         .force(this, "getSomething", false))
	 *             return;
	 *     // do something here
	 * }
	 */
	public boolean force(
			final Object callingFrom, final String methodName,
			final TaskPriority priority, final Object...args) {
		if (callingFrom == null)       throw new RequiredArgumentException("callingFrom");
		if (Utils.isEmpty(methodName)) throw new RequiredArgumentException("methodName");
		if (priority == null)          throw new RequiredArgumentException("priority");
		// already running in correct thread
		if (this.isCurrentThread())
			return false;
		// queue to run in correct thread
		final RemappedMethod<Object> run =
			new RemappedMethod<Object>(
				callingFrom,
				methodName,
				args
			);
		this.addTask(priority, run);
		return true;
	}
	public boolean force(
			final Object callingFrom, final String methodName,
			final Object...args) {
		return
			this.force(
				callingFrom,
				methodName,
				TaskPriority.LATER,
				args
			);
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
	 *             .forceResult(this, "getSomething");
	 *     } catch (ContinueException ignore) {}
	 *     // do something here
	 *     return result;
	 * }
	 */
	public <V> V forceResult(
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
		final RemappedMethod<V> run =
			new RemappedMethod<V>(
				callingFrom,
				methodName,
				args
			);
		this.addTask(priority, run);
		return run.getResult();
	}
	public <V> V forceResult(
			final Object callingFrom, final String methodName,
			final Object...args) throws ContinueException {
		return
			this.forceResult(
				callingFrom,
				methodName,
				TaskPriority.NOW,
				args
			);
	}



	// ------------------------------------------------------------------------------- //
	// throw exception if not in expected thread



	public void threadOrException() throws UnexpectedException {
		if ( ! this.isCurrentThread() )
			throw new RuntimeException();
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public abstract boolean isRunning();
	public abstract int getActiveCount();



	@Override
	public boolean isStopping() {
		if (stoppingAll.get())
			return true;
		return this.stopping.get();
	}
	public static boolean isStoppingAll() {
		return stoppingAll.get();
	}



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



	public abstract xThreadPoolWorker getCurrentWorker();

	public boolean isCurrentThread() {
		return (this.getCurrentWorker() != null);
	}



	// ------------------------------------------------------------------------------- //
	// config



	// pool name
	public String getPoolName() {
		return this.poolName;
	}



	// pool size
	public abstract int getMaxWorkers();
	public abstract void setMaxWorkers(final int maxWorkers);



	// thread group
	public ThreadGroup getThreadGroup() {
		return this.threadGroup;
	}



	// thread priority
	public int getThreadPriority() {
		return this.threadPriority.get();
	}
	public void setThreadPriority(final int priority) {
		this.threadPriority.set(priority);
		this.threadGroup.setMaxPriority(priority);
	}



	// keep one worker running
	public boolean keepOneAlive() {
		return this.keepOneAlive.get();
	}
	public void setKeepOneAlive(final boolean keepOne) {
		this.keepOneAlive.set(keepOne);
	}



	// force tasks to main pool
	public boolean isImposeMainPool() {
		return this.imposeMain.get();
	}
	public void setImposeMainPool(final boolean impose) {
		this.imposeMain.set(impose);
	}



	// allow/disable creating new worker threads
	public boolean isAllowNewThreads() {
		return this.allowNewThreads.get();
	}
	public boolean isManualNewThreads() {
		return ! this.isAllowNewThreads();
	}
	public void setAllowNewThreads(final boolean allow) {
		this.allowNewThreads.set(allow);
	}
	public void setAllowNewThreads() {
		this.setAllowNewThreads(true);
	}
	public void setManualNewThreads() {
		this.setAllowNewThreads(false);
	}



	public boolean isMainPool() {
		return false;
	}
	public boolean isEventDispatchPool() {
		return false;
	}



	// ------------------------------------------------------------------------------- //
	// stats



	public abstract long getNextWorkerIndex();

	public long getNextTaskIndex() {
		return this.taskIndexCount
				.incrementAndGet();
	}



	public long getTaskCountNow() {
		return this.countNow.get();
	}
	public long getTaskCountLater() {
		return this.countLater.get();
	}
	public long getTaskCountLazy() {
		return this.countLazy.get();
	}
	public long getTaskCount(final TaskPriority priority) {
		switch (priority) {
		case NOW:
			return this.countNow.get();
		case LATER:
			return this.countLater.get();
		case LAZY:
			return this.countLazy.get();
		default:
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



//TODO:
//	public void displayStats(final xLevel level) {
//		this.pool.log()
//			.publish(
//				level,
//				(new StringBuilder())
//					.append("Queued: [")
//						.append(this.getQueueCount())
//						.append("]  ")
//					.append("Threads: ")
//						.append(this.getCurrentThreadCount())
//						.append(" [")
//						.append(this.getMaxThreads())
//						.append("]  ")
//					.append("Active/Free: ")
//						.append(this.getActiveThreadCount())
//						.append("/")
//						.append(this.getInactiveThreadCount())
//						.append("  ")
//					.append("Global: ")
//						.append(getGlobalThreadCount())
//						.append(" [")
//						.append(getGlobalMaxThreads())
//						.append("]")
//						.toString()
//			);
//	}



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
