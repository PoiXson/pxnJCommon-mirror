package com.poixson.tools.abstractions;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xRunnable implements RunnableNamed {

	public final Runnable task;

	protected final AtomicReference<String> taskName = new AtomicReference<String>(null);

	protected final AtomicLong    runCount = new AtomicLong(0);
	protected final AtomicInteger active   = new AtomicInteger(0);



	public xRunnable() {
		this(null, null);
	}
	public xRunnable(final String taskName) {
		this(taskName, null);
	}
	public xRunnable(final xRunnable run) {
		this(run.getTaskName(), run);
	}
	public xRunnable(final Runnable run) {
		this(null, run);
	}
	public xRunnable(final String taskName, final Runnable run) {
		if (Utils.notEmpty(taskName)) {
			this.taskName.set(taskName);
		} else
		if (run instanceof RunnableNamed) {
			this.taskName.set(
				((RunnableNamed) run).getTaskName()
			);
		}
		this.task = run;
	}



	// -------------------------------------------------------------------------------
	// cast



	public static xRunnable cast(final Runnable run) {
		if (run == null)
			return null;
		// already correct type
		if (run instanceof xRunnable)
			return (xRunnable) run;
		// get name from interface
		if (run instanceof RunnableNamed) {
			return
				new xRunnable(
					((RunnableNamed) run).getTaskName(),
					run
				);
		}
		return new xRunnable(run);
	}



	public Callable<?> toCallable() {
		return Executors.callable(this);
	}



	// -------------------------------------------------------------------------------
	// run task



	@Override
	public void run() {
		final Runnable task = this.task;
		if (task == null) throw new RequiredArgumentException("task");
		this.runCount.getAndIncrement();
		this.active.getAndIncrement();
		try {
			task.run();
		} finally {
			this.active.getAndDecrement();
		}
	}
	public void runOnce() {
		final Runnable task = this.task;
		if (task == null) throw new RequiredArgumentException("task");
		if (!this.runCount.compareAndSet(0, 1))
			return;
		this.active.getAndIncrement();
		try {
			task.run();
		} finally {
			this.active.getAndDecrement();
		}
	}



	// -------------------------------------------------------------------------------



	@Override
	public String getTaskName() {
		if (this.task != null) {
			if (this.task instanceof RunnableNamed) {
				final String taskName = ((RunnableNamed) this.task).getTaskName();
				if (Utils.notEmpty(taskName))
					return taskName;
			}
		}
		return this.taskName.get();
	}
	@Override
	public void setTaskName(final String taskName) {
		this.taskName.set(Utils.ifEmpty(taskName, null));
	}
	@Override
	public boolean equalsTaskName(final String matchName) {
		return StringUtils.MatchStringExact(this.getTaskName(), matchName);
	}



	public boolean hasRun() {
		return (this.runCount.get() > 0);
	}
	public boolean notRun() {
		return (this.runCount.get() == 0);
	}



	public boolean isActive() {
		return (this.active.get() > 0);
	}
	public boolean notActive() {
		return (this.active.get() == 0);
	}
	public int getActive() {
		return this.active.get();
	}



}
