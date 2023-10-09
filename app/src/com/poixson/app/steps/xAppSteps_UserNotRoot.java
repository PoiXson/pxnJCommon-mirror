package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepType;
import com.poixson.utils.Utils;


/*
 * Startup sequence
 *  5 | check user
 */
public class xAppSteps_UserNotRoot {



	// -------------------------------------------------------------------------------
	// startup steps



	// ensure not root
	@xAppStep(type=xAppStepType.STARTUP, step=5, title="Check User")
	public void __START__user_not_root() {
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
