package com.poixson.app;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.abstractions.xStartable;
import com.poixson.app.xAppStep.StepType;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.AttachedLogger;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.AppProps;
import com.poixson.tools.CoolDown;
import com.poixson.tools.HangCatcher;
import com.poixson.tools.Keeper;
import com.poixson.tools.xTime;
import com.poixson.tools.comparators.IntComparator;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ProcUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


/*
 * Startup sequence
 *   10  prevent root        - xAppSteps_Tool
 *   50  load configs        - xAppSteps_Config
 *   70  lock file           - xAppSteps_LockFile
 *   80  display logo        - xAppSteps_Logo
 *   85  sync clock          - xAppStandard
 *  100  prepare commands    - xCommandHandler
 *  105  start console input - xAppSteps_Console
 *  200  startup time        - xAppStandard
 *  400  load plugins        - xPluginManager
 *  400  start plugins       - xPluginManager
 *
 * Shutdown sequence
 *  400  stop plugins        - xPluginManager
 *  400  unload plugins      - xPluginManager
 *  150  stop schedulers     - xAppSteps_Scheduler
 *  105  stop console input  - xAppSteps_Console
 *  100  stop thread pools   - xAppStandard
 *   60  display uptime      - xAppStandard
 *   20  release lock file   - xAppSteps_LockFile
 *   10  garbage collect     - xApp
 *    1  exit
 */
public abstract class xApp implements xStartable, AttachedLogger {

	// app instances
	protected static final CopyOnWriteArraySet<xApp> apps =
			new CopyOnWriteArraySet<xApp>();


	// current steps
	protected final AtomicInteger state = new AtomicInteger(0);
	protected final HashMap<Integer, List<xAppStepDAO>> currentSteps =
			new HashMap<Integer, List<xAppStepDAO>>();
	protected final AtomicReference<xAppStepDAO> nextStepDAO =
			new AtomicReference<xAppStepDAO>(null);

	protected final AtomicReference<HangCatcher> hangCatcher =
			new AtomicReference<HangCatcher>(null);
	protected final AtomicBoolean restartAfterUnloaded = new AtomicBoolean(false);

	// properties
	protected final AppProps props;



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



	public xApp() {
		this.props = AppProps.LoadFromClassRef( this.getClass() );
		final xLog log = this.log();
		// debug mode
		if (ProcUtils.isDebugWireEnabled()) {
			xVars.setDebug(true);
			log.fine("Detected IDE");
		} else
		// search for .debug file
		if (Utils.notEmpty(xVars.SEARCH_DEBUG_FILES)) {
			final String result =
				FileUtils.SearchLocalFile(
					xVars.SEARCH_DEBUG_FILES,
					xVars.SEARCH_DEBUG_PARENTS
				);
			if (result != null) {
				xVars.setDebug(true);
				log.fine("Detected .debug file");
			}
		}
		apps.add(this);
		Failure.register(
			new Runnable() {
				@Override
				public void run() {
					xApp.this.failed();
				}
			}
		);
		Keeper.add(this);
	}
//TODO:
//	// register shutdown hook
//	xThreadPool.addShutdownHook(
//		new RemappedMethod(this, "stop")
//	);
//TODO:
//		// process command line arguments
//		final List<String> argsList = new LinkedList<String>();
//		argsList.addAll(Arrays.asList(args));
//		instance.processArgs(argsList);
//		instance.processDefaultArgs(argsList);
//		if (utils.notEmpty(argsList)) {
//			final StringBuilder str = new StringBuilder();
//			for (final String arg : argsList) {
//				if (utils.isEmpty(arg)) continue;
//				if (str.length() > 0)
//					str.append(" ");
//				str.append(arg);
//			}
//			if (str.length() > 0) {
//				xVars.getOriginalOut()
//					.println("Unknown arguments: "+str.toString());
//				System.exit(1);
//				return;
//			}
//		}
//		// handle command-line arguments
//		instance.displayStartupVars();
//		// main thread ended
//		Failure.fail("@|FG_RED Main process ended! (this shouldn't happen)|@");
//		System.exit(1);



	// ------------------------------------------------------------------------------- //
	// start/stop app



	// override this to add steps
	protected Object[] getStepObjects(final StepType type) {
		return null;
	}



