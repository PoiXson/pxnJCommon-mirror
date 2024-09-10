package com.poixson.shell;

import static com.poixson.utils.ShellUtils.RawTerminal;
import static com.poixson.utils.ShellUtils.RestoreTerminal;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.handlers.xLogHandler_StdIO;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.commands.xCommandProcessor;
import com.poixson.utils.ThreadUtils;


public class xConsolePrompt extends xConsole {
	public static final boolean DEBUG_UNKNOWN_KEYS = false;

	public static final String THREAD_NAME = "prompt";
	public static final String DEFAULT_PROMPT = " #>";
	public static final String HISTORY_FILE = "history.txt";
	public static final int    HISTORY_SIZE = 1000;

	protected final AtomicReference<String>  prompt = new AtomicReference<String>(null);
	protected final AtomicReference<Character> mask = new AtomicReference<Character>(null);

	protected final StringBuilder buffer_in;
	protected final StringBuilder buffer_out;
	protected final Object lock = new Object();

	protected final InputStream in;
	protected final boolean dumb;

	protected final AtomicReference<xCommandProcessor> processor = new AtomicReference<xCommandProcessor>(null);

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);



	public xConsolePrompt() {
		this(
			StdIO.OriginalOut(),
			StdIO.OriginalIn(),
			null
		);
	}
	public xConsolePrompt(final PrintStream out, final InputStream in, final String prompt) {
		super(out);
		this.in = in;
		this.prompt.set(prompt);
		this.buffer_in  = new StringBuilder();
		this.buffer_out = new StringBuilder();
		this.dumb = (System.console() == null);
	}



	@Override
	public void start() {
		if (this.dumb) return;
		if (this.stopping.get())
			throw new IllegalStateException("Prompt thread already stopped");
		final Thread thread = new Thread() {
			@Override
			public void run() {
				xConsolePrompt.this.run();
			}
		};
		thread.setName(THREAD_NAME);
		thread.setDaemon(true);
		if (!this.thread.compareAndSet(null, thread))
			throw new IllegalStateException("Prompt thread already started");
		thread.start();
		ThreadUtils.Sleep(50L);
	}

	@Override
	public void stop() {
		this.stopping.set(true);
		final Thread thread = this.thread.get();
		if (thread != null) {
			try {
				thread.interrupt();
			} catch (Exception ignore) {}
			try {
				thread.notifyAll();
			} catch (Exception ignore) {}
			ThreadUtils.Sleep(10L);
		}
	}



	// prompt thread
	@Override
	public void run() {
		if (this.dumb) return;
		if (!Thread.currentThread().equals(this.thread.get())) {
			this.stopping.set(true);
			(new IllegalStateException("Run called from wrong thread")).printStackTrace(this);
			return;
		}
		if (this.getProcessor() == null) {
			this.stopping.set(true);
			(new RuntimeException("Command processor not set")).printStackTrace(this);
			return;
		}
		try {
			RawTerminal();
		} catch (IOException e) {
			this.stopping.set(true);
			e.printStackTrace(this);
			return;
		} catch (InterruptedException e) {
			this.stopping.set(true);
			e.printStackTrace(this);
			return;
		}
		final xLogHandler handler = xLog.Get().getHandler(xLogHandler_StdIO.class);
		if (handler != null)
			((xLogHandler_StdIO)handler).setOut(this);
		Keeper.add(this);
		Runtime.getRuntime().addShutdownHook(
			new Thread() {
				@Override
				public void run() {
					final PrintStream out = new PrintStream(xConsolePrompt.this.out, true);
					try {
						RestoreTerminal();
					} catch (IOException e) {
						e.printStackTrace(out);
					} catch (InterruptedException e) {
						e.printStackTrace(out);
					}
					out.println();
				}
			}
		);
		try {
			this.log().fine("Console prompt started..");
			this.drawPrompt();
			LOOP_PROMPT:
			while (true) {
				if (this.stopping.get())
					break LOOP_PROMPT;
				final int chr = this.in.read();
				SWITCH_CHAR:
				switch (chr) {
//TODO
//				// ctrl + a
//				case 1:
//				// esc
//				case 27:
				// ctrl + c
				case 3:
					System.exit(1);
					break LOOP_PROMPT;
				// backspace
				case 127: {
					final int len = this.buffer_in.length();
					if (len > 0) {
						this.buffer_in.setLength(len-1);
						this.drawPrompt();
					}
					break SWITCH_CHAR;
				}
				// enter
				case 13: {
					synchronized (this.lock) {
						this.out.write("\r\n".getBytes());
						this.drawPrompt();
					}
					final String cmd = this.buffer_in.toString();
					this.buffer_in.setLength(0);
					if (!IsEmpty(cmd)) {
						final xCommandProcessor processor = this.getProcessor();
						if (processor == null) {
							(new RuntimeException("Command processor not set")).printStackTrace(this);
							break LOOP_PROMPT;
						}
						if (!processor.process(cmd)) {
							if (this.hasMask()) this.println("Unknown command: <masked>");
							else                this.println("Unknown command: "+cmd);
						}
					}
					break SWITCH_CHAR;
				}
				default: {
					if (chr > 31 && chr < 127) {
						this.buffer_in.append( (char)chr );
						final Character mask = this.getMask();
						if (mask == null) this.print( (char)chr );
						else              this.print(mask.charValue());
					} else
					if (DEBUG_UNKNOWN_KEYS) {
						this.println(chr);
					}
					break SWITCH_CHAR;
				}
				} // end SWITCH_CHAR
			} // end LOOP_PROMPT
		} catch (IOException e) {
			e.printStackTrace(this);
		} finally {
			this.log().fine("Console prompt stopped");
			Keeper.remove(this);
			try {
				RestoreTerminal();
			} catch (IOException e) {
				e.printStackTrace(this);
			} catch (InterruptedException e) {
				e.printStackTrace(this);
			}
		}
		this.stopping.set(true);
	}



	@Override
	public boolean isRunning() {
		return (this.thread.get() != null);
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	// -------------------------------------------------------------------------------



	@Override
	public void println(final String str) {
		synchronized (this.lock) {
			this.write(str.getBytes());
			this.write('\n');
		}
	}
	@Override
	public void write(byte[] b) {
		synchronized (this.lock) {
			final int len = b.length;
			for (int i=0; i<len; i++)
				this.write(b[i]);
		}
	}
	@Override
	public void write(final int b) {
		if (b == '\r') {
		} else
		if (b == '\n') {
			synchronized (this.lock) {
				try {
					this.clearLine();
					this.out.write(this.buffer_out.toString().getBytes());
					this.out.write('\r'); this.out.write('\n');
					this.drawPrompt();
					this.out.flush();
				} catch (IOException e) {
					e.printStackTrace(new PrintStream(this.out));
				}
				this.buffer_out.setLength(0);
			}
		} else {
			this.buffer_out.append( (char)b );
		}
	}



	// -------------------------------------------------------------------------------



	public void clearLine() {
		if (this.isRunning()) {
			synchronized (this.lock) {
				try {
					this.out.write('\r');
					final int len = this.buffer_in.length() + this.getPrompt().length() + 5;
					for (int i=0; i<len; i++)
						this.out.write(' ');
					this.out.write('\r');
				} catch (IOException e) {
					e.printStackTrace(new PrintStream(this.out));
				}
			}
		}
	}



	@Override
	public void drawPrompt() {
		synchronized (this.lock) {
			try {
				this.clearLine();
				this.out.write(this.getPrompt().getBytes());
				final Character mask = this.getMask();
				if (mask == null) {
					this.out.write(this.buffer_in.toString().getBytes());
				} else {
					final int len = this.buffer_in.length();
					for (int i=0; i<len; i++)
						this.out.write(mask.charValue());
				}
			} catch (IOException e) {
				e.printStackTrace(this);
			}
		}
	}



	@Override
	public String getPrompt() {
		final String prompt = this.prompt.get();
		return (IsEmpty(prompt) ? DEFAULT_PROMPT : prompt);
	}
	@Override
	public String setPrompt(final String prompt) {
		return this.prompt.getAndSet(prompt);
	}



	@Override
	public Character getMask() {
		return this.mask.get();
	}
	@Override
	public Character setMask(final Character chr) {
		return this.mask.getAndSet(chr);
	}
	@Override
	public boolean hasMask() {
		return (this.mask.get() != null);
	}



	@Override
	public xCommandProcessor getProcessor() {
		return this.processor.get();
	}
	@Override
	public xCommandProcessor setProcessor(final xCommandProcessor processor) {
		return this.processor.getAndSet(processor);
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log == null) this._log.set(null);
				else             return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get();
	}



}
