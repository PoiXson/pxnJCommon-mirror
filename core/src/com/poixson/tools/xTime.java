package com.poixson.tools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.exceptions.UnmodifiableObjectException;
import com.poixson.utils.Utils;


public class xTime {

	// stored value in ms
	public final AtomicLong value = new AtomicLong(0L);
	// write lock
	protected final AtomicBoolean locked = new AtomicBoolean(false);



	// ------------------------------------------------------------------------------- //
	// get object



	public static xTime getNew() {
		return new xTime(0);
	}
	public static xTime getNew(final long ms) {
		return getNew(ms, xTimeU.MS);
	}
	public static xTime getNew(final long value, final xTimeU xunit) {
		if (value < 0) return null;
		return getNew().set(value, xunit);
	}
	public static xTime getNew(final long value, final TimeUnit unit) {
		if (value < 0) return null;
		return getNew().set(value, unit);
	}
	public static xTime getNew(final String value) {
		if (Utils.isEmpty(value)) return null;
		return getNew().set(value);
	}
	public static xTime getNew(final xTime time) {
		if (time == null) return null;
		return getNew().set(time);
	}
	// new object
	protected xTime(final long ms) {
		this.value.set(ms);
	}
	// clone object
	@Override
	public xTime clone() {
		return getNew(this);
	}



	// ------------------------------------------------------------------------------- //
	// lock value



	public xTime lock() {
		this.locked.set(true);
		return this;
	}
	public boolean isLocked() {
		return this.locked.get();
	}



	// ------------------------------------------------------------------------------- //
	// get/set value



	// get value
	public long get(final xTimeU xunit) {
		if (xunit == null) throw new RequiredArgumentException("xunit");
		return xunit.convertTo( this.value.get() );
	}
	public long get(final TimeUnit unit) {
		if (unit == null)  throw new RequiredArgumentException("unit");
		final xTimeU xunit = xTimeU.GetUnit(unit);
		if (xunit == null) throw new RuntimeException("Unknown time unit: "+unit.toString());
		return xunit.convertTo( this.value.get() );
	}
	public String getString() {
		return xTime.ToString(this);
	}
	public long getMS() {
		return this.value.get();
	}
	public int getTicks() {
		return (int) xTimeU.T.convertFrom( this.value.get() );
	}



