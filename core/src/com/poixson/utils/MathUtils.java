package com.poixson.utils;

import java.awt.Color;

import com.poixson.tools.Keeper;


public final class MathUtils {
	private MathUtils() {}
	static { Keeper.add(new MathUtils()); }



	public static int BitValue(final int bit) {
		int result = 1;
		for (int i=0; i<bit; i++)
			result *= 2;
		return result;
	}



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



	// ------------------------------------------------------------------------------- //
	// remap number between ranges



	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value) {
		int result = (highB - lowB) / (highA - lowA);
		result *= (value - lowA);
		result += lowB;
		return result;
	}



	public static int Remap(final int low, final int high, final double percent) {
		double result = ((double)(high - low)) * percent;
		return ((int)result) + low;
	}



	public static Color Remap(final Color colorA, final Color colorB, final double percent) {
		return
			new Color(
				NumberUtils.MinMax(Remap(colorA.getRed(),   colorB.getRed(),   percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getGreen(), colorB.getGreen(), percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getBlue(),  colorB.getBlue(),  percent), 0, 255)
			);
	}



}
