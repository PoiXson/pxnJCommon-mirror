package com.poixson.tools;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class CacheMap<K, V> implements Map<K, V> {

	protected static final int DEFAULT_CLEANUP_TICKS  = 10;
	protected static final int DEFAULT_CLEANUP_CYCLES = 20;

	protected final ConcurrentHashMap<K, SoftReference<V>> map = new ConcurrentHashMap<K, SoftReference<V>>();
	protected final ConcurrentHashMap<K, AtomicInteger> map_cycles = new ConcurrentHashMap<K, AtomicInteger>();

	protected final int cleanup_ticks;
	protected final int cleanup_cycles;
	protected final AtomicInteger ticks = new AtomicInteger(0);



	public CacheMap() {
		this(
			DEFAULT_CLEANUP_TICKS,
			DEFAULT_CLEANUP_CYCLES
		);
	}
	public CacheMap(final int cleanup_ticks, final int cleanup_cycles) {
		this.cleanup_ticks  = cleanup_ticks;
		this.cleanup_cycles = cleanup_cycles;
	}



	public void cleanup() {
		if (this.ticks.incrementAndGet() >= this.cleanup_ticks) {
			this.ticks.set(0);
			final LinkedList<K> remove = new LinkedList<K>();
			final Iterator<Entry<K, AtomicInteger>> it = this.map_cycles.entrySet().iterator();
			while (it.hasNext()) {
				final Entry<K, AtomicInteger> entry = it.next();
				final int cycles = entry.getValue().incrementAndGet();
				if (cycles >= this.cleanup_cycles)
					remove.add(entry.getKey());
			}
			for (final K key : remove) {
				this.map.remove(key);
				this.map_cycles.remove(key);
			}
		}
	}



	@Override
	public int size() {
		return this.map.size();
	}
	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}



	@Override
	public boolean containsKey(final Object key) {
		return this.map.containsKey(key);
	}
	@Override
	public boolean containsValue(final Object match) {
		final LinkedList<K> remove = new LinkedList<K>();
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
		// cleanup
		if (!remove.isEmpty()) {
			for (final K key : remove) {
				this.map.remove(key);
				this.map_cycles.remove(key);
			}
		}
		return false;
	}



	@Override
	public V get(final Object key) {
		final SoftReference<V> ref = this.map.get(key);
		final AtomicInteger cycles = this.map_cycles.get(key);
		if (cycles != null)
			cycles.set(0);
		this.cleanup();
		return (ref==null ? null : ref.get());
	}
	@Override
	public V put(final K key, final V value) {
		final SoftReference<V> ref = new SoftReference<V>(value);
		final SoftReference<V> result = this.map.put(key, ref);
		this.map_cycles.put(key, new AtomicInteger(0));
		return (result==null ? null : result.get());
	}
	@Override
	public void putAll(final Map<? extends K, ? extends V> map) {
		final Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			final Entry<K, ? extends V> entry = (Entry<K, ? extends V>) it.next();
			final SoftReference<V> ref = new SoftReference<V>( (V)entry.getValue() );
			final K key = (K) entry.getKey();
			this.map.put(key, ref);
			this.map_cycles.put(key, new AtomicInteger(0));
		}
	}



	@Override
	public V remove(final Object key) {
		final SoftReference<V> ref = this.map.remove(key);
		this.map_cycles.remove(key);
		return (ref==null ? null : ref.get());
	}
	@Override
	public void clear() {
		this.map.clear();
	}



	@Override
	public Set<K> keySet() {
		return null;
	}
	@Override
	public Collection<V> values() {
		return null;
	}
	public Map<K, V> getMap() {
		final Map<K, V> result = new HashMap<K, V>();
		final Iterator<Entry<K, SoftReference<V>>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<K, SoftReference<V>> entry = it.next();
			final SoftReference<V> ref = entry.getValue();
			final V value = ref.get();
			if (value != null)
				result.put(entry.getKey(), value);
		}
		return result;
	}
	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.getMap().entrySet();
	}



}
