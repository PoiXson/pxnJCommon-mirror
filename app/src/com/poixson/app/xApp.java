package com.poixson.app;

import static com.poixson.utils.ProcUtils.IsDebugWireEnabled;
import static com.poixson.utils.ThreadUtils.Sleep;
import static com.poixson.utils.Utils.GetMS;
import static com.poixson.utils.Utils.IsEmpty;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.AppProperties;
import com.poixson.tools.HangCatcher;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.xDebug;
import com.poixson.tools.xTime;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.xFailableApp;
import com.poixson.tools.abstractions.startstop.xStartStop;
import com.poixson.tools.abstractions.startstop.xStop;


/*
 * Startup sequence
 *  10 | startup time
 *
 * Shutdown sequence
 *  60 | display uptime
 *  50 | stop thread pools
 *  10 | garbage collect
 *   1 | exit
 */
public abstract class xApp implements AppProperties, xStartStop, Runnable, xFailableApp {

	public static final int EXIT_HUNG = 3;

	// app instances
	protected static final CopyOnWriteArraySet<xApp> apps = new CopyOnWriteArraySet<xApp>();
	protected final Keeper keeper;

	protected final AppPropsDAO props = AppPropsDAO.LoadSafe(this.getClass());

	protected final xTime time_start = new xTime();

	protected final AtomicReference<String[]> args = new AtomicReference<String[]>(null);

	// app state
	protected final AtomicInteger                state       = new AtomicInteger(xAppState.OFF.value);
	protected final AtomicBoolean                paused      = new AtomicBoolean(false);
	protected final AtomicReference<Throwable>   failure     = new AtomicReference<Throwable>(null);
	protected final AtomicInteger                failcode    = new AtomicInteger(0);
	protected final AtomicReference<HangCatcher> hangcatcher = new AtomicReference<HangCatcher>(null);

	protected final AtomicReference<xAppStepLoader> step_loader = new AtomicReference<xAppStepLoader>(null);
	protected final AtomicReference<xAppStepDAO>    nextStepDAO = new AtomicReference<xAppStepDAO>(null);
	protected final AtomicInteger                   step_count  = new AtomicInteger(0);

//TODO: replace with event listeners
	protected final CopyOnWriteArraySet<Runnable> hookReady = new CopyOnWriteArraySet<Runnable>();

	protected static final xTime EXIT_TIMEOUT = new xTime(200L);



	public xApp(final String[] args) {
		StdIO.Init();
		AddApp(this);
		xDebug.Init();
		// queue app->start()
		xThreadPool_Main.Get()
			.runTaskNow( new RunnableMethod<Object>(this, "start") );
		this.keeper = Keeper.Get();
	}



	protected static void AddApp(final xApp app) {
		if (app == null) throw new RequiredArgumentException("app");
		final String appClassName = app.getClass().getName();
		synchronized (apps) {
			final Iterator<xApp> it = apps.iterator();
			while (it.hasNext()) {
				final xApp a = it.next();
				final String clss = a.getClass().getName();
				if (appClassName.equals(clss))
					throw new RuntimeException("App of this type already active: "+appClassName);
			}
			apps.add(app);
		}
	}
	public static xApp Get(final Class<xApp> classType) {
		if (classType == null) throw new RequiredArgumentException("classType");
		return Get(classType.getName());
	}
	public static xApp Get(final String classType) {
		if (IsEmpty(classType)) throw new RequiredArgumentException("classType");
		final Iterator<xApp> it = apps.iterator();
		while (it.hasNext()) {
			final xApp app = it.next();
			final String clss = app.getClass().getName();
			if (classType.equals(clss))
				return app;
		}
		return null;
	}
	public static xApp[] GetApps() {
		return apps.toArray(new xApp[0]);
	}



	// -------------------------------------------------------------------------------
	// start/stop app



