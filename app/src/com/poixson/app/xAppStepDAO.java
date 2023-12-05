package com.poixson.app;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.app.xAppStep.PauseWhen;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.abstractions.RunnableNamed;
import com.poixson.utils.StringUtils;


public class xAppStepDAO implements RunnableNamed {

	public final xApp     app;
	public final xAppStep anno;
	public final int      step;
	public final int      step_abs;
	public final xAppStepType type;

	public final boolean multi;
	public final PauseWhen pause;
	public final AtomicInteger loop_count = new AtomicInteger(0);

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
		this.step_abs = Math.abs(anno.step());
		this.step = (
			xAppStepType.STARTUP.equals(this.type)
			? anno.step()
			: 0 - this.step_abs
		);
		this.multi = anno.multi();
		this.pause = anno.pause();
		if (!IsEmpty(anno.title())) {
			this.title = anno.title();
		} else {
			final String name =
				StringUtils.sifTrim(
					method.getName(),
					"_",
					"startup",
					"start",
					"shutdown",
					"stop"
				);
			this.title = (IsEmpty(name) ? method.getName() : name);
		}
		this.titleFull =
			String.format(
				"%s-%d-%s",
				this.type.name(),
				this.step_abs,
				this.title
			);
		this.container = container;
		this.method    = method;
	}



	// -------------------------------------------------------------------------------
	// run the step



	@Override
	public void run() {
		if (this.app.isFailed()) return;
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
				} catch (IllegalArgumentException ignore2) {
					// (xAppStepDAO)
					try {
						this.method.invoke(this.container, this);
					} catch (IllegalArgumentException e) {
						// method with arguments not found/supported
						throw new RuntimeException("Method arguments not supported: "+this.method.getName(), e);
					} // end (xAppStepDAO)
				} // end (log)
			} // end ()
		} catch (xAppStepMultiFinishedException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof xAppStepMultiFinishedException)
				throw (xAppStepMultiFinishedException) cause;
			throw new RuntimeException(e);
		} finally {
			currentThread.setName(originalThreadName);
		}
	}



	// -------------------------------------------------------------------------------
	// step params



	// startup/shutdown type
	public boolean isType(final xAppStepType type) {
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

	public boolean isFirstLoop() {
		return (this.loop_count.get() == 1);
	}



	// -------------------------------------------------------------------------------
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
	public boolean equalsTaskName(final String name) {
		return StringUtils.MatchString(name, this.title)
			|| StringUtils.MatchString(name, this.titleFull);
	}



	// -------------------------------------------------------------------------------
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



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return this.app.log()
				.getWeak(this.titleFull);
	}



}
