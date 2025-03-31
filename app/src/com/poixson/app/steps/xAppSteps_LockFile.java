/*
package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepType;
import com.poixson.tools.xLockFile;


/ *
 * Startup sequence
 *  15 | lock file
 *
 * Shutdown sequence
 *  15 | release lock file
 * /
public class xAppSteps_LockFile {

	protected final String filename;



	public xAppSteps_LockFile(final String filename) {
		this.filename = filename;
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// 15 | lock file
	@xAppStep(type=xAppStepType.STARTUP, title="Lock File", step=15)
	public void __START__lockfile() {
		final xLockFile lock = xLockFile.Get(this.filename);
		if ( ! lock.acquire() )
			throw new RuntimeException("Failed to get lock on file: "+this.filename);
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	// 15 | release lock file
	@xAppStep(type=xAppStepType.SHUTDOWN, title="Lock File", step=15)
	public void __STOP__lockfile() {
		xLockFile.Release(this.filename);
	}



}
*/
