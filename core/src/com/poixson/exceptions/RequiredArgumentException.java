package com.poixson.exceptions;


public class RequiredArgumentException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public static final String MSG = "%s argument is required";



	public RequiredArgumentException(final String arg_name) {
		super(String.format(MSG, arg_name));
	}



}
