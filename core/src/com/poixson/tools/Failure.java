package com.poixson.tools;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public final class Failure {

	protected final CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<String>();

	protected final AtomicInteger exitCode = new AtomicInteger(0);



	public static boolean AtomicFail(final AtomicReference<Failure> atomic,
			final xLog log, final String msg, final Object...args) {
		if (log != null) {
			log.fatal(
				(new StringBuilder())
					.append("@|FG_RED ")
					.append(msg)
					.append("|@")
					.toString(),
				args
			);
		}
		if (atomic.get() == null) {
			final Failure failure = new Failure();
			if (atomic.compareAndSet(null, failure)) {
				failure.addMessage(msg, args);
				return true;
			}
		}
		final Failure failure = atomic.get();
		failure.addMessage(msg, args);
		return false;
	}



	public Failure() {
	}



	public void addMessage(final int exitCode, final String msg, final Object...args) {
		this.addMessage(msg, args);
		this.exitCode.set( NumberUtils.MinMax(exitCode, 0, 255) );
	}
	public void addMessage(final String msg, final Object...args) {
		if (Utils.notEmpty(msg)) {
			this.messages.add(
				StringUtils.ReplaceTags(msg, args)
			);
		}
	}
	public String[] getMessages() {
		return this.messages.toArray(new String[0]);
	}



	@Override
	public String toString() {
		final String[] msgs = this.getMessages();
		if (msgs == null)     return null;
		if (msgs.length == 0) return "";
		return StringUtils.MergeStrings("; ", msgs);
	}



	public int getExitCode() {
		return this.exitCode.get();
	}
	public void setExitCode(final int exitCode) {
		this.exitCode.set(exitCode);
	}



}
