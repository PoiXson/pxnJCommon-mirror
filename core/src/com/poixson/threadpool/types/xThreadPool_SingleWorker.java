package com.poixson.threadpool.types;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolWorker;


public abstract class xThreadPool_SingleWorker extends xThreadPool {

	protected final AtomicReference<xThreadPoolWorker> worker =
			new AtomicReference<xThreadPoolWorker>(null);



	protected xThreadPool_SingleWorker(final String poolName) {
		super(poolName);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop



	@Override
	public void stop() {
		if (this.stopping.get()) return;
		super.stop();
		xThreadPoolWorker worker = this.worker.get();
		if (worker != null)
			worker.stop();
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		if (stoppingAll.get() || this.stopping.get()) return;
		// existing worker
		if (this.worker.get() != null)
			return;
		if ( ! this.allowNewThreads() )
			return;
		// new worker
		{
			final xThreadPoolWorker worker = new xThreadPoolWorker(this);
			if (this.worker.compareAndSet(null, worker)) {
				worker.startAndWait();
			}
		}
	}



	@Override
	public void registerWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if ( ! this.worker.compareAndSet(null, worker) ) {
			if ( ! worker.equals(this.worker.get()) )
				throw new RuntimeException("Single thread pool is already registered");
		}
	}
	@Override
	public void unregisterWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if ( ! this.worker.compareAndSet(worker, null) )
			throw new RuntimeException("Invalid worker to unregister!");
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
	@Override
	public void joinWorkers() {
		this.joinWorkers(0L);
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return false;
		return worker.isRunning();
	}
	@Override
	public int getActiveCount() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)    return 0;
		if (worker.isActive()) return 1;
		return 0;
	}



	@Override
	public xThreadPoolWorker getCurrentWorker() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return null;
		return (
			worker.isCurrentThread()
			? worker
			: null
		);
	}
	@Override
	public boolean isCurrentThread() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			return false;
		return worker.isCurrentThread();
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



	// ------------------------------------------------------------------------------- //
	// stats



	@Override
	public long getNextWorkerIndex() {
		if (this.worker.get() != null)
			throw new RuntimeException("Single worker already loaded!");
		return 1L;
	}



}
