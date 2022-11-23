package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepMode;
import com.poixson.utils.Utils;


/*
 * Startup sequence
 *   10  check user
 */
public interface xAppSteps_UserOnlyRoot {



	// -------------------------------------------------------------------------------
	// startup steps



	// ensure is root
	@xAppStep(mode=xAppStepMode.STARTUP, weight=10, title="Check User")
	default void __START_user_only_root() {
		final String user = System.getProperty("user.name");
		if (Utils.isEmpty(user))
			return;
		switch (user) {
		case "root":
		case "admin":
		case "administrator":
			break;
		default:
			throw new RuntimeException("This program can only run as root/administrator");
		}
	}



}
