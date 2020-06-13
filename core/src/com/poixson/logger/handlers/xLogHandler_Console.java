package com.poixson.logger.handlers;

import java.io.InputStream;
import java.io.PrintStream;

import com.poixson.tools.StdIO;
import com.poixson.utils.ShellUtils;
import com.poixson.utils.Utils;


public class xLogHandler_Console extends xLogHandler {

	protected final PrintStream out;
	protected final PrintStream err;
	protected final InputStream in;



	public xLogHandler_Console() {
		super();
		this.out = StdIO.OriginalOut;
		this.err = StdIO.OriginalErr;
		this.in  = StdIO.OriginalIn;
	}



	// ------------------------------------------------------------------------------- //
	// publish



	@Override
	public void publish(final String line) {
		if (Utils.isEmpty(line)) {
			this.out.println();
		} else {
			this.out.println( ShellUtils.RenderAnsi(line) );
		}
	}



	@Override
	public void flush() {
		this.out.flush();
		this.err.flush();
	}
	@Override
	public void clearScreen() {
	}
	@Override
	public void beep() {
	}



}
