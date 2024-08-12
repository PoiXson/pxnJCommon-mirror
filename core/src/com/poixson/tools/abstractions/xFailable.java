package com.poixson.tools.abstractions;


public interface xFailable {


	public boolean fail(final Throwable e);
	public boolean fail(final String msg, final Object...args);

	public boolean isFailed();

	public void onFailure();


}
