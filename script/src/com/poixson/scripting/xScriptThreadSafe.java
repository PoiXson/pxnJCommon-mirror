package com.poixson.scripting;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.scripting.loader.xScriptLoader;
import com.poixson.tools.CoolDown;
import com.poixson.tools.abstractions.Tuple;


public class xScriptThreadSafe extends xScript {

	protected final xScriptLoader loader;
	protected final boolean safe;

	protected final AtomicReference<xScript> script = new AtomicReference<xScript>(null);
	protected final AtomicReference<Thread>  thread = new AtomicReference<Thread>(null);

	protected final AtomicBoolean stopping = new AtomicBoolean(false);
//TODO: use this to detect file changes and reload
	protected final CoolDown reload_cool = new CoolDown("5s");

	protected final LinkedBlockingQueue<Tuple<String, Object[]>> queue_calls = new LinkedBlockingQueue<Tuple<String, Object[]>>();

	protected final ConcurrentHashMap<String, Object>    vars_in  = new ConcurrentHashMap<String, Object>();
	protected final AtomicReference<Map<String, Object>> vars_out = new AtomicReference<Map<String, Object>>();



	public xScriptThreadSafe(final xScriptLoader loader, final boolean safe) {
		super();
		this.loader = loader;
		this.safe   = safe;
	}

	public xScript build() {
		return new xScriptInstance(this.loader, this.safe);
	}



	@Override
	public void start() {
		if (this.stopping.get()) return;
		if (this.thread.get() == null) {
			final Thread thread = new Thread(this);
			if (this.thread.compareAndSet(null, thread))
				thread.start();
		}
	}
	@Override
	public void stop() {
		final xScript script = this.script.get();
		if (script != null)
			script.stop();
		this.stopping.set(true);
	}



	@Override
	public void run() {
		if (this.stopping.get()) return;
		final xScript script = this.build();
		if (!this.script.compareAndSet(null, script))
			throw new IllegalStateException("script already running?");
		this.push(script);
		script.start();
		this.pull(script);
		RUN_LOOP:
		while (true) {
			if (this.stopping.get()) break RUN_LOOP;
			try {
				final Tuple<String, Object[]> entry = this.queue_calls.poll(100L, TimeUnit.MILLISECONDS);
				if (entry != null) {
					this.push(script);
					if (entry.key == "loop") script.run();
					else                     script.call(entry.key, entry.val);
					this.pull(script);
				}
			} catch (InterruptedException ignore) {}
		} // end RUN_LOOP
		this.stop();
	}

	@Override
	public Object call(final String func, final Object... args) {
		try {
			this.queue_calls.put(new Tuple<String, Object[]>(func, args));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	protected void compile() {
		throw new UnsupportedOperationException();
	}



	@Override
	public void resetLastUsed() {
		super.resetLastUsed();
		final xScript script = this.script.get();
		if (script != null)
			script.resetLastUsed();
	}



	// -------------------------------------------------------------------------------
	// variables



	public void push(final xScript script) {
		final Iterator<Entry<String, Object>> it = this.vars_in.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, Object> entry = it.next();
			script.setVariable(entry.getKey(), entry.getValue());
		}
	}
	public void pull(final xScript script) {
		final ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<String, Object>();
		final Iterator<Entry<String, Object>> it = this.vars_in.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, Object> entry = it.next();
			final String name = entry.getKey();
			final Object value = script.getVariable(name);
			result.put(name, value);
		}
		this.vars_out.set(result);
	}



	@Override
	public xScript setVariable(final String key, final Object value) {
		this.vars_in.put(key, value);
		return this;
	}
	@Override
	public Object getVariable(final String key) {
		final Map<String, Object> vars = this.vars_out.get();
		return (vars==null ? null : vars.get(key));
	}



}
