package com.poixson.logger.handlers;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.records.xLogRecord;
import com.poixson.shell.xConsole;
import com.poixson.tools.StdIO;
import com.poixson.utils.ShellUtils;


public class xLogHandler_StdIO extends xLogHandler {

	protected final AtomicReference<PrintStream> out = new AtomicReference<PrintStream>(null);
	protected final AtomicReference<PrintStream> err = new AtomicReference<PrintStream>(null);
	protected final AtomicReference<InputStream> in  = new AtomicReference<InputStream>(null);



	public xLogHandler_StdIO() {
		super();
		this.out.set(StdIO.OriginalOut());
		this.err.set(StdIO.OriginalErr());
		this.in .set(StdIO.OriginalIn());
	}
	// with console prompt
	public xLogHandler_StdIO(final xConsole console) {
		super();
		this.out.set(console);
		this.err.set(console);
		this.in .set(StdIO.OriginalIn());
	}



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void publish(final xLogRecord record) {
		this.getPublishLock();
		try {
			final PrintStream out = this.getOutOrDefault();
			final String msg = this.format(record);
			if (IsEmpty(msg) || "\n".equals(msg)) {
				out.println();
			} else {
				final String[] lines = msg.split("\n");
				for (final String line : lines)
					out.println( ShellUtils.RenderAnsi(line) );
				out.flush();
			}
		} finally {
			this.releasePublishLock();
		}
	}



	// -------------------------------------------------------------------------------
	// stdio



	public PrintStream getOutOrDefault() {
		final PrintStream out = this.getOut();
		return (out==null ? StdIO.OriginalOut() : out);
	}
	public PrintStream getOut() {
		return this.out.get();
	}
	public PrintStream setOut(final PrintStream out) {
		return this.out.getAndSet(out);
	}



	public PrintStream getErrOrDefault() {
		final PrintStream err = this.getErr();
		return (err==null ? StdIO.OriginalErr() : err);
	}
	public PrintStream getErr() {
		return this.err.get();
	}
	public PrintStream setErr(final PrintStream err) {
		return this.err.getAndSet(err);
	}



}

//	public InputStream getInOrDefault() {
//		final InputStream in = this.getIn();
//		return (in==null ? StdIO.OriginalIn() : in);
//	}
//	public InputStream getIn() {
//		return this.in.get();
//	}
//	public InputStream setIn(final InputStream in) {
//		return this.in.getAndSet(in);
//	}

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
