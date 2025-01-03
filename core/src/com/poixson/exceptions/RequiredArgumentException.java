package com.poixson.exceptions;


public class RequiredArgumentException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;



	public RequiredArgumentException(final String arg_name) {
		super(String.format("%s argument is required", arg_name));
	}



}
