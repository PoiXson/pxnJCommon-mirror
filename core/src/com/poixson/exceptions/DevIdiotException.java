package com.poixson.exceptions;


// This is an exception that should NEVER happen,
// unless someone did something stupid in the code.
public class DevIdiotException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public static final String MSG_IDIOT = "WHOEVER WROTE THAT CODE IS AN IDIOT";



	public DevIdiotException() {
		super(MSG_IDIOT);
	}
	public DevIdiotException(final String msg) {
		super(MSG_IDIOT+" "+msg);
	}
	public DevIdiotException(final Throwable e) {
		super(MSG_IDIOT, e);
	}
	public DevIdiotException(final String msg, final Throwable e) {
		super(MSG_IDIOT+" "+msg, e);
	}



}
