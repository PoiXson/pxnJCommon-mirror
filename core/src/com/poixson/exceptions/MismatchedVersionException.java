package com.poixson.exceptions;


public class MismatchedVersionException extends Exception {
	private static final long serialVersionUID = 1L;

	public final String versionExpected;
	public final String versionActual;



	public MismatchedVersionException(
			final String versionExpected, final String versionActual) {
		super(String.format("Expected version: %s Found version: %s", versionExpected, versionActual));
		this.versionExpected = versionExpected;
		this.versionActual  = versionActual;
	}



}
