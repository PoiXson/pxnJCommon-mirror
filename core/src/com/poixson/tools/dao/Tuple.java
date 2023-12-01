package com.poixson.tools.dao;


public class Tuple<X, Y> {

	public final X x;
	public final Y y;



	public Tuple(final X x, final Y y) {
		this.x = x;
		this.y = y;
	}



	@Override
	public boolean equals(final Object match) {
		if (this == match)             return true;
		if (!(match instanceof Tuple)) return false;
		@SuppressWarnings("unchecked")
		final Tuple<X, Y> tup = (Tuple<X, Y>) match;
		int i = 0;
		if (this.x == null && tup.x == null) i++;
		if (this.y == null && tup.y == null) i++;
		if (i == 2) return true;
		if (i == 1) {
			if (this.x == null) return this.y.equals(tup.y);
			else                return this.x.equals(tup.x);
		}
		return (
			this.x.equals(tup.x) &&
			this.y.equals(tup.y)
		);
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append('[')
			.append(this.x)
			.append(',')
			.append(this.y)
			.append(']')
			.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (result * 31) + (this.x == null ? 0 : this.x.hashCode());
		result = (result * 31) + (this.y == null ? 0 : this.y.hashCode());
		return result;
	}



}
