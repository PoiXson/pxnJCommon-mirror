package com.poixson.app.commands;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xApp;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.events.xHandler;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xCommandProcessor extends xHandler<xCommand> {

	protected final xApp app;

	protected final AtomicReference<xCommandDAO[]> commands = new AtomicReference<xCommandDAO[]>(null);



	public xCommandProcessor(final xApp app) {
		super(xCommand.class);
		this.app = app;
	}



	@Override
	public int register(final Object...objects) {
		final int count =
			super.register(objects);
		if (count > 0)
			xLog.Get().fine("Found %d commands", count);
		return count;
	}



	@Override
	protected boolean register(
			final Object object, final Method method, final xCommand anno) {
		if (object == null) throw new RequiredArgumentException("object");
		if (method == null) throw new RequiredArgumentException("method");
		if (anno == null)   throw new RequiredArgumentException("anno");
		if ( ! (anno instanceof xCommand) )
			throw new IllegalArgumentException( "Invalid annotation type: " + anno.getClass().getName() );
		final xCommand cmd = (xCommand) anno;
		final xCommandDAO dao =
			new xCommandDAO(
				cmd.Name(),
				StringUtils.Split(anno.Aliases(), ','),
				object, method
			);
		//ADD_LOOP:
		for (int loops=0; loops<10; loops++) {
			final xCommandDAO[] previous = this.commands.get();
			if (previous == null) {
				final xCommandDAO[] result = new xCommandDAO[] { dao };
				if (this.commands.compareAndSet(previous, result))
					return true;
			} else {
				final int len = previous.length;
				final xCommandDAO[] result = new xCommandDAO[len+1];
				for (int i=0; i<len; i++)
					result[i] = previous[i];
				result[len] = dao;
				if (this.commands.compareAndSet(previous, result))
					return true;
			}
		} // end ADD_LOOP
		throw new RuntimeException("Failed to add command to array");
	}



	@Override
	public void unregister(final Object object) {
		if (object == null) return;
		//REMOVE_LOOP:
		for (int loops=0; loops<10; loops++) {
			final xCommandDAO[] previous = this.commands.get();
			if (previous == null)
				return;
			final LinkedList<xCommandDAO> result = new LinkedList<xCommandDAO>();
			for (final xCommandDAO cmd : previous) {
				if (!cmd.isObject(object))
					result.add(cmd);
			}
			if (this.commands.compareAndSet(previous, result.toArray(new xCommandDAO[0])))
				return;
		} // end ADD_LOOP
		throw new RuntimeException("Failed to remove command from array");
	}
	@Override
	public void unregister(final Object object, final String methodName) {
		if (object == null || Utils.isEmpty(methodName)) return;
		//REMOVE_LOOP:
		for (int loops=0; loops<10; loops++) {
			final xCommandDAO[] previous = this.commands.get();
			if (previous == null)
				return;
			final LinkedList<xCommandDAO> result = new LinkedList<xCommandDAO>();
			for (final xCommandDAO cmd : previous) {
				if (!cmd.isMethod(object, methodName))
					result.add(cmd);
			}
			if (this.commands.compareAndSet(previous, result.toArray(new xCommandDAO[0])))
				return;
		} // end ADD_LOOP
		throw new RuntimeException("Failed to remove command from array");
	}
	@Override
	public void unregisterAll() {
		this.commands.set(null);
	}

	public void replace(final xCommandDAO[] commands) {
		this.commands.set(commands);
	}



	public xCommandDAO[] getCommands() {
		// stored commands
		{
			final xCommandDAO[] cmds = this.commands.get();
			if (cmds != null)
				return cmds;
		}
		// load root commands
		this.register(this.app.getCommands());
		return this.commands.get();
	}



	public boolean process(final String line) {
		if (Utils.isEmpty(line))
			return false;
		final xCommandDAO dao =
			this.findCommand(
				StringUtils.FirstPart(line, ' ')
			);
		if (dao == null)
			return false;
		dao.invoke(line);
		return true;
	}
	protected xCommandDAO findCommand(final String cmd) {
		if (Utils.isEmpty(cmd)) return null;
		// find matching command
		{
			final xCommandDAO[] cmds = this.getCommands();
			for (final xCommandDAO dao : cmds) {
				if (dao.isCommand(cmd))
					return dao;
			}
		}
		// find matching alias
		{
			final xCommandDAO[] cmds = this.getCommands();
			for (final xCommandDAO dao : cmds) {
				if (dao.isAlias(cmd))
					return dao;
			}
		}
		// no match
		return null;
	}



}
