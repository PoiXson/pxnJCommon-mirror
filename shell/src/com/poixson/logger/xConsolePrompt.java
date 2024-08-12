package com.poixson.logger;

import static com.poixson.ShellDefines.DEFAULT_PROMPT;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.poixson.ShellDefines;
import com.poixson.logger.handlers.xLogHandler_ConsolePrompt;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.abstractions.xFailable;
import com.poixson.tools.commands.xCommandProcessor;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ThreadUtils;


public class xConsolePrompt extends xConsole implements xFailable {
	protected static final String THREAD_NAME = "Console-Input";

	protected final AtomicReference<xCommandProcessor> processor = new AtomicReference<xCommandProcessor>(null);
	protected final AtomicReference<String>  prompt = new AtomicReference<String>(null);
	protected final AtomicReference<Character> mask = new AtomicReference<Character>(null);

	protected final AtomicReference<Terminal>   terminal = new AtomicReference<Terminal>(null);
	protected final AtomicReference<LineReader> reader   = new AtomicReference<LineReader>(null);
	protected final AtomicReference<History>    history  = new AtomicReference<History>(null);
	protected final xLogHandler_ConsolePrompt handler;

	protected final InputStream in;

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);

	protected final AtomicBoolean stopping = new AtomicBoolean(false);
	protected final CopyOnWriteArraySet<Runnable> listeners_close = new CopyOnWriteArraySet<Runnable>();

	protected final AtomicReference<Throwable> failure = new AtomicReference<Throwable>(null);



	public xConsolePrompt() {
		this(null, null, null);
	}
	protected xConsolePrompt(final OutputStream out, final InputStream in,
			final xLogHandler_ConsolePrompt handler) {
		super(out==null ? StdIO.OriginalOut() : out);
		StdIO.Init();
		this.in      = (in==null ? StdIO.OriginalIn() : in);
		this.handler = (handler==null ? new xLogHandler_ConsolePrompt(this) : handler);
		Keeper.add(this);
	}
	public void unload() {
		Keeper.remove(this);
	}



	// start console input thread
	@Override
	public void start() {
		if (this.isFailed()) return;
		if (this.stopping.get())
			throw new IllegalStateException("Cannot start console prompt, already stopped");
		if (this.thread.get() == null) {
			// new thread
			final Thread thread = new Thread(this);
			if (this.thread.compareAndSet(null, thread)) {
				thread.setName(THREAD_NAME);
				thread.setDaemon(true);
				thread.start();
				ThreadUtils.Sleep(10L);
			}
		}
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
		if (this.isFailed())   return;
		if (this.isStopping()) return;
		this.log().fine("Console prompt started..");
		{
			final PrintStream hold_out = System.out;
			final InputStream hold_in  = System.in;
			synchronized (this.terminal) {
				try {
					System.setOut(StdIO.OriginalOut());
					System.setIn (StdIO.OriginalIn() );
					// jline terminal
					final Terminal term = TerminalBuilder.builder()
						.system(true)
						.streams(
							StdIO.OriginalIn(),
							StdIO.OriginalOut()
						)
						.build();
					if (term == null) {
						this.fail(new RuntimeException("Failed to create jline terminal instance"));
						return;
					}
					if ("dumb".equals(term.getType())) {
						this.fail(new RuntimeException("Detected a dumb terminal"));
						return;
					}
					if (!this.terminal.compareAndSet(null, term)) {
						this.fail(new RuntimeException("Invalid terminal state, already set"));
						return;
					}
					this.log().fine("Terminal: "+term.getType());
					if (this.isFailed()) return;
					// jline reader
					final LineReader reader =
						LineReaderBuilder.builder()
							.terminal(term)
							.build();
					if (reader == null) {
						this.fail(new RuntimeException("Failed to create jline reader instance"));
						return;
					}
					if (!this.reader.compareAndSet(null, reader)) {
						this.fail(new RuntimeException("Invalid reader state, already set"));
						return;
					}
					if (this.isFailed()) return;
					//TODO
					//( xShellDefines.BELL_ENABLED ? "audible" : "visible" )
					reader.setVariable(LineReader.BELL_STYLE, "visible");
					// command history
					reader.setVariable(LineReader.HISTORY_FILE, new File(ShellDefines.HISTORY_FILE));
					final String file_history = FileUtils.MergePaths(",", ShellDefines.HISTORY_FILE);
					reader.setVariable(LineReader.HISTORY_FILE, file_history);
					reader.setVariable(LineReader.HISTORY_SIZE, ShellDefines.HISTORY_SIZE);
					final History history = new DefaultHistory(reader);
					if (!this.history.compareAndSet(null, history)) {
						this.fail(new RuntimeException("Invalid command history state, already set"));
						return;
					}
					AnsiConsole.systemInstall();
				} catch (Exception e) {
					this.fail(e);
					return;
				} finally {
					System.setOut(hold_out);
					System.setIn( hold_in );
				}
			} // end sync terminal
		}
		if (this.isFailed()) return;
		final Thread thread = Thread.currentThread();
		int count_errors = 0;
		LOOP_READER:
		while (true) {
			if (this.isFailed())        break LOOP_READER;
			if (this.isStopping())      break LOOP_READER;
			if (thread.isInterrupted()) break LOOP_READER;
			final String line;
			try {
				// read console input
				line = this.getReader().readLine(
					this.getPrompt(),
					this.mask.get()
				);
				// handle line
				if (!IsEmpty(line)) {
					final xCommandProcessor processor = this.getProcessor();
					if (processor == null) {
						this.log().warning("No command processor to handle command: %s", line);
						continue LOOP_READER;
					}
					final boolean result = processor.process(line);
					if (!result)
						this.log().warning("Unknown command: %s", line);
				}
				count_errors = 0;
			} catch (UserInterruptException ignore) {
				break LOOP_READER;
			} catch (Exception e) {
				this.log().trace(e);
				if (++count_errors > 5) {
					this.log().trace(new RuntimeException("Too many errors"));
					break LOOP_READER;
				}
				ThreadUtils.Sleep(100L);
			}
		} // end LOOP_READER
		this.saveHistory();
		this.log().fine("Console prompt stopped");
		this.thread.set(null);
		this.stop();
		StdIO.OriginalOut().println();
		StdIO.OriginalOut().flush();
		this.flush();
		// close listeners
		for (final Runnable listener : this.listeners_close) {
			try {
				listener.run();
			} catch (Exception e) {
				this.log().trace(e);
			}
		}
	}



	@Override
	public boolean isRunning() {
		if (this.isFailed()) return false;
		return (this.thread.get() != null);
	}
	@Override
	public boolean isStopping() {
		if (this.isFailed()) return true;
		return this.stopping.get();
	}



	// -------------------------------------------------------------------------------



	public xLogHandler_ConsolePrompt getHandler() {
		return this.handler;
	}



	@Override
	public String getPrompt() {
		final String prompt = this.prompt.get();
		return (IsEmpty(prompt) ? DEFAULT_PROMPT : prompt);
	}
	@Override
	public String setPrompt(final String prompt) {
		final String previous = this.prompt.getAndSet(prompt);
//TODO: this doesn't update properly
		this.handler.redraw_prompt();
		return previous;
	}



	@Override
	public char getMask() {
		final Character mask = this.mask.get();
		return (mask==null ? null : mask.charValue());
	}
	@Override
	public char setMask(final char mask) {
		final Character previous = this.mask.getAndSet(Character.valueOf(mask));
		return (previous==null ? null : previous.charValue());
	}



	@Override
	public xCommandProcessor getProcessor() {
		return this.processor.get();
	}
	@Override
	public xCommandProcessor setProcessor(final xCommandProcessor processor) {
		return this.processor.getAndSet(processor);
	}



	public Terminal getTerminal() {
		return this.terminal.get();
	}
	public LineReader getReader() {
		return this.reader.get();
	}



	public void saveHistory() {
		if (this.isFailed()) return;
		final History hist = this.history.get();
		if (hist != null) {
			try {
				hist.save();
			} catch (IOException e) {
				xLog.Get().trace(e);
			}
		}
	}



	// -------------------------------------------------------------------------------
	// listeners



	public void addCloseListener(final Runnable run) {
		this.listeners_close.add(run);
	}



	// -------------------------------------------------------------------------------
	// failure



	@Override
	public boolean fail(final Throwable e) {
		if (this.failure.compareAndSet(null, e)) {
			this.onFailure();
			return true;
		}
		return false;
	}
	@Override
	public boolean fail(final String msg, final Object...args) {
		return this.fail(new RuntimeException(String.format(msg, args)));
	}

	@Override
	public boolean isFailed() {
		return (this.failure.get() != null);
	}

	@Override
	public void onFailure() {
		final Throwable e = this.failure.get();
		if (e != null)
			this.log().trace(e);
		this.stop();
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
