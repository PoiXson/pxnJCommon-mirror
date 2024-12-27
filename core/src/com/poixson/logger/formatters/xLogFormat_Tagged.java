package com.poixson.logger.formatters;

import static com.poixson.utils.StringUtils.FindLongestLine;
import static com.poixson.utils.StringUtils.PadCenter;
import static com.poixson.utils.StringUtils.PadEnd;
import static com.poixson.utils.StringUtils.SplitByChars;
import static com.poixson.utils.Utils.IfEmpty;
import static com.poixson.utils.Utils.IsEmpty;

import java.text.SimpleDateFormat;

import com.poixson.logger.xLevel;
import com.poixson.logger.records.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;


public class xLogFormat_Tagged implements xLogFormat {

	public static final String DEFAULT_FORMAT       = " {time} [{level}]{crumbs} {msg}";
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
		this.format      = IfEmpty(format,       DEFAULT_FORMAT);
		this.formatTime  = IfEmpty(formatTime,   DEFAULT_FORMAT_TIME);
		this.formatCrumb = IfEmpty(formatCrumbs, DEFAULT_FORMAT_CRUMB);
		this.containsTime   = this.format.contains("{time}"  );
		this.containsLevel  = this.format.contains("{level}" );
		this.containsCrumbs = this.format.contains("{crumbs}");
	}



	@Override
	public String format(final xLogRecord record) {
		if (record == null)
			return "\n";
		if (record instanceof xLogRecord_Msg)
			return this.format((xLogRecord_Msg)record );
		return record.toString();
	}
	public String format(final xLogRecord_Msg record) {
		if (record.isEmpty())
			return null;
		if (record.level == null)
			return record.toString();
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
				if (!IsEmpty(levelStr))
					result = result.replace("{level}", PadCenter(7, levelStr, ' '));
			}
			if (this.containsCrumbs)
				result = result.replace("{crumbs}", this.genCrumbs(record, this.formatCrumb));
			return result.replace("{msg}", record.toString());
		}
	}



	protected String genCrumbs(final xLogRecord_Msg record, final String formatCrumb) {
		final String[] tree = record.getNameTree();
		if (IsEmpty(tree))
			return "";
		final String[] parts = SplitByChars(formatCrumb, '{', '}');
		final String start, mid, end;
		if (IsEmpty(parts)) {
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



	// title
	protected String genTitle(final xLogRecord_Msg record) {
		return this.genTitle(record, " [[ ", " ]] ");
	}
	protected String genTitle(final xLogRecord_Msg record,
			final String strPre, final String strPost) {
		if (record.isEmpty()) {
			return (new StringBuilder())
					.append(strPre)
					.append("<null>")
					.append(strPost)
					.toString();
		}
		final String msg = record.toString();
		final String[] lines = msg.split("\n");
		final int length = FindLongestLine(lines);
		final StringBuilder result = new StringBuilder();
		for (final String line : lines) {
			result.append(strPre)
				.append( PadEnd(length, line, ' ') )
				.append(strPost);
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
				IfEmpty(format, DEFAULT_FORMAT_TIME)
			);
		return dateFormat.format( Long.valueOf(record.timestamp) );
	}



}
