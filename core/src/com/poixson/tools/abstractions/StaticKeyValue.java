package com.poixson.tools.abstractions;


public class StaticKeyValue<K, V> {

	public final K key;
	public final V value;



	public StaticKeyValue(final K key, final V value) {
		this.key   = key;
		this.value = value;
	}



}
