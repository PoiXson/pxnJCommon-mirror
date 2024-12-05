package com.poixson.tools;

import static com.poixson.utils.Utils.GetMS;

import java.util.concurrent.atomic.AtomicLong;


public class CoolDown {

	protected final AtomicLong duration = new AtomicLong(0L);
	protected final AtomicLong last     = new AtomicLong(0L);



	public CoolDown() {
		this(0L, 0L);
	}

	public CoolDown(final String duration) {
		this( xTime.Parse(duration) );
	}
	public CoolDown(final xTime timeDuration) {
		this(timeDuration.ms(), 0L);
	}
	public CoolDown(final long msDuration) {
		this(msDuration, 0L);
	}

	public CoolDown(final long msDuration, final long msLast) {
		this.duration.set(msDuration);
		this.last.set(msLast);
	}



	// -------------------------------------------------------------------------------
	// state



	public boolean peek() {
		return this.peek(this.getCurrent());
	}
	public boolean peek(final long current) {
		return this._peek(current, false);
	}

	// set current time if unset
	public boolean peekOrDefault() {
		return this.peekOrDefault(this.getCurrent());
	}
	public boolean peekOrDefault(final long current) {
		return this._peek(current, true);
	}

	protected boolean _peek(final long current, final boolean setDefault) {
		// disabled
		final long duration = this.duration.get();
		if (duration <= 0L)
			return false;
		// first run
		final long last = this.last.get();
		if (last <= 0L) {
			if (setDefault) {
				if (!this.last.compareAndSet(last, current))
					return this.peek(current);
			}
			return false;
		}
		// check timeout
		return (current - last >= duration);
	}



	public boolean again() {
		return this.again( this.getCurrent() );
	}
	public boolean again(final long current) {
		// disabled
		final long duration = this.duration.get();
		if (duration <= 0L)
			return false;
		// first run
		final long last = this.last.get();
		if (last <= 0L) {
			return (
				this.last.compareAndSet(last, current)
				? true
				: this.again()
			);
		}
		// check timeout
		if (current - last >= duration) {
			if (this.last.compareAndSet(last, current))
				return true;
			return this.again();
		}
		// cooling
		return false;
	}



	public void reset() {
		this.reset( this.getCurrent() );
	}
	public void reset(final long time) {
		this.last.set(time);
	}



	public long getCurrent() {
		return GetMS();
	}
	public long getLast() {
		return this.last.get();
	}



	public long getTimeSinceLast() {
		final long last = this.last.get();
		if (last <= 0L)
			return last;
		return (this.getCurrent() - last);
	}
	public long getTimeUntilNext() {
		final long last = this.last.get();
		if (last <= 0L)
			return last;
		final long duration = this.duration.get();
		// disabled
		if (duration <= 0L)
			return -1L;
		return ( (last + duration) - this.getCurrent() );
	}



	// -------------------------------------------------------------------------------
	// config



	public long getDuration() {
		return this.duration.get();
	}
	public void setDuration(final String time) {
		this.setDuration( xTime.Parse(time) );
	}
	public void setDuration(final xTime time) {
		this.setDuration( time.ms() );
	}
	public void setDuration(final long ms) {
		this.duration.set(ms);
	}



}
