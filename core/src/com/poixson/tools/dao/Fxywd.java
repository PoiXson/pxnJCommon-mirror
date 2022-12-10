package com.poixson.tools.dao;

import java.io.Serializable;


public class Fxywd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float x;
	public float y;
	public float w;
	public float d;



	public Fxywd() {
		this.x = 0.0F;
		this.y = 0.0F;
		this.w = 0.0F;
		this.d = 0.0F;
	}
	public Fxywd(final float x, final float y, final float w, final float d) {
		this.x = x;
		this.y = y;
		this.w = y;
		this.d = d;
	}
	public Fxywd(final Fxywd dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.w = dao.w;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Fxywd(this.x, this.y, this.w, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fxywd) {
			final Fxywd dao = (Fxywd) obj;
			return (
				this.x == dao.x &&
				this.y == dao.y &&
				this.w == dao.w &&
				this.d == dao.d
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.w)
				.append(", ").append(this.d)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.x);
		bits = (bits * 31L) + Float.floatToIntBits(this.y);
		bits = (bits * 31L) + Float.floatToIntBits(this.w);
		bits = (bits * 31L) + Float.floatToIntBits(this.d);
		return (int) (bits ^ (bits >> 32L));
	}



}
