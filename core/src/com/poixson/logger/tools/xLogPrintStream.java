package com.poixson.logger.tools;

import java.io.PrintStream;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;


public class xLogPrintStream extends PrintStream {

	protected final xLog log;



	public xLogPrintStream() {
		this(null, null);
	}
	public xLogPrintStream(final xLog log) {
		this(log, null);
	}
	public xLogPrintStream(final xLevel level) {
		this(null, level);
	}
	public xLogPrintStream(final xLog log, final xLevel level) {
		super(
			new xLogOutputStream(log, level)
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