	// set value
	public xTime set(final long value, final xTimeU xunit) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (xunit == null) throw new RequiredArgumentException("unit");
		this.value.set( xunit.convertTo(value) );
		return this;
	}
	public xTime set(final long value, final TimeUnit unit) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (unit == null)      throw new RequiredArgumentException("unit");
		final xTimeU xunit = xTimeU.GetUnit(unit);
		if (xunit == null)     throw new RuntimeException("Unknown time unit: "+unit.toString());
		this.value.set( xunit.convertTo(value) );
		return this;
	}
	public xTime set(final String val) {
		if (this.locked.get())  throw new UnmodifiableObjectException();
		if (Utils.isEmpty(val)) throw new RequiredArgumentException("val");
		final Long value = parseToLong(val);
		if (value == null)      throw new RuntimeException("Invalid value: "+val);
		this.value.set( value.longValue() );
		return this;
	}
	public xTime set(final xTime time) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (time == null)      throw new RequiredArgumentException("time");
		this.value.set( time.getMS() );
		return this;
	}



	// reset value to 0
	public void reset() {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		this.value.set(0L);
	}



	// add time
	public void add(final long val, final xTimeU xunit) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (xunit == null)     throw new RequiredArgumentException("unit");
		this.value.addAndGet( xunit.convertTo(val) );
	}
	public void add(final long val, final TimeUnit unit) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (unit == null)      throw new RequiredArgumentException("unit");
		final xTimeU xunit = xTimeU.GetUnit(unit);
		if (xunit == null)     throw new RuntimeException("Unknown time unit: "+unit.toString());
		this.value.addAndGet( xunit.convertTo(val) );
	}
	public void add(final String val) {
		if (this.locked.get())  throw new UnmodifiableObjectException();
		if (Utils.isEmpty(val)) throw new RequiredArgumentException("val");
		final Long value = parseToLong(val);
		if (value == null)      throw new RuntimeException("Invalid value: "+val);
		this.value.addAndGet( value.longValue() );
	}
	public void add(final xTime time) {
		if (this.locked.get()) throw new UnmodifiableObjectException();
		if (time == null)      throw new RequiredArgumentException("time");
		this.value.addAndGet( time.getMS() );
	}



	// parse time from string
	public static xTime parse(final String value) {
//TODO
throw new RuntimeException("UNFINISHED CODE");
//		if (Utils.isEmpty(value)) throw new RequiredArgumentException("value");
//		return xTime.getNew( parseToLong(value), xTimeU.MS );
	}
	public static Long parseToLong(final String value) {
//TODO
throw new RuntimeException("UNFINISHED CODE");
//		if (Utils.isEmpty(value)) throw new RequiredArgumentException("value");
//		long time = 0;
//		StringBuilder buf = new StringBuilder();
//		for (char c : value.toCharArray()) {
//			if (c == ' ') continue;
//			if (Character.isDigit(c) || c == '.' || c == ',') {
//				buf.append(c);
//				continue;
//			}
//			if (Character.isLetter(c)) {
//				final Character chr = new Character(
//					Character.toLowerCase(c)
//				);
//				if (timeValues.containsKey(chr)) {
//					final double unitValue = timeValues.get(chr).doubleValue();
//					final double dblValue = NumberUtils.ToDouble( buf.toString() );
//					time += (long) (dblValue * unitValue);
//				}
//				buf = new StringBuilder();
//				continue;
//			}
//		}
//		return new Long(time);
	}



	// to string
	@Override
	public String toString() {
		return this.toString();
	}
	public String ToString() {
		return xTime.ToString(this);
	}
	public static String ToString(final xTime time) {
		if (time == null) return null;
		return xTime.ToString(time.getMS());
	}
	public static String ToString(final long ms) {
		return BuildString(ms, false);
	}
	// full format
	public String toFullString() {
		return xTime.ToString(this, true);
	}
	public static String ToString(final xTime time, final boolean fullFormat) {
		return BuildString(time.getMS(), fullFormat);
	}
	public static String BuildString(final long ms, final boolean fullFormat) {
//TODO
throw new RuntimeException("UNFINISHED CODE");
//		if (ms < 1) return null;
//		long tmp = ms;
//		final StringBuilder buf = new StringBuilder();
//		for (final Entry<Character, Long> entry : timeValues.entrySet()) {
//			final char chr  = entry.getKey().charValue();
//			final long unit = entry.getValue().longValue();
//			if (tmp < unit) continue;
//			final long val = (long) Math.floor(
//				((double) tmp) / ((double) unit)
//			);
//			// append to string
//			if (buf.length() > 0) {
//				buf.append(' ');
//			}
//			buf.append(Long.toString(val));
//			if (!fullFormat) {
//				// minimal format
//				buf.append(chr);
//			} else {
//				// full format
//				switch (chr) {
//				case 'y':
//					buf.append(" year");
//					break;
//				case 'o':
//					buf.append(" month");
//					break;
//				case 'w':
//					buf.append(" week");
//					break;
//				case 'd':
//					buf.append(" day");
//					break;
//				case 'h':
//					buf.append(" hour");
//					break;
//				case 'm':
//					buf.append(" minute");
//					break;
//				case 's':
//					buf.append(" second");
//					break;
//				case 'n':
//					buf.append(" ms");
//					break;
//				default:
//					continue;
//				}
//				if (chr != 'n' && val > 1) {
//					buf.append('s');
//				}
//			}
//			tmp = tmp % unit;
//		}
//		return buf.toString();
	}



}
