package com.poixson.exceptions;

import static com.poixson.utils.StringUtils.LastPart;
import static com.poixson.utils.StringUtils.cTrim;

import java.util.Arrays;
import java.util.Iterator;


public class UnmodifiableObjectException extends UnsupportedOperationException {
	private static final long serialVersionUID = 1L;

	public static final String MSG = "Object cannot be modified! %s->%s()";



	protected static String BuildMessage() {
		final StackTraceElement[] trace = (new Exception()).getStackTrace();
		final Iterator<StackTraceElement> it = Arrays.asList(trace).iterator();
		// find calling class
		while (it.hasNext()) {
			final StackTraceElement e = it.next();
			final String class_name = e.getClassName();
			if (!class_name.endsWith("UnmodifiableObjectException")) {
				return String.format(
					MSG,
					LastPart(class_name, '.'),
					cTrim(e.getMethodName(), '<', '>')
				);
			}
		}
		return null;
	}



	public UnmodifiableObjectException() {
		super(BuildMessage());
	}
	public UnmodifiableObjectException(final String msg) {
		super(msg);
	}



}
