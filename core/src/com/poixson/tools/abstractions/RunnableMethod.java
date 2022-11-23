package com.poixson.tools.abstractions;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.Utils;


public class RunnableMethod<V> extends xRunnable {

	public final Object container;
	public final Method method;
	public final Object[] args;

	protected final AtomicReference<V> result = new AtomicReference<V>(null);



	public RunnableMethod(final Object container,
			final String methodName, final Object...args) {
		if (container == null)         throw new RequiredArgumentException("container");
		if (Utils.isEmpty(methodName)) throw new RequiredArgumentException("method");
		this.method =
			ReflectUtils.getMethodByName(
				container,
				methodName,
				ReflectUtils.ArgsToClasses(args)
			);
		this.container = container;
		this.args      = args;
	}
	public RunnableMethod(final Object container,
			final Method method, final Object...args) {
		if (container == null) throw new RequiredArgumentException("container");
		if (method == null)    throw new RequiredArgumentException("method");
		this.container = container;
		this.method    = method;
		this.args      = args;
	}
	public RunnableMethod(final String taskName, final Object container,
			final String methodName, final Object...args) {
		if (container == null) throw new RequiredArgumentException("container");
		if (Utils.isEmpty(methodName)) throw new RequiredArgumentException("method");
		this.method =
			ReflectUtils.getMethodByName(
				container,
				methodName,
				ReflectUtils.ArgsToClasses(args)
			);
		this.container = container;
		this.args      = args;
		this.setTaskName(taskName);
	}
	public RunnableMethod(final String taskName, final Object container,
			final Method method, final Object...args) {
		if (container == null) throw new RequiredArgumentException("container");
		if (method == null)    throw new RequiredArgumentException("method");
		this.container = container;
		this.method    = method;
		this.args      = args;
		this.setTaskName(taskName);
	}



	// -------------------------------------------------------------------------------
	// run method



	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		this.runCount.getAndIncrement();
		this.result.set(
			(V) ReflectUtils.InvokeMethod(
				this.container,
				this.method,
				this.args
			)
		);
	}



	// -------------------------------------------------------------------------------



	public String getFullName() {
		return
			(new StringBuilder())
			.append( ReflectUtils.GetClassName(this.container) )
			.append("->")
			.append( this.method.getName())
			.append("()")
			.toString();
	}



	public V getResult() {
		if (this.notRun())
			return null;
		return this.result.get();
	}



}
