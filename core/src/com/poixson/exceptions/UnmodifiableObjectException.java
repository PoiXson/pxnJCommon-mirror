package com.poixson.exceptions;

import java.util.Arrays;
import java.util.Iterator;

import com.poixson.utils.StringUtils;


public class UnmodifiableObjectException extends UnsupportedOperationException {
	private static final long serialVersionUID = 1L;



	private static String BuildMsg() {
		final Exception eTemp = new Exception();
		final StackTraceElement[] trace = eTemp.getStackTrace();
		final Iterator<StackTraceElement> it =
				Arrays.asList(trace).iterator();
		// find calling class
		while (it.hasNext()) {
			final StackTraceElement e = it.next();
			final String className = e.getClassName();
			if (!className.endsWith("UnmodifiableObjectException")) {
				return
					String.format(
						"Object cannot be modified! %s->%s()",
						StringUtils.LastPart(className, '.'),
						StringUtils.cTrim(e.getMethodName(), '<', '>')
					);
			}
		}
		return null;
	}



	public UnmodifiableObjectException() {
		super( BuildMsg() );
	}
	public UnmodifiableObjectException(final String msg) {
		super(msg);
	}



}
