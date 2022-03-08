package com.poixson.logger.records;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLevel;


public class xLogRecord_Special implements xLogRecord {

	public enum SpecialType {
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
	public String getMessage() {
//TODO
return null;
	}



	@Override
	public xLevel getLevel() {
//TODO
return null;
	}



	@Override
	public String[] getNameTree() {
//TODO
return null;
	}



	@Override
	public long getTimestamp() {
//TODO
return 0L;
	}



	@Override
	public boolean isEmpty() {
//TODO
return true;
	}
	@Override
	public boolean notEmpty() {
//TODO
return false;
	}



}
