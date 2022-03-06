package com.poixson.logger.records;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.utils.Utils;


public class xLogRecord_Msg implements xLogRecord {

	public final xLevel   level;
	public final long     timestamp;
	public final String   msg;
	public final Object[] args;
	public final String[] nametree;



	public xLogRecord_Msg(final xLog log, final xLevel level,
			final String msg, final Object[] args) {
		this.level     = level;
		this.timestamp = Utils.GetMS();
		this.msg       = msg;
		this.args      = args;
		this.nametree  = log.getNameTree();
	}



	@Override
	public String getMessage() {
		return String.format(this.msg, this.args);
	}
	@Override
	public String toString() {
		return this.getMessage();
	}



	@Override
	public xLevel getLevel() {
		return this.level;
	}
	public String getLevelName() {
		if (this.level == null)
			return null;
		return this.level.toString();
	}



	// [logger][crumbs]
	@Override
	public String[] getNameTree() {
		return this.nametree;
	}



	@Override
	public long getTimestamp() {
		return this.timestamp;
	}



	@Override
	public boolean isEmpty() {
		return Utils.isEmpty(this.msg);
	}
	@Override
	public boolean notEmpty() {
		return Utils.notEmpty(this.msg);
	}



}
