package com.poixson.tools.commands;

import static com.poixson.utils.Utils.IfEmpty;
import static com.poixson.utils.Utils.IsEmpty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.events.xEventListenerDAO;


public class xCommandDAO extends xEventListenerDAO {

	public final String   name;
	public final String[] aliases;



	public xCommandDAO(final String name, final String[] aliases,
			final Object object, final Method method) {
		super(object, method);
		if (IsEmpty(name)) throw new RequiredArgumentException("name");
		this.name = name;
		this.aliases = IfEmpty(aliases, null);
	}



	public void invoke(final String line) {
		// only run in main thread
		if (xThreadPool_Main.Get().proper(this, "invoke", line))
			return;
		xLog.Get().finest(
			"Invoking command: %s->%s >> %s",
			super.object.getClass().getName(),
			super.method.getName(),
			line
		);
		// method(event)
		try {
			final xCommandEvent event = new xCommandEvent(line);
			this.method.invoke(this.object, event);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method(line)
		try {
			this.method.invoke(this.object, line);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method()
		try {
			this.method.invoke(this.object);
			return;
		} catch (IllegalAccessException ignore) {
		} catch (IllegalArgumentException ignore) {
		} catch (InvocationTargetException ignore) {}
		// method with arguments not found/supported
		throw new RuntimeException(
			(new StringBuilder())
				.append("Method arguments not supported: ")
				.append(super.method.getName())
				.toString()
		);
	}



	public boolean isCommand(final String cmd) {
		if (cmd == null)
			return false;
		return cmd.equals(this.name);
	}
	public boolean isAlias(final String cmd) {
		if (cmd == null)           return false;
		if (IsEmpty(this.aliases)) return false;
		for (final String alias : this.aliases) {
			if (cmd.equals(alias))
				return true;
		}
		return false;
	}



}
