package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Iabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;



	public Iabc() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Iabc(final int a, final int b, final int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Iabc(final Iabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Iabc(this.a, this.b, this.c);
	}

	public static Iabc From(final Labc dao) {
		return new Iabc(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c
		);
	}
	public static Iabc From(final Fabc dao) {
		return new Iabc(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c
		);
	}
	public static Iabc From(final Dabc dao) {
		return new Iabc(
			(int) dao.a,
			(int) dao.b,
			(int) dao.c
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabc dao) {
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
	public static Iabc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				return new Iabc(
					Integer.parseInt(parts[0].trim()),
					Integer.parseInt(parts[1].trim()),
					Integer.parseInt(parts[2].trim())
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
		return (int) (bits ^ (bits >> 32L));
	}



}
