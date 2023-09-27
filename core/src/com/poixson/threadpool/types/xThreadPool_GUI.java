/*
package com.poixson.threadpool.types;

import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolWorker;
import com.poixson.threadpool.xThreadPoolWorker_GUI;


public class xThreadPool_GUI extends xThreadPool_Single {

	public static final String DISPATCH_POOL_NAME = "gui";

	private final static AtomicReference<xThreadPool_GUI> instance =
			new AtomicReference<xThreadPool_GUI>(null);



	public static xThreadPool_GUI Get() {
		if (instance.get() == null) {
			final xThreadPool_GUI pool = new xThreadPool_GUI();
			if (instance.compareAndSet(null, pool)) {
				final xThreadPool existing = pools.putIfAbsent(DISPATCH_POOL_NAME, pool);
				if (existing != null) {
					final xThreadPool_GUI gui = (xThreadPool_GUI) existing;
					instance.set(gui);
					return gui;
				}
				return pool;
			}
		}
		return instance.get();
	}



	protected xThreadPool_GUI() {
		super(DISPATCH_POOL_NAME);
		this.keepOneAlive.set(true);
	}



	// -------------------------------------------------------------------------------
	// start/stop



	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void go() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void stop() {
		throw new UnsupportedOperationException();
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
		EventQueue.invokeLater(
			this.getWorker()
		);
	}
	protected final xThreadPoolWorker getWorker() {
		if (this.worker.get() == null) {
			final xThreadPoolWorker worker =
				new xThreadPoolWorker_GUI(
					this,
					this.getPoolName()
				);
			if (this.worker.compareAndSet(null, worker))
				return worker;
		}
		return this.worker.get();
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isRunning() {
		return true;
	}



	@Override
	public boolean isGraphicsPool() {
		return true;
	}



}
*/
