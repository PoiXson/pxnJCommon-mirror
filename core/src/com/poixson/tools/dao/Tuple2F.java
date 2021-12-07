package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2F extends Fxy {
	private static final long serialVersionUID = 1L;



	public Tuple2F() {
		super();
	}
	public Tuple2F(final float x, final float y) {
		super(x, y);
	}
	public Tuple2F(final Tuple2F tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2F(this.x, this.y);
	}



	public void get(final Tuple2F tup) {
		tup.x = this.x;
		tup.y = this.y;
	}



	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	public void set(final Tuple2F tup) {
		this.x = tup.x;
		this.y = tup.y;
	}
	public void setX(final float x) {
		this.x = x;
	}
	public void setY(final float y) {
		this.y = y;
	}



	public void add(final float x, final float y) {
		this.x += x;
		this.y += y;
	}
	public void add(final Tuple2F tup) {
		this.x += tup.x;
		this.y += tup.y;
	}
	public void add(final Tuple2F tupA, final Tuple2F tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
	}



	public void sub(final float x, final float y) {
		this.x -= x;
		this.y -= y;
	}
	public void sub(final Tuple2F tup) {
		this.x -= tup.x;
		this.y -= tup.y;
	}
	public void sub(final Tuple2F tupA, final Tuple2F tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}



	public void neg(final Tuple2F tup) {
		this.x = 0.0F - tup.x;
		this.y = 0.0F - tup.y;
	}
	public void neg() {
		this.x = 0.0F - this.x;
		this.y = 0.0F - this.y;
	}



	public void scale(final float scale) {
		this.x *= scale;
		this.y *= scale;
	}



	public void clamp(final float min, final float max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
	}
	public void clampMin(final float min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}
	public void clampMax(final float max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}



	public void normalize(final Tuple2F tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final float norm = 1.0F / this.vectorLength();
		this.x *= norm;
		this.y *= norm;
	}



	public float vectorLength() {
		return (float) Math.sqrt( (this.x*this.x) + (this.y*this.y) );
	}



	public void interpolate(final Tuple2F tup, final float alpha) {
		this.x = ((1.0F-alpha)*this.x) + (alpha*tup.x);
		this.y = ((1.0F-alpha)*this.y) + (alpha*tup.y);
	}
	public void interpolate(final Tuple2F tupA, final Tuple2F tupB, final float alpha) {
		this.x = ((1.0F-alpha)*tupA.x) + (alpha*tupB.x);
		this.y = ((1.0F-alpha)*tupA.y) + (alpha*tupB.y);
	}



	public boolean epsilon(final Tuple2F tup, final float epsilon) {
		float dif = this.x - tup.x;
		if (Float.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.y - tup.y;
		if (Float.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
