package com.poixson.threadpool;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.CoolDown;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xThreadPoolWorker implements xStartable {

	public static final long WORKER_START_TIMEOUT = 500L;

	protected final xThreadPool pool;
	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);

	protected final long workerIndex;
	protected final String workerName;
	protected final AtomicReference<String> workerNameCached = new AtomicReference<String>(null);

	// state
	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean active   = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	protected final AtomicBoolean keepAlive = new AtomicBoolean(false);

	// stats
	protected final AtomicLong runCount = new AtomicLong(0L);



	public xThreadPoolWorker(final xThreadPool pool, final Thread thread, final String workerName) {
		if (pool == null) throw new RequiredArgumentException("pool");
		this.pool = pool;
		this.workerIndex = pool.getNextWorkerIndex();
		this.workerName = workerName;
		if (thread != null) {
			this.thread.set(thread);
			this.configureThread(thread);
		}
	}



	// ------------------------------------------------------------------------------- //
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
		while (true) {
			// is running
			if (this.isRunning())
				break;
			// timeout
			if (cool == null) {
				cool = CoolDown.getNew(timeout);
				cool.resetRun();
			} else
			if (cool.runAgain()) {
				this.log().warning("Timeout waiting for thread pool to start");
				break;
			}
			sleep += 5L;
			ThreadUtils.Sleep(sleep);
		}
	}



	@Override
	public void stop() {
		this.stopping.set(true);
	}



	// ------------------------------------------------------------------------------- //
	// run loop



	@Override
	public void run() {
		if (this.isStopping()) return;
		if (!this.running.compareAndSet(false, true))
			throw new RuntimeException("Thread pool worker already running: "+this.getWorkerName());
		if (this.isStopping()) {
			this.running.set(false);
			return;
		}
		if (this.thread.get() == null) {
			final Thread thread = Thread.currentThread();
			if (this.thread.compareAndSet(null, thread)) {
				this.configureThread(thread);
			}
		}
		if ( ! this.thread.get().equals(Thread.currentThread()) )
			throw new IllegalStateException("Invalid thread state!");
		try {
			WORKER_LOOP:
			while (true) {
				if (this.isStopping())
					break WORKER_LOOP;
				// get task from queues
				final xThreadPoolTask task;
				try {
					task = this.pool.grabNextTask();
				} catch (InterruptedException ignore) {
					continue WORKER_LOOP;
				}
				// run the task
				if (task != null) {
					final long runIndex = this.runCount.incrementAndGet();
					this.runTask(task, runIndex);
					continue WORKER_LOOP;
				}
				// idle worker
				this.log().detail("Idle thread..");
//TODO: idle thread may stop
			} // end WORKER_LOOP
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
			task.setWorker(this);
			task.setRunIndex(runIndex);
			task.run();
		} finally {
			this.active.set(false);
		}
		if (task.hasException()) {
			this.log().trace(task.e());
		}
	}



	// ------------------------------------------------------------------------------- //
	// thread



	protected Thread getThread() {
		if (this.thread.get() == null) {
			final Thread thread = this.newThread();
			if (this.thread.compareAndSet(null, thread)) {
				this.log().finer("New worker thread..");
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
		if (thread == null)
			return;
		if (timeout > 0L) {
			thread.join(timeout);
		} else {
			thread.join();
		}
	}
	public void join() throws InterruptedException {
		this.join(0L);
	}



	// ----------------------------------------------------------)--------------------- //
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



	// ------------------------------------------------------------------------------- //
	// stats



	public long getWorkerIndex() {
		return this.workerIndex;
	}



	public long getRunCount() {
		return this.runCount.get();
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getWorkerName() {
		// custom name
		if (Utils.notEmpty(this.workerName)) {
			return this.workerName;
		}
		// generated name
		if (this.workerNameCached.get() == null) {
			final String name =
				StringUtils.ReplaceTags(
					"{}-w{}",
					this.pool.getPoolName(),
					this.workerIndex
				);
			this.workerNameCached.set(name);
		}
		return this.workerNameCached.get();
	}



	public void setPriority(final int priority) {
		final Thread thread = this.thread.get();
		if (thread != null) {
			thread.setPriority(priority);
		}
	}



	public boolean isKeepAlive() {
		return this.keepAlive.get();
	}
	public void setKeepAlive(final boolean enable) {
		this.keepAlive.set(enable);
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
	protected xLog _log() {
		final String poolName = this.pool.poolName;
		final String workerName = this.getWorkerName();
		if (poolName.equalsIgnoreCase(workerName))
			return this.pool.log();
		return xLogRoot.Get().getWeak(workerName);
	}



}
