package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple2Y extends Yab {
	private static final long serialVersionUID = 1L;



	public Tuple2Y() {
		super();
	}
	public Tuple2Y(final byte a, final byte b) {
		super(a, b);
	}
	public Tuple2Y(final Tuple2Y tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2Y(this.a, this.b);
	}



	public void get(final Tuple2Y tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final byte a, final byte b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2Y tup) {
		this.a = tup.a;
		this.b = tup.b;
	}
	public void setA(final byte a) {
		this.a = a;
	}
	public void setB(final byte b) {
		this.b = b;
	}



	public void add(final byte a, final byte b) {
		this.a += a;
		this.b += b;
	}
	public void add(final Tuple2Y tup) {
		this.a += tup.a;
		this.b += tup.b;
	}
	public void add(final Tuple2Y tupA, final Tuple2Y tupB) {
		this.a = (byte) (tupA.a + tupB.a);
		this.b = (byte) (tupA.b + tupB.b);
	}



	public void sub(final byte a, final byte b) {
		this.a -= a;
		this.b -= b;
	}
	public void sub(final Tuple2Y tup) {
		this.a -= tup.a;
		this.b -= tup.b;
	}
	public void sub(final Tuple2Y tupA, final Tuple2Y tupB) {
		this.a = (byte) (tupA.a - tupB.a);
		this.b = (byte) (tupA.b - tupB.b);
	}



	public void abs() {
		this.a = (byte) Math.abs(this.a);
		this.b = (byte) Math.abs(this.b);
	}



	public void neg(final Tuple2Y tup) {
		this.a = (byte) (0x0 - tup.a);
		this.b = (byte) (0x0 - tup.b);
	}
	public void neg() {
		this.a = (byte) (0x0 - this.a);
		this.b = (byte) (0x0 - this.b);
	}



	public void scale(final byte scale) {
		this.a *= scale;
		this.b *= scale;
	}
	public void scale(final double scale) {
		this.a = (byte) ( ((double)this.a) * scale );
		this.b = (byte) ( ((double)this.b) * scale );
	}
	public void scale(final float scale) {
		this.a = (byte) ( ((float)this.a) * scale );
		this.b = (byte) ( ((float)this.b) * scale );
	}



	public void clamp(final byte min, final byte max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
	}
	public void clampMin(final byte min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
	}
	public void clampMax(final byte max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
	}



	public double vectorLength() {
		final double a = (double) this.a;
		final double b = (double) this.b;
		return Math.sqrt( (a*a) + (b*b) );
	}



}
