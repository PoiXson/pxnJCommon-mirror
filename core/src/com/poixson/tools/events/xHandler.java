package com.poixson.tools.events;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;


public abstract class xHandler <T extends Annotation> {

	protected final Class<T> type;



	public xHandler(final Class<T> type) {
		if (type == null) throw new RequiredArgumentException("type");
		this.type = type;
	}



	public int register(final Object...objects) {
		if (IsEmpty(objects)) return 0;
		int count = 0;
		OBJECTS_LOOP:
		for (final Object obj : objects) {
			if (obj == null) continue OBJECTS_LOOP;
			count += this.register(obj);
		}
		return count;
	}
	protected int register(final Object object) {
		if (object == null) return 0;
		final Method[] methods = object.getClass().getMethods();
		if (IsEmpty(methods)) return 0;
		int count = 0;
		METHODS_LOOP:
		for (final Method m : methods) {
			if (m == null) continue METHODS_LOOP;
			final T anno = m.getAnnotation(this.type);
			if (anno == null) continue METHODS_LOOP;
			// found annotation
			if (this.register(object, m, anno))
				count++;
		}
		return count;
	}
	protected abstract boolean register(final Object object, final Method method, final T anno);



	public abstract void unregister(final Object object);
	public abstract void unregister(final Object object, final String methodName);
	public abstract void unregisterAll();



}
