package com.poixson.tools.abstractions.startstop;


public interface xStop {


	public boolean stop();

	public default boolean isStopping() {
		throw new UnsupportedOperationException();
	}


}
