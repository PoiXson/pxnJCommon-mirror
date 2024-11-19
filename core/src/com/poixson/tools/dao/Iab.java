package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Iab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;



	public Iab() {
		this.a = 0;
		this.b = 0;
	}
	public Iab(final int a, final int b) {
		this.a = a;
		this.b = b;
	}
	public Iab(final Iab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Iab(this.a, this.b);
	}

	public static Iab From(final Lab dao) {
		return new Iab(
			(int) dao.a,
			(int) dao.b
		);
	}
	public static Iab From(final Fab dao) {
		return new Iab(
			(int) dao.a,
			(int) dao.b
		);
	}
	public static Iab From(final Dab dao) {
		return new Iab(
			(int) dao.a,
			(int) dao.b
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iab) {
			final Iab dao = (Iab) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b)
			.toString();
	}
	public static Iab FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 2) {
				return new Iab(
					Integer.parseInt(parts[0].trim()),
					Integer.parseInt(parts[1].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.a);
		bits = (bits * 31L) + ((long)this.b);
		return (int) (bits ^ (bits >> 32L));
	}



}
