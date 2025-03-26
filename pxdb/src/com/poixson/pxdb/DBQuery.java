package com.poixson.utils.pxdb;

import java.lang.ref.SoftReference;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

import com.poixson.utils.NumberUtils;
import com.poixson.utils.SanUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;
import com.poixson.utils.xLogger.xLevel;
import com.poixson.utils.xLogger.xLog;


public class dbQuery {

	protected final dbWorker worker;
	protected final String tablePrefix;
	protected volatile PreparedStatement st = null;
	protected volatile ResultSet rs = null;
	protected volatile String sql = null;
	protected volatile boolean quiet = false;
	protected volatile int resultInt = -1;
	private final Object lock = new Object();

	// args
	protected volatile int paramCount = 0;
	protected volatile String[] args = null;
	private static final String ARG_PRE   = "[";
	private static final String ARG_DELIM = "|";
	private static final String ARG_POST  = "]";



	// new query
	public static dbQuery getNew(final String dbKey) {
		final dbWorker worker = dbManager.getLockedWorker(dbKey);
		if (worker == null)
			return null;
		return new dbQuery(worker);
	}
	// new query (must already have lock)
	public dbQuery(final dbWorker worker) {
		if (worker == null) throw RequiredArgumentException.getNew("worker");
		this.worker = worker;
		this.tablePrefix = worker.getTablePrefix();
	}



