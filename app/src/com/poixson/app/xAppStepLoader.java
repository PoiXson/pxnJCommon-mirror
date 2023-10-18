package com.poixson.app;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.utils.Utils;


public class xAppStepLoader {

	protected final xApp app;
	protected final xAppStepType type;

	protected final Map<Integer, List<xAppStepDAO>> steps = new HashMap<Integer, List<xAppStepDAO>>();



	public xAppStepLoader(final xApp app, final xAppStepType type) {
		this.app  = app;
		this.type = type;
	}



	// -------------------------------------------------------------------------------
	// step queue



	public void scan(final Object[] containers) {
		if (containers.length == 1) {
			this.scan(containers[0]);
		} else
		if (containers.length > 1) {
			for (final Object container : containers)
				this.scan(container);
		}
	}
	public void scan(final Object container) {
		if (container == null) return;
		final Class<?> clss = container.getClass();
		if (clss == null) throw new RuntimeException("Failed to get step container class");
		final Method[] methods = clss.getMethods();
		if (Utils.isEmpty(methods)) throw new RuntimeException("No methods found in step container class: "+clss.getSimpleName());
		//METHODS_LOOP:
		for (final Method m : methods) {
			// @xAppMoreSteps
			final xAppMoreSteps anno_more = m.getAnnotation(xAppMoreSteps.class);
			if (anno_more != null) {
//				if (this.type.equals(anno_more.type())) {
//					final xAppStepDAO dao =
//						new xAppStepDAO(
//							this.app,
//							anno_step,
//							container,
//							m
//						);
//					this.add(dao);
//				}
				try {
					final Object[] results = (Object[]) m.invoke(container, this.type);
					if (results != null && results.length > 0) {
						for (final Object obj : results)
							this.scan(obj);
					}
				} catch (IllegalAccessException e) {
					this.log().trace(e);
				} catch (InvocationTargetException e) {
					this.log().trace(e);
				}
			}
			// @xAppStep(..)
			final xAppStep anno_step = m.getAnnotation(xAppStep.class);
			if (anno_step != null) {
				// found step method
				if (this.type.equals(anno_step.type())) {
					final xAppStepDAO dao =
						new xAppStepDAO(
							this.app,
							anno_step,
							container,
							m
						);
					this.add(dao);
				}
			}
		} // end METHODS_LOOP
	}



	public void add(final xAppStepDAO dao) {
//TODO: remove this
//System.out.println("FOUND STEP: " + dao.getTaskName());
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



	// -------------------------------------------------------------------------------
	// state



	public boolean isStartup() {
		return xAppStepType.STARTUP.equals(this.type);
	}
	public boolean isShutdown() {
		return xAppStepType.SHUTDOWN.equals(this.type);
	}



	public boolean isEmpty() {
		return this.steps.isEmpty();
	}
	public boolean notEmpty() {
		return ! this.steps.isEmpty();
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log != null)
					return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get(this.type.toString());
	}



}
