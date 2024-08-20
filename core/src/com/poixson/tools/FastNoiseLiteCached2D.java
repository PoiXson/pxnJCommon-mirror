package com.poixson.utils;


public class FastNoiseLiteCached2D extends FastNoiseLiteD {

	public final int a;
	public final int w, h;
	public final int abs_x, abs_y, abs_z;

	protected final double[][] values;



	public FastNoiseLiteCached2D(final int extra,
			final int w, final int h,
			final int abs_x, final int abs_y, final int abs_z) {
		super();
		this.a = extra;
		this.w = w; final int ww = w + (extra * 2);
		this.h = h; final int hh = h + (extra * 2);
		this.abs_x = abs_x; this.abs_y = abs_y; this.abs_z = abs_z;
		this.values = new double[ww][];
		for (int x=0; x<ww; x++) {
			this.values[x] = new double[hh];
			for (int y=0; y<hh; y++)
				this.values[x][y] = Double.MIN_VALUE;
		}
	}



	public double getNoise(final int x, final int y) {
		{
			final double value = this.values[x+this.a][y+this.a];
			if (value != Double.MIN_VALUE)
				return value;
		}
		{
			final double value = super.getNoise(x+this.abs_x, y+this.abs_y);
			this.values[x+this.a][y+this.a] = value;
			return value;
		}
	}



}
