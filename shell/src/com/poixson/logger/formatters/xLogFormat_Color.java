package com.poixson.logger.formatters;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLogRecord_Msg;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLogFormat_Color extends xLogFormat_Tagged {



	public xLogFormat_Color() {
		this(null, null, null);
	}
	public xLogFormat_Color(final String format,
			final String formatCrumbs, final String formatTime) {
		super(format, formatCrumbs, formatTime);
	}



	public String format_msg(final xLogRecord_Msg record) {
		final xLevel level = record.getLevel();
		// publish plain message
		if (level == null) {
			return record.toString();
		}
		// [[ title ]]
		if (xLevel.TITLE.equals(level)) {
			return
				this.genTitle(
					record,
					" @|FG_MAGENTA [[|@ @|FG_CYAN ",
					"|@ @|FG_MAGENTA ]]|@ "
				);
		}
		// format message lines
		final String[] lines = record.toString().split("\n");
		final String[] result = new String[ lines.length ];
		for (int index = 0; index < lines.length; index++) {
			// timestamp [level] [crumbs] message
			result[index] = (new StringBuilder())
				.append(" @|FG_WHITE ")
				// timestamp
				.append(this.genTimestamp(
					record,
					"D yyyy-MM-dd HH:mm:ss"
				))
				.append("|@")
				// [level]
				.append(this.genLevelColored(record))
				// [crumbs]
				.append(this.genCrumbsColored(record))
				// message
				.append(lines[index])
				.toString();
		}
		return StringUtils.MergeStrings('\n', result);
	}



	// -------------------------------------------------------------------------------
	// generate parts



	// [level]
	protected String genLevelColored(final xLogRecord_Msg record) {
		return (new StringBuilder())
			.append("@|FG_BLACK,BOLD [|@@|")
			.append( this.getLevelColor(record.level) )
			.append(' ')
			.append( StringUtils.PadCenter(7, record.getLevelName(), ' ') )
			.append("|@@|FG_BLACK,BOLD ]|@")
			.toString();
	}
	protected String getLevelColor(final xLevel level) {
		if (level == null)
			return "FG_BLACK,BOLD";
		// all, finest, finer, fine
		if (level.isLoggable(xLevel.FINE))
			return "FG_BLACK,BOLD";
		// info
		if (level.isLoggable(xLevel.INFO))
			return "FG_CYAN";
		// warning
		if (level.isLoggable(xLevel.WARNING))
			return "FG_RED";
		// severe
		if (level.isLoggable(xLevel.SEVERE))
			return "FG_RED,BOLD";
		// fatal
		if (level.isLoggable(xLevel.FATAL))
			return "FG_RED,BOLD,UNDERLINE";
//TODO: remove this?
//		// stdout
//		if (level.isLoggable(xLevel.STDOUT))
//			return "FG_GREEN";
//		// stderr
//		if (level.isLoggable(xLevel.STDERR))
//			return "FG_YELLOW";
		// off
		return "FG_BLACK,BOLD";
	}



	// crumbs
	protected String genCrumbsColored(final xLogRecord_Msg record) {
		final String crumbStr = super.genCrumbs(record, "[{][}]");
		if (Utils.isEmpty(crumbStr))
			return "";
		return (new StringBuilder())
			.append("@|FG_BLACK,BOLD ")
			.append(crumbStr)
			.append("|@")
			.toString();
	}



}
