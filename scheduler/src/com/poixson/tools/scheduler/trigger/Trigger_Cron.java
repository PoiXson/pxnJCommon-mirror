package com.poixson.tools.scheduler.trigger;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.quartz.CronExpression;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.scheduler.xSchedulerTrigger;


/*
1 | Seconds
2 | Minutes
3 | Hours
4 | Day-of-Month
5 | Month
6 | Day-of-Week
7 | Year (optional field)
*/
public class Trigger_Cron extends xSchedulerTrigger {

	private final AtomicReference<CronTriggerImpl> trigger =
			new AtomicReference<CronTriggerImpl>(null);

	private final AtomicLong next = new AtomicLong( Long.MIN_VALUE );

	private final Object updateLock = new Object();



	// builder
	public static Trigger_Cron builder() {
		return new Trigger_Cron();
	}



	public Trigger_Cron() {
	}
	public Trigger_Cron(final String patternStr) throws ParseException {
		this();
		this.setCronPattern(patternStr);
	}
	public Trigger_Cron(final CronExpression express) {
		this();
		this.setCronExpression(express);
	}



	public void update(final long now) {
		if (this.trigger == null) throw new RequiredArgumentException("CronTrigger");
		synchronized(this.updateLock) {
			final CronTriggerImpl trigger = this.trigger.get();
			if (trigger == null) throw new RequiredArgumentException("CronTrigger");
			// calculate time until next trigger
			final Date nowDate = new Date(now);
			final Date nextDate = trigger.getFireTimeAfter(nowDate);
			if (nextDate == null) {
				this.setDisabled();
				return;
			}
			this.next.set(
				nextDate.getTime()
			);
		}
	}



	@Override
	public long untilNextTrigger(final long now) {
		if (this.notEnabled())
			return Long.MIN_VALUE;
		synchronized(this.updateLock) {
			final long next = this.next.get();
			if (next == Long.MIN_VALUE)
				this.update(now);
			if (this.notEnabled())
				return Long.MIN_VALUE;
			final long untilNext = next - now;
			if (untilNext <= 0L) {
				this.update(now + 1L);
			}
			return untilNext;
		}
	}



	public TriggerCron setCronPattern(final String patternStr) throws ParseException {
		final CronExpression express = new CronExpression(patternStr);
		return this.setCronExpression(express);
	}
	public TriggerCron setCronExpression(final CronExpression express) {
		if (express == null) throw new RequiredArgumentException("expression");
		final CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setCronExpression(express);
		this.trigger.set(trigger);
		return this;
	}



	// ------------------------------------------------------------------------------- //
	// overrides



	public TriggerCron enable() {
		return ( super.enable() == null ? null : this );
	}
	public TriggerCron disable() {
		return ( super.disable() == null ? null : this );
	}
	public TriggerCron enable(final boolean enabled) {
		return ( super.enable(enabled) == null ? null : this );
	}



	public TriggerCron repeat() {
		return ( super.repeat() == null ? null : this );
	}
	public TriggerCron noRepeat() {
		return ( super.noRepeat() == null ? null : this );
	}
	public TriggerCron runOnce() {
		return ( super.runOnce() == null ? null : this );
	}
	public TriggerCron repeat(final boolean repeating) {
		return ( super.repeat(repeating) == null ? null : this );
	}



}
