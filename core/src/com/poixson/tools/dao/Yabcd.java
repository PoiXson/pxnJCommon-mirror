package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Yabcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public byte a;
	public byte b;
	public byte c;
	public byte d;



	public Yabcd() {
		this.a = 0x00;
		this.b = 0x00;
		this.c = 0x00;
		this.d = 0x00;
	}
	public Yabcd(final byte a, final byte b, final byte c, final byte d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Yabcd(final Yabcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Yabcd(this.a, this.b, this.c, this.d);
	}



	public static Yabcd From(final Iabcd dao) {
		return new Yabcd(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d
		);
	}
	public static Yabcd From(final Labcd dao) {
		return new Yabcd(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d
		);
	}
	public static Yabcd From(final Fabcd dao) {
		return new Yabcd(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d
		);
	}
	public static Yabcd From(final Dabcd dao) {
		return new Yabcd(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Yabcd dao) {
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
	public static Yabcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				return new Yabcd(
					Byte.parseByte(parts[0].trim()),
					Byte.parseByte(parts[1].trim()),
					Byte.parseByte(parts[2].trim()),
					Byte.parseByte(parts[3].trim())
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
