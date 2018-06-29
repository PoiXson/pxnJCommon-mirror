package com.poixson.threadpool.types;

import java.util.concurrent.atomic.AtomicReference;

import javax.swing.SwingUtilities;

import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolTask;
import com.poixson.threadpool.xThreadPoolWorker;


public class xThreadPool_GUI extends xThreadPool_SingleWorker {

	public static final String DISPATCH_POOL_NAME = "gui";

	private final static AtomicReference<xThreadPool_GUI> instance =
			new AtomicReference<xThreadPool_GUI>(null);



	public static xThreadPool_GUI get() {
		// existing instance
		{
			final xThreadPool_GUI pool = instance.get();
			if (pool != null)
				return pool;
		}
		// new instance
		{
			final xThreadPool_GUI pool = new xThreadPool_GUI();
			if (instance.compareAndSet(null, pool)) {
				final xThreadPool existing =
					pools.putIfAbsent(DISPATCH_POOL_NAME, pool);
				if (existing != null) {
					instance.set( (xThreadPool_GUI) existing );
					return (xThreadPool_GUI) existing;
				}
				return pool;
			}
			return instance.get();
		}
	}



	protected xThreadPool_GUI() {
		super(DISPATCH_POOL_NAME);
		this.imposeMain.set(false);
		this.keepOneAlive.set(false);
		this.imposeMain.set(false);
		this.allowNewThreads.set(false);
	}



	@Override
	public void run() {
		throw new UnsupportedOperationException();
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		if (xThreadPool.stoppingAll.get() || this.stopping.get()) return;
		{
			final xThreadPoolWorker existing = this.worker.get();
			if (existing == null) {
				// new worker
				final xThreadPoolWorker worker =
					new xThreadPoolWorker_GUI(this);
				this.worker.compareAndSet(null, worker);
			}
		}
		// start event dispatch thread
		SwingUtilities.invokeLater(
			this.worker.get()
		);
	}



	// ------------------------------------------------------------------------------- //
	// config



	@Override
	public boolean keepOneAlive() {
		return false;
	}
	@Override
	public void setKeepOneAlive(final boolean keepOne) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean isImposeMainPool() {
		return false;
	}
	@Override
	public void setImposeMainPool(final boolean impose) {
		throw new UnsupportedOperationException();
	}



	public boolean allowNewThreads() {
		return false;
	}
	public void setManualThread(final boolean manual) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean isEventDispatchPool() {
		return true;
	}



	// ------------------------------------------------------------------------------- //
	// worker class



	protected class xThreadPoolWorker_GUI extends xThreadPoolWorker {

		public xThreadPoolWorker_GUI(final xThreadPool pool) {
			super(pool);
			pool.registerWorker(this);
		}

		@Override
		public void start() {}

		@Override
		protected void configureThread(final Thread thread) {}

		@Override
		public Thread getThread() {
			return this.thread.get();
		}

		@Override
		public void run() {
			if (stoppingAll.get() || this.stopping.get()) return;
			this.running.set(true);
			{
				final Thread thread = this.thread.get();
				if (thread == null) {
					if ( this.thread.compareAndSet(null, Thread.currentThread()) ) {
						this.configureThread(thread);
					}
				} else {
					if ( ! thread.equals(Thread.currentThread()) )
						throw new IllegalStateException("Invalid thread state!");
				}
			}
			// get task from queues
			try {
				final xThreadPoolTask task =
					this.pool.grabNextTask();
				// run the task
				if (task != null) {
					this.runTask(task);
					this.runCount.incrementAndGet();
					// run more tasks
					SwingUtilities.invokeLater(this);
					return;
				}
			} catch (InterruptedException e) {
				this.log().trace(e);
				return;
			}
			// idle
			this.log().detail("Idle thread..");
			if (this.stopping.get()) {
				this.running.set(false);
			} else {
				this.log().detail("Idle..");
			}
		}

	}



}
