/*
package com.poixson.logger;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.poixson.utils.NumberUtils;
import com.poixson.utils.Utils;


public enum xLevel {
	OFF     ('x',  "off",    Integer.MAX_VALUE),
	ALL     ('a',  "all",    Integer.MIN_VALUE),
	TITLE   ('t',  "title",  9000),
	DETAIL  ('d',  "detail",  100),
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

	private static final CopyOnWriteArrayList<xLevel> levels = new CopyOnWriteArrayList<xLevel>() {
		private static final long serialVersionUID = 1L;
		{
			add( xLevel.OFF     );
			add( xLevel.ALL     );
			add( xLevel.TITLE   );
			add( xLevel.DETAIL  );
			add( xLevel.FINEST  );
			add( xLevel.FINER   );
			add( xLevel.FINE    );
			add( xLevel.STATS   );
			add( xLevel.INFO    );
			add( xLevel.WARNING );
			add( xLevel.NOTICE  );
			add( xLevel.SEVERE  );
			add( xLevel.FATAL   );
		}
	};



	private xLevel(final char chr, final String name, final int value) {
		this.chr   = chr;
		this.name  = name;
		this.value = value;
	}



	public static xLevel[] getLevels() {
		return levels.toArray(new xLevel[0]);
	}
	public static xLevel FindLevel(final String name) {
		if (Utils.isEmpty(name)) return null;
		if (NumberUtils.IsNumeric(name)) {
			return FindLevel(NumberUtils.ToInteger(name));
		}
		final Iterator<xLevel> it = levels.iterator();
		while (it.hasNext()) {
			final xLevel level = it.next();
			if (name.equalsIgnoreCase(level.name))
				return level;
		}
		return null;
	}
	public static xLevel FindLevel(final Integer value) {
		if (value == null) return null;
		final int val = value.intValue();
		if (val == xLevel.ALL.value) return xLevel.ALL;
		if (val == xLevel.OFF.value) return xLevel.OFF;
		xLevel found = xLevel.OFF;
		int offset = xLevel.OFF.value;
		final Iterator<xLevel> it = levels.iterator();
		while (it.hasNext()) {
			final xLevel lvl = it.next();
			if (xLevel.OFF.equals(lvl)) continue;
			if (xLevel.ALL.equals(lvl)) continue;
			if (val < lvl.value) continue;
			if (val - lvl.value < offset) {
				offset = val - lvl.value;
				found = lvl;
			}
		}
		return found;
	}
	public static xLevel parse(final String value) {
		return FindLevel(value);
	}



	public boolean isLoggable(final xLevel level) {
		if (level == null) return false;
		// off (disabled)
		if (this.value == xLevel.OFF.value)
			return false;
		// all (forced)
		if (this.value == xLevel.ALL.value)
			return true;
		// check level
		if (level.value == xLevel.ALL.value)
			return true;
		return (this.value <= level.value);
	}
	public boolean notLoggable(final xLevel level) {
		if (level == null) return false;
		return ! this.isLoggable(level);
	}



	public boolean equals(final xLevel level) {
		if (level == null) return false;
		return (level.value == this.value);
	}



//TODO: is this needed?
//	// to java level
//	public java.util.logging.Level getJavaLevel() {
//		return java.util.logging.Level.parse(
//			Integer.toString(this.value)
//		);
//	}



	@Override
	public String toString() {
		return this.name;
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



}
*/
