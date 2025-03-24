package com.poixson.exceptions;


public class RequiredArgumentException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public static final String MSG_SHORT = "Argument is required";
	public static final String MSG_FULL  = "%s argument is required";



	public RequiredArgumentException() {
		super(MSG_SHORT);
	}
	public RequiredArgumentException(final String arg_name) {
		super(String.format(MSG_FULL, arg_name));
	}



}
