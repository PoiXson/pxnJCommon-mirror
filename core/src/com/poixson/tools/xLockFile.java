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
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.utils.ProcUtils;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.SanUtils;
import com.poixson.utils.Utils;


public class xLockFile {

	private static final ConcurrentHashMap<String, xLockFile> instances = new ConcurrentHashMap<String, xLockFile>();

	public final String filename;
	public final File   file;

	private volatile FileLock         fileLock;
	private volatile RandomAccessFile handle;
	private volatile FileChannel      channel;



	public static xLockFile Get(final String filename) {
		if (Utils.isBlank(filename))          throw new RequiredArgumentException("filename");
		if (!SanUtils.SafeFileName(filename)) throw new IllegalArgumentException("Invalid lock file name: "+filename);
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
		if (Utils.isBlank(filename))          throw new RequiredArgumentException("filename");
		if (!SanUtils.SafeFileName(filename)) throw new IllegalArgumentException("Invalid lock file name: "+filename);
		return instances.get(filename);
	}



	public static xLockFile Lock(final String filename) {
		final xLockFile lock = Get(filename);
		if (lock == null)
			return null;
		if (lock.acquire())
			return lock;
		return null;
	}
	public static boolean getRelease(final String filename) {
		final LockFile lock = get(filename);
		if (lock == null)
			return false;
		return lock.release();
	}



	public boolean isLocked() {
		return (this.fileLock != null);
	}



	protected xLockFile(final String filename) {
		if (Utils.isBlank(filename))          throw new RequiredArgumentException("filename");
		if (!SanUtils.SafeFileName(filename)) throw new IllegalArgumentException("Invalid lock file name: "+filename);
		this.filename = filename;
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



	// get lock on file
	public boolean acquire() {
		if (this.fileLock != null)
			return true;
		try {
			this.handle = new RandomAccessFile(this.file, "rw");
		} catch (FileNotFoundException e) {
			this.log().trace(e);
			return false;
		}
		this.channel  = this.handle.getChannel();
		try {
			this.fileLock = this.channel.tryLock();
		} catch (OverlappingFileLockException e) {
			this.log().trace(e);
			return false;
		} catch (IOException e) {
			this.log().trace(e);
			return false;
		}
		if (this.fileLock == null)
			return false;
		final int pid = ProcUtils.getPid();
		try {
			this.handle.write(
				Integer.toString(pid)
					.getBytes()
			);
		} catch (IOException e) {
			this.log().trace(e);
			this.fileLock = null;
			return false;
		}
		this.log().fine("Locked file:", this.filename);
		return true;
	}
	// release file lock
	public boolean release() {
		if (this.fileLock == null)
			return false;
		try {
			this.fileLock.release();
		} catch (Exception ignore) {}
		try {
			this.fileLock.close();
		} catch (Exception ignore) {}
		Utils.safeClose(this.channel);
		Utils.safeClose(this.handle);
		this.fileLock = null;
		this.channel = null;
		this.handle  = null;
		log().fine("Released file lock:", this.filename);
		try {
			this.file.delete();
		} catch (Exception ignore) {}
		Keeper.remove(this);
		return true;
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<SoftReference<xLog>> _log =
			new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached logger
		final SoftReference<xLog> ref = this._log.get();
		if (ref != null) {
			final xLog log = ref.get();
			if (log != null)
				return log;
		}
		// get logger
		{
			final String className =
				ReflectUtils.getClassName(
					this.getClass()
				);
			final xLog log = xLogRoot.Get(className);
			this._log.set(
				new SoftReference<xLog>(
					log
				)
			);
			return log;
		}
	}



}
