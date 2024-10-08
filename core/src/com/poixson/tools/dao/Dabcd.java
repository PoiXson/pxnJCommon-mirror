package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Dabcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;
	public double c;
	public double d;



	public Dabcd() {
		this.a = 0.0;
		this.b = 0.0;
		this.c = 0.0;
		this.d = 0.0;
	}
	public Dabcd(final double a, final double b, final double c, final double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Dabcd(final Dabcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Dabcd(this.a, this.b, this.c, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dabcd) {
			final Dabcd dao = (Dabcd) obj;
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
	public static Dabcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				return new Dabcd(
					Double.parseDouble(parts[0].trim()),
					Double.parseDouble(parts[1].trim()),
					Double.parseDouble(parts[2].trim()),
					Double.parseDouble(parts[3].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
