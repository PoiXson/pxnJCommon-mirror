package com.poixson.app;

import static com.poixson.logger.xLog.XLog;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xAppStep.StepType;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.AttachedLogger;
import com.poixson.logger.xDebug;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.AppProps;
import com.poixson.tools.Failure;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.xTime;
import com.poixson.tools.xTimeU;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.xFailable;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


/*
 * Startup sequence
 *    5  startup time - xApp
 *
 * Shutdown sequence
 *   50  stop thread pools - xApp
 *   10  garbage collect   - xApp
 *    5  uptime
 */
public abstract class xApp implements xStartable, Runnable, xFailable, AttachedLogger {

	public static final xTime EXIT_TIMEOUT = new xTime(200L);

	// app instances
	protected static final CopyOnWriteArraySet<xApp> apps = new CopyOnWriteArraySet<xApp>();
	protected final AtomicReference<String[]> args = new AtomicReference<String[]>(null);

	protected final xTime startTime = new xTime();
	protected final AppProps props;

	protected final AtomicInteger state  = new AtomicInteger(xAppDefines.STATE_OFF);
	protected final AtomicBoolean paused = new AtomicBoolean(false);
	protected final AtomicBoolean queued = new AtomicBoolean(false);

	protected final AtomicReference<xAppStepLoader> stepLoader = new AtomicReference<xAppStepLoader>(null);
	protected final AtomicReference<xAppStepDAO>   nextStepDAO = new AtomicReference<xAppStepDAO>(null);

	protected final AtomicReference<Failure> failed = new AtomicReference<Failure>(null);

	protected final CopyOnWriteArraySet<Runnable> hookReady = new CopyOnWriteArraySet<Runnable>();



	@SuppressWarnings("unchecked")
	public static <T extends xApp> T getApp(final Class<T> clss) {
		if (clss == null) throw new RequiredArgumentException("clss");
		final Iterator<xApp> it = apps.iterator();
		while (it.hasNext()) {
			final xApp app = it.next();
			if (clss.isInstance(app))
				return (T) app;
		}
		return null;
	}
	public static xApp[] getApps() {
		return apps.toArray(new xApp[0]);
	}



	protected xApp() {
		// load app.properties
		{
			final AppProps props;
			try {
				props = AppProps.LoadFromClassRef( this.getClass() );
			} catch (IOException e) {
				this.props = null;
				this.fail("Failed to load app.properties file", e);
				return;
			}
			this.props = props;
		}
		xLogRoot.XLog();
		xDebug.init();
		// search for .debug file
		if (Utils.notEmpty(xAppDefines.SEARCH_DEBUG_FILES)) {
			final String result =
				FileUtils.SearchLocalFile(
					xAppDefines.SEARCH_DEBUG_FILES,
					xAppDefines.SEARCH_DEBUG_PARENTS
				);
			if (result != null) {
				xDebug.setDebug();
			}
		}
	}



	// ------------------------------------------------------------------------------- //
	// start/stop app



	// note: override this to add steps
	protected Object[] getStepObjects(final StepType type) {
		return null;
	}



	@Override
	public void start() {
		if (this.hasFailed()) return;
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "start")) return;
		// check state
		final int state = this.state.get();
		if (state == xAppDefines.STATE_RUNNING) return;
		if (state <  xAppDefines.STATE_OFF)
			throw new RuntimeException("Cannot start app, currently stopping");
		// set starting state
		if (!this.state.compareAndSet(xAppDefines.STATE_OFF, xAppDefines.STATE_START))
			throw new RuntimeException("Cannot start, invalid state: "+this.state.toString());
		// load steps
		if (this.stepLoader.get() != null)
			throw new RuntimeException("App is already starting or stopping");
		final xAppStepLoader loader = new xAppStepLoader(this, StepType.STARTUP);
		if (!this.stepLoader.compareAndSet(null, loader))
			throw new RuntimeException("App is already starting or stopping");
		if (this.hasFailed()) return;
		loader.scanObjects(
			this.getStepObjects(StepType.STARTUP)
		);
		if (this.hasFailed()) return;
		if (loader.isEmpty()) {
			this.fail("No startup steps were found!");
			return;
		}
		this.log().title("Starting {}..", this.getTitle());
