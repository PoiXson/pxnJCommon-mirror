package com.poixson.threadpool.types;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolWorker;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.ThreadUtils;


public abstract class xThreadPool_MultiWorkers extends xThreadPool {

	protected final CopyOnWriteArraySet<xThreadPoolWorker> workers =
			new CopyOnWriteArraySet<xThreadPoolWorker>();

	protected final AtomicInteger maxWorkers =
			new AtomicInteger(GLOBAL_MAX_WORKERS);

	// stats
	protected final AtomicLong workerIndexCount = new AtomicLong(0L);



	protected xThreadPool_MultiWorkers(final String poolName) {
		super(poolName);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop



	@Override
	public void stop() {
		if (this.stopping.get()) return;
		super.stop();
		// stop workers
		OUTER_LOOP:
		for (int i=0; i<3; i++) {
			if (i > 0)
				ThreadUtils.Sleep(20L);
			if (this.workers.isEmpty())
				break OUTER_LOOP;
			final Iterator<xThreadPoolWorker> it = this.workers.iterator();
			//WORKER_LOOP:
			while (it.hasNext()) {
				final xThreadPoolWorker worker = it.next();
				worker.stop();
			} // end WORKER_LOOP
		} // end OUTER_LOOP
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		if (stoppingAll.get() || this.stopping.get()) return;
		// new worker
//TODO:
//use xThreadFactory
	}



	@Override
	public void registerWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		this.workers.add(worker);
	}
	@Override
	public void unregisterWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		this.workers.remove(worker);
		// ensure it's stopping
		worker.stop();
	}



	@Override
	public void joinWorkers(final long timeout) {
		OUTER_LOOP:
		while (true) {
			if (this.workers.isEmpty())
				break OUTER_LOOP;
			final int count = this.workers.size();
			final Iterator<xThreadPoolWorker> it = this.workers.iterator();
			//INNER_LOOP:
			while (it.hasNext()) {
				final xThreadPoolWorker worker = it.next();
				try {
					if (timeout > 0L) {
						final long timot = (long)
							Math.ceil(
								( ((double)timeout) / ((double)count) )
							);
						worker.join(timot);
					} else {
						worker.join();
					}
				} catch (InterruptedException e) {
					this.log().trace(e);
				}
			} // end INNER_LOOP
			if (timeout > 0L)
				break OUTER_LOOP;
		} // end OUTER_LOOP
	}
	@Override
	public void joinWorkers() {
		this.joinWorkers(0L);
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		return (this.workers.size() > 0);
	}
	@Override
	public int getActiveCount() {
		int count = 0;
		final Iterator<xThreadPoolWorker> it = this.workers.iterator();
		while (it.hasNext()) {
			if (it.next().isActive())
				count++;
		}
		return count;
	}



	@Override
	public xThreadPoolWorker getCurrentWorker() {
		if (this.workers.isEmpty())
			return null;
		final Thread current = Thread.currentThread();
		final Iterator<xThreadPoolWorker> it =
			this.workers.iterator();
		while (it.hasNext()) {
			final xThreadPoolWorker worker = it.next();
			if (worker.isThread(current))
				return worker;
		}
		return null;
	}



	// ------------------------------------------------------------------------------- //
	// config



	// pool size
	public int getMaxWorkers() {
		return this.maxWorkers.get();
	}
	public void setMaxWorkers(final int maxWorkers) {
		this.maxWorkers.set(
			NumberUtils.MinMax(
				maxWorkers,
				0,
				GLOBAL_MAX_WORKERS
			)
		);
	}



	// thread priority
	@Override
	public void setThreadPriority(final int priority) {
		super.setThreadPriority(priority);
		ThreadUtils.Sleep(10L);
		final Iterator<xThreadPoolWorker> it = this.workers.iterator();
		while (it.hasNext()) {
			it.next().setPriority(priority);
		}
	}



//TODO:
//	@Override
//	public boolean keepAlive(final boolean enable) {
//		final boolean previous =
//			super.keepAlive(enable);
//		{
//			final Iterator<xThreadPoolWorker> it = this.workers.iterator();
//			while (it.hasNext()) {
//				final xThreadPoolWorker worker = it.next();
//				worker.keepAlive(enable);
//			}
//		}
//		return previous;
//	}



	// ------------------------------------------------------------------------------- //
	// stats



	@Override
	public long getNextWorkerIndex() {
		return this.workerIndexCount
				.incrementAndGet();
	}



}
