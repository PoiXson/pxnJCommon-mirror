package com.poixson.tools.abstractions;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.exceptions.RequiredArgumentException;


public class RunnableMultiplier implements Runnable {

	private final CopyOnWriteArraySet<Runnable> runnables = new CopyOnWriteArraySet<Runnable>();



	public RunnableMultiplier() {
		super();
	}



	public void add(final Runnable run) {
		if (run == null) throw new RequiredArgumentException("run");
		this.runnables.add(run);
	}
	public void remove(final Runnable run) {
		if (run == null) throw new RequiredArgumentException("run");
		this.runnables.remove(run);
	}
	public void clear() {
		this.runnables.clear();
	}



	@Override
	public void run() {
		final Iterator<Runnable> it = this.runnables.iterator();
		while (it.hasNext()) {
			final Runnable run = it.next();
			run.run();
		}
	}



}