	@Override
	public boolean start() {
		if (this.isFailed()) return false;
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "start"))
			return false;
		// check state
		final xAppState state = xAppState.FromInt(this.state.get());
		switch (state) {
		case RUNNING: return false;
		case STOPPING: throw new RuntimeException("Cannot start app, currently stopping");
		default: break;
		}
		// set starting state
		if (!this.state.compareAndSet(xAppState.OFF.value, xAppState.STARTING.value))
			throw new RuntimeException("Cannot start, invalid state: "+this.state.toString());
		// load steps
		if (this.step_loader.get() != null)
			throw new RuntimeException("App is already starting or stopping");
		final xAppStepLoader loader = new xAppStepLoader(this, xAppStepType.STARTUP);
		if (!this.step_loader.compareAndSet(null, loader))
			throw new RuntimeException("App is already starting or stopping");
		if (this.isFailed()) return false;
		loader.scan(this);
		if (this.isFailed()) return false;
		if (loader.isEmpty()) {
			this.fail("No startup steps were found!");
			return false;
		}
		this.step_count.set(0);
		this.log().title("Starting %s..", this.getTitle());
		// start hang catcher
		this.startHangCatcher();
		// queue first step
		this.nextStepDAO.set(null);
		this.queueNextStep();
		this.queueNextTask();
		return true;
	}

	@Override
	public boolean stop() {
		if (this.isFailed()) return false;
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "stop"))
			return false;
		// check state
		final int state = this.state.get();
		SWITCH_STATE:
		switch (xAppState.FromInt(state)) {
		case OFF:
		case STOPPING:
			return false;
		default: break SWITCH_STATE;
		}
		// set stopping state
		if (!this.state.compareAndSet(state, xAppState.STOPPING.value)) {
			this.stop();
			return false;
		}
		// load steps
		if (this.step_loader.get() != null)
			throw new RuntimeException("App is already starting or stopping");
		final xAppStepLoader loader = new xAppStepLoader(this, xAppStepType.SHUTDOWN);
		if (!this.step_loader.compareAndSet(null, loader))
			throw new RuntimeException("App is already starting or stopping");
		if (this.isFailed()) return false;
		loader.scan(this);
		if (this.isFailed()) return false;
		if (loader.isEmpty()) {
			this.fail("No shutdown steps were found!");
			return false;
		}
		this.step_count.set(0);
		this.log().title("Stopping %s..", this.getTitle());
		// start hang catcher
		this.startHangCatcher();
		// queue first step
		this.nextStepDAO.set(null);
		this.queueNextStep();
		this.queueNextTask();
		return true;
	}



	// run next step
	@Override
	public void run() {
		if (this.isFailed())   return;
		if (this.paused.get()) return;
		// finished starting
		if (this.isRunning()) {
			this.stopHangCatcher();
			this.step_loader.set(null);
//TODO: improve this
			this.finishedStartup();
			final int count = this.step_count.get();
			this.log_loader().fine("Ran %d startup steps", Integer.valueOf(count));
			this.log_loader().title("%s is ready", this.getTitle());
			final Iterator<Runnable> it = this.hookReady.iterator();
			while (it.hasNext()) {
				final Runnable run = it.next();
				xThreadPool_Main.Get()
					.runTaskLater(run);
			}
			this.hookReady.clear();
			return;
		}
		// finished stopping
		if (this.isStopped()) {
			this.step_loader.set(null);
			this.finishedShutdown();
			final int count = this.step_count.get();
			this.log_loader().fine("Ran %d shutdown steps", Integer.valueOf(count));
			this.log_loader().title("%s finished shutdown", this.getTitle());
			return;
		}
		if (this.isFailed()) return;
		// run step
		final xAppStepDAO dao = this.nextStepDAO.getAndSet(null);
		boolean multi_finished = false;
		if (dao != null) {
			this.log_loader().fine("@|white,bold %d - %s|@", dao.step_abs, dao.getTaskTitle());
			this.resetHangCatcher();
			dao.loop_count.getAndIncrement();
			try {
				this.step_count.incrementAndGet();
				dao.run();
			} catch (xAppStepMultiFinishedException ignore) {
				multi_finished = true;
			} catch (Exception e) {
				this.fail(e);
				return;
			}
			this.resetHangCatcher();
			if (dao.isPauseAfter())
				this.pause();
		}
		// queue step again
		if (dao.isMultiStep()
		&& !multi_finished) {
			this.nextStepDAO.set(dao);
		// queue next step
		} else {
			this.queueNextStep();
		}
		if (!this.paused.get())
			this.queueNextTask();
	}
	public void queueNextTask() {
		xThreadPool_Main.Get()
			.runTaskLater("xAppStep", this);
	}
	protected void queueNextStep() {
		if (this.isFailed())   return;
		if (this.paused.get()) return;
		if (this.nextStepDAO.get() != null) return;
		final xAppStepLoader loader = this.step_loader.get();
		if (loader == null) throw new NullPointerException("Step loader not found");
		final xAppStepDAO dao = loader.getNextStep();
		if (dao == null) {
			final int state = this.state.get();
			this.state.set(
				state > xAppState.OFF.value
				? xAppState.RUNNING.value
				: xAppState.OFF.value
			);
			return;
		}
		// set or revert back into queue
		if (this.nextStepDAO.compareAndSet(null, dao)) {
			dao.loop_count.set(0);
		} else {
			loader.add(dao);
			return;
		}
		this.state.set(dao.step);
		if (dao.isPauseBefore())
			this.pause();
	}



	public void kill() {
		final xLog log = xLog.Get();
		log.flush();
		final xLogHandler[] handlers = log.getHandlersOrDefault();
		for (final xLogHandler handler : handlers) {
			if (handler instanceof xStop stoppable)
				stoppable.stop();
		}
		System.exit(1);
	}



	protected void finishedStartup() {
	}
	protected void finishedShutdown() {
	}

	public void addHookReady(final Runnable run) {
		this.hookReady.add(run);
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isRunning() {
		return (this.state.get() == xAppState.RUNNING.value);
	}
	public boolean isStopped() {
		return (this.state.get() == xAppState.OFF.value);
	}

	public boolean isStarting() {
		final xAppState state = xAppState.FromInt(this.state.get());
		switch (state) {
		case STARTING: {
			final xAppStepLoader loader = this.step_loader.get();
			if (loader == null)      return false;
			if (!loader.isStartup()) return false;
			return true;
		}
		default: break;
		}
		return false;
	}
	@Override
	public boolean isStopping() {
		final xAppState state = xAppState.FromInt(this.state.get());
		switch (state) {
		case STOPPING: {
			final xAppStepLoader loader = this.step_loader.get();
			if (loader == null)       return false;
			if (!loader.isShutdown()) return false;
			return true;
		}
		default: break;
		}
		return false;
	}



	public void pause() {
		this.paused.set(true);
		this.stopHangCatcher();
		this.log_loader()
			.fine(
				"Paused %s at step: %d",
				(this.isStarting() ? "loading" : "unloading"),
				this.state.get()
			);
	}
	public void resume() {
		this.paused.set(false);
		this.startHangCatcher();
		this.log_loader()
			.fine(
				"Resumed %s from step: %d",
				(this.isStarting() ? "loading" : "unloading"),
				this.state.get()
			);
		this.queueNextStep();
		this.queueNextTask();
	}
	public boolean isPaused() {
		return this.paused.get();
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// 10 | start time
	@xAppStep(type=xAppStepType.STARTUP, step=10, title="Startup Time")
	public void __START__uptime() {
		this.time_start
			.set( GetMS(), TimeUnit.MILLISECONDS );
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	// 60 | display uptime
	@xAppStep(type=xAppStepType.SHUTDOWN, step=60, title="Uptime")
	public void __STOP__uptime(final xLog log) {
//TODO: display total time running
	}



	// 50 | stop thread pools
	@xAppStep(type=xAppStepType.SHUTDOWN, step=50, title="Stop Thread Pools")
	public void __STOP__threadpools() {
		xThreadPool.StopAll();
	}



	// 10 | garbage collect
	@xAppStep(type=xAppStepType.SHUTDOWN, step=10, title="Garbage Collect")
	public void __STOP__garbage() {
		StdIO.OriginalOut().flush();
		StdIO.OriginalErr().flush();
		System.gc();
	}



	// 1 | exit
	@xAppStep(type=xAppStepType.SHUTDOWN, step=1, title="Exit")
	public void __STOP__exit() {
		final Thread thread = new Thread() {
			private volatile int exitCode = 0;
			public Thread init(final int exitCode) {
				this.exitCode = exitCode;
				return this;
			}
			@Override
			public void run() {
				final xThreadPool_Main poolMain = xThreadPool_Main.Get();
				poolMain.stopMain();
				poolMain.join(EXIT_TIMEOUT);
				Sleep(10L);
//TODO
//				ThreadUtils.DisplayStillRunning( xApp.this.log() );
				StdIO.OriginalOut().println();
			System.exit(this.exitCode);
				// exit
				System.exit( xApp.this.getExitCode() );
			}
		}.init(this.getExitCode());
		thread.setName("EndThread");
		thread.setDaemon(false);
		thread.start();
	}



	// -------------------------------------------------------------------------------
	// hang catcher



	protected HangCatcher startHangCatcher() {
		{
			final HangCatcher catcher = this.hangcatcher.get();
			if (catcher != null)
				return catcher;
		}
		if (!IsDebugWireEnabled()) {
			final HangCatcher catcher = new HangCatcher(
				new Runnable() {
					@Override
					public void run() {
						xApp.this.fail(EXIT_HUNG, "app hung");
					}
				}
			);
			if (this.hangcatcher.compareAndSet(null, catcher)) {
				catcher.start();
				return catcher;
			}
		}
		return this.hangcatcher.get();
	}
	protected void stopHangCatcher() {
		final HangCatcher catcher = this.hangcatcher.getAndSet(null);
		if (catcher != null) {
			catcher.stop();
		}
	}
	protected boolean resetHangCatcher() {
		final HangCatcher catcher = this.hangcatcher.get();
		if (catcher != null) {
			catcher.resetTimeout();
			return true;
		}
		return false;
	}



	// -------------------------------------------------------------------------------
	// properties



	@Override
	public AppPropsDAO getProps() {
		return this.props;
	}



	public String[] getArgs() {
		return this.args.get();
	}
	public void setArgs(final String[] args) {
		this.args.set(args);
	}



	protected String getLockFile() {
		return this.getTitle()+".lock";
	}



	public long uptime() {
		final long current = GetMS();
		final long time_start = this.time_start.ms();
		return current - time_start;
	}



	// -------------------------------------------------------------------------------
	// failure



	@Override
	public boolean fail(final Throwable e) {
		this.log().trace(e);
		this.failcode.compareAndSet(0, 1);
		if (this.failure.compareAndSet(null, e)) {
			this.state.set(xAppState.OFF.value);
			this.step_loader.set(null);
			xThreadPool_Main.Get().runTaskLater(
				new RunnableMethod<Object>(this, "onFailure")
			);
			return true;
		}
		return false;
	}
	@Override
	public boolean fail(final String msg) {
		return this.fail(new RuntimeException(msg));
	}
	@Override
	public boolean fail(final int exitcode, final String msg) {
		this.failcode.compareAndSet(0, exitcode);
		return this.fail(msg);
	}



	@Override
	public boolean isFailed() {
		return (this.failure.get() != null);
	}



	@Override
	public void onFailure() {
		this.stopHangCatcher();
		System.exit( this.getExitCode() );
	}



	@Override
	public int getExitCode() {
		return this.failcode.get();
	}



	// -------------------------------------------------------------------------------
	// logger



	public xLog log_loader() {
		final xAppStepLoader loader = this.step_loader.get();
		if (loader == null)
			return this.log();
		return loader.log();
	}



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log == null) this._log.set(null);
				else             return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get();
	}



}
