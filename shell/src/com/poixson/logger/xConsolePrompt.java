package com.poixson.logger;

import static com.poixson.ShellDefines.DEFAULT_PROMPT;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.poixson.ShellDefines;
import com.poixson.exceptions.IORuntimeException;
import com.poixson.logger.handlers.xLogHandler_ConsolePrompt;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.commands.xCommandProcessor;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ThreadUtils;


public class xConsolePrompt extends xConsole {
	protected static final String THREAD_NAME = "Console-Input";

	protected final AtomicReference<xCommandProcessor> processor = new AtomicReference<xCommandProcessor>(null);
	protected final AtomicReference<String>  prompt = new AtomicReference<String>(null);
	protected final AtomicReference<Character> mask = new AtomicReference<Character>(null);

	protected final AtomicReference<Terminal>   terminal = new AtomicReference<Terminal>(null);
	protected final AtomicReference<LineReader> reader   = new AtomicReference<LineReader>(null);
	protected final AtomicReference<History>    history  = new AtomicReference<History>(null);

	protected final OutputStream out;
	protected final InputStream  in;

	protected final xLogHandler_ConsolePrompt handler;
	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);

	protected final AtomicBoolean stopping = new AtomicBoolean(false);



	public xConsolePrompt() {
		this(StdIO.OriginalOut(), StdIO.OriginalIn());
	}
	protected xConsolePrompt(final OutputStream out, final InputStream in) {
		super(out);
		this.out = out;
		this.in  = in;
		this.handler = new xLogHandler_ConsolePrompt(this);
		Keeper.add(this);
	}
	public void unload() {
		Keeper.remove(this);
	}



	@Override
	public void start() {
		// start console input thread
		if (this.thread.get() == null) {
			final Thread thread = new Thread(this);
			if (this.thread.compareAndSet(null, thread)) {
				thread.setName(THREAD_NAME);
				thread.setDaemon(true);
				thread.start();
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
			ThreadUtils.Sleep(50L);
		}
	}



	@Override
	public void run() {
		if (this.isStopping()) return;
		this.log().fine("Console prompt started..");
		final LineReader reader = getReader();
		final Thread thread = Thread.currentThread();
		READER_LOOP:
		while (true) {
			if (this.isStopping())      break READER_LOOP;
			if (thread.isInterrupted()) break READER_LOOP;
			final String line;
			try {
				// read console input
				line = reader.readLine(
					this.getPrompt(),
					null
//					this.getPrompt(),
//					this.getMask()
				);
				// handle line
				if (!IsEmpty(line)) {
					final xCommandProcessor processor = this.getProcessor();
					if (processor == null) {
						this.log().warning("No command processor to handle command: %s", line);
						continue READER_LOOP;
					}
					final boolean result = processor.process(line);
					if (!result)
						log().warning("Unknown command: %s", line);
				}
			} catch (UserInterruptException ignore) {
				break READER_LOOP;
			} catch (Exception e) {
				this.log().trace(e);
				try {
					Thread.sleep(100L);
				} catch (InterruptedException ignore) {
					break READER_LOOP;
				}
				continue READER_LOOP;
			}
		} // end READER_LOOP
		// save command history
		{
			final History history = xConsolePrompt.history.get();
			if (history != null) {
				if (history instanceof DefaultHistory) {
					try {
						((DefaultHistory) history)
							.save();
					} catch (IOException e) {
						this.log().trace(e);
					}
				}
			}
		}
		this.log().fine("Console prompt stopped");
		this.thread.set(null);
		this.stop();
		StdIO.OriginalOut().println();
		StdIO.OriginalOut().flush();
//TODO: how can we do this better?
//		if (!this.app.isStopping())
//			this.app.stop();
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



	public xLogHandler_ConsolePrompt getHandler() {
		return this.handler;
	}



	@Override
	public String getPrompt() {
		final String prompt = this.prompt.get();
		return (
			IsEmpty(prompt)
			? DEFAULT_PROMPT
			: prompt
		);
	}
	@Override
	public String setPrompt(final String prompt) {
		return this.prompt.getAndSet(prompt);
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
	// JLine



	public static Terminal getTerminal() {
		{
			final Terminal term = terminal.get();
			if (term != null)
				return term;
		}
		synchronized (terminal) {
			final Terminal term;
			try {
				final PrintStream hold_out = System.out;
				final InputStream hold_in  = System.in;
				System.setOut(StdIO.OriginalOut());
				System.setIn (StdIO.OriginalIn() );
				term = TerminalBuilder.builder()
					.system(true)
					.streams(
						StdIO.OriginalIn(),
						StdIO.OriginalOut()
					)
					.build();
				System.setOut(hold_out);
				System.setIn( hold_in );
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
			if (!terminal.compareAndSet(null, term))
				terminal.get();
			AnsiConsole.systemInstall();
			return term;
		}
	}
	public static LineReader getReader() {
		{
			final LineReader read = reader.get();
			if (read != null)
				return read;
		}
		synchronized (reader) {
			final Terminal term = getTerminal();
			final LineReader read =
				LineReaderBuilder.builder()
					.terminal(term)
					.build();
			if ( ! reader.compareAndSet(null, read) )
				return reader.get();
			read.setVariable(
				LineReader.BELL_STYLE,
//TODO
				"visible"
//				( xShellDefines.BELL_ENABLED ? "audible" : "visible" )
			);
			getHistory();
			return read;
		}
	}
	public static History getHistory() {
		{
			final History hist = history.get();
			if (hist != null)
				return hist;
		}
		{
			final String historyFile = FileUtils.MergePaths(",", ShellDefines.HISTORY_FILE);
			final LineReader read = getReader();
			read.setVariable(LineReader.HISTORY_FILE, historyFile);
			read.setVariable(LineReader.HISTORY_SIZE, ShellDefines.HISTORY_SIZE);
			final History hist = new DefaultHistory(read);
			if ( ! history.compareAndSet(null, hist) )
				return history.get();
			return hist;
		}
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
/*
	protected static final AtomicReference<xAppSteps_Console> instance = new AtomicReference<xAppSteps_Console>(null);



	protected final AtomicBoolean is_reading = new AtomicBoolean(false);
	protected final AtomicReference<CoolDown> readCool = new AtomicReference<CoolDown>(null);



	public static xAppSteps_Console Get() {
		// existing instance
		{
			final xAppSteps_Console console = instance.get();
			if (console != null)
				return console;
		}
		// new instance
		{
			final xAppSteps_Console console = new xAppSteps_Console();
			if (instance.compareAndSet(null, console))
				return console;
			return instance.get();
		}
	}














	public boolean waitReadCool() {
		if ( ! this.isreading.get() )
			return false;
		if (this.readCool.get() == null)
			return true;
		while (true) {
			try {
				Thread.sleep(5L);
			} catch (InterruptedException ignore) {
				break;
			}
			final CoolDown cool = this.readCool.get();
			if (cool == null) break;
			if (cool.runAgain()) {
				this.readCool.set(null);
			}
		}
		return true;
	}
	public void setReadCool() {
		final CoolDown cool = CoolDown.getNew(20L);
		this.readCool.set(cool);
		this.isreading.set(true);
	}
	public void resetReadCool() {
		this.isreading.set(false);
		this.readCool.set(null);
	}






	// -------------------------------------------------------------------------------
	// publish to console



	@Override
	public void doPublish(final String line) {
		final Terminal   term = getTerminal();
		final LineReader read = getReader();
		final PrintWriter out = term.writer();
		{
			final boolean isread =
				this.waitReadCool();
			if (isread) {
				try {
					read.callWidget(LineReader.CLEAR);
				} catch (Exception ignore) {}
			}
		}
		out.println(line);
		{
			final boolean isread =
				this.waitReadCool();
			if (isread) {
				try {
					read.callWidget(LineReader.REDRAW_LINE);
					read.callWidget(LineReader.REDISPLAY);
				} catch (Exception ignore) {}
			}
		}
		out.flush();
	}



	@Override
	public void doClearScreen() {
		final boolean isread =
			this.waitReadCool();
		try {
			RETRY_LOOP:
			for (int i=0; i<5; i++) {
				if (this.stopping.get())
					break RETRY_LOOP;
				if (isread) {
					try {
						final Terminal   term = getTerminal();
						final LineReader read = getReader();
						read.callWidget(LineReader.CLEAR_SCREEN);
						term.writer().flush();
						break RETRY_LOOP;
					} catch (Exception ignore) {}
					if (this.stopping.get())
						break RETRY_LOOP;
					ThreadUtils.Sleep(20L);
				} else {
					final PrintStream out = xVars.getOriginalOut();
					out.print(
						Ansi.ansi()
							.eraseScreen()
							.cursor(0, 0)
							.toString()
					);
					out.flush();
					break RETRY_LOOP;
				}
			} // end RETRY_LOOP
		} catch (Exception ignore) {}
	}
	@Override
	public void doFlush() {
		this.waitReadCool();
		try {
			getTerminal().flush();
		} catch (Exception ignore) {}
	}
	@Override
	public void doBeep() {
		final boolean isread = this.waitReadCool();
		try {
			if (isread) {
				getReader().callWidget(LineReader.BEEP);
			} else {
				getTerminal().puts(Capability.bell);
			}
		} catch (Exception ignore) {}
	}



	// -------------------------------------------------------------------------------
	// settings



	// prompt
	public String getPrompt() {
		return StringUtils.ForceStarts("\r", xShellDefines.DEFAULT_PROMPT);
	}
	@Override
	public void setPrompt(final String prompt) {
//TODO:
throw new UnsupportedOperationException("Unfinished");
	}



	// mask
	public Character getMask() {
		return null;
	}
	@Override
	public void setMask(final Character mask) {
//TODO:
throw new UnsupportedOperationException("Unfinished");
	}
*/
