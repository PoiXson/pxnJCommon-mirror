package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Iabcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;
	public int d;
	public int e;
	public int f;



	public Iabcdef() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
		this.d = 0;
		this.e = 0;
		this.f = 0;
	}
	public Iabcdef(final int a, final int b, final int c, final int d, final int e, final int f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Iabcdef(final Iabcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Iabcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}

	public static Iabcdef From(final Labcdef dao) {
		return new Iabcdef(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e,
			(int) dao.f
		);
	}
	public static Iabcdef From(final Fabcdef dao) {
		return new Iabcdef(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e,
			(int) dao.f
		);
	}
	public static Iabcdef From(final Dabcdef dao) {
		return new Iabcdef(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d,
			(int) dao.e,
			(int) dao.f
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabcdef) {
			final Iabcdef dao = (Iabcdef) obj;
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
	public static Iabcdef FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 6) {
				return new Iabcdef(
					Integer.parseInt(parts[0].trim()),
					Integer.parseInt(parts[1].trim()),
					Integer.parseInt(parts[2].trim()),
					Integer.parseInt(parts[3].trim()),
					Integer.parseInt(parts[4].trim()),
					Integer.parseInt(parts[5].trim())
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
		bits = (bits * 31L) + ((long)this.f);
		return (int) (bits ^ (bits >> 32L));
	}



}
