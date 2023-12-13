package com.poixson.tools.scheduler;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.threadpool.xThreadPool;
import com.poixson.tools.abstractions.xRunnable;
import com.poixson.tools.scheduler.trigger.xSchedTrigger;


public class xSchedulerTask extends xRunnable {

	protected final AtomicReference<xScheduler> sched = new AtomicReference<xScheduler>(null);

	protected final CopyOnWriteArraySet<xSchedTrigger> triggers = new CopyOnWriteArraySet<xSchedTrigger>();

	protected static final AtomicInteger TaskIndex = new AtomicInteger(0);
	public final int task_index;

	protected final AtomicBoolean enabled = new AtomicBoolean(true);
	protected final AtomicBoolean lazy    = new AtomicBoolean(false);

	protected final AtomicLong count_runs = new AtomicLong(0L);



	public xSchedulerTask() {
		super();
		this.task_index = TaskIndex.incrementAndGet();
//TODO: set task name somehow
//		this.setTaskName(String.format(
//			"%s:%s",
//"sched",
//			this.sched.getName(),
//			Integer.valueOf(this.task_index)
//		));
//return (new StringBuilder()).append("task").append(this.taskIndex).toString();
	}



	// -------------------------------------------------------------------------------
	// triggers



	public xSchedulerTask add(final xSchedTrigger trigger) {
		if (trigger == null) throw new RequiredArgumentException("trigger");
		this.triggers.add(trigger);
		return this;
	}
	public xSchedulerTask add(final xSchedTrigger...triggers) {
		if (triggers.length == 0) throw new RequiredArgumentException("triggers");
		for (final xSchedTrigger trigger : triggers)
			this.triggers.add(trigger);
		return this;
	}
	public xSchedulerTask clearTriggers() {
		this.triggers.clear();
		return this;
	}



	public xSchedTrigger[] getTriggers() {
		return this.triggers.toArray(new xSchedTrigger[0]);
	}
	public int getTriggersCount() {
		return this.triggers.size();
	}



	public long getUntilNext(final long now) {
		if (!this.isEnabled())
			return Long.MIN_VALUE;
		if (!this.isRepeat()) {
			if (this.count_runs.get() > 0) {
				this.setEnabled(false);
				return Long.MIN_VALUE;
			}
		}
		if (this.triggers.isEmpty())
			return Long.MIN_VALUE;
		{
			final Iterator<xSchedTrigger> it = this.triggers.iterator();
			long next = Long.MAX_VALUE;
			TRIGGERS_LOOP:
			while (it.hasNext()) {
				final xSchedTrigger trigger = it.next();
				if (!trigger.isEnabled())
					continue TRIGGERS_LOOP;
				final long until_next = trigger.getUntilNext(now);
				if (until_next == Long.MIN_VALUE)
					continue TRIGGERS_LOOP;
				if (next > until_next) {
					next = until_next;
					if (next <= 0L)
						return 0L;
				}
			} // end TRIGGERS_LOOP
			if (next == Long.MAX_VALUE)
				return Long.MIN_VALUE;
			return next;
		}
	}



	public void doTrigger() {
		if (this.isEnabled()) {
			this.count_runs.incrementAndGet();
			final xThreadPool pool = this.getThreadPool();
			if (this.isLazy()) pool.runTaskLazy(this);
			else               pool.runTaskLater(this);
		}
	}



	// -------------------------------------------------------------------------------



	public xThreadPool getThreadPool() {
		final xScheduler sched = this.getScheduler();
		return sched.getThreadPool();
	}



	public xScheduler getScheduler() {
		return this.sched.get();
	}
	public xSchedulerTask setScheduler(final xScheduler sched) {
		if (sched == null) throw new RequiredArgumentException("sched");
		if (!this.sched.compareAndSet(null, sched))
			throw new IllegalStateException("Scheduler already set");
		return this;
	}
	public xSchedulerTask unsetScheduler(final xScheduler sched) {
		if (!this.sched.compareAndSet(sched, null))
			throw new IllegalStateException("Unexpected scheduler assigned to task");
		return this;
	}



	public xSchedulerTask setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
		return this;
	}
	public xSchedulerTask setEnabled() {
		this.setEnabled(true);
		return this;
	}
	public xSchedulerTask setDisabled() {
		this.setEnabled(false);
		return this;
	}
	public boolean isEnabled() {
		if (!this.enabled.get())
			return false;
		final Iterator<xSchedTrigger> it = this.triggers.iterator();
		//TRIGGER_LOOP:
		while (it.hasNext()) {
			final xSchedTrigger trigger = it.next();
			if (trigger.isEnabled())
				return true;
		}
		return false;
	}



	public boolean isRepeat() {
		final Iterator<xSchedTrigger> it = this.triggers.iterator();
		//TRIGGER_LOOP:
		while (it.hasNext()) {
			if (it.next().isRepeat())
				return true;
		}
		return false;
	}



	public xSchedulerTask setLazy() {
		this.setLazy(true);
		return this;
	}
	public xSchedulerTask setLazy(final boolean lazy) {
		this.lazy.set(lazy);
		return this;
	}
	public boolean isLazy() {
		return this.lazy.get();
	}



	// -------------------------------------------------------------------------------
	// stats



	// task run count
	public long getRunCount() {
		return this.count_runs.get();
	}
	public long resetRunCount() {
		return this.count_runs.getAndSet(0L);
	}



}
