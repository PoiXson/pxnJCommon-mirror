package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Labcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;
	public long d;
	public long e;
	public long f;



	public Labcdef() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
		this.d = 0L;
		this.e = 0L;
		this.f = 0L;
	}
	public Labcdef(final long a, final long b, final long c, final long d, final long e, final long f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Labcdef(final Labcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Labcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}

	public static Labcdef From(final Iabcdef dao) {
		return new Labcdef(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e,
			(long) dao.f
		);
	}
	public static Labcdef From(final Fabcdef dao) {
		return new Labcdef(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e,
			(long) dao.f
		);
	}
	public static Labcdef From(final Dabcdef dao) {
		return new Labcdef(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c,
			(long) dao.d,
			(long) dao.e,
			(long) dao.f
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labcdef dao) {
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e &&
				this.f == dao.f
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
			.append(this.e).append(", ")
			.append(this.f)
			.toString();
	}
	public static Labcdef FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 6) {
				return new Labcdef(
					Long.parseLong(parts[0].trim()),
					Long.parseLong(parts[1].trim()),
					Long.parseLong(parts[2].trim()),
					Long.parseLong(parts[3].trim()),
					Long.parseLong(parts[4].trim()),
					Long.parseLong(parts[5].trim())
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
		bits = (bits * 31L) + this.f;
		return (int) (bits ^ (bits >> 32L));
	}



}
