package com.poixson.logger;

import java.util.Iterator;

import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.logger.records.xLogRecord_Special;
import com.poixson.logger.records.xLogRecord_Special.SpecialType;
import com.poixson.tools.StdIO;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLogger extends xLog {



	// root logger
	protected xLogger() {
		super();
	}
	// child logger
	protected xLogger(final xLog parent, final String logName) {
		super(parent, logName);
	}
	// logger factory
	@Override
	protected xLog create(final String logName) {
		return new xLogger(this, logName);
	}



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void flush() {
		this.publish( new xLogRecord_Special(SpecialType.FLUSH) );
	}
	@Override
	public void clearScreen() {
		this.publish( new xLogRecord_Special(SpecialType.CLEAR_SCREEN) );
	}
	@Override
	public void beep() {
		this.publish( new xLogRecord_Special(SpecialType.BEEP) );
	}



	@Override
	public void publish(final String msg) {
		if (Utils.isEmpty(msg)) {
			this.publish();
			return;
		}
		this.publish( new xLogRecord_Msg(this, (xLevel)null, msg, (Object[])null) );
	}
	@Override
	public void publish(final xLogRecord record) {
		if (record == null) {
			this.publish();
			return;
		}
		if (this.notLoggable(record.getLevel()))
			return;
		if (!this.handlers.isEmpty()) {
			final Iterator<xLogHandler> it = this.handlers.iterator();
			while (it.hasNext()) {
				final xLogHandler h = it.next();
				try {
					h.publish(record);
				} catch (Exception e) {
					e.printStackTrace(StdIO.OriginalErr);
				}
			}
		}
		// parent
		if (this.parent != null)
			this.parent.publish(record);
	}



	// std out
	@Override
	public void stdout(final String msg) {
		this.publish(msg);
	}
	// std err
	@Override
	public void stderr(final String msg) {
		this.publish(msg);
	}



	// trace exception
	@Override
	public void trace(final Throwable e) {
		this.trace(e, null);
	}
	@Override
	public void trace(final Throwable e, final String msg, final Object...args) {
		final StringBuilder str = new StringBuilder();
		if (Utils.notEmpty(msg)) {
			str.append(msg)
				.append(" - ");
		}
		str.append( StringUtils.ExceptionToString(e) );
		this.publish( new xLogRecord_Msg(this, xLevel.SEVERE, str.toString(), args) );
	}



	// title
	@Override
	public void title(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.TITLE, msg, args) );
	}
	// detail
	@Override
	public void detail(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.DETAIL, msg, args) );
	}
	// finest
	@Override
	public void finest(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINEST, msg, args) );
	}
	// finer
	@Override
	public void finer(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINER, msg, args) );
	}
	// fine
	@Override
	public void fine(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINE, msg, args) );
	}
	// stats
	@Override
	public void stats(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.STATS, msg, args) );
	}
	// info
	@Override
	public void info(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.INFO, msg, args) );
	}
	// warning
	@Override
	public void warning(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.WARNING, msg, args) );
	}
	// notice
	@Override
	public void notice(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.NOTICE, msg, args) );
	}
	// severe
	@Override
	public void severe(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.SEVERE, msg, args) );
	}
	// fatal
	@Override
	public void fatal(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FATAL, msg, args) );
	}



}
