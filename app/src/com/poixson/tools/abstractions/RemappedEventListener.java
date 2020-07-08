package com.poixson.tools.abstractions;

import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.StringParts;
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
		Method method = null;
		final Class<?> clss = this.container.getClass();
		// method(event)
		try {
			method = clss.getMethod(methodStr, eventClass);
		} catch (NoSuchMethodException e) {
			method = null;
		} catch (SecurityException e) {
			method = null;
		}
		// method()
		if (method == null) {
			try {
				method = clss.getMethod(methodStr);
			} catch (NoSuchMethodException e) {
				method = null;
			} catch (SecurityException e) {
				method = null;
			}
		}
		// method not found
		if (method == null) {
			this.log().trace(
				new NoSuchMethodException(
					StringUtils.ReplaceTags(
						"Method not found: {}::{}({})",
						clss.getName(),
						methodStr,
						eventClass.getName()
					)
				)
			);
			return null;
		}
		this.log()
			.detail(
				"New {} created for: {}::{}({})",
				this.getListenerName(),
				clss.getName(),
				methodStr,
				eventClass.getName()
			);
		return method;
	}



	public String getListenerName() {
		final String name = StringParts.PeekLastPart(this.getClass().getName(), '.');
		return StringUtils.TrimFront(name, "Remapped");
	}



	// logger
	public xLog log() {
		return guiUtils.log();
	}



}