	@Override
	public void start() {
		if (Failure.hasFailed()) return;
		if (xThreadPool_Main.get().force(this, "start")) return;
		// check state (should be 0 stopped)
		final int stepInt = this.state.get();
		{
			if (stepInt != STATE_OFF) {
				// <0 already stopping
				if (stepInt < STATE_OFF) {
					this.restartAfterUnloaded.set(true);
				}
				// >0 already starting or running
				return;
			}
			this.restartAfterUnloaded.set(false);
		}
		this.currentSteps.clear();
		this.nextStepDAO.set(null);
		// set starting state
		if ( ! this.state.compareAndSet(STATE_OFF, STATE_START) ) {
			this.warning(
				"Invalid state, cannot start:",
				this.state.get()
			);
			return;
		}
		if (Failure.hasFailed()) return;
		this.title(
			new String[] { "Starting {}.." },
			this.getTitle()
		);
		// start hang catcher
		this.startHangCatcher();
		// prepare startup steps
		this.loadSteps(StepType.START);
		if (this.currentSteps.isEmpty()) {
			this.log().severe("No startup steps were found!");
			return;
		}
		if (Failure.hasFailed()) return;
		// queue startup sequence
		this.queueNextStep();
	}



	@Override
	public void stop() {
		if (Failure.hasFailed()) return;
		if (xThreadPool_Main.get().force(this, "stop")) return;
		// check state
		final int stepInt = this.state.get();
		{
			// already stopping or stopped
			if (stepInt <= STATE_OFF)
				return;
			// running
			if (stepInt == STATE_RUNNING) {
				this.state.set(STATE_STOP);
			} else {
				this.state.set( 0 - stepInt );
			}
		}
		this.currentSteps.clear();
		this.nextStepDAO.set(null);
		if (Failure.hasFailed()) return;
		this.title(
			new String[] { "Stopping {}.." },
			this.getTitle()
		);
		// start hang catcher
		this.startHangCatcher();
		// prepare shutdown steps
		this.loadSteps(StepType.STOP);
		if (this.currentSteps.isEmpty()) {
			this.log().severe("No shutdown steps were found!");
			return;
		}
		if (Failure.hasFailed()) return;
		// queue shutdown sequence
		this.queueNextStep();
	}



	public void restart() {
		this.restartAfterUnloaded.set(true);
		this.stop();
	}



	public static void shutdown() {
		final Iterator<xApp> it = apps.iterator();
		while (it.hasNext()) {
			final xApp app = it.next();
			app.stop();
		}
	}
	public static void kill() {
		System.exit(1);
	}



	public void finished() {
		this.stopHangCatcher();
		final int state = this.state.get();
		if (state == STATE_RUNNING) {
			this.log()
				.title(
					new String[] { "{} is ready!" },
					this.getTitle()
				);
		} else
		if (state == STATE_OFF) {
			this.log()
				.title(
					new String[] { "Finished stopping {}" },
					this.getTitle()
				);
			// restart
			if (this.restartAfterUnloaded.get()) {
				this.start();
			}
		} else {
			throw new RuntimeException("Unknown finish state: "+Integer.toString(state));
		}
	}
	public void failed() {
		this.stopHangCatcher();
		this.state.set(STATE_OFF);
		this.currentSteps.clear();
		this.nextStepDAO.set(null);
	}



	// ------------------------------------------------------------------------------- //
	// run startup/shutdown steps



	// run next step
	@Override
	public void run() {
		if (Failure.hasFailed()) return;
		synchronized (this.currentSteps) {
			if (Failure.hasFailed()) return;
			final int currentStepInt = this.state.get();
			// finished
			if (currentStepInt == STATE_RUNNING || currentStepInt == STATE_OFF) {
				this.finished();
				return;
			}
			// get current step
			final xAppStepDAO step = this.grabNextStep();
			if (step != null) {
				this.log().fine(
					StringUtils.ReplaceTags(
						"{}: @|white,bold {} - {}|@",
						( currentStepInt > STATE_OFF ? "Startup" : "Shutdown"),
						step.stepValue,
						step.getTaskName()
					)
				);
				// run current step
				this.resetHangCatcher();
				try {
					step.run();
				} catch (Exception e) {
					Failure.fail(e);
				}
				this.resetHangCatcher();
			}
			if (Failure.hasFailed()) return;
			// prepare next step
			this.queueNextStep();
		} // end sync
	}



