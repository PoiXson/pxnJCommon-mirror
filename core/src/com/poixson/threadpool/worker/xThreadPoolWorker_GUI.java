package com.poixson.threadpool.worker;

import static com.poixson.utils.ThreadUtils.GetDispatchThreadSafe;
import static com.poixson.threadpool.xThreadPool.DEBUG_EXTRA;

import javax.swing.SwingUtilities;

import com.poixson.threadpool.task.xThreadPoolTask;
import com.poixson.threadpool.types.xThreadPool_GUI;


public class xThreadPoolWorker_GUI extends xThreadPoolWorker {



	public xThreadPoolWorker_GUI(final xThreadPool_GUI pool, final String poolName) {
		super(pool, (Thread) null, poolName);
		final Thread thread = GetDispatchThreadSafe();
		if (thread == null)
			throw new RuntimeException("Failed to get dispatch thread");
		if (!super.thread.compareAndSet(null, thread))
			throw new RuntimeException("Dispatch thread already set");
	}



	@Override
	public void run() {
		if (this.isStopping()) return;
		this.running.set(true);
		{
			final Thread thread = this.thread.get();
			if (thread == null) {
				if ( this.thread.compareAndSet(null, Thread.currentThread()) )
					this.configureThread(thread);
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
				final long runIndex = this.count_runs.incrementAndGet();
				this.runTask(task, runIndex);
				// run more tasks
				SwingUtilities.invokeLater(this);
				return;
			}
		} catch (InterruptedException e) {
			this.log().trace(e);
		}
		// idle
		if (this.stopping.get()) {
			this.running.set(false);
		} else {
			if (DEBUG_EXTRA)
				this.log().detail("Idle..");
		}
	}



}
