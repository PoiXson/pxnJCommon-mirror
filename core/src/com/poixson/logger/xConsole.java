package com.poixson.logger;

import java.io.OutputStream;
import java.io.PrintStream;

import com.poixson.tools.abstractions.xStartable;


public abstract class xConsole extends PrintStream implements xStartable, Runnable {



	public xConsole(final OutputStream out) {
		super(out, true);
	}



}
