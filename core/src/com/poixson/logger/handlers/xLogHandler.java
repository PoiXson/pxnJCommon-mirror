package com.poixson.logger.handlers;

import com.poixson.logger.records.xLogRecord;
import com.poixson.utils.Utils;


public abstract class xLogHandler {



	public xLogHandler() {
	}



	// ------------------------------------------------------------------------------- //
	// publish



	public abstract void publish(final String line);

	public void publish() {
		this.publish( (String) null );
	}
	public void publish(final String[] lines) {
		// blank line
		if (Utils.isEmpty(lines)) {
			this.publish();
			return;
		}
		// single line
		if (lines.length == 1) {
			this.publish(lines[0]);
			return;
		}
		// multiple lines
		for (final String line : lines) {
			this.publish(line);
		}
	}
	public void publish(final xLogRecord record) {
		// blank line
		if (record == null || record.isEmpty()) {
			this.publish();
			return;
		}
		final String[] lines = record.getLines();
		// single line
		if (lines.length == 1) {
			this.publish(lines[0]);
			return;
		}
		// multiple lines
		for (final String line : lines) {
			this.publish(line);
		}
	}



	public abstract void flush();
	public abstract void clearScreen();
	public abstract void beep();



}