	protected xAppStepDAO grabNextStep() {
		if (Failure.hasFailed()) return null;
		return this.nextStepDAO.getAndSet(null);
	}
	protected void queueNextStep() {
		// next task already queued
		if (this.nextStepDAO.get() != null)
			return;
		synchronized (this.currentSteps) {
			// check current or find next step value
			final List<xAppStepDAO> steps =
				this.findNextStepValue();
			final int nextStepInt = this.state.get();
			if (Utils.notEmpty(steps)) {
				// get next step from queue
				final xAppStepDAO nextStep = steps.get(0);
				if (nextStep == null) throw new RuntimeException("Failed to get next step");
				// set to run next
				if (this.nextStepDAO.compareAndSet(null, nextStep)) {
					steps.remove(0);
				}
				// last entry for this step value
				if (steps.size() == 0) {
					this.currentSteps.remove( Integer.valueOf(nextStepInt) );
				}
			}
			final String taskName;
			if (nextStepInt == STATE_RUNNING) {
				taskName = "Startup-Finished";
			} else
			if (nextStepInt == STATE_OFF) {
				taskName = "Shutdown-Finished";
			} else {
				taskName =
					StringUtils.ReplaceTags(
						"{}({})",
						( nextStepInt > STATE_OFF ? "Startup" : "Shutdown" ),
						nextStepInt
					);
			}
			xThreadPool_Main.get()
				.runTaskLater( taskName, this );
			this.resetHangCatcher();
		} // end sync
	}
	protected List<xAppStepDAO> findNextStepValue() {
		if (Failure.hasFailed()) return null;
		synchronized (this.currentSteps) {
			if (Failure.hasFailed()) return null;
			final int currentStepInt = this.state.get();
			if (currentStepInt == STATE_OFF)         throw new IllegalStateException();
			if (currentStepInt == Integer.MIN_VALUE) throw new IllegalStateException();
			if (currentStepInt == Integer.MAX_VALUE) throw new IllegalStateException();
			// check current step value
			{
				final List<xAppStepDAO> steps =
					this.currentSteps.get( Integer.valueOf(currentStepInt) );
				if (Utils.notEmpty(steps))
					return steps;
			}
			// find next step value
			int nextStepInt;
			{
				final Iterator<Integer> it = this.currentSteps.keySet().iterator();
				// startup
				if (currentStepInt > STATE_OFF) {
					nextStepInt = STATE_RUNNING;
					while (it.hasNext()) {
						final int index = it.next().intValue();
						if (index < nextStepInt)
							nextStepInt = index;
					}
					// shutdown
				} else {
					nextStepInt = STATE_OFF;
					while (it.hasNext()) {
						final int index = it.next().intValue();
						if (index < nextStepInt)
							nextStepInt = index;
					}
				}
			}
			if (Failure.hasFailed()) return null;
			// found next step value
			if ( ! this.state.compareAndSet(currentStepInt, nextStepInt) ) {
				throw new IllegalStateException(
					StringUtils.ReplaceTags(
						"Unexpected state change, {} should be {}",
						this.state.get(),
						currentStepInt
					)
				);
			}
			if (nextStepInt == STATE_RUNNING) return null;
			if (nextStepInt == STATE_OFF)     return null;
			return this.findNextStepValue();
		} // end sync
	}



	public void join() {
		xThreadPool_Main.get()
			.joinWorkers();
	}



	// ------------------------------------------------------------------------------- //
	// load startup/shutdown steps



	protected void loadSteps(final StepType type) {
		this.loadSteps(
			type,
			this.getStepObjects(type)
		);
	}
	protected void loadSteps(final StepType type, final Object[] containers) {
		if (type == null) throw new RequiredArgumentException("type");
		if (Failure.hasFailed()) return;
		synchronized (this.currentSteps) {
			// xApp
			this.loadSteps(type, this);
			// getStepObjects()
			if (Utils.notEmpty(containers)) {
				for (final Object obj : containers) {
					this.loadSteps(type, obj);
				}
			}
			// log loaded steps
			if (this.log().isDetailLoggable()) {
				final List<String> lines = new ArrayList<String>();
				lines.add("Found {} {} steps:");
				final TreeSet<Integer> orderedValues =
					new TreeSet<Integer>(
						new IntComparator(false)
					);
				orderedValues.addAll(
					this.currentSteps.keySet()
				);
				// log and count steps
				int count = 0;
				ORDERED_LOOP:
				for (final Integer stepInt : orderedValues) {
					final List<xAppStepDAO> list = this.currentSteps.get(stepInt);
					if (Utils.isEmpty(list))
						continue ORDERED_LOOP;
					//LIST_LOOP:
					for (final xAppStepDAO dao : list) {
						count++;
						lines.add(
							(new StringBuilder())
								.append(
									StringUtils.PadFront(
										5,
										stepInt.toString(),
										' '
									)
								)
								.append(" - ")
								.append(dao.title)
								.toString()
						);
					} // end LIST_LOOP
				} // end ORDERED_LOOP
				this.log()
					.detail(
						lines.toArray(new String[0]),
						count,
						( StepType.START.equals(type) ? "Startup" : "Shutdown" )
					);
			} // end log steps
		} // end sync
	}
	protected void loadSteps(final StepType type, final Object container) {
		if (type      == null) throw new RequiredArgumentException("type");
		if (container == null) throw new RequiredArgumentException("container");
		if (Failure.hasFailed()) return;
		synchronized (this.currentSteps) {
			// find annotations
			final Class<?> clss = container.getClass();
			if (clss == null) throw new RuntimeException("Failed to get app step container class!");
			final Method[] methods = clss.getMethods();
			if (Utils.isEmpty(methods)) throw new RuntimeException("Failed to get app methods!");
			METHODS_LOOP:
			for (final Method m : methods) {
				if (Failure.hasFailed()) return;
				final xAppStep anno = m.getAnnotation(xAppStep.class);
				if (anno == null) continue METHODS_LOOP;
				// found step method
				if (type.equals(anno.Type())) {
					final xAppStepDAO dao =
						new xAppStepDAO(
							this,
							container,
							m,
							anno
						);
					// add to existing list or new list
					this.currentSteps.computeIfAbsent(
						Integer.valueOf(dao.stepValue),
						key -> new ArrayList<xAppStepDAO>()
					).add(dao);
				}
			} // end METHODS_LOOP
		} // end sync
	}



