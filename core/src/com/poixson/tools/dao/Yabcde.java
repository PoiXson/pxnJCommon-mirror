package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Yabcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public byte a;
	public byte b;
	public byte c;
	public byte d;
	public byte e;



	public Yabcde() {
		this.a = 0x00;
		this.b = 0x00;
		this.c = 0x00;
		this.d = 0x00;
		this.e = 0x00;
	}
	public Yabcde(final byte a, final byte b, final byte c, final byte d, final byte e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Yabcde(final Yabcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Yabcde(this.a, this.b, this.c, this.d, this.e);
	}



	public static Yabcde From(final Iabcde dao) {
		return new Yabcde(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e
		);
	}
	public static Yabcde From(final Labcde dao) {
		return new Yabcde(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e
		);
	}
	public static Yabcde From(final Fabcde dao) {
		return new Yabcde(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e
		);
	}
	public static Yabcde From(final Dabcde dao) {
		return new Yabcde(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Yabcde dao) {
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
	public static Yabcde FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 5) {
				return new Yabcde(
					Byte.parseByte(parts[0].trim()),
					Byte.parseByte(parts[1].trim()),
					Byte.parseByte(parts[2].trim()),
					Byte.parseByte(parts[3].trim()),
					Byte.parseByte(parts[4].trim())
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
