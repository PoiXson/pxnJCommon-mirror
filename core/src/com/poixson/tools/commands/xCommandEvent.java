package com.poixson.tools.commands;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;


public class xCommandEvent {

	public final String[] args;
	protected final boolean isHelp;
	protected final AtomicBoolean handled = new AtomicBoolean(false);



	public xCommandEvent(final String line) {
		final String[] parts = line.split(" ");
		final LinkedList<String> args = new LinkedList<String>();
		boolean isHelp = false;
		ARGS_LOOP:
		for (final String part : parts) {
			if ("-h".equals(part) || "--help".equals(part)) {
				isHelp = true;
				continue ARGS_LOOP;
			}
		} // end ARGS_LOOP
		this.args = args.toArray(new String[0]);
		this.isHelp = isHelp;
	}
	public xCommandEvent(final String[] args, final boolean isHelp) {
		this.args   = args;
		this.isHelp = isHelp;
	}



	public boolean isHandled() {
		return this.handled.get();
	}
	public boolean setHandled() {
		return this.handled.getAndSet(true);
	}



	public boolean isHelp() {
		return this.isHelp;
	}



}
