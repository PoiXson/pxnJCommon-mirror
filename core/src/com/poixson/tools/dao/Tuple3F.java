package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple3F extends Fabc {
	private static final long serialVersionUID = 1L;



	public Tuple3F() {
		super();
	}
	public Tuple3F(final float a, final float b, final float c) {
		super(a, b, c);
	}
	public Tuple3F(final Tuple3F tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3F(this.a, this.b, this.c);
	}



	public void get(final Tuple3F tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final float a, final float b, final float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3F tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}
	public void setA(final float a) {
		this.a = a;
	}
	public void setB(final float b) {
		this.b = b;
	}
	public void setC(final float c) {
		this.c = c;
	}



	public void add(final float a, final float b, final float c) {
		this.a += a;
		this.b += b;
		this.c += c;
	}
	public void add(final Tuple3F tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
	}
	public void add(final Tuple3F tupA, final Tuple3F tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
	}



	public void sub(final float a, final float b, final float c) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
	}
	public void sub(final Tuple3F tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
	}
	public void sub(final Tuple3F tupA, final Tuple3F tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
		this.c = tupA.c - tupB.c;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		this.c = Math.abs(this.c);
	}



	public void neg(final Tuple3F tup) {
		this.a = 0.0F - tup.a;
		this.b = 0.0F - tup.b;
		this.c = 0.0F - tup.c;
	}
	public void neg() {
		this.a = 0.0F - this.a;
		this.b = 0.0F - this.b;
		this.c = 0.0F - this.c;
	}



	public void scale(final float scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
	}



	public void clamp(final float min, final float max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
	}
	public void clampMin(final float min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
	}
	public void clampMax(final float max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
		if (this.c > max) this.c = max;
	}



	public void normalize(final Tuple3F tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final float norm = 1.0F / this.vectorLength();
		this.a *= norm;
		this.b *= norm;
		this.c *= norm;
	}



	public float vectorLength() {
		return (float) Math.sqrt( (this.a*this.a) + (this.b*this.b) + (this.c*this.c) );
	}



	public void interpolate(final Tuple3F tup, final float alpha) {
		this.a = ((1.0F-alpha)*this.a) + (alpha*tup.a);
		this.b = ((1.0F-alpha)*this.b) + (alpha*tup.b);
		this.c = ((1.0F-alpha)*this.c) + (alpha*tup.c);
	}
	public void interpolate(final Tuple3F tupA, final Tuple3F tupB, final float alpha) {
		this.a = ((1.0F-alpha)*tupA.a) + (alpha*tupB.a);
		this.b = ((1.0F-alpha)*tupA.b) + (alpha*tupB.b);
		this.c = ((1.0F-alpha)*tupA.c) + (alpha*tupB.c);
	}



	public boolean epsilon(final Tuple3F tup, final float epsilon) {
		float dif = this.a - tup.a;
		if (Float.isNaN(dif))        return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.b - tup.b;
		if (Float.isNaN(dif))        return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.c - tup.c;
		if (Float.isNaN(dif))        return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
