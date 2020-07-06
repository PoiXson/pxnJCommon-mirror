package com.poixson.logger.handlers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import com.poixson.logger.xLevel;
import com.poixson.logger.records.xLogRecord;
import com.poixson.utils.Utils;


public abstract class xLogHandler {

	protected final ReentrantLock publishLock = new ReentrantLock(true);

	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);

//TODO
//	private volatile xLogFormatter formatter = null;
//	protected final AtomicReference<xLogFormatter> defaultFormatter = new AtomicReference<xLogFormatter>(null);



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
		if (this.notLoggable(record.getLevel()))
			return;
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



	// ------------------------------------------------------------------------------- //
	// publish lock



	public void getPublishLock() {
		this.getPublishLock(
			this.publishLock
		);
	}
	public void releasePublishLock() {
		this.releasePublishLock(
			this.publishLock
		);
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



	// ------------------------------------------------------------------------------- //
	// formatter



//TODO



	// ------------------------------------------------------------------------------- //
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
		final xLevel currentLevel = this.getLevel();
		if (currentLevel == null)
			return true;
		return currentLevel.isLoggable(level);
	}
	public boolean notLoggable(final xLevel level) {
		return ! this.isLoggable(level);
	}



}
