/*
package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.utils.Utils;


/ *
 * Startup sequence
 *   10  check user
 * /
public interface xAppSteps_UserNotRoot {



	// -------------------------------------------------------------------------------
	// startup steps



	// ensure not root
	@xAppStep(type=StepType.STARTUP, step=10, title="Check User")
	default void __START_user_not_root() {
		final String user = System.getProperty("user.name");
		if (Utils.isEmpty(user))
			return;
		switch (user) {
		case "root":
		case "admin":
		case "administrator":
			throw new RuntimeException("Cannot run this program as root/administrator");
		default: break;
		}
	}



}
*/
