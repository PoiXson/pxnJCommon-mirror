package com.poixson.exceptions;

import static com.poixson.utils.StringUtils.ToString;


public class InvalidValueException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;



	public InvalidValueException(final String name) {
		super("Invalid value: "+name);
	}
	public InvalidValueException(final String name, final Object value) {
		super(String.format("Invalid value: %s = %s", name, ToString(value)));
	}



}
