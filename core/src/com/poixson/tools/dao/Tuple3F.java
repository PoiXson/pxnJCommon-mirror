package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple3F extends Fxyz {
	private static final long serialVersionUID = 1L;



	public Tuple3F() {
		super();
	}
	public Tuple3F(final float x, final float y, final float z) {
		super(x, y, z);
	}
	public Tuple3F(final Tuple3F tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3F(this.x, this.y, this.z);
	}



	public void get(final Tuple3F tup) {
		tup.x = this.x;
		tup.y = this.y;
		tup.z = this.z;
	}



	public void set(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(final Tuple3F tup) {
		this.x = tup.x;
		this.y = tup.y;
		this.z = tup.z;
	}
	public void setX(final float x) {
		this.x = x;
	}
	public void setY(final float y) {
		this.y = y;
	}
	public void setZ(final float z) {
		this.z = z;
	}



	public void add(final float x, final float y, final float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(final Tuple3F tup) {
		this.x += tup.x;
		this.y += tup.y;
		this.z += tup.z;
	}
	public void add(final Tuple3F tupA, final Tuple3F tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
		this.z = tupA.z + tupB.z;
	}



	public void sub(final float x, final float y, final float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(final Tuple3F tup) {
		this.x -= tup.x;
		this.y -= tup.y;
		this.z -= tup.z;
	}
	public void sub(final Tuple3F tupA, final Tuple3F tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
		this.z = tupA.z - tupB.z;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
	}



	public void neg(final Tuple3F tup) {
		this.x = 0.0F - tup.x;
		this.y = 0.0F - tup.y;
		this.z = 0.0F - tup.z;
	}
	public void neg() {
		this.x = 0.0F - this.x;
		this.y = 0.0F - this.y;
		this.z = 0.0F - this.z;
	}



	public void scale(final float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}



	public void clamp(final float min, final float max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
		this.z = NumberUtils.MinMax(this.z, min, max);
	}
	public void clampMin(final float min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
		if (this.z < min) this.z = min;
	}
	public void clampMax(final float max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
		if (this.z > max) this.z = max;
	}



	public void normalize(final Tuple3F tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final float norm = 1.0F / this.vectorLength();
		this.x *= norm;
		this.y *= norm;
		this.z *= norm;
	}



	public float vectorLength() {
		return (float) Math.sqrt( (this.x*this.x) + (this.y*this.y) + (this.z*this.z) );
	}



	public void interpolate(final Tuple3F tup, final float alpha) {
		this.x = ((1.0F-alpha)*this.x) + (alpha*tup.x);
		this.y = ((1.0F-alpha)*this.y) + (alpha*tup.y);
		this.z = ((1.0F-alpha)*this.z) + (alpha*tup.z);
	}
	public void interpolate(final Tuple3F tupA, final Tuple3F tupB, final float alpha) {
		this.x = ((1.0F-alpha)*tupA.x) + (alpha*tupB.x);
		this.y = ((1.0F-alpha)*tupA.y) + (alpha*tupB.y);
		this.z = ((1.0F-alpha)*tupA.z) + (alpha*tupB.z);
	}



	public boolean epsilon(final Tuple3F tup, final float epsilon) {
		float dif = this.x - tup.x;
		if (Float.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.y - tup.y;
		if (Float.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.z - tup.z;
		if (Float.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
