package com.poixson.logger.records;

import static com.poixson.utils.Utils.GetMS;
import static com.poixson.utils.Utils.IsEmpty;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.lang.LangTable;


public class xLogRecord_Msg implements xLogRecord {

	public final xLevel   level;
	public final long     timestamp;
	public final String   msg;
	public final Object[] args;
	public final String[] nametree;



	public xLogRecord_Msg(final xLog log, final xLevel level,
			final String msg, final Object[] args) {
		this.level     = level;
		this.timestamp = GetMS();
		this.msg       = msg;
		this.args      = args;
		this.nametree  = log.getNameTree();
	}



	@Override
	public String toString() {
		return String.format(this.msg, this.args);
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
	public String getLevelNameLocal() {
		final LangTable lang = xLogRoot.GetLangTable();
		final String level = this.getLevelName();
		return (lang==null ? level : lang.phrase(level));
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
		return IsEmpty(this.msg);
	}



}
