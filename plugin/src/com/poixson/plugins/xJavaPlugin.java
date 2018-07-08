package com.poixson.plugins;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicReference;

import org.xeustechnologies.jcl.JarClassLoader;

import com.poixson.abstractions.xStartable;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.AttachedLogger;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.remapped.RemappedMethod;


public abstract class xJavaPlugin implements xStartable, AttachedLogger {

	protected final AtomicReference<JarClassLoader> jcl =
			new AtomicReference<JarClassLoader>(null);
	protected final AtomicReference<xPluginManager<?>> manager =
			new AtomicReference<xPluginManager<?>>(null);
	protected final AtomicReference<xPluginYML> yml =
			new AtomicReference<xPluginYML>(null);

	// state
	protected final AtomicReference<xPluginState> state =
			new AtomicReference<xPluginState>(null);



	public xJavaPlugin() {
	}



	// ------------------------------------------------------------------------------- //
	// start/stop plugin



	public void init() {}
	public void unload() {}


	@Override
	public void start() {}
	@Override
	public void stop() {}


	public void failed() {}


	@Override
	public void run() {}



	// ------------------------------------------------------------------------------- //
	// plugin state



	public xPluginState getState() {
		return this.state.get();
	}
	public boolean setState(final xPluginState expected, final xPluginState update) {
		return this.state.compareAndSet(expected, update);
	}
	public xPluginState setState(final xPluginState state) {
		return this.state.getAndSet(state);
	}



	public boolean isState(final xPluginState state) {
		if (state == null)
			return (this.state.get() == null);
		return state.equals(this.state.get());
	}
	public boolean notState(final xPluginState state) {
		if (state == null) throw new RequiredArgumentException("state");
		return ! state.equals(this.state.get());
	}



	@Override
	public boolean isRunning() {
		return xPluginState.RUNNING.equals( this.state.get() );
	}
	@Override
	public boolean isStopping() {
		final xPluginState state = this.state.get();
		if (state == null)
			return true;
		switch (state) {
		case INIT:
		case START:
		case RUNNING:
			return false;
		case STOP:
		case UNLOAD:
		case FAILED:
			return true;
		default:
			throw new RuntimeException("Unknown plugin manager state: "+state.toString());
		}
	}



	public boolean isFailed() {
		return xPluginState.FAILED.equals( this.state.get() );
	}
	public boolean notFailed() {
		return ! this.isFailed();
	}



	public boolean fail(final String msg) {
		final xPluginState previousState =
			this.state.getAndSet(xPluginState.FAILED);
		// has already failed
		if (xPluginState.FAILED.equals(previousState))
			return false;
		// fail the plugin
		this.log()
			.severe(
				"Plugin {} has failed!",
				this.getPluginName(),
				msg
			);
		xThreadPool_Main.get()
			.runTaskLater(
				"fail plugin "+this.getPluginName(),
				new RemappedMethod<Object>(this, "fail")
			);
		final xThreadPool_Main pool = xThreadPool_Main.get();
		if (previousState != null) {
			switch (previousState) {
			case RUNNING:
				pool.runTaskLater(
					"stop plugin "+this.getPluginName(),
					new RemappedMethod<Object>(this, "stop")
				);
			case INIT:
				pool.runTaskLater(
					"unload plugin "+this.getPluginName(),
					new RemappedMethod<Object>(this, "unload")
				);
			default:
				this.log().trace(
					new IllegalStateException("Unknown plugin state: "+previousState.toString())
				);
			}
		}
//TODO:
//		this.manager.get()
//			.unregister(this);
		return true;
	}
	public boolean fail() {
		return this.fail(null);
	}



	// ------------------------------------------------------------------------------- //
	// config



	public String getPluginName() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getPluginName();
	}



	public String getPluginVersion() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getPluginVersion();
	}
	public String getRequiredAppVersion() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getRequiredAppVersion();
	}



	public String getCommit() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getCommit();
	}
	public String getCommitShort() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getCommitShort();
	}
	public String getCommitShort(final int size) {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getCommitShort(size);
	}



	public String getAuthor() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getAuthor();
	}
	public String getWebsite() {
		final xPluginYML yml = this.yml.get();
		if (yml == null) throw new RuntimeException("yml not set!");
		return yml.getWebsite();
	}



//TODO:
//	public String[] getPluginDepends() {
//throw new UnsupportedOperationException();
//	}
//	public String[] getPluginSoftDepends() {
//throw new UnsupportedOperationException();
//	}



	public void setVars(final JarClassLoader jcl,
			final xPluginManager<?> manager, final xPluginYML yml) {
		if (jcl     == null) throw new RequiredArgumentException("jcl");
		if (manager == null) throw new RequiredArgumentException("manager");
		if (yml     == null) throw new RequiredArgumentException("yml");
		if ( ! this.jcl.compareAndSet(null, jcl) )
			throw new RuntimeException("jcl already set!");
		if ( ! this.manager.compareAndSet(null, manager) )
			throw new RuntimeException("manager already set!");
		if ( ! this.yml.compareAndSet(null, yml) )
			throw new RuntimeException("yml already set!");
	}



	public JarClassLoader getClassLoader() {
		return this.jcl.get();
	}
	public xPluginManager<?> getPluginManager() {
		return this.manager.get();
	}
	public xPluginYML getYML() {
		return this.yml.get();
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<SoftReference<xLog>> _log =
			new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached logger
		final SoftReference<xLog> ref = this._log.get();
		if (ref != null) {
			final xLog log = ref.get();
			if (log != null)
				return log;
		}
		// get logger
		{
			final xLog log = this._log();
			this._log.set(
				new SoftReference<xLog>( log )
			);
			return log;
		}
	}
	protected xLog _log() {
		return
			xLogRoot.Get(
				"Plugin:"+this.getPluginName()
			);
	}



}
