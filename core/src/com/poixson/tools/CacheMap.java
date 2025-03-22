package com.poixson.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.tools.abstractions.Triple;


public class CacheMap<K, V> implements Map<K, V> {

	public static final String SECONDS_PER_CYCLE = "10s";
	protected static final long DEFAULT_CYCLES_TIMEOUT  = xTime.Parse( "2m").get(SECONDS_PER_CYCLE);
	protected static final long DEFAULT_CYCLES_SAVE     = xTime.Parse("20s").get(SECONDS_PER_CYCLE);
	protected static final long DEFAULT_CYCLES_SAVE_MAX = xTime.Parse( "1m").get(SECONDS_PER_CYCLE);

	protected final AtomicBoolean inited = new AtomicBoolean(false);

	protected final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
	// <accessed, changed-first, changed-last>
	protected final ConcurrentHashMap<K, Triple<AtomicLong, AtomicLong, AtomicLong>> cycles =
			new ConcurrentHashMap<K, Triple<AtomicLong, AtomicLong, AtomicLong>>();

	// timeout and save = ticks x cycles
	protected final long cycles_timeout;
	protected final long cycles_save;
	protected final long cycles_save_max;
	protected final AtomicLong ticks = new AtomicLong(0);



	public CacheMap() {
		this(
			DEFAULT_CYCLES_TIMEOUT,
			DEFAULT_CYCLES_SAVE,
			DEFAULT_CYCLES_SAVE_MAX
		);
	}
	public CacheMap(final long cycles_timeout,
			final long cycles_save, final long cycles_save_max) {
		this.cycles_timeout  = cycles_timeout;
		this.cycles_save     = cycles_save;
		this.cycles_save_max = cycles_save_max;
	}



	public boolean init() {
		return this.inited.compareAndSet(false, true);
	}



	public V create(final K key) {
		return null;
	}

	public V load(final K key) {
		return null;
	}
	public void lazy_load(final K key, final boolean create) {
		this.get(key, false, create);
	}

	public void save(final K key) {
		this.mark_saved(key);
	}

	public int saveAll() {
		return this.saveAll(false);
	}
	public int saveAllInvalidate() {
		return this.saveAll(true);
	}
	protected int saveAll(final boolean invalidate) {
		int count = 0;
		for (final K key : this.map.keySet()) {
			this.save(key);
			if (invalidate)
				this.invalidate(key);
			count++;
		}
		return count;
	}



	public void tick() {
		// check timeout/save cycles
		{
			final LinkedList<K> keys = new LinkedList<K>();
			for (final K key : this.map.keySet())
				keys.addLast(key);
			for (final K key : keys)
				this.tick(key);
		}
		// cleanup removed
		{
			final LinkedList<K> keys = new LinkedList<K>();
			for (final K key : this.cycles.keySet())
				keys.addLast(key);
			for (final K key : keys) {
				if (!this.map.containsKey(key))
					this.cycles.remove(key);
			}
		}
	}
	protected void tick(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.getCycles(key);
		final long cycle_accessed      = cycles.key.incrementAndGet();
		final long cycle_changed_first = cycles.val.incrementAndGet();
		final long cycle_changed_last  = cycles.ent.incrementAndGet();
		if (cycle_accessed      < 0L) cycles.key.set(Long.MIN_VALUE);
		if (cycle_changed_first < 0L) cycles.val.set(Long.MIN_VALUE);
		if (cycle_changed_last  < 0L) cycles.ent.set(Long.MIN_VALUE);
		// save
		if (cycle_changed_first >= this.cycles_save_max
		||  cycle_changed_last  >= this.cycles_save) {
			this.save(key);
		} else
		// expire
		if (cycle_accessed >= this.cycles_timeout) {
			if (cycle_changed_first >= 0L
			||  cycle_changed_last  >= 0L)
				this.save(key);
			this.invalidate(key);
		}
	}

	public void invalidate(final K key) {
		this.map.remove(key);
	}



