package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Yab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public byte a;
	public byte b;



	public Yab() {
		this.a = 0x00;
		this.b = 0x00;
	}
	public Yab(final byte a, final byte b) {
		this.a = a;
		this.b = b;
	}
	public Yab(final Yab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Yab(this.a, this.b);
	}



	public static Yab From(final Iab dao) {
		return new Yab(
			(byte) dao.a,
			(byte) dao.b
		);
	}
	public static Yab From(final Lab dao) {
		return new Yab(
			(byte) dao.a,
			(byte) dao.b
		);
	}
	public static Yab From(final Fab dao) {
		return new Yab(
			(byte) dao.a,
			(byte) dao.b
		);
	}
	public static Yab From(final Dab dao) {
		return new Yab(
			(byte) dao.a,
			(byte) dao.b
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Yab dao) {
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
	public static Yab FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 2) {
				return new Yab(
					Byte.parseByte(parts[0].trim()),
					Byte.parseByte(parts[1].trim())
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.a);
		bits = (bits * 31L) + ((long)this.b);
		return (int) (bits ^ (bits >> 32L));
	}



}
