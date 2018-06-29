package com.poixson.threadpool;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.abstractions.xStartable;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.CoolDown;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xThreadPoolWorker implements xStartable {

	public static final long DEFAULT_WORKER_START_WAIT      = 500L;

	protected final xThreadPool pool;
	protected final AtomicReference<Thread> thread =
			new AtomicReference<Thread>(null);

	protected final String workerNameCustom;
	protected       String workerNameCached = null;

	// state
	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean active   = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	// stats
	protected final long workerIndex;
	protected final AtomicLong runCount = new AtomicLong(0L);



	public xThreadPoolWorker(final xThreadPool pool) {
		this(pool, null, null);
	}
	public xThreadPoolWorker(final xThreadPool pool, final Thread thread) {
		this(pool, thread, null);
	}
	public xThreadPoolWorker(final xThreadPool pool, final Thread thread, final String workerName) {
		if (pool == null) throw new RequiredArgumentException("pool");
		this.pool = pool;
		this.workerNameCustom = workerName;
		this.workerIndex = pool.getNextWorkerIndex();
		if (thread != null) {
			this.thread.set(thread);
			this.configureThread(thread);
		}
	}



	// ------------------------------------------------------------------------------- //
	// start/stop worker



	@Override
	public void start() {
		if (this.isRunning())
			return;
		try {
			this.getThread()
				.start();
		} catch (IllegalThreadStateException ignore) {}
	}
	public void startAndWait() {
		this.start();
		this.waitForStart(DEFAULT_WORKER_START_WAIT);
	}
	public void startAndWait(final long timeout) {
		this.start();
		this.waitForStart(timeout);
	}
	public void waitForStart() {
		this.waitForStart(DEFAULT_WORKER_START_WAIT);
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
			} else {
				if (cool.runAgain())
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
		if (this.stopping.get()) throw new IllegalStateException("Worker cannot run again, already stopped");
		if (this.running.get()) return;
		this.pool.registerWorker(this);
		if ( ! this.running.compareAndSet(false, true) ) {
			try {
				this.pool.unregisterWorker(this);
			} catch (RuntimeException ignore) {}
			throw new RuntimeException("Thread pool worker already running!");
		}
		if (this.stopping.get()) throw new IllegalStateException("Worker cannot run again, already stopped");
		// validate thread
		if (this.thread.get() == null) {
			final Thread thread = Thread.currentThread();
			if (this.thread.compareAndSet(null, thread)) {
				this.configureThread(thread);
			}
		}
		// is correct thread
		if ( ! this.thread.get().equals(Thread.currentThread()) )
			throw new IllegalStateException("Invalid thread state!");
		try {
			WORKER_LOOP:
			while (true) {
				if (this.pool.isStopping())
					break WORKER_LOOP;
				// get task from queues
				try {
					final xThreadPoolTask task =
						this.pool.grabNextTask();
					// run the task
					if (task != null) {
						this.runTask(task);
						this.runCount.incrementAndGet();
						continue WORKER_LOOP;
					}
				} catch (InterruptedException e) {
					this.log().trace(e);
					break;
				}
//TODO:
//				// idle
//				if ( ! this.keepAlive.get() ) {
//				}
				this.log().detail("Idle thread..");
			} // end WORKER_LOOP
		} finally {
			this.stopping.set(true);
			this.pool.unregisterWorker(this);
			this.running.set(false);
		}
	}
	protected void runTask(final xThreadPoolTask task) {
		if (task == null) throw new RequiredArgumentException("task");
		try {
			this.active.set(true);
			task.setWorker(this);
			task.run();
		} finally {
			this.active.set(false);
		}
		if (task.hasException()) {
			this.log().trace( task.getException() );
		}
	}



	public void join(final long timeout)
			throws InterruptedException {
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



	// ------------------------------------------------------------------------------- //
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
		return this.stopping.get();
	}



	public boolean isThread(final Thread match) {
		if (match == null)
			return false;
		return match.equals( this.thread.get() );
	}
	public boolean isCurrentThread() {
		return Thread.currentThread()
				.equals( this.thread.get() );
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getWorkerName() {
		// cached name
		{
			final String name = this.workerNameCached;
			if (Utils.notEmpty(name))
				return name;
		}
		// custom name
		if (Utils.notEmpty(this.workerNameCustom)) {
			this.workerNameCached = this.workerNameCustom;
			return this.workerNameCustom;
		}
		// generate name
		{
			final String name =
				StringUtils.ReplaceTags(
					"{}-w{}",
					this.pool.getPoolName(),
					this.workerIndex
			);
			this.workerNameCached = name;
			return name;
		}
	}



	public void setPriority(final int priority) {
		final Thread thread = this.thread.get();
		if (thread == null) throw new NullPointerException();
		thread.setPriority(priority);
	}



//TODO:
//	public boolean keepAlive() {
//		return this.keepAlive.get();
//	}
//	public boolean keepAlive(final boolean enable) {
//		return this.keepAlive.getAndSet(enable);
//	}



	public Thread getThread() {
		// existing thread
		{
			final Thread thread = this.thread.get();
			if (thread != null)
				return thread;
		}
		// new thread
		{
			final Thread thread = new Thread(this);
			if ( ! this.thread.compareAndSet(null, thread) )
				return this.thread.get();
			this.configureThread(thread);
			this.log()
				.finer("New worker thread..");
			return thread;
		}
	}
	public void setThread(final Thread thread) {
		if ( ! this.thread.compareAndSet(null, thread) ) {
			final String threadName = this.thread.get().getName();
			throw new RuntimeException("Worker thread already set: "+threadName);
		}
	}
	protected void configureThread(final Thread thread) {
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



	// ------------------------------------------------------------------------------- //
	// stats



	public long getWorkerIndex() {
		return this.workerIndex;
	}



	public long getRunCount() {
		return this.runCount.get();
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
			final xLog log;
			log = this._log();
			this._log.set(
				new SoftReference<xLog>(
					log
				)
			);
			return log;
		}
	}
	protected xLog _log() {
		final String poolName = this.pool.poolName;
		final String workerName = this.getWorkerName();
		if (poolName.equalsIgnoreCase(workerName))
			return this.pool.log();
		return
			xLogRoot.Get()
				.getWeak( this.getWorkerName() );
	}



}
