package com.poixson.logger;

import com.poixson.logger.xLogRecord_Special.SpecialType;
import com.poixson.logger.handlers.xLogHandler_Console;
import com.poixson.tools.StdIO;
import com.poixson.utils.Utils;


public class xLogger extends xLog {



	// root logger
	protected xLogger() {
		super();
	}
	// child logger
	protected xLogger(final xLog parent, final String log_name) {
		super(parent, log_name);
	}

	// logger factory
	@Override
	protected xLog create(final String log_name) {
		return new xLogger(this, log_name);
	}



	// -------------------------------------------------------------------------------
	// publish



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



	@Override
	public void publish(final String msg) {
		if (Utils.isEmpty(msg)) {
			this.publish();
			return;
		}
		this.publish(new xLogRecord_Msg(this, (xLevel)null, msg, (Object[])null));
	}

	@Override
	public void publish(final xLogRecord record) {
		if (record != null) {
			if (this.notLoggable(record.getLevel()))
				return;
		}
		{
			final xLogHandler[] handlers = this.getHandlersOrDefault();
			if (Utils.notEmpty(handlers)) {
				for (final xLogHandler hand : handlers) {
					try {
						hand.publish(record);
					} catch (Exception e) {
						e.printStackTrace(StdIO.OriginalErr());
					}
				}
			}
		}
		// parent
		if (this.parent != null)
			this.parent.publish(record);
	}



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



	// -------------------------------------------------------------------------------
	// handlers



	@Override
	public xLogHandler getDefaultHandler() {
		// existing default handler
		{
			final xLogHandler handler = this.defaultHandler.get();
			if (handler != null)
				return handler;
		}
		// new default handler
		if (this.isRoot()) {
			final xLogHandler handler = new xLogHandler_Console();
			this.setDefaultHandlerIfEmpty(handler);
		}
		return this.defaultHandler.get();
	}



}
