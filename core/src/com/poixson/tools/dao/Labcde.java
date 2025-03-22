package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Labcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;
	public long d;
	public long e;



	public Labcde() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
		this.d = 0L;
		this.e = 0L;
	}
	public Labcde(final long a, final long b, final long c, final long d, final long e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Labcde(final Labcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Labcde(this.a, this.b, this.c, this.d, this.e);
	}

	public static Labcde From(final Iabcde dao) {
		return new Labcde(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e
		);
	}
	public static Labcde From(final Fabcde dao) {
		return new Labcde(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e
		);
	}
	public static Labcde From(final Dabcde dao) {
		return new Labcde(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labcde dao) {
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
	public static Labcde FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 5) {
				return new Labcde(
					Long.parseLong(parts[0].trim()),
					Long.parseLong(parts[1].trim()),
					Long.parseLong(parts[2].trim()),
					Long.parseLong(parts[3].trim()),
					Long.parseLong(parts[4].trim())
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
		bits = (bits * 31L) + this.e;
		return (int) (bits ^ (bits >> 32L));
	}



}
