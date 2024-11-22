package com.poixson.tools;


public class FastNoiseLiteCached3D extends FastNoiseLiteD {

	public final int a;
	public final int w, h, d;
	public final int abs_x, abs_y, abs_z;

	protected final double[][][] values;



	public FastNoiseLiteCached3D(final int extra,
			final int w, final int h, final int d,
			final int abs_x, final int abs_y, final int abs_z) {
		super();
		this.a = extra;
		this.w = w; final int ww = w + (extra * 2);
		this.h = h; final int hh = h + (extra * 2);
		this.d = d; final int dd = d + (extra * 2);
		this.abs_x = abs_x; this.abs_y = abs_y; this.abs_z = abs_z;
		this.values = new double[ww][][];
		for (int x=0; x<ww; x++) {
			this.values[x] = new double[hh][];
			for (int y=0; y<hh; y++) {
				this.values[x][y] = new double[dd];
				for (int z=0; z<dd; z++)
					this.values[x][y][z] = Double.MIN_VALUE;
			}
		}
	}



	public double getNoise(final int x, final int y, final int z) {
		{
			final double value = this.values[x+this.a][y+this.a][z+this.a];
			if (value != Double.MIN_VALUE)
				return value;
		}
		{
			final double value = super.getNoise(x+this.abs_x, y+this.abs_y, z+this.abs_z);
			this.values[x+this.a][y+this.a][z+this.a] = value;
			return value;
		}
	}



}
