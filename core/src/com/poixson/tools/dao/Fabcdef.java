package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

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

	public static Fabcdef From(final Iabcdef dao) {
		return new Fabcdef(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d,
			(float) dao.e,
			(float) dao.f
		);
	}
	public static Fabcdef From(final Labcdef dao) {
		return new Fabcdef(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d,
			(float) dao.e,
			(float) dao.f
		);
	}
	public static Fabcdef From(final Dabcdef dao) {
		return new Fabcdef(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d,
			(float) dao.e,
			(float) dao.f
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fabcdef dao) {
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
	public static Fabcdef FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 6) {
				return new Fabcdef(
					Float.parseFloat(parts[0].trim()),
					Float.parseFloat(parts[1].trim()),
					Float.parseFloat(parts[2].trim()),
					Float.parseFloat(parts[3].trim()),
					Float.parseFloat(parts[4].trim()),
					Float.parseFloat(parts[5].trim())
				);
			}
		}
		return null;
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
