package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Dab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;



	public Dab() {
		this.a = 0.0;
		this.b = 0.0;
	}
	public Dab(final double a, final double b) {
		this.a = a;
		this.b = b;
	}
	public Dab(final Dab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Dab(this.a, this.b);
	}

	public static Dab From(final Iab dao) {
		return new Dab(
			(double) dao.a,
			(double) dao.b
		);
	}
	public static Dab From(final Lab dao) {
		return new Dab(
			(double) dao.a,
			(double) dao.b
		);
	}
	public static Dab From(final Fab dao) {
		return new Dab(
			(double) dao.a,
			(double) dao.b
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dab) {
			final Dab dao = (Dab) obj;
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
	public static Dab FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 2) {
				return new Dab(
					Double.parseDouble(parts[0].trim()),
					Double.parseDouble(parts[1].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.a == 0.0 ? 0.0 : this.a);
		bits = (bits * 31L) + Double.doubleToLongBits(this.b == 0.0 ? 0.0 : this.b);
		return (int) (bits ^ (bits >> 32L));
	}



}
