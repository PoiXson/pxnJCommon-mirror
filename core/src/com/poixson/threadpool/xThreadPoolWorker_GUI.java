/*
package com.poixson.threadpool;

import com.poixson.threadpool.types.xThreadPool_GUI;
import com.poixson.utils.ThreadUtils;


public class xThreadPoolWorker_GUI extends xThreadPoolWorker {



	public xThreadPoolWorker_GUI(final xThreadPool_GUI pool, final String poolName) {
		super(pool, (Thread) null, poolName);
		final Thread thread = ThreadUtils.getDispatchThreadSafe();
		if (thread == null)
			throw new RuntimeException("Failed to get dispatch thread");
		if (!super.thread.compareAndSet(null, thread))
			throw new RuntimeException("Dispatch thread already set");
	}



	@Override
	public void run() {
		if (this.isStopping()) return;
final boolean b = true; if (b) throw new RuntimeException("UNFINISHED CODE");
//TODO
//		this.running.set(true);
//		{
//			final Thread thread = this.thread.get();
//			if (thread == null) {
//				if ( this.thread.compareAndSet(null, Thread.currentThread()) ) {
//					this.configureThread(thread);
//				}
//			} else {
//				if ( ! thread.equals(Thread.currentThread()) )
//					throw new IllegalStateException("Invalid thread state!");
//			}
//		}
//		// get task from queues
//		try {
//			final xThreadPoolTask task =
//				this.pool.grabNextTask();
//			// run the task
//			if (task != null) {
//				this.runTask(task);
//				this.runCount.incrementAndGet();
//				// run more tasks
//				SwingUtilities.invokeLater(this);
//				return;
//			}
//		} catch (InterruptedException e) {
//			this.log().trace(e);
//			return;
//		}
//		// idle
//		this.log().detail("Idle thread..");
//		if (this.stopping.get()) {
//			this.running.set(false);
//		} else {
//			this.log().detail("Idle..");
//		}
	}



}
*/
