package com.poixson.logger.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.logger.records.xLogRecord_Msg;


public class xLogOutputStream extends OutputStream {

	private final xLog   log;
	private final xLevel level;

	private AtomicReference<StringBuilder> buffer = new AtomicReference<StringBuilder>(new StringBuilder());



	public xLogOutputStream() {
		this(null, null);
	}
	public xLogOutputStream(final xLog outputLog) {
		this(outputLog, null);
	}
	public xLogOutputStream(final xLevel printLevel) {
		this(null, printLevel);
	}
	public xLogOutputStream(final xLog outputLog, final xLevel printLevel) {
		super();
		if (outputLog == null) throw new RequiredArgumentException("outputLog");
		this.log   = outputLog;
		this.level = printLevel;
	}



	@Override
	public void write(final int b) throws IOException {
		if (this.log == null)
			return;
		if (b == '\r')
			return;
		// flush buffer
		if (b == '\n') {
			this.dump();
			return;
		}
		// append to buffer
		this.buffer.get()
			.append( (char)b );
	}
	@Override
	public void write(final byte b[]) throws IOException {
		write(b, 0, b.length);
	}
	@Override
	public void write(final byte bytes[], final int off,
			final int len) throws IOException {
		if (bytes == null) throw new NullPointerException();
		if (off < 0)                      throw new IndexOutOfBoundsException();
		if (off > bytes.length)           throw new IndexOutOfBoundsException();
		if (len < 0)                      throw new IndexOutOfBoundsException();
		if ( (off + len) > bytes.length ) throw new IndexOutOfBoundsException();
		if ( (off + len) < 0)             throw new IndexOutOfBoundsException();
		if (this.log == null) return;
		if (len == 0)         return;
		for (int i=0; i<len; i++) {
			final byte b = bytes[ off+i ];
			if (b == '\r')
				continue;
			// flush buffer
			if (b == '\n') {
				this.dump();
				continue;
			}
			// append to buffer
			this.buffer.get()
				.append( (char) b );
		}
	}



	public void dump() {
		this.log
			.publish(
				new xLogRecord_Msg(
					this.log,
					this.level,
					this.buffer.toString(),
					new Object[0]
				)
			);
		// reset buffer
		{
			final StringBuilder buf = this.buffer.get();
			if (buf.length() > 40) {
				this.buffer.set( new StringBuilder() );
			} else {
				buf.setLength(0);
			}
		}
	}



	@Override
	public void flush() throws IOException {
		this.log.flush();
	}



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return this.log;
	}



}
