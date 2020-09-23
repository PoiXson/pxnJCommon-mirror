package com.poixson.utils;

import java.util.ArrayList;
import java.util.List;

import com.poixson.tools.Keeper;
import com.poixson.tools.dao.Ixy;


public final class MathUtils {
	private MathUtils() {}
	static { Keeper.add(new MathUtils(); }



	// ------------------------------------------------------------------------------- //
	// round



	// double
	public static double Round(final double value, final double product) {
		final double val = Math.round( value / product );
		return val * product;
	}
	public static double Floor(final double value, final double product) {
		final double val = Math.floor( value / product );
		return val * product;
	}
	public static double Ceil(final double value, final double product) {
		final double val = Math.ceil( value / product );
		return val * product;
	}



	// integer
	public static int Round(final int value, final int product) {
		return (int) Round( (double) value, (double) product );
	}
	public static int Floor(final int value, final int product) {
		return (int) Floor( (double) value, (double) product );
	}
	public static int Ceil(final int value, final int product) {
		return (int) Ceil( (double) value, (double) product );
	}



	// long
	public static long Round(final long value, final int product) {
		return (long) Round( (double) value, (double) product );
	}
	public static long Floor(final long value, final int product) {
		return (long) Floor( (double) value, (double) product );
	}
	public static long Ceil(final long value, final int product) {
		return (long) Ceil( (double) value, (double) product );
	}



}
