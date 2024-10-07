package com.poixson.utils;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.Keeper;


public final class MathUtils {
	private MathUtils() {}
	static { Keeper.add(new MathUtils()); }



	// -------------------------------------------------------------------------------
	// min/max



	public static int Min(final int...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		int min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static long Min(final long...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		long min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static float Min(final float...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		float min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static double Min(final double...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		double min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}



	public static int Max(final int...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		int max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static long Max(final long...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		long max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static float Max(final float...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		float max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static double Max(final double...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		double max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}



	public static int MinMax(final int value, final int min, final int max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static byte MinMax(final byte value, final byte min, final byte max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static short MinMax(final short value, final short min, final short max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static long MinMax(final long value, final long min, final long max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static double MinMax(final double value, final double min, final double max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static float MinMax(final float value, final float min, final float max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}



	// is within range
	public static boolean IsMinMax(final int value, final int min, final int max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final byte value, final byte min, final byte max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final short value, final short min, final short max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final long value, final long min, final long max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final double value, final double min, final double max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final float value, final float min, final float max) {
		return (value >= min && value <= max);
	}



	// -------------------------------------------------------------------------------
	// time



	public static boolean IsDayTimeBetween(final long current, final long start, final long stop, final int zone) {
		return _IsDayTimeBetween(
			TimestampSinceMidnight(current, zone),
			MinMax(start, 0, 86399999),
			MinMax(stop,  0, 86399999)
		);
	}
	protected static boolean _IsDayTimeBetween(final long current, final long start, final long stop) {
		if (start < stop) return (current >= start) && (current < stop); // normal case
		else              return (current >= start) || (current < stop); // crossing midnight
	}



	public static long TimestampSinceMidnight(final long timestamp, final int zone) {
		final Instant   instant = Instant.ofEpochMilli(timestamp);
		final LocalTime current = instant.atZone(ZoneOffset.ofHours(zone)).toLocalTime();
		return current.toNanoOfDay() / 1000000L;
	}



	// -------------------------------------------------------------------------------
	// distance



	public static double Distance2D(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance2D(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance2D(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(z1 - z2)
		);
	}

	public static double Distance3D(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance3D(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance3D(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(y1 - y2) +
			Square(z1 - z2)
		);
	}



	public static double DistanceFast2D(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast2D(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast2D(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(z1 - z2)
		);
	}

	public static double DistanceFast3D(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast3D(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast3D(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(y1 - y2),
			Math.abs(z1 - z2)
		);
	}



	// -------------------------------------------------------------------------------
	// square/cube



	public static int Square(final int value) {
		return value * value;
	}
	public static long Square(final long value) {
		return value * value;
	}
	public static float Square(final float value) {
		return value * value;
	}
	public static double Square(final double value) {
		return value * value;
	}



	public static int Cube(final int value) {
		return value * value * value;
	}
	public static long Cube(final long value) {
		return value * value * value;
	}
	public static float Cube(final float value) {
		return value * value * value;
	}
	public static double Cube(final double value) {
		return value * value * value;
	}



	// -------------------------------------------------------------------------------
	// rotation



	public static double RotateX(final double x, final double y, final double angle) {
		final double ang = Math.PI * angle;
		return (Math.sin(ang) * y) - (Math.cos(ang) * x);
	}
	public static double RotateY(final double x, final double y, final double angle) {
		final double ang = Math.PI * angle;
		return (Math.sin(ang) * x) + (Math.cos(ang) * y);
	}

	public static float RotateX(final float x, final float y, final float angle) {
		final float ang = (float)( Math.PI * ((double)angle) );
		return (float) ((Math.sin(ang) * y) - (Math.cos(ang) * x));
	}
	public static float RotateY(final float x, final float y, final float angle) {
		final float ang = (float)( Math.PI * ((double)angle) );
		return (float) ((Math.sin(ang) * x) + (Math.cos(ang) * y));
	}



	// -------------------------------------------------------------------------------
	// remap number between ranges



	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value) {
		final double lA = (double) lowA;
		final double hA = (double) highA;
		final double lB = (double) lowB;
		final double hB = (double) highB;
		double result = (hB - lB) / (hA - lA);
		result *= (value - lA);
		result += lB;
		return (int) result;
	}



	public static int Remap(final int low, final int high, final double percent) {
		double result = ((double)(high - low)) * percent;
		return ((int)result) + low;
	}



	public static Color Remap(final Color colorA, final Color colorB, final double percent) {
		return
			new Color(
				MinMax(Remap(colorA.getRed(),   colorB.getRed(),   percent), 0, 255),
				MinMax(Remap(colorA.getGreen(), colorB.getGreen(), percent), 0, 255),
				MinMax(Remap(colorA.getBlue(),  colorB.getBlue(),  percent), 0, 255)
			);
	}



	// rrrgggbb
	public static Color Remap8BitColor(final int value) {
		final int r = (value & 0B00000111);
		final int g = (value & 0B00111000) >> 3;
		final int b = (value & 0B11000000) >> 6;
		return
			new Color(
				MinMax( Remap(0, 7, 0, 255, r), 0, 255),
				MinMax( Remap(0, 7, 0, 255, g), 0, 255),
				MinMax( Remap(0, 3, 0, 255, b), 0, 255)
			);
	}



	// -------------------------------------------------------------------------------
	// interleave



	public static int ZOrderInterleave(final int value, final int minor, final int major) {
		final int total = minor * major;
		return
			(Math.floorDiv(value, total) * total)   + // total offset
			Math.floorDiv( (value % total), minor ) + // group offset
			((value % minor) * major);                // step offset
	}



	// -------------------------------------------------------------------------------
	// interpolation



/*
	public static double CubicInterpolate(final double p,
			final double a, final double b, final double c, final double d) {
		final double e = (d - c - a) + b;
		final double f = a - b - e;
		final double g = c - a;
		return (e * p*p*p) + (f * p*p) + (g * p) + b;
	}

	public static double CubicInterpolateValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeOrNear(values, index-1),
			GetSafeOrNear(values, index  ),
			GetSafeOrNear(values, index+1),
			GetSafeOrNear(values, index+2)
		);
	}
	public static double CubicInterpolateLoopedValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeLooped(values, index-1),
			GetSafeLooped(values, index  ),
			GetSafeLooped(values, index+1),
			GetSafeLooped(values, index+2)
		);
	}
	public static double CubicInterpolateCircularValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeLoopExtend(values, index-1),
			GetSafeLoopExtend(values, index  ),
			GetSafeLoopExtend(values, index+1),
			GetSafeLoopExtend(values, index+2)
		);
	}
*/



}
