package com.poixson.exceptions;

import com.poixson.utils.StringUtils;

public class InvalidValueException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;



	public InvalidValueException(final String name) {
		super("Invalid value: "+name);
	}
	public InvalidValueException(final String name, final Object value) {
		super("Invalid value: "+name+" = "+StringUtils.ToString(value));
	}



}
