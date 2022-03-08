package com.poixson.logger;

import com.poixson.logger.records.xLogRecord;


public interface AttachedLogger {



	public xLog log();



	// publish
	default void publish() {
		this.log().publish();
	}
	default void publish(final String msg) {
		this.log().publish(msg);
	}
	default void publish(final xLogRecord record) {
		this.log().publish(record);
	}



	// title
	default void title(final String msg) {
		this.log().title(msg);
	}



	// trace exception
	default void trace(final Throwable e) {
		this.log().trace(e);
	}
	default void trace(final Throwable e, final String msg, final Object...args) {
		this.log().trace(e, msg, args);
	}



	// detail
	default void detail(final String msg, final Object...args) {
		this.log().detail(msg, args);
	}

	// finest
	default void finest(final String msg, final Object...args) {
		this.log().finest(msg, args);
	}

	// finer
	default void finer(final String msg, final Object...args) {
		this.log().finer(msg, args);
	}

	// fine
	default void fine(final String msg, final Object...args) {
		this.log().fine(msg, args);
	}

	// stats
	default void stats(final String msg, final Object...args) {
		this.log().stats(msg, args);
	}

	// info
	default void info(final String msg, final Object...args) {
		this.log().info(msg, args);
	}

	// warning
	default void warning(final String msg, final Object...args) {
		this.log().warning(msg, args);
	}

	// severe
	default void severe(final String msg, final Object...args) {
		this.log().severe(msg, args);
	}

	// fatal
	default void fatal(final String msg, final Object...args) {
		this.log().fatal(msg, args);
	}



}
