/*
package com.poixson.tools;

import static com.poixson.utils.StringUtils.ForceEnds;
import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.StringUtils.IndexOf;
import static com.poixson.utils.StringUtils.IndexOfLast;
import static com.poixson.utils.StringUtils.PadCenter;
import static com.poixson.utils.StringUtils.PadEnd;
import static com.poixson.utils.StringUtils.PadFront;
import static com.poixson.utils.StringUtils.RemoveFromString;
import static com.poixson.utils.StringUtils.ReplaceWith;
import static com.poixson.utils.StringUtils.ToString;
import static com.poixson.utils.StringUtils.sTrim;
import static com.poixson.utils.Utils.IsEmpty;


public class xString {

	protected String data  = null;



	public static xString getNew() {
		return new xString("");
	}
	public static xString getNew(final String data) {
		return new xString(data);
	}
	public static xString getNew(final Object obj) {
		return new xString(obj);
	}



	public xString(final String data) {
		this.data = data;
	}
	public xString(final Object obj) {
		this.data = ToString(obj);
	}



	@Override
	public String toString() {
		return this.data;
	}
	public xString set(final String data) {
		this.data = data;
		return this;
	}



	public boolean isEmpty() {
		return IsEmpty(this.data);
	}



	public xString append(final String append) {
		this.data = (
			this.data == null
			? append
			: this.data + append
		);
		return this;
	}



	public xString remove(final String...strip) {
		final String data = this.data;
		if (data != null) {
			this.data = RemoveFromString(
				data,
				strip
			);
		}
		return this;
	}



	public xString upper() {
		final String data = this.data;
		if (data != null) {
			this.data = data.toUpperCase();
		}
		return this;
	}
	public xString lower() {
		final String data = this.data;
		if (data != null) {
			this.data = data.toLowerCase();
		}
		return this;
	}



	public xString trim() {
		final String data = this.data;
		if (data != null) {
			this.data = data.trim();
		}
		return this;
	}
	public xString trims(final String...strip) {
		final String data = this.data;
		if (data != null) {
			this.data = sTrim(data, strip);
		}
		return this;
	}



	public boolean startsWith(final String prefix) {
		final String data = this.data;
		if (data == null) {
			return false;
		}
		return data.startsWith(prefix);
	}
	public boolean endsWith(final String suffix) {
		final String data = this.data;
		if (data == null) {
			return false;
		}
		return data.endsWith(suffix);
	}



	public boolean contains(final CharSequence str) {
		final String data = this.data;
		if (data == null)
			return false;
		return data.contains(str);
	}



	public int indexOf(final String...delims) {
		final String data = this.data;
		if (data == null)
			return -1;
		return IndexOf(data, delims);
	}
	public int indexOf(final int fromIndex, final String...delims) {
		final String data = this.data;
		if (data == null)
			return -1;
		return IndexOf(data, fromIndex, delims);
	}
	public int indexOfLast(final String...delims) {
		final String data = this.data;
		if (data == null)
			return -1;
		return IndexOfLast(data, delims);
	}



	public xString ensureStarts(final String start) {
		final String data = this.data;
		if (data != null) {
			this.data = ForceStarts(start, data);
		}
		return this;
	}
	public xString ensureEnds(final String end) {
		final String data = this.data;
		if (data != null) {
			this.data = ForceEnds(end, data);
		}
		return this;
	}



	public xString replaceWith(final String replaceWhat, final String[] withWhat) {
		final String data = this.data;
		if (data != null) {
			this.data = ReplaceWith(data, replaceWhat, withWhat);
		}
		return this;
	}



	public xString padFront(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = PadFront(width, data, padding);
		}
		return this;
	}
	public xString padEnd(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = PadEnd(width, data, padding);
		}
		return this;
	}
	public xString padCenter(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = PadCenter(width, data, padding);
		}
		return this;
	}



}
*/
