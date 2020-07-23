package com.poixson.tools.abstractions;


public interface xFailable {


	public void fail(final Throwable e);
	public void fail(final String msg, final Object...args);
	public void fail(final int exitCode, final String msg, final Object...args);

	public boolean hasFailed();


}
