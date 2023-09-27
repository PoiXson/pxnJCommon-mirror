/*
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


public abstract class xThreadPool_Multi extends xThreadPool {

	protected final CopyOnWriteArraySet<xThreadPoolWorker> workers = new CopyOnWriteArraySet<xThreadPoolWorker>();
	protected final AtomicInteger maxWorkers = new AtomicInteger(0);

	// stats
	protected final AtomicLong workerIndexCount = new AtomicLong(0L);



	public xThreadPool_Multi(final String poolName) {
		super(poolName);
		this.maxWorkers.set(ThreadUtils.getSystemCoresPlus(1));
	}



	// -------------------------------------------------------------------------------
	// start/stop



	// start with current thread
	@Override
	public void go() {
		if (!this.okStart()) return;
		if (!this.running.compareAndSet(false, true))
			return;
		final xThreadPoolWorker worker =
			new xThreadPoolWorker(
				this,
				Thread.currentThread(),
				this.getPoolName()
			);
		this.workers.add(worker);
		worker.run();
	}



//TODO
//	@Override
//	public void waitForStop() {
//	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		if (this.isStopping()) return;
		if (!this.isRunning()) return;
throw new RuntimeException("UNFINISHED CODE");
//TODO:
//use xThreadFactory
	}



	// -------------------------------------------------------------------------------
	// workers



	@Override
	public xThreadPoolWorker[] getWorkers() {
		return this.workers.toArray(new xThreadPoolWorker[0]);
	}



	@Override
	protected void stopWorkers() {
		boolean changed = true;
		//CHANGED_LOOP:
		while (changed) {
			changed = false;
			final Iterator<xThreadPoolWorker> it = this.workers.iterator();
			//WORKERS_LOOP:
			while (it.hasNext()) {
				final xThreadPoolWorker worker= it.next();
				if (!worker.isStopping()) {
					worker.stop();
					changed = true;
				}
			} // end WORKERS_LOOP
			if (changed) {
				ThreadUtils.Sleep(10L);
			}
		} // end CHANGED_LOOP
	}



	@Override
	public void join(final long timeout) {
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
	public void unregisterWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if (!this.workers.remove(worker)) {
			throw new RuntimeException(
				String.format(
					"Cannot unregister worker not owned by pool:",
					this.getPoolName(),
					worker.getWorkerName()
				)
			);
		}
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isRunning() {
		if (!super.isRunning())
			return false;
		return (this.workers.size() > 0);
	}



	// active workers
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



	// next worker index
	@Override
	public long getNextWorkerIndex() {
		return this.workerIndexCount
				.incrementAndGet();
	}



	@Override
	public xThreadPoolWorker getCurrentThreadWorker() {
		if (this.workers.isEmpty()) return null;
		final Thread current = Thread.currentThread();
		final Iterator<xThreadPoolWorker> it = this.workers.iterator();
		while (it.hasNext()) {
			final xThreadPoolWorker worker = it.next();
			if (worker.isThread(current))
				return worker;
		}
		return null;
	}
	@Override
	public boolean isCurrentThread() {
		if (this.workers.isEmpty()) return false;
		final Thread current = Thread.currentThread();
		final Iterator<xThreadPoolWorker> it = this.workers.iterator();
		while (it.hasNext()) {
			final xThreadPoolWorker worker = it.next();
			if (worker.isThread(current))
				return true;
		}
		return false;
	}



	@Override
	public int getWorkerCount() {
		return this.workers.size();
	}
	@Override
	public int getActiveWorkerCount() {
//TODO
return 0;
	}
	@Override
	public int getInactiveWorkerCount() {
//TODO
return 0;
	}



	// -------------------------------------------------------------------------------
	// config



	// pool size
	@Override
	public int getMaxWorkers() {
		return this.maxWorkers.get();
	}
	@Override
	public void setMaxWorkers(final int maxWorkers) {
		this.maxWorkers.set(
			NumberUtils.MinMax(
				maxWorkers,
				0,
				xThreadPool.HARD_MAX_WORKERS
			)
		);
	}



	// thread priority
	@Override
	public void setThreadPriority(final int priority) {
		super.setThreadPriority(priority);
//TODO
//		this.threadFactory.setPriority(priority);
		ThreadUtils.Sleep(10L);
		final Iterator<xThreadPoolWorker> it = this.workers.iterator();
		while (it.hasNext()) {
			it.next().setPriority(priority);
		}
	}



}
*/
