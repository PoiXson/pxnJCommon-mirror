package com.poixson.threadpool.worker;

import static com.poixson.threadpool.xThreadPool.DEBUG_EXTRA;
import static com.poixson.threadpool.xThreadPool.WORKER_START_TIMEOUT;
import static com.poixson.utils.Utils.IsEmpty;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.task.xThreadPoolTask;
import com.poixson.tools.CoolDown;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.ThreadUtils;


public class xThreadPoolWorker implements xStartable, Runnable {

	protected final xThreadPool pool;
	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);

	protected final long   worker_index;
	protected final String worker_name;
	protected final AtomicReference<String> worker_name_cached = new AtomicReference<String>(null);

	// state
	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean active   = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	protected final AtomicBoolean keepAlive = new AtomicBoolean(false);

	// stats
	protected final AtomicLong count_runs = new AtomicLong(0L);



	public xThreadPoolWorker(final xThreadPool pool, final Thread thread, final String workerName) {
		if (pool == null) throw new RequiredArgumentException("pool");
		this.pool = pool;
		this.worker_index = pool.getNextWorkerIndex();
		this.worker_name = workerName;
		if (thread != null) {
			this.thread.set(thread);
			this.configureThread(thread);
		}
	}



	// -------------------------------------------------------------------------------
	// start/stop worker



	@Override
	public void start() {
		if (this.isRunning()) return;
		try {
			final Thread thread = this.getThread();
			thread.start();
		} catch (IllegalThreadStateException ignore) {}
	}



	public void waitForStart() {
		this.waitForStart(WORKER_START_TIMEOUT);
	}
	public void waitForStart(final long timeout) {
		// wait for worker to start
		long sleep = 0L;
		CoolDown cool = null;
		LOOP_WAIT:
		while (true) {
			// is running
			if (this.isRunning())
				break LOOP_WAIT;
			// timeout
			if (cool == null) {
				cool = new CoolDown(timeout);
				cool.reset();
			} else
			if (cool.again()) {
				this.log().warning("Timeout waiting for thread pool to start");
				break LOOP_WAIT;
			}
			sleep += 5L;
			ThreadUtils.Sleep(sleep);
		} // end LOOP_WAIT
	}



	@Override
	public void stop() {
		this.stopping.set(true);
	}



	// -------------------------------------------------------------------------------
	// run loop



	@Override
	public void run() {
		if (!this.running.compareAndSet(false, true))
			throw new RuntimeException("Thread pool worker already running: "+this.getWorkerName());
		if (this.thread.get() == null) {
			final Thread thread = Thread.currentThread();
			if (this.thread.compareAndSet(null, thread))
				this.configureThread(thread);
		}
		if ( ! this.thread.get().equals(Thread.currentThread()) )
			throw new IllegalStateException("Invalid thread state!");
		try {
			LOOP_WORKER:
			while (true) {
				// get task from queues
				final xThreadPoolTask task;
				try {
					task = this.pool.grabNextTask();
				} catch (InterruptedException ignore) {
					continue LOOP_WORKER;
				}
				// run the task
				if (task != null) {
					final long runIndex = this.count_runs.incrementAndGet();
					this.runTask(task, runIndex);
					continue LOOP_WORKER;
				}
				if (this.isStopping())
					break LOOP_WORKER;
				// idle worker
				if (DEBUG_EXTRA) this.log().detail("Idle..");
//TODO: idle thread may stop
			} // end LOOP_WORKER
		} finally {
			this.stopping.set(true);
			this.running.set(false);
			this.pool.unregisterWorker(this);
		}
	}
	protected void runTask(final xThreadPoolTask task, final long runIndex) {
		if (task == null) throw new RequiredArgumentException("task");
		this.active.set(true);
		try {
			if (DEBUG_EXTRA) this.log().finest("Run Task: %s", task.getTaskName());
			task.setWorker(this);
			task.setRunIndex(runIndex);
			task.runTask();
		} finally {
			this.active.set(false);
		}
		if (task.hasException())
			this.log().trace(task.e());
	}



	// -------------------------------------------------------------------------------
	// thread



	protected Thread getThread() {
		if (this.thread.get() == null) {
			final Thread thread = this.newThread();
			if (this.thread.compareAndSet(null, thread)) {
				if (DEBUG_EXTRA) this.log().finer("New worker thread..");
				this.configureThread(thread);
				return thread;
			}
		}
		return this.thread.get();
	}
	protected Thread newThread() {
		return new Thread(this);
	}
	public void setThread(final Thread thread) {
		if ( ! this.thread.compareAndSet(null, thread) ) {
			final String threadName = this.thread.get().getName();
			throw new RuntimeException("Worker thread already set: "+threadName);
		}
	}
	public void configureThread(final Thread thread) {
		try {
			thread.setName(this.getWorkerName());
		} catch (Exception ignore) {}
		try {
			thread.setDaemon(false);
		} catch (Exception ignore) {}
		try {
			thread.setPriority(this.pool.getThreadPriority());
		} catch (Exception ignore) {}
	}



	public void join(final long timeout) throws InterruptedException {
		final Thread thread = this.thread.get();
		if (thread == null) return;
		if (timeout > 0L) thread.join(timeout);
		else              thread.join();
	}
	public void join() throws InterruptedException {
		this.join(0L);
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isRunning() {
		return this.running.get();
	}
	public boolean isActive() {
		return this.active.get();
	}
	@Override
	public boolean isStopping() {
		if (this.pool.isStopping())
			return true;
		return this.stopping.get();
	}



	public boolean isThread(final Thread match) {
		final Thread thread = this.thread.get();
		if (thread == null) return false;
		return thread.equals( match );
	}
	public boolean isCurrentThread() {
		final Thread thread = this.thread.get();
		if (thread == null) return false;
		return thread.equals( Thread.currentThread() );
	}



	// -------------------------------------------------------------------------------
	// stats



	public long getWorkerIndex() {
		return this.worker_index;
	}



	public long getRunCount() {
		return this.count_runs.get();
	}



	// -------------------------------------------------------------------------------
	// config



	public String getWorkerName() {
		// custom name
		if (!IsEmpty(this.worker_name))
			return this.worker_name;
		// generated name
		if (this.worker_name_cached.get() == null) {
			final String name =
				String.format(
					"%s-w%d",
					this.pool.getPoolName(),
					Long.valueOf(this.worker_index)
				);
			this.worker_name_cached.set(name);
		}
		return this.worker_name_cached.get();
	}



	public void setPriority(final int priority) {
		final Thread thread = this.thread.get();
		if (thread != null)
			thread.setPriority(priority);
	}



	public boolean isKeepAlive() {
		return this.keepAlive.get();
	}
	public void setKeepAlive(final boolean enable) {
		this.keepAlive.set(enable);
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
		final String pool_name = this.pool.pool_name;
		final String worker_name = this.getWorkerName();
		final StringBuilder name = new StringBuilder();
		name.append("pool:").append(pool_name);
		if (!worker_name.isEmpty()) {
			if (!worker_name.equalsIgnoreCase(pool_name))
				name.append(":").append(worker_name);
		}
		return xLog.Get(name.toString());
	}



}
