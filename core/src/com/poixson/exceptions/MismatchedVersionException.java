package com.poixson.exceptions;


public class MismatchedVersionException extends Exception {
	private static final long serialVersionUID = 1L;

	public final String version_expected;
	public final String version_actual;



	public MismatchedVersionException(final String version_expected, final String version_actual) {
		super(String.format("Expected version: %s Found version: %s", version_expected, version_actual));
		this.version_expected = version_expected;
		this.version_actual   = version_actual;
	}



}
