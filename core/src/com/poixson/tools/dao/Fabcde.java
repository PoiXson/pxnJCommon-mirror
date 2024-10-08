package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Fabcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float a;
	public float b;
	public float c;
	public float d;
	public float e;



	public Fabcde() {
		this.a = 0.0F;
		this.b = 0.0F;
		this.c = 0.0F;
		this.d = 0.0F;
		this.e = 0.0F;
	}
	public Fabcde(final float a, final float b, final float c, final float d, final float e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Fabcde(final Fabcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Fabcde(this.a, this.b, this.c, this.d, this.e);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fabcde) {
			final Fabcde dao = (Fabcde) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e
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
			.append(this.e)
			.toString();
	}
	public static Fabcde FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 5) {
				return new Fabcde(
					Float.parseFloat(parts[0].trim()),
					Float.parseFloat(parts[1].trim()),
					Float.parseFloat(parts[2].trim()),
					Float.parseFloat(parts[3].trim()),
					Float.parseFloat(parts[4].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
