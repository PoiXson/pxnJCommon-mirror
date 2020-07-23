package com.poixson.app.steps;

import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.tools.xLockFile;


/*
 * Startup sequence
 *   20  lock file
 *
 * Shutdown sequence
 *   20  release lock file
 */
public class xAppSteps_LockFile {

	protected final String filename;



	public xAppSteps_LockFile(final String filename) {
		this.filename = filename;
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	// lock file
	@xAppStep(type=StepType.STARTUP, step=20, title="Lock File")
	public void __START_lockfile() {
		final xLockFile lock = xLockFile.Get(this.filename);
		if ( ! lock.acquire() )
			throw new RuntimeException("Failed to get lock on file: "+this.filename);
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// release lock file
	@xAppStep(type=StepType.SHUTDOWN, step=20, title="Lock File")
	public void __STOP_lockfile() {
		xLockFile.Release(this.filename);
	}



}
