package com.poixson.plugins;


public enum xPluginState {

	INIT,
	START,
	RUNNING,
	STOP,
	UNLOAD,
	FAILED;



	public xPluginState previous() {
		switch (this) {
		case INIT:    return null;
		case START:   return INIT;
		case RUNNING: return START;
		case STOP:    return RUNNING;
		case UNLOAD:  return STOP;
		case FAILED:  return null;
		}
		return null;
	}
	public xPluginState next() {
		switch (this) {
		case INIT:    return START;
		case START:   return RUNNING;
		case RUNNING: return STOP;
		case STOP:    return UNLOAD;
		case UNLOAD:  return null;
		case FAILED:  return null;
		}
		return null;
	}



	public static xPluginState PreviousState(final xPluginState state) {
		if (state == null)
			return null;
		return state.previous();
	}
	public static xPluginState NextState(final xPluginState state) {
		if (state == null)
			return INIT;
		return state.next();
	}



}
