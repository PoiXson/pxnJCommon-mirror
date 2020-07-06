package com.poixson.logger.handlers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.jline.builtins.SystemRegistry;
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
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.utils.ShellUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xLogHandler_ConsolePrompt extends xLogHandler_Console implements xStartable, Runnable {
	protected static final String THREAD_NAME = "Console-Input";

	protected final AtomicReference<String>  prompt = new AtomicReference<String>(null);
	protected final AtomicReference<Character> mask = new AtomicReference<Character>(null);

	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	protected final AtomicReference<Thread> promptThread = new AtomicReference<Thread>(null);

	protected final AtomicReference<LineReader> reader = new AtomicReference<LineReader>(null);
	protected final AtomicReference<Terminal> terminal = new AtomicReference<Terminal>(null);
	protected final AtomicReference<History>   history = new AtomicReference<History>(null);

	protected final AtomicReference<Runnable> hookClose = new AtomicReference<Runnable>(null);



	public xLogHandler_ConsolePrompt() {
		super();
		final LineReader reader = this.getReader();
		reader.setVariable(LineReader.HISTORY_FILE, new File(ShellDefines.HISTORY_FILE));
	}



	@Override
	public void start() {
		if (this.running.get()) return;
		this.stopping.set(false);
		// new thread
		if (this.promptThread.get() == null) {
			final Thread thread = new Thread(this);
			thread.setName(THREAD_NAME);
			thread.setDaemon(true);
			this.promptThread.compareAndSet(null, thread);
		}
		// start thread
		{
			final Thread thread = this.promptThread.get();
			thread.start();
			ThreadUtils.Sleep(20L);
		}
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		final Thread thread = this.promptThread.get();
		if (thread != null) {
			try {
				thread.interrupt();
			} catch (Exception ignore) {}
			try {
				thread.notifyAll();
			} catch (Exception ignore) {}
			ThreadUtils.Sleep(20L);
		}
	}



	// prompt thread
	@Override
	public void run() {
		if (this.stopping.get()) return;
		if (!this.running.compareAndSet(false, true))
			throw new RuntimeException("Console prompt input thread already running");
		final xLog log = this.log();
		log.fine("Starting console input thread..");
		final LineReader read = this.getReader();
		final Thread thread = this.promptThread.get();
		READER_LOOP:
		while (true) {
			if (this.stopping.get())
				break READER_LOOP;
			if (thread.isInterrupted())
				break READER_LOOP;
			final String line;
			try {
//TODO
//this.setReadCool();
				// read console input
				line = read.readLine(
					this.getPrompt(),
					this.getMask()
				);
			} catch (UserInterruptException e) {
				break READER_LOOP;
			} catch (Exception e) {
//TODO
//this.resetReadCool();
				log.trace(e);
				ThreadUtils.Sleep(100L);
				continue READER_LOOP;
//TODO
//			} finally {
//this.resetReadCool();
			}
			// handle console input
			if (Utils.notEmpty(line)) {
//TODO: remove this
log.publish("GOT INPUT: "+line);
			}
		} // end READER_LOOP
		this.running.set(false);
		log.fine("Stopped console prompt input thread");
		this.flush();
		this.saveHistory();
		{
			final SystemRegistry reg = SystemRegistry.get();
			if (reg != null)
				reg.close();
		}
		// run hook: close
		{
			final Runnable hook = this.hookClose.getAndSet(null);
			if (hook != null) {
				try {
					hook.run();
				} catch (Exception e) {
					log.trace(e);
				}
			}
		}
	}



	@Override
	public boolean isRunning() {
		return this.running.get();
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	// ------------------------------------------------------------------------------- //
	// hooks



//TODO: use xHandler
	public void setHook_Close(final Runnable run) {
		this.hookClose.set(run);
	}



	// ------------------------------------------------------------------------------- //
	// publish



	@Override
	public void publish(final String line) {
		this.getPublishLock();
		try {
			if (Utils.isEmpty(line)) {
				this.out.println();
			} else {
				final int promptLength = this.getPromptLineLength();
				this.out.println(
					(new StringBuilder())
						.append('\r')
						.append(ShellUtils.RenderAnsi(line))
						.append(StringUtils.Repeat(promptLength, ' '))
						.toString()
				);
			}
			this.redraw();
		} finally {
			this.releasePublishLock();
		}
	}



	public void redraw() {
		final LineReaderImpl read = this.getReaderImpl();
		if (read != null) {
			if (read.isReading()) {
				read.redrawLine();
				read.redisplay();
			}
		}
	}
	public int getPromptLineLength() {
		final LineReader read = this.reader.get();
		if (read == null)
			return 0;
		final int promptLen = this.getPrompt().length();
		final int bufferLen = read.getBuffer().length();
		return promptLen + bufferLen;
	}



	@Override
	public void flush() {
		this.getPublishLock();
		try {
			final Terminal term = this.terminal.get();
			if (term == null) {
				this.out.flush();
				this.err.flush();
			} else {
				term.flush();
			}
		} finally {
			this.releasePublishLock();
		}
	}
	@Override
	public void clearScreen() {
		this.getReaderImpl().clearScreen();
	}
	@Override
	public void beep() {
		this.getReaderImpl().beep();
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getPrompt() {
		final String prompt = this.prompt.get();
		if (prompt == null)
			return ShellDefines.DEFAULT_PROMPT;
		return prompt;
	}
	public void setPrompt(final String prompt) {
		this.prompt.set(prompt);
	}



	public Character getMask() {
		return this.mask.get();
	}
	public void setMask(final char chr) {
		if (chr == 0) {
			this.mask.set(null);
		} else {
			this.mask.set(Character.valueOf(chr));
		}
	}
	public void setMask(final Character chr) {
		this.mask.set(chr);
	}



	// ------------------------------------------------------------------------------- //
	// jLine



	public LineReaderImpl getReaderImpl() {
		return (LineReaderImpl) this.getReader();
	}
	public LineReader getReader() {
		if (this.reader.get() == null) {
			synchronized (this.reader) {
				if (this.reader.get() == null) {
					final Terminal term = this.getTerminal();
					final LineReader read =
						LineReaderBuilder.builder()
							.terminal(term)
							.history(this.getHistory())
							.build();
					if (this.reader.compareAndSet(null, read)) {
						// audible or visible
						if (ShellDefines.BELL_ENABLED) {
							read.setVariable(LineReader.BELL_STYLE, "audible");
						} else {
							read.setVariable(LineReader.BELL_STYLE, "visible");
						}
						// load history
						this.getHistory();
						return read;
					}
				}
			}
		}
		return this.reader.get();
	}
	public Terminal getTerminal() {
		if (this.terminal.get() == null) {
			synchronized (this.terminal) {
				if (this.terminal.get() == null) {
					try {
						final Terminal term =
							TerminalBuilder.builder()
								.system(true)
								.streams(this.in, this.out)
								.build();
						if (this.terminal.compareAndSet(null, term))
							return term;
					} catch (IOException e) {
						throw new IORuntimeException(e);
					}
				}
			}
		}
		return this.terminal.get();
	}



	public History getHistory() {
		if (this.history.get() == null) {
			final History history = new DefaultHistory();
			if (this.history.compareAndSet(null, history))
				return history;
		}
		return this.history.get();
	}
	public void saveHistory() {
		final History history = this.history.get();
		if (history != null) {
			try {
				history.save();
			} catch (IOException e) {
				this.log().trace(e);
			}
		}
	}



	// ------------------------------------------------------------------------------- //
	// logger



	public xLog log() {
		return xLogRoot.Get();
	}



}
