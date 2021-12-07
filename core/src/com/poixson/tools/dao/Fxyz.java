package com.poixson.tools.dao;

import java.io.Serializable;


public class Fxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float x;
	public float y;
	public float z;



	public Fxyz() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Fxyz(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Fxyz(final Fxyz dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Fxyz(this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fxyz) {
			final Fxyz dao = (Fxyz) obj;
			return (this.x == dao.x && this.y == dao.y && this.z == dao.z);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(')
				.append(this.x)
				.append(", ")
				.append(this.y)
				.append(", ")
				.append(this.z)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.x);
		bits = (bits * 31L) + Float.floatToIntBits(this.y);
		bits = (bits * 31L) + Float.floatToIntBits(this.z);
		return (int) (bits ^ (bits >> 32L));
	}



}
