package com.poixson.tools;

import java.util.concurrent.atomic.AtomicLong;

import com.poixson.utils.Utils;


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
			if (this.last.compareAndSet(last, current))
				return true;
			return this.again();
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
		return Utils.GetMS();
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
