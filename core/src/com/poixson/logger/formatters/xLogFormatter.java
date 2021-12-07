package com.poixson.logger.formatters;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.poixson.logger.xLevel;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLogFormatter {
	public static final String DEFAULT_FORMAT = " {time} [{level}]{crumbs} {msg}";
	public static final String DEFAULT_FORMAT_CRUMB = " [{][}]";
	public static final String DEFAULT_FORMAT_TIME  = "D yyyy-MM-dd HH:mm:ss";

	protected final String format;
	protected final String formatTime;
	protected final String formatCrumb;
	protected final boolean containsTime;
	protected final boolean containsLevel;
	protected final boolean containsCrumbs;



	public xLogFormatter() {
		this(null, null, null);
	}
	public xLogFormatter(final String format,
			final String formatCrumb, final String formatTime) {
		this.format      = Utils.ifEmpty(format,      DEFAULT_FORMAT);
		this.formatTime  = Utils.ifEmpty(formatTime,  DEFAULT_FORMAT_TIME);
		this.formatCrumb = Utils.ifEmpty(formatCrumb, DEFAULT_FORMAT_CRUMB);
		this.containsTime   = this.format.contains("{time}");
		this.containsLevel  = this.format.contains("{level}");
		this.containsCrumbs = this.format.contains("{crumbs}");
	}



	public String[] formatMessage(final xLogRecord_Msg record) {
		final String[] lines = record.getLines();
		if (Utils.isEmpty(lines))
			return new String[0];
		// [[ title ]]
		if (xLevel.TITLE.equals(record.level))
			return this.genTitle(record);
		// message
		final HashMap<String, Object> args = new HashMap<String, Object>();
		if (this.containsTime)
			args.put( "time", this.genTimestamp(record) );
		if (this.containsLevel) {
			final String levelStr = record.getLevelName();
			if (Utils.notEmpty(levelStr))
				args.put("level", StringUtils.PadCenter(7, levelStr, ' '));
		}
		if (this.containsCrumbs)
			args.put( "crumbs", this.genCrumbs(record) );
		// single line
		if (lines.length == 1) {
			args.put("msg", lines[0]);
			return new String[] {
				StringUtils.ReplaceTags(this.format, args)
			};
		// multiple lines
		} else {
			final String[] result = new String[lines.length];
			for (int index=0; index<lines.length; index++) {
				args.put("msg", lines[index]);
				result[index] = StringUtils.ReplaceTags(this.format, args);
			}
			return result;
		}
	}



	// -------------------------------------------------------------------------------
	// generate parts



	// title
	protected String[] genTitle(final xLogRecord_Msg record) {
		return this.genTitle(record, " [[ ", " ]] ");
	}
	protected String[] genTitle(final xLogRecord_Msg record,
			final String preStr, final String postStr) {
		if (record.isEmpty()) {
			return new String[] {
				(new StringBuilder())
					.append(preStr)
					.append("<null>")
					.append(postStr)
					.toString()
			};
		}
		final String[] lines = record.getLines();
		final String[] result = new String[ lines.length ];
		final int len = StringUtils.FindLongestLine(lines);
		for (int index = 0; index < lines.length; index++) {
			final String line = StringUtils.PadEnd(len, lines[index], ' ');
			result[index] =
				(new StringBuilder())
					.append(preStr)
					.append(line)
					.append(postStr)
					.toString();
		}
		return result;
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
