package com.poixson.utils;


public class FastNoiseLiteCached2F extends FastNoiseLiteF {

	public final int a;
	public final int w, h;
	public final int abs_x, abs_y, abs_z;

	protected final float[][] values;



	public FastNoiseLiteCached2F(final int extra,
			final int w, final int h,
			final int abs_x, final int abs_y, final int abs_z) {
		super();
		this.a = extra;
		this.w = w; final int ww = w + (extra * 2);
		this.h = h; final int hh = h + (extra * 2);
		this.abs_x = abs_x; this.abs_y = abs_y; this.abs_z = abs_z;
		this.values = new float[ww][];
		for (int x=0; x<ww; x++) {
			this.values[x] = new float[hh];
			for (int y=0; y<hh; y++)
				this.values[x][y] = Float.MIN_VALUE;
		}
	}



	public float getNoise(final int x, final int y) {
		{
			final float value = this.values[x+this.a][y+this.a];
			if (value != Float.MIN_VALUE)
				return value;
		}
		{
			final float value = super.getNoise(x+this.abs_x, y+this.abs_y);
			this.values[x+this.a][y+this.a] = value;
			return value;
		}
	}



}
