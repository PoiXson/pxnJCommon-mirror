package com.poixson.logger.handlers;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.InputStream;
import java.io.PrintStream;

import com.poixson.logger.xConsole;
import com.poixson.logger.xLogHandler;
import com.poixson.logger.records.xLogRecord;
import com.poixson.tools.StdIO;
import com.poixson.utils.ShellUtils;


public class xLogHandler_Console extends xLogHandler {

	protected final PrintStream out;
	protected final PrintStream err;
	protected final InputStream in;



	public xLogHandler_Console() {
		super();
		this.out = StdIO.OriginalOut();
		this.err = StdIO.OriginalErr();
		this.in  = StdIO.OriginalIn();
	}
	// with console prompt
	public xLogHandler_Console(final xConsole console) {
		super();
		this.out = console;
		this.err = console;
		this.in  = StdIO.OriginalIn();
	}



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void publish(final xLogRecord record) {
		this.getPublishLock();
		try {
			final String msg = this.format(record);
			if (IsEmpty(msg)) {
			} else
			if ("\n".equals(msg)) {
				this.out.println();
			} else {
				final String[] lines = msg.split("\n");
				for (final String line : lines)
					this.out.println( ShellUtils.RenderAnsi(line) );
				this.out.flush();
			}
		} finally {
			this.releasePublishLock();
		}
	}



//TODO
//	@Override
//	public void flush() {
//		this.getPublishLock();
//		try {
//			this.out.flush();
//			this.err.flush();
//		} finally {
//			this.releasePublishLock();
//		}
//	}
//	@Override
//	public void clearScreen() {
//	}
//	@Override
//	public void beep() {
//	}
//TODO: from xLogHandler_ConsolePrompt
//	@Override
//	public void flush() {
//		this.getPublishLock();
//		try {
//			final Terminal term = this.terminal.get();
//			if (term == null) {
//				this.out.flush();
//				this.err.flush();
//			} else {
//				term.flush();
//			}
//		} finally {
//			this.releasePublishLock();
//		}
//	}
//	@Override
//	public void clearScreen() {
//		this.getReaderImpl().clearScreen();
//	}
//	@Override
//	public void beep() {
//		this.getReaderImpl().beep();
//	}



}
