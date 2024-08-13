/*
package com.poixson.logger.tools;

import java.io.PrintStream;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;


// xLog print stream
public class xLogPrintStream extends PrintStream {

	private final xLog log;



	public xLogPrintStream() {
		this(null, null);
	}
	public xLogPrintStream(final xLog log) {
		this(log, null);
	}
	public xLogPrintStream(final xLevel printLevel) {
		this(null, printLevel);
	}
	public xLogPrintStream(final xLog log, final xLevel printLevel) {
		super(
			new xLogOutputStream(log, printLevel)
		);
		this.log = log;
	}



	@Override
	public void println() {
		if (this.log != null)
			this.log.publish();
	}



	@Override
	public void flush() {
		this.log.flush();
	}



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return this.log;
	}



}
*/
