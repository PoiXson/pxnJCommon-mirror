package com.poixson.tools.scheduler.trigger;

import java.util.concurrent.TimeUnit;

import com.poixson.tools.xTime;
import com.poixson.tools.xTimeU;


public class Trigger_Interval extends Trigger {

	protected final long delay;
	protected final long interval;



	public Trigger_Interval(final long delay) {
		this(delay, 0L);
	}
	public Trigger_Interval(final long delay, final long interval) {
		super();
		if (delay    < 0L) throw new IllegalArgumentException("Invalid delay value: "+Long.toString(delay));
		if (interval < 0L) throw new IllegalArgumentException("Invalid interval value: "+Long.toString(interval));
		this.delay    = delay;
		this.interval = interval;
		this.setRepeat( this.interval > 0 );
	}



	// ------------------------------------------------------------------------------- //
	// calculate time



	@Override
	public long untilNext(final long now) {
		if (this.notEnabled())
			return Long.MIN_VALUE;
		final long last = this.last.get();
		// delay until first trigger
		if (last == Long.MIN_VALUE) {
			if (this.delay <= 0L) {
				if (this.last.compareAndSet(Long.MIN_VALUE, now))
					return 0L;
			} else {
				final long sinceLast = this.interval - this.delay;
				if (this.last.compareAndSet(Long.MIN_VALUE, now - sinceLast))
					return this.delay;
			}
			return 1L;
		}
		// until next interval
		final long sinceLast = now - last;
		final long untilNext = this.interval - sinceLast;
		// trigger now
		if (untilNext <= 0L) {
			// adjust last value (keeping sync with time)
			final long mult = sinceLast / this.interval;
			this.last.set( last + (mult * this.interval) );
		}
		// sleep time
		return untilNext;
	}



	// ------------------------------------------------------------------------------- //
	// factory



	public static class TriggerFactory_Interval extends TriggerFactory<Trigger_Interval> {

		protected final xTime delay    = new xTime();
		protected final xTime interval = new xTime();



		public static TriggerFactory_Interval New() {
			return new TriggerFactory_Interval();
		}
		public TriggerFactory_Interval() {}



		@Override
		public Trigger_Interval build() {
			return
				new Trigger_Interval(
					this.delay.ms(),
					this.interval.ms()
				);
		}



		public TriggerFactory_Interval delay(final long time) {
			this.delay.set(time);
			return this;
		}
		public TriggerFactory_Interval delay(final long delay, final xTimeU xunit) {
			this.delay.set(delay, xunit);
			return this;
		}
		public TriggerFactory_Interval delay(final long delay, final TimeUnit unit) {
			this.delay.set(delay, unit);
			return this;
		}
		public TriggerFactory_Interval delay(final String delayStr) {
			this.delay.set(delayStr);
			return this;
		}
		public TriggerFactory_Interval delay(final xTime time) {
			this.delay.set(time);
			return this;
		}



		public TriggerFactory_Interval interval(final long time) {
			this.interval.set(time);
			return this;
		}
		public TriggerFactory_Interval interval(final long interval, final xTimeU xunit) {
			this.interval.set(interval, xunit);
			return this;
		}
		public TriggerFactory_Interval interval(final long interval, final TimeUnit unit) {
			this.interval.set(interval, unit);
			return this;
		}
		public TriggerFactory_Interval interval(final String intervalStr) {
			this.interval.set(intervalStr);
			return this;
		}
		public TriggerFactory_Interval interval(final xTime time) {
			this.interval.set(time);
			return this;
		}



	}



}
