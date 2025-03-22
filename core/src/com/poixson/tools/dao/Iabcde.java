package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Iabcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;
	public int d;
	public int e;



	public Iabcde() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
		this.d = 0;
		this.e = 0;
	}
	public Iabcde(final int a, final int b, final int c, final int d, final int e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Iabcde(final Iabcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Iabcde(this.a, this.b, this.c, this.d, this.e);
	}

	public static Iabcde From(final Labcde dao) {
		return new Iabcde(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e
		);
	}
	public static Iabcde From(final Fabcde dao) {
		return new Iabcde(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e
		);
	}
	public static Iabcde From(final Dabcde dao) {
		return new Iabcde(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabcde dao) {
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
	public static Iabcde FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 5) {
				return new Iabcde(
					Integer.parseInt(parts[0].trim()),
					Integer.parseInt(parts[1].trim()),
					Integer.parseInt(parts[2].trim()),
					Integer.parseInt(parts[3].trim()),
					Integer.parseInt(parts[4].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.a);
		bits = (bits * 31L) + ((long)this.b);
		bits = (bits * 31L) + ((long)this.c);
		bits = (bits * 31L) + ((long)this.d);
		bits = (bits * 31L) + ((long)this.e);
		return (int) (bits ^ (bits >> 32L));
	}



}