	// ------------------------------------------------------------------------------- //
	// hang catcher



	private void startHangCatcher() {
		if (ProcUtils.isDebugWireEnabled())
			return;
		final HangCatcher catcher =
			new HangCatcher(
				xTime.getNew("5s").getMS(),
				100L,
				new Runnable() {
					@Override
					public void run() {
						final int step = xApp.this.state.get();
						xApp.this.publish(
							new String[] {
								(
									step > 0
									? "Startup step: "
									: "Shutdown step: "
								) + Integer.toString(step),
								"                                  ",
								" ******************************** ",
								" *  Startup/Shutdown has hung!  * ",
								" ******************************** ",
								"                                  "
							}
						);
						ThreadUtils.DisplayStillRunning();
						System.exit(1);
					}
				}
		);
		catcher.start();
		this.hangCatcher.set(catcher);
	}
	private void resetHangCatcher() {
		final HangCatcher catcher = this.hangCatcher.get();
		if (catcher != null) {
			catcher.resetTimeout();
		}
	}
	private void stopHangCatcher() {
		final HangCatcher catcher = this.hangCatcher.get();
		if (catcher != null) {
			catcher.stop();
		}
	}



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		return (this.state.get() == STATE_RUNNING);
	}
	public boolean isStarting() {
		return (this.state.get() > STATE_OFF);
	}
	@Override
	public boolean isStopping() {
		return (this.state.get() < STATE_OFF);
	}
	public boolean isStopped() {
		return (this.state.get() == STATE_OFF);
	}



	// ------------------------------------------------------------------------------- //
	// properties



	public String getName() {
		return this.props.name;
	}
	public String getTitle() {
		return this.props.title;
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



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// garbage collect
	@xAppStep( Type=StepType.STOP, Title="Garbage Collect", StepValue=10 )
	public void STOP_garbage(final xApp app, final xLog log) {
		Keeper.remove(this);
		ThreadUtils.Sleep(50L);
		xVars.getOriginalOut()
			.flush();
		System.gc();
	}



//TODO: move this?
	@xAppStep( Type=StepType.STOP, Title="Exit", StepValue=1)
	public void STOP_exit() {
		final Thread stopThread =
			new Thread() {
				private volatile int exitCode = 0;
				public Thread init(final int exitCode) {
					this.exitCode = exitCode;
					return this;
				}
				@Override
				public void run() {
					// stop thread pools
					xThreadPool.StopAll();
					if (xVars.isDebug()) {
						final CoolDown cool = CoolDown.getNew("1s");
						cool.resetRun();
						try {
							while ( ! cool.runAgain() ) {
								Thread.sleep(50L);
								if (ThreadUtils.CountStillRunning() == 0)
									break;
							}
						} catch (InterruptedException ignore) {}
						ThreadUtils.DisplayStillRunning();
					}
					xVars.getOriginalOut()
						.println();
					System.exit(this.exitCode);
				}
			}.init(0);
		stopThread.setName("EndThread");
		stopThread.setDaemon(true);
		stopThread.start();
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<SoftReference<xLog>> _log =
			new AtomicReference<SoftReference<xLog>>(null);

	@Override
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
				new SoftReference<xLog>( log )
			);
			return log;
		}
	}
	protected xLog _log() {
		return xLogRoot.Get();
	}



}
