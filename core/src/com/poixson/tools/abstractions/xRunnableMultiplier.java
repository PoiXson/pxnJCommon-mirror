package com.poixson.tools.abstractions;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.exceptions.RequiredArgumentException;


public class xRunnableMultiplier extends xRunnable {

	protected final CopyOnWriteArraySet<xRunnable> runnables = new CopyOnWriteArraySet<xRunnable>();



	public xRunnableMultiplier() {
		super();
	}
	public xRunnableMultiplier(final String taskName) {
		super(taskName);
	}
	public xRunnableMultiplier(final xRunnable run) {
		super(run);
	}
	public xRunnableMultiplier(final Runnable run) {
		super(run);
	}
	public xRunnableMultiplier(final String taskName, final Runnable run) {
		super(taskName, run);
	}



	public void add(final xRunnable run) {
		if (run == null) throw new RequiredArgumentException("run");
		this.runnables.add(run);
	}
	public void remove(final xRunnable run) {
		if (run == null) throw new RequiredArgumentException("run");
		this.runnables.remove(run);
	}
	public void clear() {
		this.runnables.clear();
	}



	@Override
	public void run() {
		final Iterator<xRunnable> it = this.runnables.iterator();
		while (it.hasNext()) {
			final xRunnable run = it.next();
			run.run();
		}
	}



}
