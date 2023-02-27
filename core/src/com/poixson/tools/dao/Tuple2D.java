package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2D extends Dab {
	private static final long serialVersionUID = 1L;



	public Tuple2D() {
		super();
	}
	public Tuple2D(final double a, final double b) {
		super(a, b);
	}
	public Tuple2D(final Tuple2D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2D(this.a, this.b);
	}



	public void get(final Tuple2D tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final double a, final double b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2D tup) {
		this.a = tup.a;
		this.b = tup.b;
	}
	public void setX(final double a) {
		this.a = a;
	}
	public void setY(final double b) {
		this.b = b;
	}



	public void add(final double a, final double b) {
		this.a += a;
		this.b += b;
	}
	public void add(final Tuple2D tup) {
		this.a += tup.a;
		this.b += tup.b;
	}
	public void add(final Tuple2D tupA, final Tuple2D tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
	}



	public void sub(final double a, final double b) {
		this.a -= a;
		this.b -= b;
	}
	public void sub(final Tuple2D tup) {
		this.a -= tup.a;
		this.b -= tup.b;
	}
	public void sub(final Tuple2D tupA, final Tuple2D tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
	}



	public void neg(final Tuple2D tup) {
		this.a = 0.0 - tup.a;
		this.b = 0.0 - tup.b;
	}
	public void neg() {
		this.a = 0.0 - this.a;
		this.b = 0.0 - this.b;
	}



	public void scale(final double scale) {
		this.a *= scale;
		this.b *= scale;
	}



	public void clamp(final double min, final double max) {
		this.a = NumberUtils.MinMax(this.a, min, max);
		this.b = NumberUtils.MinMax(this.b, min, max);
	}
	public void clampMin(final double min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
	}
	public void clampMax(final double max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
	}



	public void normalize(final Tuple2D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.a *= norm;
		this.b *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.a*this.a) + (this.b*this.b) );
	}



	public void interpolate(final Tuple2D tup, final double alpha) {
		this.a = ((1.0-alpha)*this.a) + (alpha*tup.a);
		this.b = ((1.0-alpha)*this.b) + (alpha*tup.b);
	}
	public void interpolate(final Tuple2D tupA, final Tuple2D tupB, final double alpha) {
		this.a = ((1.0-alpha)*tupA.a) + (alpha*tupB.a);
		this.b = ((1.0-alpha)*tupA.b) + (alpha*tupB.b);
	}



	public boolean epsilon(final Tuple2D tup, final double epsilon) {
		double dif = this.a - tup.a;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.b - tup.b;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
