package com.poixson.logger.handlers;

import static com.poixson.utils.Utils.IsEmpty;

import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;

import com.poixson.logger.xConsolePrompt;
import com.poixson.logger.records.xLogRecord;
import com.poixson.utils.ShellUtils;
import com.poixson.utils.StringUtils;


public class xLogHandler_ConsolePrompt extends xLogHandler_Console {

	protected final xConsolePrompt console;



	public xLogHandler_ConsolePrompt(final xConsolePrompt console) {
		super();
		this.console = console;
	}



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
				final int prompt_len = this.getPromptLineLength();
				this.out.print("\r");
				final String[] lines = msg.split("\n");
				for (final String line : lines)
					this.out.println( ShellUtils.RenderAnsi(line) + StringUtils.Repeat(prompt_len, ' ') );
				this.redraw_prompt();
				this.out.flush();
			}
		} finally {
			this.releasePublishLock();
		}
	}



	public void redraw_prompt() {
		final LineReaderImpl read = this.console.getReaderImpl();
		if (read != null) {
			if (read.isReading()) {
				read.redrawLine();
				read.redisplay();
			}
		}
	}



	protected int getPromptLineLength() {
		final LineReader read = this.console.getReader();
		final String prompt = this.console.getPrompt();
		if (read == null) return 0;
		final int len_prompt = prompt.length();
		final int len_buffer = read.getBuffer().length();
		return len_prompt + len_buffer;
	}



}
