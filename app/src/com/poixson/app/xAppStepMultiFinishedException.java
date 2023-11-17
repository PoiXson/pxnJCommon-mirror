package com.poixson.app;


public class xAppStepMultiFinishedException extends RuntimeException {
	private static final long serialVersionUID = 1L;



	public xAppStepMultiFinishedException() {
	}



	@Override
	public String toString() {
		return "xApp multi-step finished; This exception should never be seen!";
	}



}
