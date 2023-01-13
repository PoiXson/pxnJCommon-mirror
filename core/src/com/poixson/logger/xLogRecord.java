package com.poixson.logger;


public interface xLogRecord {


	@Override
	public String   toString();
	public xLevel   getLevel();
	public String[] getNameTree();
	public long     getTimestamp();

	public boolean isEmpty();
	public boolean notEmpty();


}
