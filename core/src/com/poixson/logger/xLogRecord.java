package com.poixson.logger;


public interface xLogRecord {


	public String   getMessage();
	public xLevel   getLevel();
	public String[] getNameTree();
	public long     getTimestamp();

	public boolean isEmpty();
	public boolean notEmpty();


}
