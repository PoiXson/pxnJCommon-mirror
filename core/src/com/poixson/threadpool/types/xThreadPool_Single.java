package com.poixson.threadpool.types;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolWorker;
import com.poixson.utils.StringUtils;


public abstract class xThreadPool_Single extends xThreadPool {

	protected final AtomicReference<xThreadPoolWorker> worker =
			new AtomicReference<xThreadPoolWorker>(null);



	public xThreadPool_Single(final String poolName) {
		super(poolName);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop



	// start with current thread
	@Override
	public void go() {
		if (!this.okStart()) return;
		if (!this.running.compareAndSet(false, true))
			return;
		if (this.worker.get() == null) {
			final xThreadPoolWorker worker =
				new xThreadPoolWorker(
					this,
					Thread.currentThread(),
					this.getPoolName()
				);
			if (this.worker.compareAndSet(null, worker)) {
				worker.run();
			}
		}
	}



	// ------------------------------------------------------------------------------- //
	// workers



	@Override
	public xThreadPoolWorker[] getWorkers() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return new xThreadPoolWorker[0];
		return new xThreadPoolWorker[] { worker };
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		if (this.isStopping()) return;
		if (!this.isRunning()) return;
		if (this.worker.get() == null) {
			// new worker
			final xThreadPoolWorker worker =
				new xThreadPoolWorker(
					this,
					(Thread) null,
					this.getPoolName()
				);
			if (this.worker.compareAndSet(null, worker)) {
				worker.start();
				worker.waitForStart();
			}
		}
	}



	@Override
	protected void stopWorkers() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker != null) {
			worker.stop();
		}
	}



	@Override
	public void unregisterWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if (!this.worker.compareAndSet(worker, null)) {
			throw new RuntimeException(
				StringUtils.ReplaceTags("Cannot unregister worker not owned by pool: ",
					this.getPoolName(), worker.getWorkerName())
			);
		}
		// ensure it's stopping
		worker.stop();
	}



	@Override
	public void joinWorkers(final long timeout) {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return;
		try {
			worker.join(timeout);
		} catch (InterruptedException e) {
			this.log().trace(e);
		}
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		if (!super.isRunning())
			return false;
		return (this.worker.get() != null);
	}



	// active workers
	@Override
	public int getActiveCount() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker != null) {
			if (worker.isActive())
				return 1;
		}
		return 0;
	}



	// next worker index
	@Override
	public long getNextWorkerIndex() {
		return 0L;
	}



	@Override
	public xThreadPoolWorker getCurrentThreadWorker() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker != null) {
			if (worker.isCurrentThread())
				return worker;
		}
		return null;
	}
	@Override
	public boolean isCurrentThread() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker != null) {
			return worker.isCurrentThread();
		}
		return false;
	}



	@Override
	public int getWorkerCount() {
		if (this.worker.get() == null)
			return 0;
		return 1;
	}
	@Override
	public int getActiveWorkerCount() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return 0;
		if (worker.isActive())
			return 1;
		return 0;
	}
	@Override
	public int getInactiveWorkerCount() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return 0;
		if (worker.isActive())
			return 0;
		return 1;
	}



	// ------------------------------------------------------------------------------- //
	// config



	// pool size
	@Override
	public int getMaxWorkers() {
		return 1;
	}
	@Override
	public void setMaxWorkers(final int maxWorkers) {
		throw new UnsupportedOperationException();
	}



	// thread priority
	@Override
	public void setThreadPriority(final int priority) {
		super.setThreadPriority(priority);
		final xThreadPoolWorker worker = this.worker.get();
		if (worker != null) {
			worker.setPriority(priority);
		}
	}



}
