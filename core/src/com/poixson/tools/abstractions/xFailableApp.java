package com.poixson.tools.abstractions;


public interface xFailableApp extends xFailable {


	public boolean fail(final int exitCode, final String msg, final Object...args);
	public int getExitCode();


}
