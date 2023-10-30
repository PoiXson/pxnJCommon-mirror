package com.poixson;

import com.poixson.tools.Keeper;


public class ShellDefines {
	private ShellDefines() {}
	static { Keeper.add(new ShellDefines()); }


	public static final String HISTORY_FILE = "history.txt";
	public static final int    HISTORY_SIZE = 1000;

	public static final String  DEFAULT_PROMPT = " #>";
	public static final boolean BELL_ENABLED   = true;


}
