package com.poixson.exceptions;


public class UnknownThreadPoolException extends RuntimeException {
	private static final long serialVersionUID = 1L;



	public UnknownThreadPoolException(final String pool_name) {
		super("Unknown xThreadPool: "+pool_name);
	}



}
