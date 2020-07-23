package com.poixson.app;

import static com.poixson.logger.xLog.XLog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xAppStep.StepType;
import com.poixson.logger.xLog;
import com.poixson.utils.Utils;


public class xAppStepLoader {

	protected final xApp app;
	protected final StepType type;

	protected final HashMap<Integer, List<xAppStepDAO>> steps =
			new HashMap<Integer, List<xAppStepDAO>>();



	public xAppStepLoader(final xApp app, StepType type) {
		this.app  = app;
		this.type = type;
		this.scanObject(app);
	}



	public void scanObjects(final Object[] containers) {
		if (containers.length == 1) {
			this.scanObject(containers[0]);
		} else
		if (containers.length > 1) {
			for (final Object container : containers) {
				this.scanObject(container);
			}
		}
	}
	public void scanObject(final Object container) {
		if (container == null) return;
		final Class<?> clss = container.getClass();
		if (clss == null) throw new RuntimeException("Failed to get step container class");
		final Method[] methods = clss.getMethods();
		if (Utils.isEmpty(methods)) throw new RuntimeException("No methods found in step container class: "+clss.getSimpleName());
		METHODS_LOOP:
		for (final Method m : methods) {
			final xAppStep anno = m.getAnnotation(xAppStep.class);
			if (anno == null)
				continue METHODS_LOOP;
			// found step method
			if (this.type.equals(anno.type())) {
				final xAppStepDAO dao =
					new xAppStepDAO(
						this.app,
						anno,
						container,
						m
					);
				this.addStep(dao);
			}
		} // end METHODS_LOOP
	}



	// ------------------------------------------------------------------------------- //
	// step queue



	public void addStep(final xAppStepDAO dao) {
		// add to list or new list
		this.steps.computeIfAbsent(
			Integer.valueOf(dao.step),
			key -> new ArrayList<xAppStepDAO>()
		).add(dao);
	}
	public xAppStepDAO getNextStep() {
		if (this.steps.isEmpty())
			return null;
		// find lowest step
		int lowest = Integer.MAX_VALUE;
		{
			final Iterator<Integer> it = this.steps.keySet().iterator();
			while (it.hasNext()) {
				final int value = it.next().intValue();
				if (value < lowest)
					lowest = value;
			}
			if (lowest == Integer.MAX_VALUE)
				throw new RuntimeException("Failed to find lowest step value");
		}
		// get step from group
		final List<xAppStepDAO> list = this.steps.get(Integer.valueOf(lowest));
		if (list.isEmpty()) {
			this.steps.remove(lowest);
			return this.getNextStep();
		}
		final xAppStepDAO dao = list.remove(0);
		if (list.isEmpty()) {
			this.steps.remove(lowest);
		}
		return dao;
	}



	// ------------------------------------------------------------------------------- //
	// state



	public boolean isStartup() {
		return StepType.STARTUP.equals(this.type);
	}
	public boolean isShutdown() {
		return StepType.SHUTDOWN.equals(this.type);
	}



	public boolean isEmpty() {
		return this.steps.isEmpty();
	}
	public boolean notEmpty() {
		return ! this.steps.isEmpty();
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<xLog> _log = new AtomicReference<xLog>(null);

	public xLog log() {
		if (this._log.get() == null) {
			final xLog log = this._log();
			if (this._log.compareAndSet(null, log))
				return log;
		}
		return this._log.get();
	}
	protected xLog _log() {
		return XLog().getWeak(this.type.toString());
	}



}
