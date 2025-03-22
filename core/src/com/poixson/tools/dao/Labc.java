package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Labc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;



	public Labc() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
	}
	public Labc(final long a, final long b, final long c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Labc(final Labc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Labc(this.a, this.b, this.c);
	}

	public static Labc From(final Iabc dao) {
		return new Labc(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c
		);
	}
	public static Labc From(final Fabc dao) {
		return new Labc(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c
		);
	}
	public static Labc From(final Dabc dao) {
		return new Labc(
			(long) dao.a,
			(long) dao.b,
			(long) dao.c
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labc dao) {
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
	public static Labc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				return new Labc(
					Long.parseLong(parts[0].trim()),
					Long.parseLong(parts[1].trim()),
					Long.parseLong(parts[2].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
