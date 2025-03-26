package com.poixson.utils.pxdb;

import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.utils.Utils;
import com.poixson.utils.xCloseable;
import com.poixson.utils.xLogger.xLog;


public class dbWorker implements xCloseable {

	private final String dbKey;
	private final int    index;
	private final AtomicReference<String> desc = new AtomicReference<String>(null);
	private volatile SoftReference<String> tablePrefix = null;

	private volatile Connection conn = null;
	private final AtomicBoolean inUse = new AtomicBoolean(false);

	private static final AtomicInteger nextIndex = new AtomicInteger(0);



	protected dbWorker(final String dbKey, final Connection conn) {
		this.dbKey = dbKey;
		this.conn  = conn;
		this.index = getNextIndex();
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}



	// get db connection
	protected Connection getConnection() {
		return this.conn;
	}
	// get db key
	public String dbKey() {
		return this.dbKey;
	}
	public String getTablePrefix() {
		// cached
		{
			final SoftReference<String> ref = this.tablePrefix;
			if (ref != null) {
				final String tablePrefix = ref.get();
				return tablePrefix;
			}
		}
		// get from config
		{
			final dbPool pool = dbManager.getPool(this.dbKey());
			if (pool == null)
				return null;
			final dbConfig config = pool.getConfig();
			final String tablePrefix = config.getTablePrefix();
			this.tablePrefix = new SoftReference<String>(tablePrefix);
			return tablePrefix;
		}
	}



	// close connection
	@Override
	public void close() {
		if (this.conn != null) {
			Utils.safeClose(this.conn);
			try {
				this.conn.close();
			} catch (Exception ignore) {}
		}
		this.conn = null;
	}
	// has errored / disconnected
	@Override
	public boolean isClosed() {
		return (this.conn == null);
	}



	// in-use lock
	public boolean inUse() {
		return this.inUse.get();
	}
	public boolean getLock() {
		final boolean result = this.inUse.compareAndSet(false, true);
		if (result) {
			log().finest( "Locked #"+Integer.toString(this.getIndex()) );
		}
		return result;
	}
	public void free() {
		// flush desc
		this.logDesc();
		// release lock
		if (this.inUse.compareAndSet(true, false)) {
			log().finest( "Released #"+Integer.toString(this.getIndex()) );
		}
	}
//TODO:
//	/**
//	 * Get the time connection has been locked for
//	 * @return time in milliseconds
//	 */
//	@Override
//	public long getLockTime() {
//TODO: this isn't being used yet
//		if (lockTime < 1)
//			return -1;
//		return pxnUtils.getCurrentMillis() - lockTime;
//	}



	// query description
	public void desc(final String descStr) {
		this.desc.set(
			Utils.isBlank(descStr)
			? null
			: descStr
		);
	}
	public void logDesc() {
		final String desc = this.desc.get();
		if (Utils.isBlank(desc))
			return;
		if (!this.desc.compareAndSet(desc, null))
			return;
		log().fine("Query: "+desc);
//TODO:
//		.append(duration)
	}



	private static int getNextIndex() {
		return nextIndex.incrementAndGet();
	}
	public int getIndex() {
		return this.index;
	}



	// logger
	public static xLog log() {
		return dbManager.log();
	}



}
