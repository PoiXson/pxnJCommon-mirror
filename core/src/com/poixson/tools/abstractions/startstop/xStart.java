package com.poixson.tools.abstractions.startstop;


public interface xStart {


	public boolean start();

	public default boolean isRunning() {
		throw new UnsupportedOperationException();
	}


}
