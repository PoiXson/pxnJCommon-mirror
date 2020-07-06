package com.poixson.tools.abstractions;

import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.guiUtils;


public abstract class RemappedEventListener {

	protected final Object container;
	protected final Method method;



	protected RemappedEventListener(final Object container,
			final String methodStr, final Class<?> eventClass) {
		if (container == null) throw new RequiredArgumentException("container");
		this.container = container;
		this.method = this.findMethod(methodStr, eventClass);
	}



	protected Method findMethod(final String methodStr, final Class<?> eventClass) {
		if (Utils.isEmpty(methodStr)) return null;
		if (eventClass == null)       return null;
		try {
			final Class<?> clss = this.container.getClass();
			final Method method = clss.getMethod(methodStr, eventClass);
			if (method == null) {
				throw new NoSuchMethodException(
					StringUtils.ReplaceTags(
						"Method not found: {}::{}({})",
						clss.getName(),
						methodStr,
						eventClass.getName()
					)
				);
			}
			this.log()
				.detail(
					"New ItemListener created for: {}::{}({})",
					clss.getName(),
					methodStr,
					eventClass.getName()
				);
			return method;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}



	// logger
	public xLog log() {
		return guiUtils.log();
	}



}
