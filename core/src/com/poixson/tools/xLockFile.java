/*
package com.poixson.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.utils.ProcUtils;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.Utils;


public class xLockFile {

	private static final ConcurrentHashMap<String, xLockFile> instances = new ConcurrentHashMap<String, xLockFile>();

	public final File file;

	protected FileLock         lock    = null;
	protected FileChannel      channel = null;
	protected RandomAccessFile handle  = null;



	public static xLockFile Get(final String filename) {
		if (Utils.isEmpty(filename)) throw new RequiredArgumentException("filename");
		// existing lock instance
		{
			final xLockFile lock = instances.get(filename);
			if (lock != null)
				return lock;
		}
		// new lock instance
		{
			final xLockFile lock = new xLockFile(filename);
			final xLockFile existing = instances.putIfAbsent(filename, lock);
			if (existing != null)
				return existing;
			Keeper.add(lock);
			return lock;
		}
	}
	public static xLockFile Peek(final String filename) {
		if (Utils.isEmpty(filename)) throw new RequiredArgumentException("filename");
		return instances.get(filename);
	}



	public static xLockFile Lock(final String filename) {
		final xLockFile lock = Get(filename);
		if (lock == null)    return null;
		if (!lock.acquire()) return null;
		return lock;
	}
	public static boolean Release(final String filename) {
		final xLockFile lock = Get(filename);
		if (lock != null) {
			Keeper.remove(lock);
			return lock.release();
		}
		return false;
	}



	protected xLockFile(final String filename) {
		if (Utils.isEmpty(filename)) throw new RequiredArgumentException("filename");
		this.file = new File(filename);
		// register shutdown hook
		Runtime.getRuntime().addShutdownHook(
			new Thread() {
				@Override
				public void run() {
					xLockFile.this.release();
				}
			}
		);
	}



	public boolean isLocked() {
		return (this.lock != null);
	}



	// get lock on file
	public boolean acquire() {
		if (this.lock != null) return true;
		try {
			this.handle = new RandomAccessFile(this.file, "rw");
		} catch (FileNotFoundException e) {
			this.log().trace(e);
			Utils.SafeClose(this.handle);
			this.handle = null;
			return false;
		}
		this.channel = this.handle.getChannel();
		try {
			this.lock = this.channel.tryLock();
			if (this.lock == null) {
				Utils.SafeClose(this.handle);
				Utils.SafeClose(this.channel);
				this.handle  = null;
				this.channel = null;
				return false;
			}
			final int pid = ProcUtils.getPid();
			this.handle.write(
				Integer.toString(pid).getBytes()
			);
		} catch (OverlappingFileLockException e) {
			this.log().trace(e);
			Utils.SafeClose(this.lock);
			Utils.SafeClose(this.handle);
			Utils.SafeClose(this.channel);
			this.lock    = null;
			this.handle  = null;
			this.channel = null;
			return false;
		} catch (IOException e) {
			this.log().trace(e);
			Utils.SafeClose(this.lock);
			Utils.SafeClose(this.handle);
			Utils.SafeClose(this.channel);
			this.lock    = null;
			this.handle  = null;
			this.channel = null;
			return false;
		}
		this.log().fine("Locked file:", this.file.getName());
		return true;
	}
	// release file lock
	public boolean release() {
		if (this.lock == null) return false;
		try {
			this.lock.release();
		} catch (Exception ignore) {}
		Utils.SafeClose(this.lock);
		Utils.SafeClose(this.channel);
		Utils.SafeClose(this.handle);
		this.lock    = null;
		this.channel = null;
		this.handle  = null;
		log().fine("Released file lock:", this.file.getName());
		try {
			this.file.delete();
		} catch (Exception ignore) {}
		Keeper.remove(this);
		return true;
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log != null)
					return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get( ReflectUtils.GetClassName(this) );
	}



}
*/
