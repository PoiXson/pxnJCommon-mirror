package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Fabcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float a;
	public float b;
	public float c;
	public float d;



	public Fabcd() {
		this.a = 0.0F;
		this.b = 0.0F;
		this.c = 0.0F;
		this.d = 0.0F;
	}
	public Fabcd(final float a, final float b, final float c, final float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Fabcd(final Fabcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Fabcd(this.a, this.b, this.c, this.d);
	}

	public static Fabcd From(final Iabcd dao) {
		return new Fabcd(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d
		);
	}
	public static Fabcd From(final Labcd dao) {
		return new Fabcd(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d
		);
	}
	public static Fabcd From(final Dabcd dao) {
		return new Fabcd(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c,
			(float) dao.d
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fabcd) {
			final Fabcd dao = (Fabcd) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d
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
			.append(this.d)
			.toString();
	}
	public static Fabcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				return new Fabcd(
					Float.parseFloat(parts[0].trim()),
					Float.parseFloat(parts[1].trim()),
					Float.parseFloat(parts[2].trim()),
					Float.parseFloat(parts[3].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
