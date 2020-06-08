package com.poixson.tools;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public enum xTimeU {
	T ( 't', "ticks",  null,                 -1L ),
	MS( 'n', "ms",     TimeUnit.MILLISECONDS, 1L ),
	S ( 's', "second", TimeUnit.SECONDS,   1000L ),
	M ( 'm', "minute", TimeUnit.MINUTES,   1000L * 60L ),
	H ( 'h', "hour",   TimeUnit.HOURS,     1000L * 60L * 60L ),
	D ( 'd', "day",    TimeUnit.DAYS,      1000L * 60L * 60L * 24L ),
	W ( 'w', "week",   null,               1000L * 60L * 60L * 24L * 7L ),
	MO( 'o', "month",  null,               1000L * 60L * 60L * 24L * 30L ),
	Y ( 'y', "year",   null,              (1000L * 60L * 60L * 24L * 365L) + (1000L * 60L * 60L * 6L) );

	public final AtomicInteger ticksPerSecond = new AtomicInteger(20);

	public final char     chr;
	public final String   name;
	public final TimeUnit timeUnit;
	public final long     value;

	private static final CopyOnWriteArrayList<xTimeU> xunits =
			new CopyOnWriteArrayList<xTimeU>() {
		private static final long serialVersionUID = 1L;
		{
			add( xTimeU.T  );
			add( xTimeU.MS );
			add( xTimeU.S  );
			add( xTimeU.M  );
			add( xTimeU.H  );
			add( xTimeU.D  );
			add( xTimeU.W  );
			add( xTimeU.MO );
			add( xTimeU.Y  );
		}
	};



	private xTimeU(final char chr, final String name,
			final TimeUnit timeUnit, final Long value) {
		this.chr      = chr;
		this.name     = name;
		this.timeUnit = timeUnit;
		this.value    = value;
	}



	public static xTimeU[] GetUnits() {
		return xunits.toArray(new xTimeU[0]);
	}
	public static xTimeU GetUnit(final TimeUnit timeUnit) {
		final Iterator<xTimeU> it = xunits.iterator();
		while (it.hasNext()) {
			final xTimeU xunit = it.next();
			if (timeUnit.equals(xunit.timeUnit))
				return xunit;
		}
		return null;
	}
	public static xTimeU GetUnit(final char chr) {
		final Iterator<xTimeU> it = xunits.iterator();
		while (it.hasNext()) {
			final xTimeU xunit = it.next();
			if (xunit.chr == Character.toLowerCase(chr))
				return xunit;
		}
		return null;
	}
	public static xTimeU GetUnit(final String str) {
		final Iterator<xTimeU> it = xunits.iterator();
		while (it.hasNext()) {
			final xTimeU xunit = it.next();
			if (xunit.name.equalsIgnoreCase(str))
				return xunit;
		}
		return null;
	}
	public static xTimeU GetUnit(final long value) {
		final Iterator<xTimeU> it = xunits.iterator();
		while (it.hasNext()) {
			final xTimeU xunit = it.next();
			if (xunit.value == value)
				return xunit;
		}
		return null;
	}



	public long convertTo(final long value) {
		return value * this.value;
	}
	public long convertFrom(final long value) {
		return Math.floorDiv(value, this.value);
	}



	public TimeUnit getTimeUnit() {
		return this.timeUnit;
	}
	public long getUnitValue() {
		if (xTimeU.T.equals(this)) {
			return 1000L / ((long) this.ticksPerSecond.get());
		}
		return this.value;
	}



	public int getTicksPerSecond() {
		return this.ticksPerSecond.get();
	}
	public void setTicksPerSecond(final int value) {
		this.ticksPerSecond.set(value);
	}



	@Override
	public String toString() {
		return this.name;
	}



}
