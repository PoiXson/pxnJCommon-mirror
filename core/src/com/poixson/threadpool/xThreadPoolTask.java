/*
package com.poixson.threadpool;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.abstractions.xRunnable;


public class xThreadPoolTask extends xRunnable {

	protected final xThreadPool pool;
	// worker owning this task
	protected final AtomicReference<xThreadPoolWorker> worker =
			new AtomicReference<xThreadPoolWorker>(null);

	// state
	protected final AtomicBoolean active = new AtomicBoolean(false);

	public final long taskIndex;
	protected final AtomicLong runIndex = new AtomicLong(-1L);

	// task exception
	protected final AtomicReference<Exception> e = new AtomicReference<Exception>(null);



	public xThreadPoolTask(final xThreadPool pool, final String taskName) {
		this(pool, taskName, null);
	}
	public xThreadPoolTask(final xThreadPool pool, final String taskName,
			final Runnable run) {
		super(taskName, run);
		this.pool = pool;
		this.taskIndex = this.pool.getNextTaskIndex();
	}



	// -------------------------------------------------------------------------------
	// run task



	public void runTask() {
		if (!this.active.compareAndSet(false, true))
			throw new IllegalStateException("Task already running: "+this.getTaskName());
		this.log().finest("Running task:", this.getTaskName());
		try {
			this.run();
		} catch (Exception e) {
			this.setException(e);
		} finally {
			this.active.set(false);
		}
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isActive() {
		return this.active.get();
	}
	@Override
	public boolean notActive() {
		return ! this.active.get();
	}
	@Override
	public int getActive() {
		return (this.isActive() ? 1 : 0);
	}



	public xThreadPoolWorker getWorker() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null)
			throw new RuntimeException("Worker not registered for task: "+this.getTaskName());
		return worker;
	}
	public void setWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if (!this.worker.compareAndSet(null, worker)) {
			if (!worker.equals(this.worker.get()))
				throw new RuntimeException("Task worker already registered: "+this.worker.get().getWorkerName());
		}
	}



	public long getTaskIndex() {
		return this.taskIndex;
	}



	public long getRunIndex() {
		return this.runIndex.get();
	}
	public void setRunIndex(final long index) {
		this.runIndex.set(index);
	}



	// -------------------------------------------------------------------------------
	// task exception



	public Exception e() {
		return this.e.get();
	}
	public void setException(final Exception e) {
		if (e != null) {
			final Exception existing = this.e.getAndSet(e);
			if (existing != null) {
				this.log().trace(existing, "Task has multiple exceptions");
			}
		}
	}
	public boolean hasException() {
		return (this.e.get() != null);
	}



	// -------------------------------------------------------------------------------
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
		final xThreadPoolWorker worker = this.worker.get();
		final xLog logParent = (worker == null ? this.pool.log() : worker.log());
		return logParent.getWeak( this.getTaskName() );
	}



}
*/
