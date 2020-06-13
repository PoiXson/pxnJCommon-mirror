package com.poixson.logger;

import com.poixson.logger.records.xLogRecord;


public interface AttachedLogger {



	public xLog log();



	// publish
	default void publish() {
		this.log().publish();
	}
	default void publish(final String line) {
		this.log().publish(line);
	}
	default void publish(final String[] lines) {
		this.log().publish(lines);
	}
	default void publish(final xLogRecord record) {
		this.log().publish(record);
	}



	// title
	default void title(final String...lines) {
		this.log().title(lines);
	}



	// trace exception
	default void trace(final Throwable e) {
		this.log().trace(e);
	}
	default void trace(final Throwable e, final String line, final Object...args) {
		this.log().trace(e, line, args);
	}



	// detail
	default void detail(final String line, final Object...args) {
		this.log().detail(line, args);
	}
	default void detail(final String[] lines, final Object...args) {
		this.log().detail(lines, args);
	}



	// finest
	default void finest(final String line, final Object...args) {
		this.log().finest(line, args);
	}
	default void finest(final String[] lines, final Object...args) {
		this.log().finest(lines, args);
	}



	// finer
	default void finer(final String line, final Object...args) {
		this.log().finer(line, args);
	}
	default void finer(final String[] lines, final Object...args) {
		this.log().finer(lines, args);
	}



	// fine
	default void fine(final String line, final Object...args) {
		this.log().fine(line, args);
	}
	default void fine(final String[] lines, final Object...args) {
		this.log().fine(lines, args);
	}



	// stats
	default void stats(final String line, final Object...args) {
		this.log().stats(line, args);
	}
	default void stats(final String[] lines, final Object...args) {
		this.log().stats(lines, args);
	}



	// info
	default void info(final String line, final Object...args) {
		this.log().info(line, args);
	}
	default void info(final String[] lines, final Object...args) {
		this.log().info(lines, args);
	}



	// warning
	default void warning(final String line, final Object...args) {
		this.log().warning(line, args);
	}
	default void warning(final String[] lines, final Object...args) {
		this.log().warning(lines, args);
	}



	// severe
	default void severe(final String line, final Object...args) {
		this.log().severe(line, args);
	}
	default void severe(final String[] lines, final Object...args) {
		this.log().severe(lines, args);
	}



	// fatal
	default void fatal(final String line, final Object...args) {
		this.log().fatal(line, args);
	}
	default void fatal(final String[] lines, final Object...args) {
		this.log().fatal(lines, args);
	}



}
