/*
package com.poixson.tools.commands;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.exceptions.RequiredArgumentException;


public class xCommandGroup {

	protected final CopyOnWriteArraySet<xCommandDAO> commands = new CopyOnWriteArraySet<xCommandDAO>();



	public xCommandGroup() {
	}



	public xCommandDAO[] getCommands() {
		return this.commands.toArray(new xCommandDAO[0]);
	}



	public int scan(final Object obj) {
		if (obj == null) throw new RequiredArgumentException("obj");
		int count = 0;
		final Method[] methods = obj.getClass().getMethods();
		if (IsEmpty(methods)) return 0;
		LOOP_METHODS:
		for (final Method m : methods) {
			if (m == null) continue LOOP_METHODS;
			final xCommand anno = m.getAnnotation(xCommand.class);
			if (anno == null) continue LOOP_METHODS;
			final xCommandDAO dao = new xCommandDAO(obj, m, anno);
			this.add(dao);
			count++;
		}
		return count;
	}



	public void add(final xCommandDAO cmd) {
		this.commands.add(cmd);
	}
	public boolean remove(final String cmd) {
		for (final xCommandDAO dao : this.commands) {
			if (dao.isCommand(cmd)) {
				this.commands.remove(dao);
				return true;
			}
		}
		return false;
	}



}
*/
