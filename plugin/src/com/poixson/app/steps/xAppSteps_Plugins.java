package com.poixson.app.steps;

import com.poixson.app.xApp;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.plugins.xPluginManager;


public class xAppSteps_Plugins {

	protected final xPluginManager<?> manager;



	public xAppSteps_Plugins(final xPluginManager<?> manager) {
		if (manager == null) throw new RequiredArgumentException("manager");
		this.manager = manager;
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	@xAppStep( Type=StepType.START, Title="Plugins", StepValue=400 )
	public void _START_plugins(final xApp app) {
		this.manager.start();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// stop console input
	@xAppStep( Type=StepType.STOP, Title="Plugins", StepValue=400)
	public void _STOP_plugins() {
		this.manager.stop();
	}



}
