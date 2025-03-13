package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Yabcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public byte a;
	public byte b;
	public byte c;
	public byte d;
	public byte e;
	public byte f;



	public Yabcdef() {
		this.a = 0x00;
		this.b = 0x00;
		this.c = 0x00;
		this.d = 0x00;
		this.e = 0x00;
		this.f = 0x00;
	}
	public Yabcdef(final byte a, final byte b, final byte c, final byte d, final byte e, final byte f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Yabcdef(final Yabcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Yabcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}



	public static Yabcdef From(final Iabcdef dao) {
		return new Yabcdef(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e,
			(byte) dao.f
		);
	}
	public static Yabcdef From(final Labcdef dao) {
		return new Yabcdef(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e,
			(byte) dao.f
		);
	}
	public static Yabcdef From(final Fabcdef dao) {
		return new Yabcdef(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e,
			(byte) dao.f
		);
	}
	public static Yabcdef From(final Dabcdef dao) {
		return new Yabcdef(
			(byte) dao.a,
			(byte) dao.b,
			(byte) dao.c,
			(byte) dao.d,
			(byte) dao.e,
			(byte) dao.f
		);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Yabcdef dao) {
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
	public static Yabcdef FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 6) {
				return new Yabcdef(
					Byte.parseByte(parts[0].trim()),
					Byte.parseByte(parts[1].trim()),
					Byte.parseByte(parts[2].trim()),
					Byte.parseByte(parts[3].trim()),
					Byte.parseByte(parts[4].trim()),
					Byte.parseByte(parts[5].trim())
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
