package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Yabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public byte a;
	public byte b;
	public byte c;



	public Yabc() {
		this.a = 0x00;
		this.b = 0x00;
		this.c = 0x00;
	}
	public Yabc(final byte a, final byte b, final byte c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Yabc(final Yabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Yabc(this.a, this.b, this.c);
	}



	public static Yabc From(final Iabc dao) {
		return new Yabc(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c
		);
	}
	public static Yabc From(final Labc dao) {
		return new Yabc(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c
		);
	}
	public static Yabc From(final Fabc dao) {
		return new Yabc(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c
		);
	}
	public static Yabc From(final Dabc dao) {
		return new Yabc(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Yabc dao) {
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
	public static Yabc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				return new Yabc(
					Byte.parseByte(parts[0].trim()),
					Byte.parseByte(parts[1].trim()),
					Byte.parseByte(parts[2].trim())
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
