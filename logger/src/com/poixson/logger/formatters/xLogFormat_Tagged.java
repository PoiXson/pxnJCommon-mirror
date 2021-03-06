package com.poixson.logger.formatters;

import java.text.SimpleDateFormat;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLogFormat;
import com.poixson.logger.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLogFormat_Tagged implements xLogFormat {
	public static final String DEFAULT_FORMAT = " {time} [{level}]{crumbs} {msg}";
	public static final String DEFAULT_FORMAT_CRUMB = " [{][}]";
	public static final String DEFAULT_FORMAT_TIME  = "D yyyy-MM-dd HH:mm:ss";

	protected final String format;
	protected final String formatTime;
	protected final String formatCrumb;

	protected final boolean containsTime;
	protected final boolean containsLevel;
	protected final boolean containsCrumbs;



	public xLogFormat_Tagged() {
		this(null, null, null);
	}
	public xLogFormat_Tagged(final String format,
			final String formatCrumbs, final String formatTime) {
		this.format      = Utils.ifEmpty(format,       DEFAULT_FORMAT);
		this.formatTime  = Utils.ifEmpty(formatTime,   DEFAULT_FORMAT_TIME);
		this.formatCrumb = Utils.ifEmpty(formatCrumbs, DEFAULT_FORMAT_CRUMB);
		this.containsTime   = this.format.contains("{time}");
		this.containsLevel  = this.format.contains("{level}");
		this.containsCrumbs = this.format.contains("{crumbs}");
	}



	@Override
	public String format(final xLogRecord record) {
		if (record instanceof xLogRecord_Msg)
			return this.format((xLogRecord_Msg)record );
		return record.toString();
	}
	public String format(final xLogRecord_Msg record) {
		if (record.isEmpty())
			return null;
		// [[ title ]]
		if (xLevel.TITLE.equals(record.level))
			return this.genTitle(record);
		// message
		{
			String result = this.format;
			if (this.containsTime)
				result = result.replace("{time}", this.genTimestamp(record));
			if (this.containsLevel) {
				final String levelStr = record.getLevelName();
				if (Utils.notEmpty(levelStr))
					result = result.replace("{level}", StringUtils.PadCenter(7, levelStr, ' '));
			}
			if (this.containsCrumbs)
				result = result.replace("{crumbs}", this.genCrumbs(record));
			return result.replace("{msg}", record.toString());
		}
	}



	// title
	protected String genTitle(final xLogRecord_Msg record) {
		return this.genTitle(record, " [[ ", " ]] ");
	}
	protected String genTitle(final xLogRecord_Msg record,
			final String preStr, final String postStr) {
		if (record.isEmpty()) {
			return (new StringBuilder())
					.append(preStr)
					.append("<null>")
					.append(postStr)
					.toString();
		}
		final String msg = record.toString();
		final String[] lines = msg.split("\n");
		final int length = StringUtils.FindLongestLine(lines);
		final StringBuilder result = new StringBuilder();
		for (final String line : lines) {
			if (result.length() == 0)
				result.append('\n');
			result.append(preStr)
				.append( StringUtils.PadEnd(length, line, ' ') )
				.append(postStr);
		}
		return result.toString();
	}



	// timestamp
	protected String genTimestamp(final xLogRecord_Msg record) {
		return this.genTimestamp(record, null);
	}
	protected String genTimestamp(final xLogRecord_Msg record, final String format) {
		final SimpleDateFormat dateFormat =
			new SimpleDateFormat(
				Utils.ifEmpty(format, DEFAULT_FORMAT_TIME)
			);
		return dateFormat.format( Long.valueOf(record.timestamp) );
	}



	// crumbs
	protected String genCrumbs(final xLogRecord_Msg record) {
		final String[] tree = record.getNameTree();
		if (Utils.isEmpty(tree))
			return "";
		final String[] parts = StringUtils.Split(this.formatCrumb, '{', '}');
		final String start, mid, end;
		if (Utils.isEmpty(parts)) {
			start = "";
			mid   = ",";
			end   = "";
		} else
		if (parts.length == 1) {
			start = "";
			mid   = parts[0];
			end   = "";
		} else
		if (parts.length == 2) {
			start = parts[0];
			mid   = ",";
			end   = parts[1];
		} else {
			start = parts[0];
			mid   = parts[1];
			end   = parts[2];
		}
		final StringBuilder result = new StringBuilder();
		result.append(start);
		boolean first = true;
		for (final String name : tree) {
			if (first) {
				first = false;
			} else {
				result.append(mid);
			}
			result.append(name);
		}
		result.append(end);
		return result.toString();
	}



}
