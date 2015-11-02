package com.poixson.commonjava.Utils;

import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;


public class xRunnable implements Runnable {

	protected volatile String taskName = null;
	protected final Runnable task;



	public xRunnable() {
		this.task = null;
	}
	public xRunnable(final String taskName) {
		this(taskName, null);
	}
	public xRunnable(final Runnable run) {
		this(null, run);
	}
	public xRunnable(final String taskName, final Runnable run) {
		if(utils.notEmpty(taskName))
			this.taskName = taskName;
		else
		// clone existing xRunnable
		if(run instanceof xRunnable)
			this.taskName = ((xRunnable) run).getTaskName();
		this.task = run;
	}



	public static xRunnable cast(final Runnable run) {
		return cast(null, run);
	}
	public static xRunnable cast(final String name, final Runnable run) {
		if(run == null) throw new RequiredArgumentException("run");
		if(run instanceof xRunnable) {
			final xRunnable xrun = (xRunnable) run;
			if(utils.notEmpty(name))
				xrun.setTaskName(name);
			return xrun;
		}
		return new xRunnable(
				utils.isEmpty(name) ? "<Runnable>" : name,
				run
		);
	}



	@Override
	public void run() {
		if(this.task == null) throw new RequiredArgumentException("task");
		this.task.run();
	}



	public String getTaskName() {
		return this.taskName;
	}
	public void setTaskName(final String name) {
		this.taskName = utils.isEmpty(name) ? null : name;
	}
	public boolean taskNameEquals(final String name) {
		if(utils.isEmpty(name))
			return utils.isEmpty(this.taskName);
		return name.equals(this.taskName);
	}



}
