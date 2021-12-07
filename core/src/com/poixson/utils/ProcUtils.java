package com.poixson.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.Keeper;


public final class ProcUtils {
	private ProcUtils() {}
	static { Keeper.add(new ProcUtils()); }

	private static final AtomicInteger pid = new AtomicInteger(Integer.MIN_VALUE);

	private static final boolean debugWireEnabled = initDebugWire();



	public static boolean isDebugWireEnabled() {
		return debugWireEnabled;
	}
	private static boolean initDebugWire() {
		final RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		if (bean != null) {
			final List<String> args = bean.getInputArguments();
			if (args != null) {
				final String argsStr = args.toString();
				if (argsStr.indexOf("jdwp") >= 0)
					return true;
			}
		}
		return false;
	}



	/**
	 * Get the pid for the jvm process.
	 * @return process id number (pid)
	 */
	public static int getPid() {
		if (pid.get() == Integer.MIN_VALUE) {
			int value = -1;
			final RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
			if (bean != null) {
				final String procName = bean.getName();
				if (Utils.notEmpty(procName)) {
					final String[] parts = procName.split("@", 2);
					if (parts != null) {
						if (parts.length == 2) {
							value = NumberUtils.ToInteger(parts[0], -1);
						}
					}
				}
			}
			pid.set(value);
		}
		return pid.get();
	}



//TODO:
/*
	// single instance lock
	public static boolean lockInstance(final String filepath) {
		String path = filepath;
		if (Utils.isEmpty(path)) {
			final xApp app = xApp.peek();
			if (app != null) {
				final String appName = app.getName();
				path = appName+".lock";
			}
		}
		if (Utils.isEmpty(path)) throw new RequiredArgumentException("path");
		final File file = new File(path);
		RandomAccessFile access = null;
		try {
			access = new RandomAccessFile(file, "rw");
			final FileLock lock = access.getChannel().tryLock();
			final int pid = getPid();
			if (pid > 0) {
				access.write(Integer.toString(pid).getBytes());
			} else {
				access.writeUTF("<PID>");
			}
			if (lock == null) {
				Utils.safeClose(access);
				return false;
			}
			// register shutdown hook
			Runtime.getRuntime().addShutdownHook(
				new LockFileReleaseThread(
					file,
					access
				)
			);
			return true;
		} catch (OverlappingFileLockException e) {
			final xLog log = xLog.GetRoot();
			log.severe("Unable to create or lock file:", file.toString());
			log.severe("File may already be locked!");
			return false;
		} catch (Exception e) {
			final xLog log = xLog.GetRoot();
			log.severe("Unable to create or lock file:", file.toString());
			log.trace(e);
		} finally {
			Utils.safeClose(access);
		}
		return false;
	}



	protected static class LockFileReleaseThread extends Thread {

		private final File file;
		private final RandomAccessFile access;

		public LockFileReleaseThread(final File file,
				final RandomAccessFile access) {
			this.file   = file;
			this.access = access;
			this.setName("LockFileRelease");
		}

		@Override
		public void run() {
			try {
				utils.safeClose(this.access);
				this.file.delete();
			} catch (Exception e) {
				final xLog log = xLog.GetRoot();
				log.severe("Unable to release lock file:", this.file.toString());
				log.trace(e);
			}
		}

	}
*/



}
