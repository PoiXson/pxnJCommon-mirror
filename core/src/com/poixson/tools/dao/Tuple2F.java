package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple2F extends Fab {
	private static final long serialVersionUID = 1L;



	public Tuple2F() {
		super();
	}
	public Tuple2F(final float a, final float b) {
		super(a, b);
	}
	public Tuple2F(final Tuple2F tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2F(this.a, this.b);
	}



	public void get(final Tuple2F tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final float a, final float b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2F tup) {
		this.a = tup.a;
		this.b = tup.b;
	}
	public void setA(final float a) {
		this.a = a;
	}
	public void setB(final float b) {
		this.b = b;
	}



	public void add(final float a, final float b) {
		this.a += a;
		this.b += b;
	}
	public void add(final Tuple2F tup) {
		this.a += tup.a;
		this.b += tup.b;
	}
	public void add(final Tuple2F tupA, final Tuple2F tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
	}



	public void sub(final float a, final float b) {
		this.a -= a;
		this.b -= b;
	}
	public void sub(final Tuple2F tup) {
		this.a -= tup.a;
		this.b -= tup.b;
	}
	public void sub(final Tuple2F tupA, final Tuple2F tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
	}



	public void neg(final Tuple2F tup) {
		this.a = 0.0F - tup.a;
		this.b = 0.0F - tup.b;
	}
	public void neg() {
		this.a = 0.0F - this.a;
		this.b = 0.0F - this.b;
	}



	public void scale(final float scale) {
		this.a *= scale;
		this.b *= scale;
	}



	public void clamp(final float min, final float max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
	}
	public void clampMin(final float min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
	}
	public void clampMax(final float max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
	}



	public void normalize(final Tuple2F tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final float norm = 1.0F / this.vectorLength();
		this.a *= norm;
		this.b *= norm;
	}



	public float vectorLength() {
		return (float) Math.sqrt( (this.a*this.a) + (this.b*this.b) );
	}



	public void interpolate(final Tuple2F tup, final float alpha) {
		this.a = ((1.0F-alpha)*this.a) + (alpha*tup.a);
		this.b = ((1.0F-alpha)*this.b) + (alpha*tup.b);
	}
	public void interpolate(final Tuple2F tupA, final Tuple2F tupB, final float alpha) {
		this.a = ((1.0F-alpha)*tupA.a) + (alpha*tupB.a);
		this.b = ((1.0F-alpha)*tupA.b) + (alpha*tupB.b);
	}



	public boolean epsilon(final Tuple2F tup, final float epsilon) {
		float dif = this.a - tup.a;
		if (Float.isNaN(dif))        return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.b - tup.b;
		if (Float.isNaN(dif))        return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