	// prepared query
	public dbQuery prepare(final String sqlStr) throws SQLException {
		if (Utils.isEmpty(sqlStr)) throw RequiredArgumentException.getNew("sqlStr");
		synchronized(this.lock) {
			if (!this.worker.inUse()) {
				log().trace(new IllegalAccessException("dbWorker not locked!"));
				return null;
			}
			this.clean();
			this.sql = sqlStr.replace(
				"_table_",
				this.getTablePrefix()
			);
			try {
				// prepared statement
				this.st = this.worker.getConnection().prepareStatement(this.sql);
				// parameters for debugging
				this.paramCount = this.st.getParameterMetaData().getParameterCount();
				this.args = new String[this.paramCount];
			} catch (SQLNonTransientConnectionException | NullPointerException ignore) {
				log().severe("db connection closed!");
				this.close();
				return null;
			}
		}
		return this;
	}
	public boolean prep(final String sqlStr) {
		if (Utils.isEmpty(sqlStr)) throw RequiredArgumentException.getNew("sqlStr");
		try {
			if (this.prepare(sqlStr) != null) {
				return true;
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		this.clean();
		return false;
	}



	// execute query
	public boolean execute() throws SQLException {
		synchronized(this.lock) {
			if (!this.worker.inUse()) {
				log().trace(new IllegalAccessException("dbWorker not locked!"));
				return false;
			}
			if (this.st == null)         return false;
			if (Utils.isEmpty(this.sql)) return false;
			String str = this.sql;
			while (str.startsWith(" ")) {
				str = str.substring(1);
			}
			if (str.isEmpty()) return false;
			final String queryType;
			{
				final int pos = str.indexOf(" ");
				queryType = (
					pos == -1
					? str.toUpperCase()
					: str.substring(0, pos).toUpperCase()
				);
			}
			try {
				// log query
				this.worker.logDesc();
				if (isFinestLogging()) {
					// replace ? with values
					log().finest(
						"({}) QUERY: {}",
						Integer.toString(
							this.worker.getIndex()
						),
						StringUtils.ReplaceWith(
							"?",
							this.args,
							str
						)
					);
				}
				// execute query
				if (queryType.equals("INSERT") || queryType.equals("UPDATE")
				|| queryType.equals("CREATE") || queryType.equals("DELETE")) {
					this.resultInt = this.st.executeUpdate();
				} else {
					this.rs = this.st.executeQuery();
				}
			} catch (SQLNonTransientConnectionException e) {
				log().severe("db connection closed!");
				this.close();
				return false;
			} catch (SQLException e) {
				this.clean();
				throw e;
			}
		}
		return true;
	}
	public boolean execute(final String sqlStr) throws SQLException {
		if (Utils.isEmpty(sqlStr))
			return false;
		if (prepare(sqlStr) == null)
			return false;
		return execute();
	}



	public boolean Exec() {
		try {
			return this.execute();
		} catch (SQLException e) {
			log().trace(e);
			this.clean();
		}
		return false;
	}
	public boolean Exec(final String sqlStr) {
		try {
			return this.execute(sqlStr);
		} catch (SQLException e) {
			log().trace(e);
			this.clean();
		}
		return false;
	}



	public void desc(final String desc) {
		this.worker.desc(desc);
	}



	// set quiet mode
	public boolean quiet() {
		return this.quiet(true);
	}
	public boolean quiet(final boolean setQuiet) {
		this.quiet = setQuiet;
		return this.quiet;
	}



	// get db key
	public String dbKey() {
		if (this.worker == null)
			return null;
		return this.worker.dbKey();
	}
	public String getTablePrefix() {
		if (Utils.isEmpty(this.tablePrefix))
			return "";
		return this.tablePrefix;
	}



	// clean vars
	public void clean() {
		synchronized(this.lock) {
			if (this.rs != null) {
				try {
					this.rs.close();
				} catch (SQLException ignore) {}
				this.rs = null;
			}
			if (this.st != null) {
				try {
					this.st.close();
				} catch (SQLException ignore) {}
				this.st = null;
			}
			this.sql = null;
			this.paramCount = 0;
			this.args = null;
			this.quiet = false;
			this.resultInt = -1;
		}
	}
	public void free() {
		this.clean();
		this.worker.free();
	}
	public void close() {
		this.clean();
		this.worker.close();
	}



	// san string for sql
	public static String san(final String text) {
		return SanUtils.AlphaNumUnderscore(text);
	}



	// has next row
	public boolean hasNext() {
		synchronized(this.lock) {
			if (this.rs == null)
				return false;
			try {
				return this.rs.next();
			} catch (SQLException e) {
				log().trace(e);
			}
		}
		return false;
	}



	public ResultSet getResultSet() {
		return this.rs;
	}



	// result count
	public int getResultInt() {
		return this.resultInt;
	}
	public int getAffectedRows() {
		return getResultInt();
	}
	public int getInsertId() {
		return getResultInt();
	}



	// query parameters
	public dbQuery setString(final int index, final String value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setString(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("str")
							.append(ARG_DELIM)
							.append(value)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get string
	public String getString(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getString(label);
		}
	}
	public String getStr(final String label) {
		try {
			return this.getString(label);
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// set int
	public dbQuery setInt(final int index, final int value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setInt(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("int")
							.append(ARG_DELIM)
							.append(value)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get integer
	public int getInteger(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getInt(label);
		}
	}
	public Integer getInt(final String label) {
		try {
			final String value = this.getString(label);
			if (value != null) {
				return NumberUtils.toInteger(value);
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// set long
	public dbQuery setLong(final int index, final long value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setLong(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("lng")
							.append(ARG_DELIM)
							.append(value)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get long
	public long getLong(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getLong(label);
		}
	}
	public Long getLng(final String label) {
		try {
			final String value = this.getString(label);
			if (value != null) {
				return NumberUtils.toLong(value);
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// set decimal
	public dbQuery setDecimal(final int index, final double value) {
		if (this.setDouble(index, value) == null)
			return null;
		if (this.paramCount > 0) {
			this.args[index-1] =
				(new StringBuilder())
					.append(ARG_PRE)
					.append("dec")
					.append(ARG_DELIM)
					.append(value)
					.append(ARG_POST)
					.toString();
		}
		return this;
	}
	// get decimal
	public double getDecimal(final String label) throws SQLException {
		return this.getDouble(label);
	}
	public Double getDec(final String label) {
		return this.getDbl(label);
	}



	// set double
	public dbQuery setDouble(final int index, final double value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setDouble(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("dbl")
							.append(ARG_DELIM)
							.append(value)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get double
	public double getDouble(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getDouble(label);
		}
	}
	public Double getDbl(final String label) {
		try {
			final String value = this.getString(label);
			if (value != null) {
				return NumberUtils.toDouble(value);
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// set float
	public dbQuery setFloat(final int index, final float value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setFloat(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("flt")
							.append(ARG_DELIM)
							.append(value)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get float
	public float getFloat(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getFloat(label);
		}
	}
	public Float getFlt(final String label) {
		try {
			final String value = this.getString(label);
			if (value != null) {
				return NumberUtils.toFloat(value);
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// set boolean
	public dbQuery setBool(final int index, final boolean value) {
		synchronized(this.lock) {
			if (this.st == null)
				return null;
			try {
				this.st.setBoolean(index, value);
				if (this.paramCount > 0) {
					this.args[index-1] =
						(new StringBuilder())
							.append(ARG_PRE)
							.append("bool")
							.append(ARG_DELIM)
							.append(
								value
								? "True"
								: "False"
							)
							.append(ARG_POST)
							.toString();
				}
			} catch (SQLException e) {
				log().trace(e);
				this.clean();
				return null;
			}
		}
		return this;
	}
	// get boolean
	public boolean getBoolean(final String label) throws SQLException {
		synchronized(this.lock) {
			return this.rs.getBoolean(label);
		}
	}
	public Boolean getBool(final String label) {
		try {
			final String value = this.getString(label);
			if (value != null) {
				return NumberUtils.toBoolean(value);
			}
		} catch (SQLException e) {
			log().trace(e);
		}
		return null;
	}



	// lock table (readable/unreadable)
	public boolean lockTable(final String tableName, final boolean readable) {
		if (Utils.isEmpty(tableName)) throw RequiredArgumentException.getNew("tableName");
		synchronized(this.lock) {
			final StringBuilder str =
				(new StringBuilder())
					.append("LOCK TABLES `").append(tableName).append("` ")
					.append(readable ? "READ" : "WRITE")
					.append(" /"+"* lock table *"+"/");
			if (!prep(str.toString()) || !Exec()) {
				log().severe("Failed to lock table {}", tableName);
				return false;
			}
		}
		return true;
	}
	// lock table (unreadable)
	public boolean lockTable(final String tableName) {
		return this.lockTable(
				tableName,
				false
		);
	}
	// unlock table
	public void unlockTables() {
		synchronized(this.lock) {
			final String sqlStr = "UNLOCK TABLES /"+"* unlock table *"+"/";
			if (!prep(sqlStr) || !Exec()) {
				log().severe("Failed to unlock tables");
			}
		}
	}



	// logger
	public static xLog log() {
		return dbManager.log();
	}



	// cached log level
	private static volatile SoftReference<Boolean> _finest = null;
	public static boolean isFinestLogging() {
		if (_finest != null) {
			final Boolean finest = _finest.get();
			if (finest != null)
				return finest.booleanValue();
		}
		final boolean finest =
			log()
				.isLoggable(xLevel.FINEST);
		_finest = new SoftReference<Boolean>(Boolean.valueOf(finest));
		return finest;
	}



}
