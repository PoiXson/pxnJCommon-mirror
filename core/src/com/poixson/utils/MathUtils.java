package com.poixson.utils;

import static com.poixson.utils.ArrayUtils.GetSafeLoopExtend;
import static com.poixson.utils.ArrayUtils.GetSafeLooped;
import static com.poixson.utils.ArrayUtils.GetSafeOrNear;
import static com.poixson.utils.NumberUtils.MinMax;

import java.awt.Color;

import com.poixson.tools.Keeper;


public final class MathUtils {
	private MathUtils() {}
	static { Keeper.add(new MathUtils()); }



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
				NumberUtils.MinMax(Remap(colorA.getRed(),   colorB.getRed(),   percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getGreen(), colorB.getGreen(), percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getBlue(),  colorB.getBlue(),  percent), 0, 255)
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
	// interpolation



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



}
