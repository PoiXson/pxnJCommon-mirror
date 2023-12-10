package com.poixson.tools.commands;

import java.util.concurrent.atomic.AtomicBoolean;


public class xCommandEvent {

	public final String line;

	protected final AtomicBoolean handled = new AtomicBoolean(false);
	protected final boolean help;



	public xCommandEvent(final String line) {
		this.line = line;
		final String str = " "+line+"";
		this.help = str.contains(" -h ") || str.contains(" --help ");
	}



	public boolean setHandled() {
		return this.setHandled(true);
	}
	public boolean setHandled(final boolean handled) {
		return this.handled.getAndSet(handled);
	}
	public boolean isHandled() {
		return this.handled.get();
	}



	public boolean isHelp() {
		return this.help;
	}



}
