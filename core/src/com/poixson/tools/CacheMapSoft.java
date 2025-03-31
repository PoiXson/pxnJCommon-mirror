/*
package com.poixson.tools;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedList;


public class CacheMapSoft<K, V> extends CacheMap<K, SoftReference<V>> {



	public CacheMapSoft() {
		super();
	}
	public CacheMapSoft(final int cleanup_ticks,
			final int cleanup_cycles, final int cleanup_cycles_max) {
		super(cleanup_ticks, cleanup_cycles, cleanup_cycles_max);
	}



	@Override
	public boolean containsValue(final Object match) {
		final LinkedList<K> remove = new LinkedList<K>();
		try {
			final Iterator<Entry<K, SoftReference<V>>> it = this.map.entrySet().iterator();
			while (it.hasNext()) {
				final Entry<K, SoftReference<V>> entry = it.next();
				final SoftReference<V> ref = entry.getValue();
				final V val = ref.get();
				if (val == null) {
					remove.add(entry.getKey());
				} else {
					if (val.equals(match))
						return true;
				}
			}
		} finally {
			if (!remove.isEmpty()) {
				for (final K key : remove)
					this.invalidate(key);
			}
		}
		return false;
	}



	public V getref(final Object key) {
		final SoftReference<V> ref = this.map.get(key);
		final V value = ref.get();
		if (value == null) {
			this.invalidate(this.castKey(key));
			return null;
		} else {
			this.mark_accessed(this.castKey(key));
			return value;
		}
	}
	public V putref(final K key, final V value) {
		final SoftReference<V> result = this.map.put(key, new SoftReference<V>(value));
		this.mark_changed(key);
		return (result==null ? null : result.get());
	}



}
*/
