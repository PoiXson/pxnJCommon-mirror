package com.poixson.logger;

import com.poixson.exceptions.RequiredArgumentException;


public class xLogRecord_Special implements xLogRecord {

	public enum SpecialType {
		FLUSH,
		CLEAR_SCREEN,
		CLEAR_LINE,
		BEEP
	};

	protected SpecialType type;



	public xLogRecord_Special(final SpecialType type) {
		if (type == null) throw new RequiredArgumentException("type");
		this.type = type;
	}



	@Override
	public String toString() {
		return null;
	}
	@Override
	public xLevel getLevel() {
		return null;
	}
	@Override
	public String[] getNameTree() {
		return null;
	}
	@Override
	public long getTimestamp() {
		return 0L;
	}



	@Override
	public boolean isEmpty() {
		return true;
	}
	@Override
	public boolean notEmpty() {
		return false;
	}



}