	public void mark_accessed(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.getCycles(key);
		cycles.key.set(0L); // accessed
	}
	public void mark_changed(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.getCycles(key);
		cycles.key.set(0L); // accessed
		cycles.ent.set(0L); // changed-last
		final long first = cycles.val.get(); // changed-first
		if (first < 0L) cycles.val.compareAndSet(first, 0L);
	}
	public void mark_saved(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.getCycles(key);
		cycles.val.set(Long.MIN_VALUE); // changed-first
		cycles.ent.set(Long.MIN_VALUE); // changed-last
	}

	public boolean isChanged(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.cycles.get(key);
		if (cycles == null        ) return false; // not loaded
		if (cycles.val.get() >= 0L) return true;  // changed-first
		if (cycles.ent.get() >= 0L) return true;  // changed-last
		return false; // unchanged
	}

	public Triple<AtomicLong, AtomicLong, AtomicLong> getCycles(final K key) {
		final Triple<AtomicLong, AtomicLong, AtomicLong> cycles = this.cycles.get(key);
		if (cycles == null) {
			final AtomicLong cycle_accessed      = new AtomicLong(0L);
			final AtomicLong cycle_changed_first = new AtomicLong(Long.MIN_VALUE);
			final AtomicLong cycle_changed_last  = new AtomicLong(Long.MIN_VALUE);
			final Triple<AtomicLong, AtomicLong, AtomicLong> trip =
				new Triple<AtomicLong, AtomicLong, AtomicLong>(cycle_accessed, cycle_changed_first, cycle_changed_last);
			final Triple<AtomicLong, AtomicLong, AtomicLong> existing = this.cycles.putIfAbsent(key, trip);
			return (existing==null ? trip : existing);
		}
		return cycles;
	}



	@Override
	public int size() {
		return this.map.size();
	}
	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}



	@SuppressWarnings("unchecked")
	public K castKey(final Object obj) {
		return (K) obj;
	}



	@Override
	public boolean containsKey(final Object key) {
		return this.map.containsKey(key);
	}
	@Override
	public boolean containsValue(final Object match) {
		final Iterator<Entry<K, V>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<K, V> entry = it.next();
			final V val = entry.getValue();
			if (val == null) {
				if (match == null)
					return true;
			} else {
				if (val.equals(match))
					return true;
			}
		}
		return false;
	}



	@Override
	public V get(final Object key) {
		return this.get(key, false, true);
	}
	public V get(final Object key,
			final boolean lazy, final boolean create) {
		final K k = this.castKey(key);
		// cached
		{
			final V value = this.map.get(key);
			if (value != null) {
				this.mark_accessed(k);
				return value;
			}
		}
		// lazy load
		if (lazy) {
			this.lazy_load(k, create);
			return null;
		}
		// load now
		this.init();
		{
			final V value = this.load(k);
			if (value != null) {
				this.mark_accessed(k);
				return value;
			}
		}
		// create new
		if (create) {
			final V value = this.create(k);
			if (value != null) {
				final V existing = this.map.putIfAbsent(k, value);
				if (existing == null) {
					this.mark_changed(k);
					return value;
				} else {
					return existing;
				}
			}
		}
		return null;
	}
	@Override
	public V put(final K key, final V value) {
		this.map.put(key, value);
		this.mark_changed(key);
		return value;
	}
	@Override
	public void putAll(final Map<? extends K, ? extends V> map) {
		final Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			final Entry<K, V> entry = (Entry<K, V>) it.next();
			this.put(entry.getKey(), entry.getValue());
		}
	}



	@Override
	public V remove(final Object key) {
		return this.map.remove(key);
	}
	@Override
	public void clear() {
		this.map.clear();
	}



	@Override
	public Set<K> keySet() {
		final Set<K> result = new HashSet<K>();
		for (final K key : this.map.keySet())
			result.add(key);
		return result;
	}
	@Override
	public Collection<V> values() {
		final Set<V> result = new HashSet<V>();
		for (final V value : this.map.values())
			result.add(value);
		return result;
	}
	public Map<K, V> getMap() {
		final Map<K, V> result = new HashMap<K, V>();
		final Iterator<Entry<K, V>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<K, V> entry = it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.getMap().entrySet();
	}



}
