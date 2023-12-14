package com.poixson.logger;

import java.io.OutputStream;
import java.io.PrintStream;

import com.poixson.tools.abstractions.xStartable;
import com.poixson.tools.commands.xCommandProcessor;


public abstract class xConsole extends PrintStream implements xStartable, Runnable {



	public xConsole(final OutputStream out) {
		super(out, true);
	}



	public abstract xCommandProcessor getProcessor();
	public abstract xCommandProcessor setProcessor(final xCommandProcessor processor);

	public abstract String getPrompt();
	public abstract String setPrompt(final String prompt);

	public abstract char getMask();
	public abstract char setMask(final char mask);



}
