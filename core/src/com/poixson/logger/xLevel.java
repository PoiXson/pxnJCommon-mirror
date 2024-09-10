package com.poixson.logger;

import static com.poixson.utils.Utils.IsEmpty;

import com.poixson.utils.NumberUtils;


public enum xLevel {
	OFF     ('x',  "off",    Integer.MAX_VALUE),
	ALL     ('a',  "all",    Integer.MIN_VALUE),
	TITLE   ('t',  "title",  9000),
	DETAIL  ('0',  "detail",  100),
	FINEST  ('1',  "finest",  200),
	FINER   ('2',  "finer",   300),
	FINE    ('3',  "fine",    400),
	STATS   ('s',  "stats",   500),
	INFO    ('i',  "info",    600),
	WARNING ('w',  "warning", 700),
	NOTICE  ('n',  "notice",  800),
	SEVERE  ('s',  "severe",  900),
	FATAL   ('f',  "fatal",  1000);



	public final char   chr;
	public final String name;
	public final int    value;



	private xLevel(final char chr, final String name, final int value) {
		this.chr   = chr;
		this.name  = name;
		this.value = value;
	}



	public static xLevel GetLevel(final String name) {
		if (IsEmpty(name)) return null;
		if (NumberUtils.IsNumeric(name)) {
			return GetLevel(NumberUtils.ToInteger(name));
		}
		for (final xLevel level : xLevel.values()) {
			if (name.equalsIgnoreCase(level.name))
				return level;
		}
		return null;
	}
	public static xLevel GetLevel(final Integer value) {
		if (value == null) return null;
		final int val = value.intValue();
		if (val == xLevel.ALL.value) return xLevel.ALL;
		if (val == xLevel.OFF.value) return xLevel.OFF;
		xLevel found = xLevel.OFF;
		int offset = xLevel.OFF.value;
		for (final xLevel level : xLevel.values()) {
			if (xLevel.OFF.equals(level)) continue;
			if (xLevel.ALL.equals(level)) continue;
			if (val < level.value) continue;
			if (val - level.value < offset) {
				offset = val - level.value;
				found = level;
			}
		}
		return found;
	}
	public static xLevel FromJavaLevel(final java.util.logging.Level lvl) {
		if (lvl.equals(java.util.logging.Level.ALL))     return xLevel.ALL;
		if (lvl.equals(java.util.logging.Level.FINEST))  return xLevel.FINEST;
		if (lvl.equals(java.util.logging.Level.FINER))   return xLevel.FINER;
		if (lvl.equals(java.util.logging.Level.FINE))    return xLevel.FINE;
		if (lvl.equals(java.util.logging.Level.CONFIG))  return xLevel.STATS;
		if (lvl.equals(java.util.logging.Level.INFO))    return xLevel.INFO;
		if (lvl.equals(java.util.logging.Level.WARNING)) return xLevel.WARNING;
		if (lvl.equals(java.util.logging.Level.SEVERE))  return xLevel.SEVERE;
		if (lvl.equals(java.util.logging.Level.OFF))     return xLevel.OFF;
		return null;
	}



	public boolean isLoggable(final xLevel level) {
		if (level == null) return false;
		if (this.value == xLevel.OFF.value)  return false; // off (disabled)
		if (this.value == xLevel.ALL.value)  return true;  // all (forced)
		if (level.value == xLevel.ALL.value) return true;  // check level
		if (this.value >= 9000)              return true;  // title
		return (this.value <= level.value);
	}



	public boolean equals(final xLevel level) {
		if (level == null) return false;
		return (level.value == this.value);
	}



	@Override
	public String toString() {
		return this.name;
	}



}
