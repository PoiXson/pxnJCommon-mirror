/*
package com.poixson.tools;

import static com.poixson.utils.ThreadUtils.Sleep;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.abstractions.startstop.xStartStop;


public class HangCatcher implements xStartStop, Runnable {

	public static final long DEFAULT_TIMEOUT = xTime.ParseToLong(  "10s");
	public static final long DEFAULT_SLEEP   = xTime.ParseToLong("100ms");

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);

	protected final String name;

	protected final CoolDown timeout;
	protected final long sleep;

	protected final AtomicBoolean triggered = new AtomicBoolean(false);
	protected final AtomicBoolean canceled  = new AtomicBoolean(false);

	protected final Runnable runWhenHung;



	public HangCatcher(final Runnable runWhenHung) {
		this(
			null,
			runWhenHung
		);
	}
	public HangCatcher(final String name, final Runnable runWhenHung) {
		this(
			DEFAULT_TIMEOUT,
			DEFAULT_SLEEP,
			name,
			runWhenHung
		);
	}
	public HangCatcher(final long timeout, final long sleep,
			final Runnable runWhenHung) {
		this(
			timeout,
			sleep,
			null,
			runWhenHung
		);
	}
	public HangCatcher(final long timeout, final long sleep,
			final String name, final Runnable runWhenHung) {
		if (runWhenHung == null) throw new RequiredArgumentException("runWhenHung");
		this.name = name;
		this.timeout = new CoolDown(
			timeout <= 0L
			? DEFAULT_TIMEOUT
			: timeout
		);
		this.sleep = (
			sleep <= 0L
			? DEFAULT_SLEEP
			: sleep
		);
		this.runWhenHung = runWhenHung;
	}



	@Override
	public boolean start() {
		if (this.hasCanceled())  return false;
		if (this.hasTriggered()) return false;
		this.resetTimeout();
		if (this.thread.get() == null) {
			// new thread
			final Thread thread = new Thread(this);
			if (this.thread.compareAndSet(null, thread)) {
				thread.setDaemon(true);
				if (IsEmpty(this.name)) {
					thread.setName("HangCatcher");
				} else {
					thread.setName(
						(new StringBuilder())
						.append("HangCatcher")
						.append('-')
						.append(this.name)
						.toString()
					);
				}
				thread.start();
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean stop() {
		return !this.canceled.getAndSet(true);
	}



	@Override
	public void run() {
		try {
			while (true) {
				if (this.canceled.get())  break;
				if (this.triggered.get()) break;
				if (this.timeout.again()) {
					this.trigger();
					break;
				}
				Sleep(this.sleep);
			}
		} finally {
			this.thread.set(null);
		}
	}
	public void trigger() {
		this.triggered.set(true);
		this.runWhenHung.run();
	}



	@Override
	public boolean isRunning() {
		return (this.thread.get() != null);
	}
	@Override
	public boolean isStopping() {
		return ! this.isRunning();
	}
	public boolean hasTriggered() {
		return this.triggered.get();
	}
	public boolean hasCanceled() {
		return this.canceled.get();
	}



	public void resetTimeout() {
		this.timeout
			.reset();
	}



}
*/
