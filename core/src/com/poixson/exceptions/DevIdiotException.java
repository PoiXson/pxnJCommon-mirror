package com.poixson.exceptions;


// This is an exception that should NEVER happen,
// unless someone did something stupid in the code.
public class DevIdiotException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public static final String IDIOT_MSG = "WHOEVER WROTE THAT CODE IS AN IDIOT";



	public DevIdiotException() {
		super(IDIOT_MSG);
	}
	public DevIdiotException(final String msg) {
		super(IDIOT_MSG+" "+msg);
	}
	public DevIdiotException(final Throwable e) {
		super(IDIOT_MSG, e);
	}
	public DevIdiotException(final String msg, final Throwable e) {
		super(IDIOT_MSG+" "+msg, e);
	}



}
