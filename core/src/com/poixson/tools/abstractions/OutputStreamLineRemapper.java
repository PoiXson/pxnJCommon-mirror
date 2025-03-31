/*
package com.poixson.tools.abstractions;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


public abstract class OutputStreamLineRemapper extends OutputStream {

	private final StringBuilder buffer = new StringBuilder();



	public static PrintStream toPrintStream(final OutputStream outstream) {
		return new PrintStream(outstream, false);
	}

	public OutputStreamLineRemapper() {
		super();
	}



	public abstract void print(final String line);



	@Override
	public void write(final int chr) throws IOException {
		if (chr == '\r') return;
		if (chr == '\n') this.flush();
		else this.buffer.append( (char)chr );
	}



	@Override
	public void flush() {
		final String line = this.buffer.toString();
		this.buffer.setLength(0);
		this.print(line);
	}



	@Override
	public void close() {
		this.flush();
	}



}
*/
