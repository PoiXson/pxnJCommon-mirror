package com.poixson.tools.config;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.poixson.utils.NumberUtils;
import com.poixson.utils.ObjectUtils;


public class xConfig {

	protected final Map<String, Object> datamap = new ConcurrentHashMap<String, Object>();



	public xConfig(final Map<String, Object> datamap) {
		this();
		if (datamap != null)
			this.datamap.putAll(datamap);
	}
	protected xConfig() {
	}



	public boolean hasKey(final String key) {
		return this.datamap.containsKey(key);
	}



	// -------------------------------------------------------------------------------
	// data getters



	public Object read(final String key) {
		if (IsEmpty(key))
			return null;
		return this.datamap.get(key);
	}
	public Object readSafe(final String key) {
		try {
			return this.read(key);
		} catch (Exception ignore) {}
		return null;
	}



	// -------------------------------------------------------------------------------
	// primitives



	// string
	public String getString(final String key) {
		return (String) this.read(key);
	}
	public String getStr(final String key, final String defVal) {
		return this.getStr(key, defVal, false);
	}
	public String getStr(final String key, final String defVal, final boolean blankable) {
		try {
			final String value = this.getString(key);
			if (blankable) {
				if (value != null)
					return value;
			} else {
				if (IsEmpty(value))
					return value;
			}
		} catch (Exception ignore) {}
		return defVal;
	}



	// boolean
	public Boolean getBoolean(final String key) {
		return NumberUtils.CastBoolean( this.read(key) );
	}
	public boolean getBool(final String key, final boolean defVal) {
		try {
			final Boolean value = this.getBoolean(key);
			if (value != null)
				return value.booleanValue();
		} catch (Exception ignore) {}
		return defVal;
	}



	// integer
	public Integer getInteger(final String key) {
		return NumberUtils.CastInteger( this.read(key) );
	}
	public int getInt(final String key, final int defVal) {
		try {
			final Integer value = this.getInteger(key);
			if (value != null)
				return value.intValue();
		} catch (Exception ignore) {}
		return defVal;
	}



	// long
	public Long getLong(final String key) {
		return NumberUtils.CastLong( this.read(key) );
	}
	public long getLng(final String key, final long defVal) {
		try {
			final Long value = this.getLong(key);
			if (value != null)
				return value.longValue();
		} catch (Exception ignore) {}
		return defVal;
	}



	// double
	public Double getDouble(final String key) {
		return NumberUtils.CastDouble( this.read(key) );
	}
	public double getDbl(final String key, final double defVal) {
		try {
			final Double value = this.getDouble(key);
			if (value != null)
				return value.doubleValue();
		} catch (Exception ignore) {}
		return defVal;
	}



	// float
	public Float getFloat(final String key) {
		return NumberUtils.CastFloat( this.read(key) );
	}
	public float getFlt(final String key, final float defVal) {
		try {
			final Float value = this.getFloat(key);
			if (value != null)
				return value.floatValue();
		} catch (Exception ignore) {}
		return defVal;
	}



	// -------------------------------------------------------------------------------
	// set/list/map data getters



	// set
	public <C> Set<C> getSet(final String key, final Class<? extends C> clss) {
		return ObjectUtils.CastSet(this.read(key), clss);
	}
	public Set<String> getStringSet(final String key) {
		return this.getSet(key, String.class);
	}



	// list
	public <C> List<C> getList(final String key, final Class<? extends C> clss) {
		return ObjectUtils.CastList(this.read(key), clss);
	}
	public List<String> getStringList(final String key) {
		return this.getList(key, String.class);
	}



	// map
	public <K, V> Map<K, V> getMap(final String key,
			final Class<? extends K> clssK, final Class<? extends V> clssV) {
		return ObjectUtils.CastMap(this.read(key), clssK, clssV);
	}
	public Map<String, Object> getStringObjectMap(final String key) {
		return this.getMap(key, String.class, Object.class);
	}
	public Map<String, String> getStringMap(final String key) {
		return this.getMap(key, String.class, String.class);
	}



	public <T extends xConfig> List<T> getConfigList(final String key,
			final Class<T> cfgClass) {
		final List<Object> datalist = this.getList(key, Object.class);
		if (datalist == null)
			return null;
		final List<T> list = new ArrayList<T>();
		final Iterator<Object> it = datalist.iterator();
		while (it.hasNext()) {
			final Object obj = it.next();
			final Map<String, Object> datamap = ObjectUtils.CastMap(obj, String.class, Object.class);
			if (datamap == null)
				throw new RuntimeException("Failed to get Map constructor for class: "+cfgClass.getName());
			final T cfg = xConfigLoader.NewConfig(datamap, cfgClass);
			list.add(cfg);
		}
		return list;
	}



}
