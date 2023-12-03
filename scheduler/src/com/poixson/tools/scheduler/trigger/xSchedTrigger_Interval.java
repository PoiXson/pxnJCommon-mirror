package com.poixson.tools.scheduler.trigger;


public class xSchedTrigger_Interval extends xSchedTrigger {

	protected final long delay;
	protected final long interval;



	public xSchedTrigger_Interval(final long interval) {
		this(interval, interval);
	}
	public xSchedTrigger_Interval(final long delay, final long interval) {
		super();
		if (delay    < 0L) throw new IllegalArgumentException("Invalid delay value: "+Long.toString(delay));
		if (interval < 0L) throw new IllegalArgumentException("Invalid interval value: "+Long.toString(interval));
		this.delay    = delay;
		this.interval = interval;
		this.setRepeat( this.interval > 0 );
	}



	// -------------------------------------------------------------------------------
	// calculate time



	@Override
	public long getUntilNext(final long now) {
		if (!this.isEnabled())
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



}
