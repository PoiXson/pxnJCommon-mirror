/*
package com.poixson.threadpool.task;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.Failure;
import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.HangCatcher;
import com.poixson.tools.Keeper;
import com.poixson.tools.xTime;


public class ShutdownTask implements Runnable {

	private static final AtomicReference<ShutdownTask> instance =
			new AtomicReference<ShutdownTask>(null);

	protected final xThreadPool pool;

	protected final HangCatcher hangCatch;

	protected final AtomicBoolean running = new AtomicBoolean(false);

	// hang timeout
	public final long hangTimeout = xTime.getNew("10s").getMS();
	// sleep interval
	public final long hangSleep = 100L;



	public static ShutdownTask get() {
		// existing instance
		{
			final ShutdownTask task = instance.get();
			if (task != null)
				return task;
		}
		// new instance
		{
			final ShutdownTask task =
				new ShutdownTask();
			if (instance.compareAndSet(null, task))
				return task;
			return instance.get();
		}
	}
	private ShutdownTask() {
		this.pool = xThreadPool_Main.get();
		this.hangCatch =
			new HangCatcher(
				this.hangTimeout,
				this.hangSleep,
				"Shutdown",
				new Runnable() {
					@Override
					public void run() {
//TODO:
						System.out.println();
						System.out.println(" ************************ ");
						System.out.println(" *  Shutdown has hung!  * ");
						System.out.println(" ************************ ");
						System.out.println();
//						final PrintStream out = AnsiConsole.out;
//						out.println();
//						out.println(Ansi.ansi().a(" ").fg(Ansi.Color.RED).a("************************").reset().a(" "));
//						out.println(Ansi.ansi().a(" ").fg(Ansi.Color.RED).a("*  Shutdown has hung!  *").reset().a(" "));
//						out.println(Ansi.ansi().a(" ").fg(Ansi.Color.RED).a("************************").reset().a(" "));
//						out.println();
						ThreadUtils.DisplayStillRunning();
						System.exit(1);
					}
				}
			);
		this.hangCatch.start();
	}



	public void queueIt() {
		xThreadPool_Main.get()
			.runTaskLazy(
				this
			);
	}



	@Override
	public void run() {
		this.resetTimeout();
		if (!this.running.compareAndSet(false, true))
			return;
		int totalCount = 0;
		LOOP_OUTER:
		while (true) {
			int count = 0;
			final Iterator<Runnable> it =
				xThreadPool.shutdownHooks.iterator();
			//LOOP_INNER:
			while (it.hasNext()) {
				final Runnable run = it.next();
				this.pool
					.runTaskLazy(run);
				count++;
				this.resetTimeout();
			}
			if (count == 0)
				break LOOP_OUTER;
			totalCount += count;
			XLog().fine("Running %d shutdown hooks..", count);
			ThreadUtils.Sleep(20L);
		} // end LOOP_OUTER
		// queue another shutdown task (to wait for things to finish)
		if (totalCount > 0) {
			// run this again
			this.running.set(false);
			this.resetTimeout();
			this.queueIt();
		// finished running hooks
		} else {
			final Thread kill = new KillTask();
			Keeper.removeAll();
			Keeper.add(kill);
			kill.start();
		}
	}



	protected static class KillTask extends Thread {

		@Override
		public void run() {
			// wait for thread pools to stop
			ThreadUtils.Sleep(10L);
			xThreadPool.StopAllAndWait();
			ThreadUtils.Sleep(50L);
			// end
			System.exit(
				Failure.isFailed()
				? 1
				: 0
			);
		}

	}



	public void resetTimeout() {
		this.hangCatch.resetTimeout();
	}



}
*/
