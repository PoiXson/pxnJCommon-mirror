package com.poixson.tools.dao;

import java.io.Serializable;


public class Fwxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float w;
	public float x;
	public float y;
	public float z;



	public Fwxyz() {
		this.w = 0.0F;
		this.x = 0.0F;
		this.y = 0.0F;
		this.z = 0.0F;
	}
	public Fwxyz(final float w, final float x, final float y, final float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Fwxyz(final Fwxyz dao) {
		this.w = dao.w;
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Fwxyz(this.w, this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fwxyz) {
			final Fwxyz dao = (Fwxyz) obj;
			return (
				this.w == dao.w &&
				this.x == dao.x &&
				this.y == dao.y &&
				this.z == dao.z
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.w)
				.append(", ").append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.z)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.w);
		bits = (bits * 31L) + Float.floatToIntBits(this.x);
		bits = (bits * 31L) + Float.floatToIntBits(this.y);
		bits = (bits * 31L) + Float.floatToIntBits(this.z);
		return (int) (bits ^ (bits >> 32L));
	}



}
