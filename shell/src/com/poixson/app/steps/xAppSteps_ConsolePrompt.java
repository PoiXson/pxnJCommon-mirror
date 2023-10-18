package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepType;
import com.poixson.app.commands.xCommandHandler;
import com.poixson.logger.xLog;
import com.poixson.tools.xConsolePrompt;


/*
 * Startup sequence
 *  95 | command prompt
 *
 * Shutdown sequence
 *  95 | command prompt
 */
public class xAppSteps_ConsolePrompt extends xConsolePrompt {

	protected final xCommandHandler commands;



	public xAppSteps_ConsolePrompt(final xCommandHandler commands) {
		this.commands = commands;
	}



	// -------------------------------------------------------------------------------
	// startup steps



	@xAppStep(type=xAppStepType.STARTUP, title="ConsolePrompt", step=95)
	public void __START__command_prompt() {
		final xLog log = xLog.Get();
		log.setConsole(this);
//		final xCommandHandler handler = new xCommandHandlerJLine();
//		ShellUtils.SetCommandHandler(handler);
//	}
//TODO
//		// initialize console and enable colors
//		if (System.console() != null) {
//			if (!ShellUtils.isJLineAvailable()) {
//				Failure.fail("jline library not found");
//				return;
//			}
//		}
		// start reading console input
		this.start();
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	// stop console input
	@xAppStep(type=xAppStepType.SHUTDOWN, title="ConsolePrompt", step=95)
	public void __STOP__console() {
//TODO
//		// stop reading console input
//		this.stop();
	}



}
