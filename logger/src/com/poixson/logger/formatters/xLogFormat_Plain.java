package com.poixson.logger.formatters;

import com.poixson.logger.xLogFormat;
import com.poixson.logger.xLogRecord;


public class xLogFormat_Plain implements xLogFormat {



	public xLogFormat_Plain() {
		super();
	}



	@Override
	public String format(final xLogRecord record) {
		if (record == null)
			return null;
		return record.toString();
	}



}
