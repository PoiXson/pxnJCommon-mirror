package com.poixson.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.app.xAppStep.PauseWhen;
import com.poixson.app.xAppStep.StepType;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.abstractions.RunnableNamed;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xAppStepDAO implements RunnableNamed {

	public final xApp     app;
	public final xAppStep anno;
	public final int      step;
	public final StepType type;
	public final boolean  multi;
	public final PauseWhen pause;

	public final String title;
	public final String titleFull;

	public final Object container;
	public final Method method;



	public xAppStepDAO(final xApp app, final xAppStep anno,
			final Object container, final Method method) {
		if (app       == null) throw new RequiredArgumentException("app");
		if (anno      == null) throw new RequiredArgumentException("annotation");
		if (container == null) throw new RequiredArgumentException("container");
		if (method    == null) throw new RequiredArgumentException("method");
		this.app  = app;
		this.anno = anno;
		this.type = anno.type();
		this.step =
			(
				StepType.STARTUP.equals(this.type)
				? anno.step()
				: 0 - Math.abs(anno.step())
			);
		this.multi = anno.multi();
		this.pause = anno.pause();
		if (Utils.notEmpty(anno.title())) {
			this.title = anno.title();
		} else {
			final String name =
				StringUtils.iTrimFront(
					method.getName(),
					"_",
					"startup",
					"start",
					"shutdown",
					"stop"
				);
			if (Utils.notEmpty(name)) {
				this.title = name;
			} else {
				this.title = method.getName();
			}
		}
		this.titleFull =
			StringUtils.MergeStrings('-',
				this.type.name(),
				Integer.toString(this.step),
				this.title
			);
		this.container = container;
		this.method    = method;
	}



	// ------------------------------------------------------------------------------- //
	// run the step



	@Override
	public void run() {
		if (this.app.hasFailed()) return;
		final Thread currentThread = Thread.currentThread();
		final String originalThreadName = currentThread.getName();
		currentThread.setName(this.getTaskName());
		try {
			// ()
			try {
				this.method.invoke(this.container);
			} catch (IllegalArgumentException ignore1) {
				final xLog log = this.log();
				// (log)
				try {
					this.method.invoke(this.container, log);
				} catch (IllegalArgumentException e) {
					// method with arguments not found/supported
					throw new RuntimeException("Method arguments not supported: "+this.method.getName(), e);
				} // end (log)
			} // end ()
		} catch (RuntimeException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		currentThread.setName(originalThreadName);
	}



	// ------------------------------------------------------------------------------- //



	// startup/shutdown type
	public boolean isType(final StepType type) {
		if (type == null)
			return false;
		return type.equals(this.type);
	}



	// step value
	public boolean isStepValue(final int step) {
		return (this.step == step);
	}



	// multi step
	public boolean isMultiStep() {
		return this.multi;
	}



	// pause before/after
	public boolean noPause() {
		return PauseWhen.NONE.equals(this.pause);
	}
	public boolean isPauseBefore() {
		return PauseWhen.BEFORE.equals(this.pause);
	}
	public boolean isPauseAfter() {
		return PauseWhen.AFTER.equals(this.pause);
	}


	// step name
	@Override
	public String getTaskName() {
		return this.titleFull;
	}
	@Override
	public void setTaskName(final String name) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean taskNameEquals(final String name) {
		return StringUtils.StrEquals(name, this.title)
			|| StringUtils.StrEquals(name, this.titleFull);
	}



	// ------------------------------------------------------------------------------- //
	// logger



	public xLog log() {
		return this.app.log()
				.getWeak(this.titleFull);
	}



}
