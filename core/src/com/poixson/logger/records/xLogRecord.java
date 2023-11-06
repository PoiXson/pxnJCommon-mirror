package com.poixson.logger.records;

import com.poixson.logger.xLevel;


public interface xLogRecord {


	@Override
	public String   toString();
	public xLevel   getLevel();
	public String[] getNameTree();
	public long     getTimestamp();

	public boolean isEmpty();
	public boolean notEmpty();


}
