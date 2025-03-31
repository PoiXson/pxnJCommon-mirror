/*
package com.poixson.app;


public enum xAppState {

	OFF     (0                ),
	STARTING(1                ),
	STOPPING(Integer.MIN_VALUE),
	RUNNING (Integer.MAX_VALUE);



	public final int value;



	private xAppState(final int value) {
		this.value = value;
	}



	public static xAppState FromInt(final int value) {
		if (value == xAppState.OFF.value    ) return xAppState.OFF;
		if (value == xAppState.RUNNING.value) return xAppState.RUNNING;
		if (value < 0                       ) return xAppState.STOPPING;
		if (value > 0                       ) return xAppState.STARTING;
		throw new IllegalStateException("Unknown app state");
	}



}
*/
