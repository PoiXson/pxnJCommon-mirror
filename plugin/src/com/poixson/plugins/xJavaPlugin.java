/*
package com.poixson.plugins;

import static com.poixson.utils.StringUtils.LastPart;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.xFailable;
import com.poixson.tools.abstractions.xStartStop;
import com.poixson.tools.config.xConfig;


public abstract class xJavaPlugin implements xStartStop, Runnable, xFailable {

	protected final xPluginManager<?> manager;
	protected final xPluginYML        yml;

	// state
	protected final AtomicReference<xPluginState> state = new AtomicReference<xPluginState>(null);
	protected final AtomicReference<Throwable>  failure = new AtomicReference<Throwable>(null);



	public xJavaPlugin(final xPluginManager<?> manager, final xPluginYML yml) {
		this.manager = manager;
		this.yml     = yml;
	}



	// -------------------------------------------------------------------------------
	// start/stop plugin overrides



	// load/unload
	public void init() {}
	public void term() {}

	// config
	public void config(final xConfig config) {}
	public void save(final xConfig config) {}

	// start/stop
	@Override
	public void start() {}
	@Override
	public void stop() {}

	// run loop
	@Override
	public void run() {}



	// -------------------------------------------------------------------------------
	// plugin state



	public xPluginState getState() {
		return this.state.get();
	}
	public boolean isState(final xPluginState state) {
		if (state == null)
			return (this.state.get() == null);
		return state.equals(this.state.get());
	}

	public xPluginState setState(final xPluginState state) {
		return this.state.getAndSet(state);
	}
	public boolean setState(final xPluginState expected, final xPluginState update) {
		return this.state.compareAndSet(expected, update);
	}



	// -------------------------------------------------------------------------------
	// failure



	@Override
	public boolean fail(final Throwable e) {
		this.log().trace(e);
		if (this.failure.compareAndSet(null, e)) {
			xThreadPool_Main.Get().runTaskLazy(
				new RunnableMethod<Object>(this, "onFailure")
			);
			return true;
		}
		return false;
	}
	@Override
	public boolean fail(final String msg) {
		return this.fail(new RuntimeException(msg));
	}

	@Override
	public boolean isFailed() {
		return (this.failure.get() != null);
	}

	@Override
	public void onFailure() {
	}



	// -------------------------------------------------------------------------------
	// config



	public String getPluginName() {
		return this.yml.getPluginName();
	}
	public String getPluginNameSafe() {
		try {
			final String name = this.getPluginName();
			if (!IsEmpty(name))
				return name;
		} catch (RuntimeException ignore) {}
		{
			final String class_name = LastPart(this.getClass().getName(), '.');
			if (IsEmpty(class_name))
				throw new RuntimeException("Failed to detect window class name");
			return class_name;
		}
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<xLog> _log = new AtomicReference<xLog>(null);

	public xLog log() {
		if (this._log.get() == null) {
			final xLog log = this._log();
			if (this._log.compareAndSet(null, log))
				return log;
		}
		return this._log.get();
	}
	protected xLog _log() {
		return xLog.Get("Plugin:"+this.getPluginNameSafe());
	}



}
*/
