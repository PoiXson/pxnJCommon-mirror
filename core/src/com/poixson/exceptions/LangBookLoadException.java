package com.poixson.exceptions;


public class LangTableLoadException extends IORuntimeException {
	private static final long serialVersionUID = 1L;



	public LangTableLoadException() {
		super();
	}
	public LangTableLoadException(final String msg) {
		super(msg);
	}
	public LangTableLoadException(final Throwable e) {
		super(e);
	}
	public LangTableLoadException(final String msg, final Throwable e) {
		super(msg, e);
	}



}
