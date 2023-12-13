package com.poixson.tools.scheduler.trigger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public abstract class xSchedTrigger {

	protected final AtomicBoolean enabled   = new AtomicBoolean(true);
	protected final AtomicBoolean repeating = new AtomicBoolean(false);

	protected final AtomicLong last = new AtomicLong( Long.MIN_VALUE );



	public xSchedTrigger() {
	}



	public abstract long getUntilNext(final long now);



	public xSchedTrigger setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
		return this;
	}
	public xSchedTrigger setEnabled() {
		this.setEnabled(true);
		return this;
	}
	public xSchedTrigger setDisabled() {
		this.setEnabled(false);
		return this;
	}
	public boolean isEnabled() {
		return this.enabled.get();
	}



	public xSchedTrigger setRepeat(final boolean repeating) {
		this.repeating.set(repeating);
		return this;
	}
	public xSchedTrigger setRepeat() {
		this.setRepeat(true);
		return this;
	}
	public xSchedTrigger disableRepeat() {
		this.setRepeat(false);
		return this;
	}
	public xSchedTrigger setRunOnce() {
		this.setRepeat(false);
		return this;
	}
	public boolean isRepeat() {
		return this.repeating.get();
	}



}
