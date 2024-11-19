package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Labcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;
	public long d;



	public Labcd() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
		this.d = 0L;
	}
	public Labcd(final long a, final long b, final long c, final long d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Labcd(final Labcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Labcd(this.a, this.b, this.c, this.d);
	}

	public static Labcd From(final Iabcd dao) {
		return new Labcd(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d
		);
	}
	public static Labcd From(final Fabcd dao) {
		return new Labcd(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d
		);
	}
	public static Labcd From(final Dabcd dao) {
		return new Labcd(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labcd) {
			final Labcd dao = (Labcd) obj;
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
	public static Labcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				return new Labcd(
					Long.parseLong(parts[0].trim()),
					Long.parseLong(parts[1].trim()),
					Long.parseLong(parts[2].trim()),
					Long.parseLong(parts[3].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + this.a;
		bits = (bits * 31L) + this.b;
		bits = (bits * 31L) + this.c;
		bits = (bits * 31L) + this.d;
		return (int) (bits ^ (bits >> 32L));
	}



}
