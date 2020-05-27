package com.poixson.tools;

import com.poixson.utils.Utils;


public class StringParts extends xString {

	protected String next  = null;
	protected String delim = null;



	public StringParts(final String data) {
		super(data);
	}



	public xString delim(final String delimStr) {
		this.delim = (
			Utils.isEmpty(delimStr)
			? null
			: delimStr
		);
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



}
