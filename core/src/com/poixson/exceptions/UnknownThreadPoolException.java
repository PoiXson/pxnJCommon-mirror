package com.poixson.exceptions;


public class UnknownThreadPoolException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public static final String MSG = "Unknown xThreadPool: %s";



	public UnknownThreadPoolException(final String pool_name) {
		super(String.format(MSG, pool_name));
	}



}
