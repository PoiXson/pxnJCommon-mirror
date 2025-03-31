/*
package com.poixson.scripting;

import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.scripting.exceptions.JSFunctionNotFoundException;
import com.poixson.scripting.loader.xScriptLoader;
import com.poixson.scripting.loader.xScriptSourceDAO;


public class xScriptInstance extends xScript {

	protected final xScriptLoader loader;
	protected final boolean safe;

	protected final Scriptable scope;
	protected final AtomicReference<Script[]> compiled = new AtomicReference<Script[]>(null);

	protected final AtomicInteger count_loops = new AtomicInteger(0);



	public xScriptInstance(final xScriptLoader loader, final boolean safe) {
		super();
		this.loader = loader;
		this.safe   = safe;
		try {
			xScriptContextFactory.Init();
		} catch (IllegalStateException e) {
			if (!"zip file closed".equals(e.getMessage()))
				throw e;
		}
		this.scope = InitScope(safe);
		this.resetLastUsed();
	}



	// top level scope
	public static Scriptable InitScope(final boolean safe) {
		Context context = null;
		try {
			context = Context.enter();
			if (safe) return context.initStandardObjects(null, true);
			else      return new ImporterTopLevel(context);
		} finally {
			SafeClose(context);
		}
	}



	@Override
	public Object call(final String func_name, final Object... args)
			throws JSFunctionNotFoundException {
		if (this.stopping.get()) return null;
		if (IsEmpty(func_name)) throw new RequiredArgumentException("func");
		this.count_loops.incrementAndGet();
		this.active.set(true);
		this.resetLastUsed();
		final Context context = Context.enter();
		Object result = null;
		try {
			final Object func_obj = this.scope.get(func_name, this.scope);
			if (  func_obj == null             ) throw new JSFunctionNotFoundException(this.loader.getName(), func_name, func_obj);
			if (!(func_obj instanceof Function)) throw new JSFunctionNotFoundException(this.loader.getName(), func_name, func_obj);
			final Function func = (Function) func_obj;
			result = func.call(context, this.scope, this.scope, args);
		} catch (JSFunctionNotFoundException e) {
			throw e;
		} catch (Exception e) {
			this.stop();
			throw new RuntimeException(e);
		} finally {
			SafeClose(context);
			this.active.set(false);
			this.resetLastUsed();
		}
		return result;
	}



	public xScriptSourceDAO[] getSources() throws IOException {
		if (this.stopping.get()) return null;
		try {
			return this.loader.getSources();
		} catch (FileNotFoundException e) {
			this.stop();
			throw e;
		}
	}
	@Override
	protected void compile() {
		if (this.stopping.get()) return;
		this.resetLastUsed();
		final Context context = Context.enter();
		try {
			context.setLanguageVersion(Context.VERSION_ES6);
			final xScriptSourceDAO[] sources = this.getSources();
			final LinkedList<Script> list = new LinkedList<Script>();
			for (final xScriptSourceDAO src : sources) {
				final Script script = context.compileString(src.code, src.filename, 1, null);
				list.add(script);
			}
			if (this.stopping.get()) return;
			this.compiled.set(list.toArray(new Script[0]));
			// run scripts
			for (final Script src : list) {
				if (this.stopping.get()) return;
				this.resetLastUsed();
				src.exec(context, this.scope);
			}
		} catch (FileNotFoundException e) {
			// stop without script calls
			this.stopping.set(true);
			this.stop();
			throw new RuntimeException(e);
		} catch (Exception e) {
			// stop without script calls
			this.stopping.set(true);
			this.stop();
			throw new RuntimeException(e);
		} finally {
			SafeClose(context);
			this.resetLastUsed();
		}
	}



	// -------------------------------------------------------------------------------
	// variables



	@Override
	public xScript setVariable(final String key, final Object value) {
		this.scope.put(key, this.scope, value);
		return this;
	}
	@Override
	public Object getVariable(final String key) {
		return this.scope.get(key, this.scope);
	}



}
*/
