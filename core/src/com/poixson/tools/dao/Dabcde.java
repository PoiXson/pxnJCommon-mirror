package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Dabcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;
	public double c;
	public double d;
	public double e;



	public Dabcde() {
		this.a = 0.0;
		this.b = 0.0;
		this.c = 0.0;
		this.d = 0.0;
		this.e = 0.0;
	}
	public Dabcde(final double a, final double b, final double c, final double d, final double e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Dabcde(final Dabcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Dabcde(this.a, this.b, this.c, this.d, this.e);
	}

	public static Dabcde From(final Iabcde dao) {
		return new Dabcde(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c,
			(double) dao.d,
			(double) dao.e
		);
	}
	public static Dabcde From(final Labcde dao) {
		return new Dabcde(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c,
			(double) dao.d,
			(double) dao.e
		);
	}
	public static Dabcde From(final Fabcde dao) {
		return new Dabcde(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c,
			(double) dao.d,
			(double) dao.e
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dabcde) {
			final Dabcde dao = (Dabcde) obj;
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
	public static Dabcde FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 5) {
				return new Dabcde(
					Double.parseDouble(parts[0].trim()),
					Double.parseDouble(parts[1].trim()),
					Double.parseDouble(parts[2].trim()),
					Double.parseDouble(parts[3].trim()),
					Double.parseDouble(parts[4].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.a == 0.0 ? 0.0 : this.a);
		bits = (bits * 31L) + Double.doubleToLongBits(this.b == 0.0 ? 0.0 : this.b);
		bits = (bits * 31L) + Double.doubleToLongBits(this.c == 0.0 ? 0.0 : this.c);
		bits = (bits * 31L) + Double.doubleToLongBits(this.d == 0.0 ? 0.0 : this.d);
		bits = (bits * 31L) + Double.doubleToLongBits(this.e == 0.0 ? 0.0 : this.e);
		return (int) (bits ^ (bits >> 32L));
	}



}
