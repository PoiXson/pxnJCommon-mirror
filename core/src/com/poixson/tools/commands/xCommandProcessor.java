package com.poixson.tools.commands;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.utils.StringUtils;


public class xCommandProcessor {

	protected final AtomicReference<xCommandGroup> group = new AtomicReference<xCommandGroup>(null);



	public xCommandProcessor() {
	}



	public boolean process(final String line) {
		if (IsEmpty(line)) return false;
		final xCommandEvent event = new xCommandEvent(line);
		final xCommandDAO[] cmds = this.getCommands();
		final String first = StringUtils.FirstPart(line, ' ');
		if (IsEmpty(first)) return false;
		// command name
		for (final xCommandDAO dao : cmds) {
			if (dao.isName(first)) {
				if (!event.isHandled() || !dao.ignore_handled) {
					try {
						dao.invoke(event);
					} catch (Exception e) {
						this.log().trace(e);
					}
					if (dao.auto_handled)
						event.setHandled();
				}
			}
		}
		// command aliases
		for (final xCommandDAO dao : cmds) {
			if (dao.isAlias(first)) {
				if (!event.isHandled() || !dao.ignore_handled) {
					try {
						dao.invoke(event);
					} catch (Exception e) {
						this.log().trace(e);
					}
					if (dao.auto_handled)
						event.setHandled();
				}
			}
		}
		return event.isHandled();
	}



	protected final xCommandDAO[] getCommands() {
		final xCommandGroup group = this.group.get();
		if (group == null)
			return new xCommandDAO[0];
		return group.getCommands();
	}



	public xCommandGroup getGroup() {
		return this.group.get();
	}
	public xCommandGroup setGroup(final xCommandGroup group) {
		return this.group.getAndSet(group);
	}



//TODO: remove this?
/*
	public static xCommandProcessor GetProcessor() {
		final xLog log = xLog.Get();
		final xConsole console = log.getConsole();
		return console.getProcessor();
	}

	public static xCommandGroup GetActiveCommandsGroup() {
		return GetProcessor().getGroup();
	}
	public static xCommandGroup SetActiveCommandsGroup(final xCommandGroup group) {
		return GetProcessor().setGroup(group);
	}

	public static String GetPrompt() {
		final xLog log = xLog.Get();
		final xConsole console = log.getConsole();
		return console.getPrompt();
	}
	public static String SetPrompt(final String prompt) {
		final xLog log = xLog.Get();
		final xConsole console = log.getConsole();
		return console.setPrompt(prompt);
	}
*/



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return xLog.Get();
	}



}
