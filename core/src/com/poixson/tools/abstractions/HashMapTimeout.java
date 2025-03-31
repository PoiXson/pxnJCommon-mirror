/*
package com.poixson.tools.abstractions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;


public class HashMapTimeout<K, V> extends ConcurrentHashMap<K, V> implements Runnable {
	private static final long serialVersionUID = 1L;

	protected final AtomicLong timeout = new AtomicLong(0L);

	protected final ConcurrentHashMap<K, Long> ticks = new ConcurrentHashMap<K, Long>();

	protected final CopyOnWriteArraySet<TimeoutHook<K, V>> hooks_timeout = new CopyOnWriteArraySet<TimeoutHook<K, V>>();



	public HashMapTimeout() {
		super();
	}
	public HashMapTimeout(final int initial_capacity) {
		super(initial_capacity);
	}
	public HashMapTimeout(final Map<? extends K, ? extends V> map) {
		super(map);
	}
	public HashMapTimeout(final int initial_capacity, final float load_factor) {
		super(initial_capacity, load_factor);
	}
	public HashMapTimeout(final int initial_capacity, final float load_factor, final int concurrency_level) {
		super(initial_capacity, load_factor, concurrency_level);
	}



	@Override
	public void run() {
		final long timeout = this.timeout.get();
		if (timeout > 0L) {
			// increment and clean
			for (final K key : this.ticks.keySet()) {
				final long ticks = this.ticks.get(key).longValue() + 1L;
				if (ticks < timeout) {
					this.ticks.put(key, Long.valueOf(ticks));
				} else {
					this.flush(key);
					this.timeout(key);
				}
			}
			// new entries
			for (final K key : this.keySet()) {
				if (!this.ticks.containsKey(key))
					this.ticks.put(key, Long.valueOf(0L));
			}
		} else {
			this.flushAll();
		}
	}



	public void flush(final K key) {
		this.ticks.remove(key);
	}
	public void flushAll() {
		this.ticks.clear();
	}



	// -------------------------------------------------------------------------------
	// timeout



	public interface TimeoutHook<K, V> {

		public void onTimeout(final K key, final V value);

	}



	public void setTimeoutHook(final TimeoutHook<K, V> hook) {
		this.hooks_timeout.add(hook);
	}

	public long setTimeoutTicks(final long ticks) {
		return this.timeout.getAndSet(ticks);
	}



	public void timeout(final K key) {
		final V value = this.remove(key);
		if (!this.hooks_timeout.isEmpty()) {
			for (final TimeoutHook<K, V> hook : this.hooks_timeout)
				hook.onTimeout(key, value);
		}
	}



	// -------------------------------------------------------------------------------



	@Override
	public V put(final K key, final V value) {
		this.flush(key);
		return super.put(key, value);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> map) {
		super.putAll(map);
		for (final K key : map.keySet())
			this.flush(key);
	}

	@Override
	public V putIfAbsent(final K key, final V value) {
		final V result = super.putIfAbsent(key, value);
		if (result == null)
			this.flush(key);
		return result;
	}



	@Override
	public V replace(final K key, final V value) {
		this.flush(key);
		return super.replace(key, value);
	}
	@Override
	public boolean replace(K key, V value_old, V value_new) {
		if (super.replace(key, value_old, value_new)) {
			this.flush(key);
			return true;
		}
		return false;
	}



	@Override
	public void clear() {
		super.clear();
		this.flushAll();
	}



}
*/
