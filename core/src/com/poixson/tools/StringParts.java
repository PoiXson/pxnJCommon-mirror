package com.poixson.tools;

import com.poixson.utils.Utils;


public class StringParts extends xString {

	protected String next  = null;
	protected String delim = null;



	public static StringParts getNew() {
		return new StringParts("");
	}
	public static StringParts getNew(final String data) {
		return new StringParts(data);
	}
	public static StringParts getNew(final Object obj) {
		return new StringParts(obj);
	}



	public StringParts(final String data) {
		super(data);
	}
	public StringParts(final Object obj) {
		super(obj);
	}



	public StringParts delim(final String delimStr) {
		this.delim = Utils.ifEmpty(delimStr, null);
		return this;
	}
	public String delim() {
		return this.delim;
	}



	public boolean hasNext() {
		if (this.isEmpty())
			return false;
		final String dlm = this.delim;
		if (Utils.isEmpty(dlm))
			return false;
		String data = super.data.trim();
		while (data.startsWith(dlm)) {
			data = data.substring(dlm.length()).trim();
		}
		if (data.isEmpty()) {
			super.data = "";
			return false;
		}
		// find next delim
		final int pos = data.indexOf(dlm);
		if (pos == -1) {
			this.next = data;
			super.data = "";
		} else {
			this.next = data.substring(0, pos);
			super.data = data.substring(pos + dlm.length());
		}
		return true;
	}
	public String part() {
		return this.next;
	}
	public String getNext() {
		return (
			this.hasNext()
			? this.part()
			: null
		);
	}



	// get first part (many delims)
	public static String PeekFirstPart(final String string, final char...delims) {
		if (Utils.isEmpty(string))
			return string;
		int pos = Integer.MAX_VALUE;
		// find earliest delim
		for (final char delim : delims) {
			final int p = string.indexOf(delim);
			// delim not found
			if (p == -1) continue;
			// earlier delim
			if (p < pos) {
				pos = p;
				if (p == 0) break;
			}
		}
		return (
			pos == Integer.MAX_VALUE
			? string
			: string.substring(0, pos)
		);
	}
	public static String PeekFirstPart(final String string, final String...delims) {
		if (Utils.isEmpty(string))
			return string;
		int pos = Integer.MAX_VALUE;
		// find earliest delim
		for (final String delim : delims) {
			if (Utils.isEmpty(delim)) continue;
			final int p = string.indexOf(delim);
			// delim not found
			if (p == -1) continue;
			// earlier delim
			if (p < pos) {
				pos = p;
				if (p == 0) break;
			}
		}
		return (
			pos == Integer.MAX_VALUE
			? string
			: string.substring(0, pos)
		);
	}



	// get last part (many delims)
	public static String PeekLastPart(final String string, final char...delims) {
		if (Utils.isEmpty(string))
			return string;
		int pos = Integer.MIN_VALUE;
		// find latest delim
		for (final char delim : delims) {
			final int p = string.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// later delim
			if (p > pos) {
				pos = p;
			}
		}
		return (
			pos == Integer.MIN_VALUE
			? string
			: string.substring(pos + 1)
		);
	}
	public static String PeekLastPart(final String string, final String...delims) {
		if (Utils.isEmpty(string))
			return string;
		int pos = Integer.MIN_VALUE;
		int delimSize = 0;
		// find latest delim
		for (final String delim : delims) {
			if (Utils.isEmpty(delim)) continue;
			final int p = string.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// longer delim
			if (p == pos) {
				if (delim.length() > delimSize) {
					delimSize = delim.length();
				}
				continue;
			}
			// later delim
			if (p+delimSize > pos+delim.length()) {
				pos = p;
				delimSize = delim.length();
			}
		}
		return (
			pos == Integer.MIN_VALUE
			? string
			: string.substring(pos + delimSize)
		);
	}



}
