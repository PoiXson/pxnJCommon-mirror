package com.poixson.app;

import com.poixson.tools.Keeper;


public final class xAppDefines {
	private xAppDefines() {}
	static { Keeper.add(new xAppDefines()); }



	// .debug file
	public static final int SEARCH_DEBUG_PARENTS = 2;
	public static final String[] SEARCH_DEBUG_FILES =
		new String[] {
			".debug",
			"debug"
		};



	public static final int EXIT_HUNG = 3;



//TODO: remove this
//	// defaults
//	public static final xTime DEFAULT_TICK_INTERVAL = xTime.getNew("60s");



}
