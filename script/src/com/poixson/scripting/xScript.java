/*
package com.poixson.scripting;

import static com.poixson.utils.Utils.GetMS;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.tools.abstractions.startstop.xStartStop;


public abstract class xScript implements xStartStop, Runnable {

	protected final AtomicBoolean active   = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);
	protected final AtomicLong last_used = new AtomicLong(0L);



	public xScript() {
	}



	@Override
	public boolean start() {
		if (!this.stopping.get()) {
			this.compile();
			return true;
		}
		return false;
	}
	@Override
	public boolean stop() {
		if (this.stopping.compareAndSet(false, true)) {
//TODO: what thread does this run as?
			this.call("stop");
			return true;
		}
		return false;
	}



	@Override
	public void run() {
		this.call("loop");
	}

	public abstract Object call(final String func, final Object...args);

	protected abstract void compile();



	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}

	public boolean isActive() {
		return this.active.get();
	}



	public void resetLastUsed() {
		this.last_used.set(GetMS());
	}



	// -------------------------------------------------------------------------------
	// variables



	public abstract xScript setVariable(final String key, final Object value);
	public abstract Object  getVariable(final String key);



	public String getVarString(final String key) {
		final Object val = this.getVariable(key);
		return val.toString();
	}
	public int getVarInt(final String key) {
		final Object val = this.getVariable(key);
		if (val instanceof Integer v)
			return v.intValue();
		return Integer.parseInt(val.toString());
	}
	public long getVarLong(final String key) {
		final Object val = this.getVariable(key);
		if (val instanceof Long v)
			return v.longValue();
		return Long.parseLong(val.toString());
	}
	public double getVarDouble(final String key) {
		final Object val = this.getVariable(key);
		if (val instanceof Double v)
			return v.doubleValue();
		return Double.parseDouble(val.toString());
	}



}
*/
