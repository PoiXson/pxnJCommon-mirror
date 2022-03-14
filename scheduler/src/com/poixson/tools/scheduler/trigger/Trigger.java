/*
package com.poixson.tools.scheduler.trigger;

import static com.poixson.logger.xLog.XLog;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.logger.xLog;
import com.poixson.tools.abstractions.xEnableable;


public abstract class Trigger implements xEnableable {

	protected final AtomicBoolean enabled   = new AtomicBoolean(true);
	protected final AtomicBoolean repeating = new AtomicBoolean(false);

	protected final AtomicLong last = new AtomicLong( Long.MIN_VALUE );



	public Trigger() {
	}



	public abstract long untilNext(final long now);



	// enabled
	@Override
	public boolean isEnabled() {
		return this.enabled.get();
	}
	@Override
	public boolean notEnabled() {
		return ! this.enabled.get();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
	}
	@Override
	public void setEnabled() {
		this.setEnabled(true);
	}
	@Override
	public void setDisabled() {
		this.setEnabled(false);
	}



	// repeating trigger
	public boolean isRepeat() {
		return this.repeating.get();
	}
	public boolean notRepeat() {
		return ! this.repeating.get();
	}

	public void setRepeat(final boolean repeating) {
		this.repeating.set(repeating);
	}
	public void setRepeat() {
		this.setRepeat(true);
	}
	public void disableRepeat() {
		this.setRepeat(false);
	}
	public void setRunOnce() {
		this.setRepeat(false);
	}



	// ------------------------------------------------------------------------------- //
	// logger



	public xLog log() {
		return XLog();
	}



}
*/
