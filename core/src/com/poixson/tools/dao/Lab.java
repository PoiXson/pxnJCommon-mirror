package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Lab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;



	public Lab() {
		this.a = 0L;
		this.b = 0L;
	}
	public Lab(final long a, final long b) {
		this.a = a;
		this.b = b;
	}
	public Lab(final Lab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Lab(this.a, this.b);
	}

	public static Lab From(final Iab dao) {
		return new Lab(
			(long) dao.a,
			(long) dao.b
		);
	}
	public static Lab From(final Fab dao) {
		return new Lab(
			(long) dao.a,
			(long) dao.b
		);
	}
	public static Lab From(final Dab dao) {
		return new Lab(
			(long) dao.a,
			(long) dao.b
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lab dao) {
			return (
				this.a == dao.a &&
				this.b == dao.b
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b)
			.toString();
	}
	public static Lab FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 2) {
				return new Lab(
					Long.parseLong(parts[0].trim()),
					Long.parseLong(parts[1].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + this.a;
		bits = (bits * 31L) + this.b;
		return (int) (bits ^ (bits >> 32L));
	}



}
