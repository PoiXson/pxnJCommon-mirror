/*
package com.poixson.tools.scheduler.trigger;

import java.text.ParseException;
import java.util.Date;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;


public class xSchedTrigger_Clock extends xSchedTrigger {

//TODO: remove this?
//	public static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";
//	public static final long  DEFAULT_GRACE_TIME  = 1000L;

	protected final Date date;
	protected final long grace;



	public xSchedTrigger_Clock(final Date date, final long grace)
			throws ParseException {
		super();
		if (date == null) throw new RequiredArgumentException("date");
		this.date  = date;
		this.grace = (grace < 0L ? 0L : grace);
		this.setRepeat(false);
	}



	// -------------------------------------------------------------------------------
	// calculate time



	@Override
	public long getUntilNext(final long now) {
		if (!this.isEnabled())
			return Long.MIN_VALUE;
		if (this.date == null) throw new RequiredArgumentException("date");
		final long time = this.date.getTime();
		final long last = this.last.get();
		if (last == Long.MIN_VALUE) {
			this.last.compareAndSet(Long.MIN_VALUE, now);
			return this.getUntilNext(now);
		}
		// until next trigger
		final long until_next = time - now;
		if (0 - until_next > this.grace) {
			this.log().warning("Skipping old scheduled clock trigger..");
			this.setEnabled(false);
			return Long.MIN_VALUE;
		}
		return until_next;
	}



	// -------------------------------------------------------------------------------
	// logger



//TODO: change and move this
	public xLog log() {
		return xLog.Get();
	}



}
*/
