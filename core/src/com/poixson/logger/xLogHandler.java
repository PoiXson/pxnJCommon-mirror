package com.poixson.logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


public abstract class xLogHandler {

	// publish lock
	protected final ReentrantLock lock = new ReentrantLock(true);

	protected final AtomicReference<xLogFormat> format = new AtomicReference<xLogFormat>(null);
	protected final AtomicReference<xLevel>     level  = new AtomicReference<xLevel>    (null);



	public xLogHandler() {
	}



	// -------------------------------------------------------------------------------
	// publish



	public abstract void publish(final xLogRecord record);



/*
	protected abstract void publish(final String[] lines);



	public void publish() {
		this.publish( (String[])null );
	}
	public void publish(final String msg) {
		if (Utils.isEmpty(msg)) {
			this.publish();
			return;
		}
		final String trimmed = StringUtils.cTrim(msg, '\n');
		final String[] lines = trimmed.split("\n");
		if (Utils.isEmpty(lines)) {
			this.publish();
			return;
		}
		this.publish(lines);
	}
	public void publish(final xLogRecord record) {
		if (!this.isLoggable(record.getLevel()))
			return;
		// blank line
		if (record == null || record.isEmpty()) {
			this.publish();
			return;
		}
		this.publish( this.format(record) );
	}



	public abstract void flush();
	public abstract void clearScreen();
	public abstract void beep();
*/



	// -------------------------------------------------------------------------------
	// publish lock



	public void getPublishLock() {
		this.getPublishLock(this.lock);
	}
	public void releasePublishLock() {
		this.releasePublishLock(this.lock);
	}

	protected void getPublishLock(final ReentrantLock lock) {
		TIMEOUT_LOOP:
		for (int i=0; i<50; i++) {
			try {
				if (lock.tryLock(5L, TimeUnit.MILLISECONDS))
					return;
			} catch (InterruptedException e) {
				break TIMEOUT_LOOP;
			}
			if (Thread.interrupted())
				break TIMEOUT_LOOP;
		} // end TIMEOUT_LOOP
	}
	protected void releasePublishLock(final ReentrantLock lock) {
		try {
			lock.unlock();
		} catch (IllegalMonitorStateException ignore) {}
	}



	// -------------------------------------------------------------------------------
	// format



	public String format(final xLogRecord record) {
		final xLogFormat format = this.format.get();
		if (format == null)
			return record.toString();
		return format.format(record);
	}

	public xLogFormat getFormat() {
		return this.format.get();
	}
	public void setFormat(final xLogFormat format) {
		this.format.set(format);
	}



	// -------------------------------------------------------------------------------
	// log level



	// log level
	public xLevel getLevel() {
		return this.level.get();
	}
	public void setLevel(final xLevel level) {
		this.level.set(level);
	}



	public boolean isLoggable(final xLevel level) {
		if (level == null)
			return true;
		final xLevel current = this.getLevel();
		if (current == null)
			return true;
		return current.isLoggable(level);
	}



}
