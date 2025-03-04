/*
package com.poixson.tools.scheduler.trigger;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.CronExpression;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.poixson.exceptions.RequiredArgumentException;


/ *
  1 | Seconds
  2 | Minutes
  3 | Hours
  4 | Day-of-Month
  5 | Month
  6 | Day-of-Week
  7 | Year (optional field)
 * /
public class xSchedTrigger_Cron extends xSchedTrigger {

	protected final CronTriggerImpl trigger;

	protected final AtomicLong next = new AtomicLong( Long.MIN_VALUE );



	public xSchedTrigger_Cron(final CronExpression pattern) {
		super();
		if (pattern == null) throw new RequiredArgumentException("pattern");
		this.trigger = new CronTriggerImpl();
		this.trigger.setCronExpression(pattern);
		this.setRepeat(true);
	}
	public xSchedTrigger_Cron(final CronTriggerImpl trigger) {
		super();
		if (trigger == null) throw new RequiredArgumentException("trigger");
		this.trigger = trigger;
		this.setRepeat(true);
	}



	// -------------------------------------------------------------------------------
	// calculate time



	@Override
	public long getUntilNext(long now) {
		if (!this.isEnabled())
			return Long.MIN_VALUE;
		final long next = this.next.get();
		if (next == Long.MIN_VALUE) {
			this.update(now);
			return this.getUntilNext(now);
		}
		final long until_next = next - now;
		if (until_next <= 0L) {
			this.update(now + 1L);
		}
		return until_next;
	}



	// calculate time until next trigger
	public void update(final long now) {
		final Date nextDate = this.trigger.getFireTimeAfter( new Date(now) );
		if (nextDate == null) {
			this.setDisabled();
			return;
		}
		this.next.set( nextDate.getTime() );
	}



}
*/
