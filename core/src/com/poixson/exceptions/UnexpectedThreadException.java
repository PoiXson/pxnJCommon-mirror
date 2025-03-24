package com.poixson.exceptions;


public class UnexpectedThreadException extends RuntimeException {
	private static final long serialVersionUID = 1L;



	public UnexpectedThreadException() {
		super();
	}
	public UnexpectedThreadException(final String msg) {
		super(msg);
	}
	public UnexpectedThreadException(final Throwable e) {
		super(e);
	}
	public UnexpectedThreadException(final String msg, final Throwable e) {
		super(msg, e);
	}



}
