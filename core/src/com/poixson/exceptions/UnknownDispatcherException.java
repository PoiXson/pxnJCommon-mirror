package com.poixson.exceptions;


public class UnknownDispatcherException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public static final String MSG = "Unknown xDispatcher: %s";



	public UnknownDispatcherException(final String key) {
		super(String.format(MSG, key));
	}



}
