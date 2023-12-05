package com.poixson.tools.abstractions;


public class Tuple<K, V> {

	public final K key;
	public final V val;



	public Tuple(final K key, final V val) {
		this.key = key;
		this.val = val;
	}



	@Override
	public boolean equals(final Object match) {
		if (this == match)             return true;
		if (!(match instanceof Tuple)) return false;
		@SuppressWarnings("unchecked")
		final Tuple<K, V> tup = (Tuple<K, V>) match;
		int i = 0;
		if (this.key == null && tup.key == null) i++;
		if (this.val == null && tup.val == null) i++;
		if (i == 2) return true;
		if (i == 1) {
			if (this.key == null) return this.val.equals(tup.val);
			else                  return this.key.equals(tup.key);
		}
		return (
			this.key.equals(tup.key) &&
			this.val.equals(tup.val)
		);
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append('[')
			.append(this.key)
			.append(',')
			.append(this.val)
			.append(']')
			.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (result * 31) + (this.key == null ? 0 : this.key.hashCode());
		result = (result * 31) + (this.val == null ? 0 : this.val.hashCode());
		return result;
	}



}
