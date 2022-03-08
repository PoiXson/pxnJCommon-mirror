package com.poixson.logger.handlers;

import java.io.File;


public class xLogHandler_File extends xLogHandler {

	public final File file;



	public xLogHandler_File(final File file) {
		super();
		this.file = file;
	}



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void publish(final String[] lines) {
//TODO
throw new RuntimeException("UNFINISHED CODE");
	}



	@Override
	public void flush() {
//TODO
throw new RuntimeException("UNFINISHED CODE");
	}
	@Override
	public void clearScreen() {
//TODO: sync
		this.publish();
		this.publish();
		this.publish();
	}
	@Override
	public void beep() {
	}



}
