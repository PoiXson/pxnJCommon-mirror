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



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void publish(final String line) {
		this.getPublishLock();
		try {
			if (Utils.isEmpty(line)) {
				this.out.println();
			} else {
				this.out.println( ShellUtils.RenderAnsi(line) );
			}
			this.out.flush();
		} finally {
			this.releasePublishLock();
		}
	}
	@Override
	public void publish(final String[] lines) {
		this.getPublishLock();
		try {
			if (Utils.isEmpty(lines)) {
				this.out.println();
			} else
			// single line
			if (lines.length == 1) {
				this.out.println( ShellUtils.RenderAnsi(lines[0]) );
			// multiple lines
			} else {
				for (final String line : lines) {
					this.out.println( ShellUtils.RenderAnsi(line) );
				}
			}
			this.out.flush();
		} finally {
			this.releasePublishLock();
		}
	}



	@Override
	public void flush() {
		this.getPublishLock();
		try {
			this.out.flush();
			this.err.flush();
		} finally {
			this.releasePublishLock();
		}
	}
	@Override
	public void clearScreen() {
	}
	@Override
	public void beep() {
	}



	// -------------------------------------------------------------------------------
	// formatter



//TODO



}
