package com.poixson.tools.dao;

import java.io.Serializable;


public class Fabcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float a;
	public float b;
	public float c;
	public float d;
	public float e;
	public float f;



	public Fabcdef() {
		this.a = 0.0F;
		this.b = 0.0F;
		this.c = 0.0F;
		this.d = 0.0F;
		this.e = 0.0F;
		this.f = 0.0F;
	}
	public Fabcdef(final float a, final float b, final float c, final float d, final float e, final float f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Fabcdef(final Fabcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Fabcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fabcdef) {
			final Fabcdef dao = (Fabcdef) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e &&
				this.f == dao.f
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c).append(", ")
			.append(this.d).append(", ")
			.append(this.e).append(", ")
			.append(this.f)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.a);
		bits = (bits * 31L) + Float.floatToIntBits(this.b);
		bits = (bits * 31L) + Float.floatToIntBits(this.c);
		bits = (bits * 31L) + Float.floatToIntBits(this.d);
		bits = (bits * 31L) + Float.floatToIntBits(this.e);
		bits = (bits * 31L) + Float.floatToIntBits(this.f);
		return (int) (bits ^ (bits >> 32L));
	}



}
