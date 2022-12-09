package com.poixson.tools.dao;

import java.io.Serializable;


public class Fnsew implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float n;
	public float s;
	public float e;
	public float w;



	public Fnsew() {
		this.n = 0.0F;
		this.s = 0.0F;
		this.e = 0.0F;
		this.w = 0.0F;
	}
	public Fnsew(final float n, final float s, final float e, final float w) {
		this.n = n;
		this.s = s;
		this.e = e;
		this.w = w;
	}
	public Fnsew(final Fnsew dao) {
		this.n = dao.n;
		this.s = dao.s;
		this.e = dao.e;
		this.w = dao.w;
	}



	@Override
	public Object clone() {
		return new Fnsew(this.n, this.s, this.e, this.w);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fnsew) {
			final Fnsew dao = (Fnsew) obj;
			return (
				this.n == dao.n &&
				this.s == dao.s &&
				this.e == dao.e &&
				this.w == dao.w
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.n)
				.append(", ").append(this.s)
				.append(", ").append(this.e)
				.append(", ").append(this.w)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.n);
		bits = (bits * 31L) + Float.floatToIntBits(this.s);
		bits = (bits * 31L) + Float.floatToIntBits(this.e);
		bits = (bits * 31L) + Float.floatToIntBits(this.w);
		return (int) (bits ^ (bits >> 32L));
	}



}
