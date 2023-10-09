package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepType;
import com.poixson.utils.Utils;


/*
 * Startup sequence
 *  5 | check user
 */
public class xAppSteps_UserOnlyRoot {



	// -------------------------------------------------------------------------------
	// startup steps



	// ensure is root
	@xAppStep(type=xAppStepType.STARTUP, step=5, title="Check User")
	public void __START__user_only_root() {
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
