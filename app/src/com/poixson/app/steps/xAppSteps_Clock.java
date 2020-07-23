package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;


/*
 * Startup sequence
 *   55  sync clock
 */
public class xAppSteps_Clock {



	// ------------------------------------------------------------------------------- //
	// startup steps



	// ensure not root
	@xAppStep(type=StepType.STARTUP, step=55, title="Clock Sync")
	public void __START_clock_sync() {
//TODO
//		xClock.get(true);
	}



}
