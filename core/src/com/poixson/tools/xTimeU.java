package com.poixson.tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public enum xTimeU {
	T ( 't', "tick",   null,                 -1L ),
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

	public static final List<xTimeU> xunits =
		Collections.unmodifiableList(
			Arrays.asList(
				xTimeU.Y,
				xTimeU.MO,
				xTimeU.W,
				xTimeU.D,
				xTimeU.H,
				xTimeU.M,
				xTimeU.S,
				xTimeU.MS,
				xTimeU.T
			)
		);



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
		final String match = str.toUpperCase();
		switch (match) {
		case "T":
		case "TK":
		case "TCK":
		case "TICK":
		case "TICKS":
			return xTimeU.T;
		case "N":
		case "MS":
			return xTimeU.MS;
		case "S":
		case "SEC":
		case "SECS":
		case "SECOND":
		case "SECONDS":
			return xTimeU.S;
		case "M":
		case "MIN":
		case "MINUTE":
		case "MINUTES":
			return xTimeU.M;
		case "H":
		case "HR":
		case "HOUR":
		case "HOURS":
			return xTimeU.H;
		case "D":
		case "DY":
		case "DAY":
		case "DAYS":
			return xTimeU.D;
		case "W":
		case "WK":
		case "WEEK":
		case "WEEKS":
			return xTimeU.W;
		case "O":
		case "MN":
		case "MTH":
		case "MONTH":
		case "MONTHS":
			return xTimeU.MO;
		case "Y":
		case "YR":
		case "YEAR":
		case "YEARS":
			return xTimeU.Y;
		default:
		}
		return null;
	}
	public static xTimeU GetUnit(final long value) {
		final Iterator<xTimeU> it = xunits.iterator();
		while (it.hasNext()) {
			final xTimeU xunit = it.next();
			if (xunit.getUnitValue() == value)
				return xunit;
		}
		return null;
	}



	public long convertTo(final long value) {
		return value * this.getUnitValue();
	}
	public double convertTo(final double value) {
		return value * ((double)this.getUnitValue());
	}



	public long convertFrom(final long value) {
		return Math.floorDiv(value, this.getUnitValue());
	}
	public double convertFrom(final double value) {
		return value / ((double)this.getUnitValue());
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
