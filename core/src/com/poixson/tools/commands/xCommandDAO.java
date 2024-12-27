package com.poixson.tools.commands;

import static com.poixson.utils.StringUtils.SplitByChars;
import static com.poixson.utils.Utils.IsEmpty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_Main;


public class xCommandDAO {

	public final String   name;
	public final String[] aliases;

	public final Object container;
	public final Method method;

	public final boolean auto_handled;
	public final boolean ignore_handled;



	public xCommandDAO(final Object container, final Method method, final xCommand anno) {
		this.container      = container;
		this.method         = method;
		this.name           = anno.Name();
		this.aliases        = SplitByChars(anno.Aliases(), ',');
		this.auto_handled   = anno.autoHandled();
		this.ignore_handled = anno.ignoreHandled();
	}



	public void invoke(final xCommandEvent event) {
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "invoke", event))
			return;
		xLog.Get().finest(
			"Invoking command: %s->%s >> %s",
			this.container.getClass().getName(),
			this.method.getName(),
			event.line
		);
		// method(event)
		try {
			this.method.invoke(this.container, event);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method(line)
		try {
			this.method.invoke(this.container, event.line);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method()
		try {
			this.method.invoke(this.container);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method with arguments not found/supported
		throw new RuntimeException(
			(new StringBuilder())
				.append("Method arguments not supported: ")
				.append(this.method.getName())
				.toString()
		);
	}



	public boolean isCommand(final String name) {
		if (IsEmpty(name)) return IsEmpty(this.name);
		for (final String alias : this.aliases) {
			if (alias.equals(name))
				return true;
		}
		return false;
	}
	public boolean isName(final String name) {
		if (IsEmpty(name)) return IsEmpty(this.name);
		return this.name.equals(name);
	}
	public boolean isAlias(final String alias) {
		if (IsEmpty(alias)) return false;
		for (final String a : this.aliases) {
			if (a.equals(alias))
				return true;
		}
		return false;
	}



}
