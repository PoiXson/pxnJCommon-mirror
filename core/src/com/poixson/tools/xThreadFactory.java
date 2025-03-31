/*
package com.poixson.tools;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class xThreadFactory implements ThreadFactory {

	protected final String  name;
	protected final ThreadGroup group;
	protected final boolean daemon;
	protected final AtomicInteger priority = new AtomicInteger(Thread.NORM_PRIORITY);

	protected final CopyOnWriteArraySet<Thread> threads = new CopyOnWriteArraySet<Thread>();
	protected final AtomicLong threadIndexCount = new AtomicLong(0L);

	protected final CoolDown cool = new CoolDown();



	public xThreadFactory(final String name) {
		this(
			name,
			false
		);
	}
	public xThreadFactory(final String name, final boolean daemon) {
		this(
			name,
			null,
			daemon,
			Thread.NORM_PRIORITY
		);
	}
	public xThreadFactory(final String name, final ThreadGroup group, final boolean daemon, final int priority) {
		this.name     = name;
		this.group    = group;
		this.daemon   = daemon;
		this.priority.set(priority);
		this.cool.setDuration(1000L);
	}



	@Override
	public Thread newThread(final Runnable run) {
		this.cleanupThreads();
		final long index = this.getNextThreadIndex();
		final Thread thread = new Thread(this.group, run);
		thread.setPriority(this.priority.get());
		thread.setDaemon(this.daemon);
		thread.setName(
			(new StringBuilder()).append(this.name).append(':').append(index).toString()
		);
		return thread;
	}



	public void cleanupThreads() {
		if (!this.cool.again()) return;
		final Iterator<Thread> it = this.threads.iterator();
		while (it.hasNext()) {
			final Thread thread = it.next();
			if (!thread.isAlive()) {
				this.threads.remove(thread);
			}
		}
	}



	public void setPriority(final int value) {
		this.priority.set(value);
		this.group.setMaxPriority(value);
	}



	public long getNextThreadIndex() {
		return this.threadIndexCount.incrementAndGet();
	}



}
*/
