package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Iabcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;
	public int d;



	public Iabcd() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
		this.d = 0;
	}
	public Iabcd(final int a, final int b, final int c, final int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Iabcd(final Iabcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Iabcd(this.a, this.b, this.c, this.d);
	}



	public static Iabcd From(final Labcd dao) {
		return new Iabcd(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d
		);
	}
	public static Iabcd From(final Yabcd dao) {
		return new Iabcd(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d
		);
	}
	public static Iabcd From(final Fabcd dao) {
		return new Iabcd(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d
		);
	}
	public static Iabcd From(final Dabcd dao) {
		return new Iabcd(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c,
			(int) dao.d
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabcd dao) {
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
	public static Iabcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				return new Iabcd(
					Integer.parseInt(parts[0].trim()),
					Integer.parseInt(parts[1].trim()),
					Integer.parseInt(parts[2].trim()),
					Integer.parseInt(parts[3].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
