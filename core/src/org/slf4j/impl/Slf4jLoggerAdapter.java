package org.slf4j.impl;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;


public class Slf4jLoggerAdapter extends MarkerIgnoringBase {
	private static final long serialVersionUID = 1L;

	private final xLog log;



	public Slf4jLoggerAdapter(final String name, final xLog log) {
		this.name = name;
		this.log  = log;
	}



	// trace
	@Override
	public boolean isTraceEnabled() {
		return this.log.isLoggable(xLevel.FINEST);
	}
	@Override
	public void trace(final String msg) {
		if (this.log.isLoggable(xLevel.FINEST)) {
			this.log.finest(msg);
		}
	}
	@Override
	public void trace(final String msg, final Throwable e) {
		if (this.log.isLoggable(xLevel.FINEST)) {
			this.log.trace(e, msg);
		}
	}
	@Override
	public void trace(final String format, final Object arg) {
		if (this.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			this.trace(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void trace(final String format, final Object arg1, final Object arg2) {
		if (this.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			this.trace(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void trace(final String format, final Object...args) {
		if (this.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, args);
			this.trace(ft.getMessage(), ft.getThrowable());
		}
	}



	// debug
	@Override
	public boolean isDebugEnabled() {
		return this.log.isLoggable(xLevel.DETAIL);
	}
	@Override
	public void debug(final String msg) {
		if (this.log.isLoggable(xLevel.DETAIL)) {
			this.log.detail(msg);
		}
	}
	@Override
	public void debug(final String msg, final Throwable e) {
		if (this.log.isLoggable(xLevel.DETAIL)) {
			if (e == null) {
				this.log.detail(msg);
			} else {
				this.log.trace(e, msg);
			}
		}
	}
	@Override
	public void debug(final String format, final Object arg) {
		if (this.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			this.debug(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void debug(final String format, final Object arg1, final Object arg2) {
		if (this.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			this.debug(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void debug(final String format, final Object...args) {
		if (this.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, args);
			this.debug(ft.getMessage(), ft.getThrowable());
		}
	}



	// info
	@Override
	public boolean isInfoEnabled() {
		return this.log.isLoggable(xLevel.INFO);
	}
	@Override
	public void info(final String msg) {
		if (this.log.isLoggable(xLevel.INFO)) {
			this.log.info(msg);
		}
	}
	@Override
	public void info(final String msg, final Throwable e) {
		if (this.log.isLoggable(xLevel.INFO)) {
			if (e == null) {
				this.log.info(msg);
			} else {
				this.log.trace(e, msg);
			}
		}
	}
	@Override
	public void info(final String format, final Object arg) {
		if (this.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			this.info(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void info(final String format, final Object arg1, final Object arg2) {
		if (this.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			this.info(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void info(final String format, final Object...args) {
		if (this.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, args);
			this.info(ft.getMessage(), ft.getThrowable());
		}
	}



	// warning
	@Override
	public boolean isWarnEnabled() {
		return this.log.isLoggable(xLevel.WARNING);
	}
	@Override
	public void warn(final String msg) {
		if (this.log.isLoggable(xLevel.WARNING)) {
			this.log.warning(msg);
		}
	}
	@Override
	public void warn(final String msg, final Throwable e) {
		if (this.log.isLoggable(xLevel.WARNING)) {
			if (e == null) {
				this.log.warning(msg);
			} else {
				this.log.trace(e, msg);
			}
		}
	}
	@Override
	public void warn(final String format, final Object arg) {
		if (this.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			this.warn(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void warn(final String format, final Object arg1, final Object arg2) {
		if (this.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			this.warn(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void warn(final String format, final Object...args) {
		if (this.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, args);
			this.warn(ft.getMessage(), ft.getThrowable());
		}
	}



	// error
	@Override
	public boolean isErrorEnabled() {
		return this.log.isLoggable(xLevel.SEVERE);
	}
	@Override
	public void error(final String msg) {
		if (this.log.isLoggable(xLevel.SEVERE)) {
			this.log.severe(msg);
		}
	}
	@Override
	public void error(final String msg, final Throwable e) {
		if (this.log.isLoggable(xLevel.SEVERE)) {
			if (e == null) {
				this.log.severe(msg);
			} else {
				this.log.trace(e, msg);
			}
		}
	}
	@Override
	public void error(final String format, final Object arg) {
		if (this.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			this.error(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void error(final String format, final Object arg1, final Object arg2) {
		if (this.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			this.error(ft.getMessage(), ft.getThrowable());
		}
	}
	@Override
	public void error(final String format, final Object...args) {
		if (this.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, args);
			this.error(ft.getMessage(), ft.getThrowable());
		}
	}



}
