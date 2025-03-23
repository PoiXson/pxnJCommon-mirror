package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Dabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;
	public double c;



	public Dabc() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Dabc(final double a, final double b, final double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Dabc(final Dabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Dabc(this.a, this.b, this.c);
	}



	public static Dabc From(final Iabc dao) {
		return new Dabc(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c
		);
	}
	public static Dabc From(final Labc dao) {
		return new Dabc(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c
		);
	}
	public static Dabc From(final Yabc dao) {
		return new Dabc(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c
		);
	}
	public static Dabc From(final Fabc dao) {
		return new Dabc(
			(double) dao.a,
			(double) dao.b,
			(double) dao.c
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dabc dao) {
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
	public static Dabc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				return new Dabc(
					Double.parseDouble(parts[0].trim()),
					Double.parseDouble(parts[1].trim()),
					Double.parseDouble(parts[2].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