//TODO
//		// start hang catcher
//		this.startHangCatcher();
		// queue first step
		this.nextStepDAO.set(null);
		this.queueNextStep();
		this.queue();
	}



	@Override
	public void stop() {
		if (this.hasFailed()) return;
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "stop")) return;
		// check state
		final int state = this.state.get();
		if (state == xAppDefines.STATE_OFF) return;
		if (state <  xAppDefines.STATE_OFF) return;
		// set stopping state
		if (!this.state.compareAndSet(state, xAppDefines.STATE_STOP)) {
			this.stop();
			return;
		}
		// load steps
		if (this.stepLoader.get() != null)
			throw new RuntimeException("App is already starting or stopping");
		final xAppStepLoader loader = new xAppStepLoader(this, StepType.SHUTDOWN);
		if (!this.stepLoader.compareAndSet(null, loader))
			throw new RuntimeException("App is already starting or stopping");
		if (this.hasFailed()) return;
		loader.scanObjects(
			this.getStepObjects(StepType.SHUTDOWN)
		);
		if (this.hasFailed()) return;
		if (loader.isEmpty()) {
			this.fail("No shutdown steps were found!");
			return;
		}
		this.log().title("Stopping {}..", this.getTitle());
//TODO
//		// start hang catcher
//		this.startHangCatcher();
		// queue first step
		this.nextStepDAO.set(null);
		this.queueNextStep();
		this.queue();
	}



	public void kill() {
		final xLog log = XLog();
		log.flush();
		final xLogHandler[] handlers = log.getLogHandlers();
		for (final xLogHandler handler : handlers) {
			if (handler instanceof xStartable) {
				((xStartable) handler).stop();
			}
		}
		System.exit(1);
	}



	protected void finishedStartup() {
	}
	protected void finishedShutdown() {
	}



	// run next step
	@Override
	public void run() {
		this.queued.set(false);
		if (this.hasFailed()) return;
		if (this.paused.get()) return;
		// finished starting
		if (this.isRunning()) {
			this.stepLoader.set(null);
			this.finishedStartup();
			this.log_loader().title("{} is ready", this.getTitle());
			final Iterator<Runnable> it = this.hookReady.iterator();
			while (it.hasNext()) {
				final Runnable run = it.next();
				xThreadPool_Main.Get().runTaskLater(run);
			}
			this.hookReady.clear();
			return;
		}
		// finished shutdown
		if (this.isStopped()) {
			this.stepLoader.set(null);
			this.finishedShutdown();
			this.log_loader().title("{} finished shutdown", this.getTitle());
			return;
		}
		// run step
		final xAppStepDAO dao = this.nextStepDAO.getAndSet(null);
		if (this.hasFailed()) return;
		if (dao != null) {
			this.log_loader().fine("@|white,bold {} - {}|@", dao.step, dao.getTaskName());
//TODO
//			this.resetHangCatcher();
			try {
				dao.run();
			} catch (Exception e) {
				this.fail(e);
			}
//TODO
//			this.resetHangCatcher();
			if (dao.isPauseAfter()) {
				this.pause();
			}
		}
		// queue next step
		this.queueNextStep();
		if (dao == null) {
			this.queue();
		} else
		if (!dao.isMultiStep()) {
			this.queue();
		}
	}
	public void queue() {
		if (this.queued.compareAndSet(false, true)) {
			xThreadPool_Main.Get()
				.runTaskLater("Next-App-Step", this);
		}
	}
	protected void queueNextStep() {
		if (this.hasFailed()) return;
		if (this.paused.get()) return;
		if (this.nextStepDAO.get() != null) return;
		final xAppStepLoader loader = this.stepLoader.get();
		if (loader == null) throw new NullPointerException("Step loader not found");
		final xAppStepDAO dao = loader.getNextStep();
		if (dao == null) {
			final int state = this.state.get();
			this.state.set(
				state > xAppDefines.STATE_OFF
				? xAppDefines.STATE_RUNNING
				: xAppDefines.STATE_OFF
			);
			return;
		}
		// revert back into queue
		if (!this.nextStepDAO.compareAndSet(null, dao)) {
			loader.addStep(dao);
			return;
		}
		this.state.set(dao.step);
		if (dao.isPauseBefore()) {
			this.pause();
		}
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		return (this.state.get() == xAppDefines.STATE_RUNNING);
	}
	public boolean isStopped() {
		return (this.state.get() == xAppDefines.STATE_OFF);
	}



	public boolean isStarting() {
		if (this.state.get() == xAppDefines.STATE_RUNNING)
			return false;
		final xAppStepLoader loader = this.stepLoader.get();
		if (loader == null)
			return false;
		if (!loader.isStartup())
			return false;
		return true;
	}
	@Override
	public boolean isStopping() {
		if (this.state.get() == xAppDefines.STATE_OFF)
			return false;
		final xAppStepLoader loader = this.stepLoader.get();
		if (loader == null)
			return false;
		if (!loader.isShutdown())
			return false;
		return true;
	}



	public int getExitCode() {
		final Failure failure = this.failed.get();
		if (failure == null)
			return 0;
		return failure.getExitCode();
	}



	public void pause() {
		this.paused.set(true);
		this.log_loader()
			.fine(
				"Paused {} at step: {}",
				(this.isStarting() ? "loading" : "unloading"),
				this.state.get()
			);
	}
	public void resume() {
		this.paused.set(false);
		this.log_loader()
			.fine(
				"Resumed {} from step: {}",
				(this.isStarting() ? "loading" : "unloading"),
				this.state.get()
			);
		this.queueNextStep();
		this.queue();
	}
	public boolean isPaused() {
		return this.paused.get();
	}



	// ------------------------------------------------------------------------------- //
	// failure



	@Override
	public void fail(final Throwable e) {
		final Throwable cause = Utils.RootCause(e);
		this.fail(cause.getMessage(), e);
	}
	@Override
	public void fail(final String msg, final Object...args) {
		if (Failure.AtomicFail(this.failed, this.log(), msg, args)) {
			this.state.set(xAppDefines.STATE_OFF);
			this.stepLoader.set(null);
			xThreadPool_Main.Get().runTaskLazy(
				new RunnableMethod<Object>(this, "doFailed")
			);
			this.doFailed();
		}
	}
	@Override
	public void fail(final int exitCode, final String msg, final Object...args) {
		this.fail(msg, args);
		this.failed.get().setExitCode(exitCode);
	}



	protected void doFailed() {
//TODO
//		this.stopHangCatcher();
		System.exit( this.getExitCode() );
	}
	@Override
	public boolean hasFailed() {
		return (this.failed.get() != null);
	}



	// ------------------------------------------------------------------------------- //
	// hooks



	public void addHookReady(final Runnable run) {
		this.hookReady.add(run);
	}



	// ------------------------------------------------------------------------------- //
	// configs



	public String[] getArgs() {
		return this.args.get();
	}
	public void setArgs(final String[] args) {
		this.args.set(args);
	}



	protected String getLockFile() {
		return this.getTitle()+".lock";
	}



	// ------------------------------------------------------------------------------- //
	// properties



	public String getName() {
		return this.props.name;
	}
	public String getTitle() {
		if (Utils.notEmpty(this.props.title))
			return this.props.title;
		return this.getName();
	}
	public String getVersion() {
		return this.props.version;
	}
	public String getCommitHashFull() {
		return this.props.commitHashFull;
	}
	public String getCommitHashShort() {
		return this.props.commitHashShort;
	}
	public String getURL() {
		return this.props.url;
	}
	public String getOrgName() {
		return this.props.orgName;
	}
	public String getOrgURL() {
		return this.props.orgUrl;
	}
	public String getIssueName() {
		return this.props.issueName;
	}
	public String getIssueURL() {
		return this.props.issueUrl;
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	// start time
	@xAppStep(type=StepType.STARTUP, step=5, title="Startup Time")
	public void __START_uptime() {
		this.startTime
			.set( Utils.getSystemMillis(), xTimeU.MS )
			.lock();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// stop thread pools
	@xAppStep(type=StepType.SHUTDOWN, step=50, title="Stop Thread Pools")
	public void __STOP_threadpools() {
		xThreadPool.StopAll();
	}



	// garbage collect
	@xAppStep(type=StepType.SHUTDOWN, step=10, title="Garbage Collect")
	public void __STOP_garbage() {
		StdIO.OriginalOut.flush();
		StdIO.OriginalErr.flush();
		Keeper.removeAll();
		System.gc();
	}



	// display uptime
	@xAppStep(type=StepType.SHUTDOWN, step=5, title="Uptime")
	public void __STOP_uptime(final xLog log) {
//TODO: display total time running
	}



	// exit
	@xAppStep(type=StepType.SHUTDOWN, step=1, title="Exit")
	public void __STOP_exit() {
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
				ThreadUtils.Sleep(10L);
				ThreadUtils.DisplayStillRunning( xApp.this.log() );
				StdIO.OriginalOut.println();
			System.exit(this.exitCode);
				// exit
				System.exit( xApp.this.getExitCode() );
			}
		}.init(this.getExitCode());
		thread.setName("EndThread");
		thread.setDaemon(false);
		thread.start();
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<xLog> _log = new AtomicReference<xLog>(null);

	@Override
	public xLog log() {
		if (this._log.get() == null) {
			final xLog log = this._log();
			if (this._log.compareAndSet(null, log))
				return log;
		}
		return this._log.get();
	}
	protected xLog _log() {
		return XLog();
	}



	public xLog log_loader() {
		final xAppStepLoader loader = this.stepLoader.get();
		if (loader == null)
			return this.log();
		return loader.log();
	}



}
