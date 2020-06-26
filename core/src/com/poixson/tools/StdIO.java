package com.poixson.tools;

import java.io.InputStream;
import java.io.PrintStream;


public final class StdIO {
	private StdIO() {}
	static { Keeper.add(new StdIO()); }


	public static void init() {}


	public static final PrintStream OriginalOut = System.out;
	public static final PrintStream OriginalErr = System.err;
	public static final InputStream OriginalIn  = System.in;


}
