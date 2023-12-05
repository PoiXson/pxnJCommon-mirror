package com.poixson.tools;

import static com.poixson.utils.Utils.IsEmpty;

import com.poixson.utils.StringUtils;


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
		this.data = StringUtils.ToString(obj);
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
			this.data = StringUtils.RemoveFromString(
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
			this.data = StringUtils.sTrim(data, strip);
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
		return StringUtils.IndexOf(data, delims);
	}
	public int indexOf(final int fromIndex, final String...delims) {
		final String data = this.data;
		if (data == null)
			return -1;
		return StringUtils.IndexOf(data, fromIndex, delims);
	}
	public int indexOfLast(final String...delims) {
		final String data = this.data;
		if (data == null)
			return -1;
		return StringUtils.IndexOfLast(data, delims);
	}



	public xString ensureStarts(final String start) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.ForceStarts(start, data);
		}
		return this;
	}
	public xString ensureEnds(final String end) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.ForceEnds(end, data);
		}
		return this;
	}



	public xString replaceWith(final String replaceWhat, final String[] withWhat) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.ReplaceWith(data, replaceWhat, withWhat);
		}
		return this;
	}



	public xString padFront(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.PadFront(width, data, padding);
		}
		return this;
	}
	public xString padEnd(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.PadEnd(width, data, padding);
		}
		return this;
	}
	public xString padCenter(final int width, final char padding) {
		final String data = this.data;
		if (data != null) {
			this.data = StringUtils.PadCenter(width, data, padding);
		}
		return this;
	}



}
