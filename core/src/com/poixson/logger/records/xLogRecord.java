package com.poixson.logger.records;

import com.poixson.logger.xLevel;


public interface xLogRecord {


	public String[] getLines();
	public xLevel   getLevel();
	public long     getTimestamp();

	public boolean isEmpty();
	public boolean notEmpty();


}
