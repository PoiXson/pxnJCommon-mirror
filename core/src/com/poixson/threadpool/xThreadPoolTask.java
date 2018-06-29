package com.poixson.threadpool;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.remapped.RunnableNamed;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xThreadPoolTask implements Runnable {

	protected final xThreadPool pool;
	protected final AtomicReference<String> taskName =
			new AtomicReference<String>(null);
	protected final Runnable run;
	protected final long taskIndex;

	// state
	protected final AtomicLong runIndex = new AtomicLong(0);
	protected final AtomicBoolean running = new AtomicBoolean(false);
	protected final AtomicReference<Exception> ex =
			new AtomicReference<Exception>(null);

	// worker owning this task
	protected final AtomicReference<xThreadPoolWorker> worker =
			new AtomicReference<xThreadPoolWorker>(null);



	public xThreadPoolTask(final xThreadPool pool, final Runnable run) {
		this(null, pool, run);
	}
	public xThreadPoolTask(final String taskName,
			final xThreadPool pool, final Runnable run) {
		if (pool == null) throw new RequiredArgumentException("pool");
		if (run  == null) throw new RequiredArgumentException("run");
		this.pool = pool;
		this.run  = run;
		if (Utils.notEmpty(taskName)) {
			this.taskName.set(taskName);
		} else {
			final String name = RunnableNamed.GetName(run);
			if (Utils.notEmpty(name))
				this.taskName.set(name);
		}
		this.taskIndex = this.pool.getNextTaskIndex();
	}



	// ------------------------------------------------------------------------------- //
	// run task



	@Override
	public void run() {
		if ( ! this.running.compareAndSet(false, true) )
			throw new IllegalStateException("Task already running");
		final Thread currentThread = Thread.currentThread();
		final String originalThreadName = currentThread.getName();
		try {
			// set thread name
			currentThread.setName( this.getTaskName() );
			this.worker.get().log()
				.finest("Running task:", taskName);
			// run the task
			this.run.run();
		} catch (Exception e) {
			this.ex.set(e);
		} finally {
			// finished task
			this.running.set(false);
			// restore thread name
			currentThread.setName(originalThreadName);
		}
	}



	// ------------------------------------------------------------------------------- //
	// task state



	public boolean isRunning() {
		return this.running.get();
	}
	public boolean isDone() {
		final long index = this.runIndex.get();
		if (index <= 0)
			return false;
		if (index == 1) {
			if (this.running.get())
				return false;
			return true;
		}
		return true;
	}



	public boolean hasException() {
		return (this.ex.get() != null);
	}
	public Exception getException() {
		return this.ex.get();
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getTaskName() {
		final String name = this.taskName.get();
		if (Utils.notEmpty(name))
			return name;
		return
			(new StringBuilder())
				.append("Task")
				.append(this.taskIndex)
				.toString();
	}
	public void setTaskName(final String taskName) {
		this.taskName.set(taskName);
		this._log.set(null);
	}
	public boolean taskNameEquals(final String taskName) {
		if (Utils.isEmpty(taskName))
			return Utils.isEmpty(this.taskName.get());
		return taskName.equals(this.taskName.get());
	}



	public xThreadPool getPool() {
		return this.pool;
	}



	// worker running this task
	public xThreadPoolWorker getWorker() {
		final xThreadPoolWorker worker = this.worker.get();
		if (worker == null) {
			throw new NullPointerException(
				StringUtils.ReplaceTags(
					"Task '{}' doesn't have a worker set! This should be handled by the thread pool!",
					this.getTaskName()
				)
			);
		}
		return worker;
	}
	public void setWorker(final xThreadPoolWorker worker) {
		if (worker == null) throw new RequiredArgumentException("worker");
		if ( ! this.worker.compareAndSet(null, worker) )
			throw new RuntimeException("worker already set!");
	}



	// ------------------------------------------------------------------------------- //
	// stats



	public long getTaskIndex() {
		return this.taskIndex;
	}
	public long getRunIndex() {
		return this.runIndex.get();
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
			final xLog log = this._log();
			this._log.set(
				new SoftReference<xLog>(
					log
				)
			);
			return log;
		}
	}
	protected xLog _log() {
		return
			this.worker.get()
				.log()
				.get( this.getTaskName() );
	}



}
