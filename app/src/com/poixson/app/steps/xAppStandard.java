package com.poixson.app.steps;

import com.poixson.app.xApp;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.logger.xLog;
import com.poixson.tools.xTime;
import com.poixson.tools.xTimeU;
import com.poixson.utils.Utils;


public abstract class xAppStandard extends xApp {

	protected final xTime startTime = xTime.getNew();



	public xAppStandard() {
	}



//TODO: remove this
//TODO: this should be improved
//	@Override
//	protected void initLogger() {
//		super.initLogger();
//		if (Failure.hasFailed()) {
//			xVars.getOriginalOut()
//				.println("Failure, pre-init!");
//			System.exit(1);
//		}
//		final xLog log = xLog.getRoot();
//		if (System.console() != null) {
//			log.setHandler(
//				new xLogHandlerConsole()
//			);
//		}
//	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	// ensure not root
	@xAppStep( Type=StepType.START, Title="Root Check", StepValue=10 )
	public void _START_rootcheck(final xLog log) {
		final String user = System.getProperty("user.name");
		if ("root".equals(user)) {
			log.warning("It is recommended to run as a non-root user");
		} else
		if ("administrator".equalsIgnoreCase(user)
		|| "admin".equalsIgnoreCase(user)) {
			log.warning("It is recommended to run as a non-administrator user");
		}
	}



	// start time
	@xAppStep( Type=StepType.STARTUP, Title="Startup Time", StepValue=200 )
	public void START_uptime() {
		this.startTime
			.set( Utils.getSystemMillis(), xTimeU.MS )
			.lock();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



//	// stop thread pools
//	@xAppStep( Type=StepType.SHUTDOWN, Title="Thread Pools", StepValue=100 )
//	public void STOP_threadpools(final xLog log) {
//TODO:
//		xThreadPool.StopAll();
//	}



//	// display uptime
//	@xAppStep( Type=StepType.SHUTDOWN, Title="Uptime", StepValue=60 )
//	public void STOP_uptime(final xLog log) {
//TODO: display total time running
//	}



}
