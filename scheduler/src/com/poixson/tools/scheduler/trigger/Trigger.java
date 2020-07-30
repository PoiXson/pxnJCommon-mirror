package com.poixson.tools.scheduler;

import java.util.concurrent.atomic.AtomicBoolean;

import com.poixson.tools.abstractions.xEnableable;


public abstract class xSchedulerTrigger implements xEnableable {

	protected final AtomicBoolean enabled   = new AtomicBoolean(true);
	protected final AtomicBoolean repeating = new AtomicBoolean(false);



	public xSchedulerTrigger() {
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



}
