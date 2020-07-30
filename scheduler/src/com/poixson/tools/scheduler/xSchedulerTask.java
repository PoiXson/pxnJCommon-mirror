package com.poixson.tools.scheduler;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.abstractions.xEnableable;
import com.poixson.tools.abstractions.xRunnable;
import com.poixson.tools.scheduler.trigger.Trigger;
import com.poixson.utils.Utils;


public class xSchedulerTask extends xRunnable implements xEnableable {

	protected static final AtomicInteger nextTaskIndex = new AtomicInteger(0);
	public final int taskIndex;

	protected final AtomicReference<xScheduler> sched = new AtomicReference<xScheduler>(null);
	protected final AtomicReference<xThreadPool> pool = new AtomicReference<xThreadPool>(null);

	protected final AtomicBoolean enabled = new AtomicBoolean(true);
	protected final AtomicBoolean lazy    = new AtomicBoolean(false);

	protected final CopyOnWriteArraySet<Trigger> triggers = new CopyOnWriteArraySet<Trigger>();

	protected final AtomicLong runCount = new AtomicLong(0L);



	public xSchedulerTask() {
		super();
		this.taskIndex = nextTaskIndex.getAndIncrement();
	}
	public xSchedulerTask(final Trigger...triggers) {
		super();
		this.add(triggers);
		this.taskIndex = nextTaskIndex.getAndIncrement();
	}
	public xSchedulerTask(final String taskName, final Runnable run) {
		super(taskName, run);
		this.taskIndex = nextTaskIndex.getAndIncrement();
	}
	public xSchedulerTask(final xRunnable run) {
		super(run);
		this.taskIndex = nextTaskIndex.getAndIncrement();
	}



	// ------------------------------------------------------------------------------- //
	// trigger



	public void doTrigger() {
		if (this.notEnabled()) return;
		this.runCount.incrementAndGet();
		final xThreadPool pool = this.getPoolOrMain();
		if (this.isLazy()) {
			pool.runTaskLazy(this);
		} else {
			pool.runTaskLater(this);
		}
	}



	public long untilNext(final long now) {
		if (this.notEnabled())
			return Long.MIN_VALUE;
		if (this.notRepeat()) {
			if (this.runCount.get() > 0) {
				this.setEnabled(false);
				return Long.MIN_VALUE;
			}
		}
		if (this.triggers.isEmpty())
			return Long.MIN_VALUE;
		final Iterator<Trigger> it = this.triggers.iterator();
		long next = Long.MAX_VALUE;
		TRIGGERS_LOOP:
		while (it.hasNext()) {
			final Trigger trigger = it.next();
			if (trigger.notEnabled())
				continue TRIGGERS_LOOP;
			final long untilNext = trigger.untilNext(now);
			if (untilNext == Long.MIN_VALUE)
				continue TRIGGERS_LOOP;
			if (next > untilNext) {
				next = untilNext;
				if (next <= 0L)
					return 0L;
			}
		} // end TRIGGERS_LOOP
		if (next == Long.MAX_VALUE)
			return Long.MIN_VALUE;
		return next;
	}



	public Trigger[] getTriggers() {
		return this.triggers.toArray(new Trigger[0]);
	}
	public int getTriggersCount() {
		return this.triggers.size();
	}
	public void add(final Trigger trigger) {
		if (trigger == null) throw new RequiredArgumentException("trigger");
		this.triggers.add(trigger);
	}
	public void add(final Trigger...triggers) {
		if (triggers.length == 0) throw new RequiredArgumentException("triggers");
		for (final Trigger trigger : triggers) {
			this.triggers.add(trigger);
		}
	}
	public void clearTriggers() {
		this.triggers.clear();
	}



	// ------------------------------------------------------------------------------- //
	// configs



	@Override
	public String getTaskName() {
		final String name = super.getTaskName();
		if (Utils.notEmpty(name))
			return name;
		return (new StringBuilder()).append("task").append(this.taskIndex).toString();
	}



	// scheduler
	public xScheduler getScheduler() {
		return this.sched.get();
	}
	public void setScheduler(final xScheduler sched) {
		this.sched.set(sched);
	}



	// thread pool
	public xThreadPool getPool() {
		return this.pool.get();
	}
	public xThreadPool getPoolOrMain() {
		final xThreadPool pool = this.getPool();
		if (pool == null)
			return xThreadPool_Main.Get();
		return pool;
	}
	public void setPool(final xThreadPool pool) {
		this.pool.set(pool);
	}



	// enabled
	@Override
	public boolean isEnabled() {
		if (!this.enabled.get())
			return false;
		final Iterator<Trigger> it = this.triggers.iterator();
		while (it.hasNext()) {
			final Trigger trigger = it.next();
			if (trigger.isEnabled())
				return true;
		}
		return false;
	}
	@Override
	public boolean notEnabled() {
		return ! this.isEnabled();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
	}
	@Override
	public void setEnabled() {
		this.setEnabled(true);
	}
	@Override
	public void setDisabled() {
		this.setEnabled(false);
	}



	// repeating triggers
	public boolean isRepeat() {
		final Iterator<Trigger> it = this.triggers.iterator();
		while (it.hasNext()) {
			if (it.next().isRepeat())
				return true;
		}
		return false;
	}
	public boolean notRepeat() {
		return ! this.isRepeat();
	}



	// run lazy
	public boolean isLazy() {
		return this.lazy.get();
	}
	public boolean notLazy() {
		return ! this.lazy.get();
	}
	public void setLazy(final boolean lazy) {
		this.lazy.set(lazy);
	}
	public void setLazy() {
		this.setLazy(true);
	}



	// ------------------------------------------------------------------------------- //
	// stats



	// task run count
	public long getRunCount() {
		return this.runCount.get();
	}
	public long resetRunCount() {
		return this.runCount
				.getAndSet(0L);
	}



}
