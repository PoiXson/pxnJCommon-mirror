/*
package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.logger.xLog;


/ *
 * Startup sequence
 *   10  prevent root
 * /
public class xAppSteps_Standard {



	public xAppSteps_Standard() {
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// ensure not root
	@xAppStep(type=StepType.STARTUP, step=10, title="Prevent Root")
	public void __START_root_check(final xLog log) {
		final String user = System.getProperty("user.name");
		if ("root".equals(user)) {
			log.warning("It is recommended to run as a non-root user");
		} else
		if ("administrator".equalsIgnoreCase(user)
		|| "admin".equalsIgnoreCase(user)) {
			log.warning("It is recommended to run as a non-administrator user");
		}
	}



}
*/
