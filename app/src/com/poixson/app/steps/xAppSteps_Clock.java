package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.logger.xLog;
import com.poixson.tools.xClock;


public class xAppSteps_Clock {



	// ------------------------------------------------------------------------------- //
	// startup steps



	// ensure not root
	@xAppStep( Type=StepType.START, Title="Clock Sync", StepValue=85 )
	public void _START_clock_sync(final xLog log) {
		xClock.get(true);
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



}
