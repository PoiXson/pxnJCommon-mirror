package com.poixson.exceptions;

import static com.poixson.utils.StringUtils.ToString;


public class InvalidValueException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public static final String MSG_NAME       = "Invalid value: %s";
	public static final String MSG_NAME_VALUE = "Invalid value: %s = %s";



	public InvalidValueException() {
		super();
	}
	public InvalidValueException(final String name) {
		super(String.format(MSG_NAME, name));
	}
	public InvalidValueException(final String name, final Object value) {
		super(String.format(MSG_NAME_VALUE, name, ToString(value)));
	}



}
