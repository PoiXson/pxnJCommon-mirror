package com.poixson.plugins;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Failure;
import com.poixson.tools.abstractions.RunnableMethod;
import com.poixson.tools.abstractions.xFailable;
import com.poixson.tools.abstractions.xStartStop;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public abstract class xJavaPlugin implements xStartStop, Runnable, xFailable {

	protected final AtomicReference<xPluginManager<?>> manager = new AtomicReference<xPluginManager<?>>(null);
	protected final AtomicReference<xPluginYML>        yml     = new AtomicReference<xPluginYML>(null);

	// state
	protected final AtomicReference<xPluginState> state = new AtomicReference<xPluginState>(null);
	protected final AtomicReference<Failure>    failure = new AtomicReference<Failure>(null);



	public xJavaPlugin() {
	}



	public void setVars(final xPluginManager<?> manager, final xPluginYML yml) {
		if (manager == null) throw new RequiredArgumentException("manager");
		if (yml     == null) throw new RequiredArgumentException("yml");
		if (!this.manager.compareAndSet(null, manager)) throw new RuntimeException("manager already set");
		if (!this.yml    .compareAndSet(null, yml    )) throw new RuntimeException("yml already set");
	}



	// -------------------------------------------------------------------------------
	// start/stop plugin overrides



	// load/unload
	public void init() {}
	public void term() {}


	// start/stop
	@Override
	public void start() {}
	@Override
	public void stop() {}


	// run loop
	@Override
	public void run() {}


	// failed
	@Override
	public void onFailure() {}



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
		return this.fail(
			(new StringBuilder())
				.append(e.getMessage())
				.append('\n')
				.append(StringUtils.ExceptionToString(e))
				.toString(),
			e
		);
	}
	@Override
	public boolean fail(final String msg, final Object...args) {
		if (Failure.AtomicFail(this.failure, this.log(), msg, args)) {
			xThreadPool_Main.Get().runTaskLazy(
				new RunnableMethod<Object>(this, "onFailure")
			);
			return true;
		}
		return false;
	}
	@Override
	public boolean fail(final int exitCode, final String msg, final Object... args) {
		return this.fail(msg, args);
	}



	@Override
	public boolean isFailed() {
		return (this.failure.get() != null);
	}



	// -------------------------------------------------------------------------------
	// config



	public String getPluginName() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getPluginName();
	}
	public String getPluginNameSafe() {
		try {
			final String name = this.getPluginName();
			if (Utils.notEmpty(name))
				return name;
		} catch (RuntimeException ignore) {}
		{
			final String class_name = StringUtils.LastPart(this.getClass().getName(), '.');
			if (Utils.isEmpty(class_name))
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
		return xLog.Get( "Plugin:" + this.getPluginNameSafe() );
	}



}
