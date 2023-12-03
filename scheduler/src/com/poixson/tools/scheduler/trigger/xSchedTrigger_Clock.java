/*
package com.poixson.tools.scheduler.trigger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.xTime;
import com.poixson.tools.xTimeU;
import com.poixson.utils.Utils;


public class xSchedTrigger_Clock extends xSchedTrigger {
	public static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";
	public static final long  DEFAULT_GRACE_TIME  = 1000L;

	protected final Date date;
	protected final long grace;



	public Trigger_Clock(final Date date, final long grace)
			throws ParseException {
		super();
		if (date == null) throw new RequiredArgumentException("date");
		this.date  = date;
		this.grace = (grace < 0L ? 0L : grace);
		this.setRepeat(false);
	}



	// ------------------------------------------------------------------------------- //
	// calculate time



	@Override
	public long untilNext(final long now) {
		if (this.notEnabled())
			return Long.MIN_VALUE;
		if (this.date == null) throw new RequiredArgumentException("date");
		final long time = this.date.getTime();
		final long last = this.last.get();
		if (last == Long.MIN_VALUE) {
			this.last.compareAndSet(Long.MIN_VALUE, now);
			return this.untilNext(now);
		}
		// until next trigger
		final long untilNext = time - now;
		if (0 - untilNext > this.grace) {
			this.log().warning("Skipping old scheduled clock trigger..");
			this.setEnabled(false);
			return Long.MIN_VALUE;
		}
		return untilNext;
	}



	// ------------------------------------------------------------------------------- //
	// factory



	public static class TriggerFactory_Clock extends TriggerFactory<Trigger_Clock> {

		protected Date  date = null;
		protected xTime grace = new xTime();



		public static TriggerFactory_Clock New() {
			return new TriggerFactory_Clock();
		}
		public TriggerFactory_Clock() {}



		@Override
		public Trigger_Clock build() {
			try {
				return
					new Trigger_Clock(
						this.date,
						this.grace.ms()
					);
			} catch (ParseException e) {
				this.log().trace(e);
			}
			return null;
		}



		public TriggerFactory_Clock date(final long time) {
			if (time < 0L) throw new IllegalArgumentException("Invalid time value: "+Long.toString(time));
			this.date = new Date(time);
			return this;
		}
		public TriggerFactory_Clock date(final String dateStr, final String formatStr)
				throws ParseException {
			if (Utils.isEmpty(dateStr))   throw new RequiredArgumentException("dateStr");
			final DateFormat format =
				new SimpleDateFormat(
					( Utils.isEmpty(formatStr) ? DEFAULT_DATE_FORMAT : formatStr ),
					Locale.ENGLISH
				);
			this.date = format.parse(dateStr);
			return this;
		}
		public TriggerFactory_Clock date(final String dateStr)
				throws ParseException {
			return this.date(dateStr, null);
		}
		public TriggerFactory_Clock date(final Date date) {
			if (date == null) throw new RequiredArgumentException("date");
			this.date = date;
			return this;
		}



		public TriggerFactory_Clock grace(final long grace) {
			this.grace.set(grace);
			return this;
		}
		public TriggerFactory_Clock grace(final long grace, xTimeU xunit) {
			this.grace.set(grace, xunit);
			return this;
		}
		public TriggerFactory_Clock grace(final long grace, final TimeUnit unit) {
			this.grace.set(grace, unit);
			return this;
		}
		public TriggerFactory_Clock grace(final String graceStr) {
			this.grace.set(graceStr);
			return this;
		}
		public TriggerFactory_Clock grace(final xTime time) {
			this.grace.set(time);
			return this;
		}



	}



}
*/
