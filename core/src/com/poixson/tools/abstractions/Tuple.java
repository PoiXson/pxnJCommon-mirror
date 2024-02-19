package com.poixson.tools.abstractions;


public class Tuple<K, V> {

	public final K key;
	public final V val;



	public Tuple(final K key, final V val) {
		this.key = key;
		this.val = val;
	}



//TODO: fix this and unit test
	@Override
	public boolean equals(final Object match) {
		if (this == match)             return true;
		if (!(match instanceof Tuple)) return false;
		@SuppressWarnings("unchecked")
		final Tuple<K, V> tup = (Tuple<K, V>) match;
		return (this.hashCode() == tup.hashCode());
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append('[').append(this.key)
			.append(',').append(this.val)
			.append(']').toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (result * 31) + (this.key == null ? 0 : this.key.hashCode());
		result = (result * 31) + (this.val == null ? 0 : this.val.hashCode());
		return result;
	}



}
