package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Fabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float a;
	public float b;
	public float c;



	public Fabc() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Fabc(final float a, final float b, final float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Fabc(final Fabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Fabc(this.a, this.b, this.c);
	}



	public static Fabc From(final Iabc dao) {
		return new Fabc(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c
		);
	}
	public static Fabc From(final Labc dao) {
		return new Fabc(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c
		);
	}
	public static Fabc From(final Yabc dao) {
		return new Fabc(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c
		);
	}
	public static Fabc From(final Dabc dao) {
		return new Fabc(
			(float) dao.a,
			(float) dao.b,
			(float) dao.c
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fabc dao) {
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c)
			.toString();
	}
	public static Fabc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				return new Fabc(
					Float.parseFloat(parts[0].trim()),
					Float.parseFloat(parts[1].trim()),
					Float.parseFloat(parts[2].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
