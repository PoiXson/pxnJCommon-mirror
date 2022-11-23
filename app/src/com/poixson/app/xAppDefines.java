package com.poixson.app;

import com.poixson.tools.Keeper;


public final class xAppDefines {
	private xAppDefines() {}
	static { Keeper.add(new xAppDefines()); }


	// app state
	protected static final int STATE_OFF     = 0;
	protected static final int STATE_START   = 1;
	protected static final int STATE_STOP    = Integer.MIN_VALUE;
	protected static final int STATE_RUNNING = Integer.MAX_VALUE;


	// .debug file
	public static final int SEARCH_DEBUG_PARENTS = 2;
	public static final String[] SEARCH_DEBUG_FILES =
		new String[] {
			".debug",
			"debug"
		};


//TODO:
//	// defaults
//	public static final xTime DEFAULT_TICK_INTERVAL = xTime.getNew("60s");


}
