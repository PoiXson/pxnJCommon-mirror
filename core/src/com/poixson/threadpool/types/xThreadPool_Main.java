package com.poixson.threadpool.types;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.worker.xThreadPoolWorker;
import com.poixson.tools.Keeper;


public class xThreadPool_Main extends xThreadPool_Single {

	public static final String MAIN_POOL_NAME = "main";

	protected final static AtomicReference<xThreadPool_Main> instance = new AtomicReference<xThreadPool_Main>(null);



	public static xThreadPool_Main Get() {
		// existing instance
		{
			final xThreadPool_Main pool = instance.get();
			if (pool != null)
				return pool;
		}
		// new instance
		{
			final xThreadPool_Main pool = new xThreadPool_Main();
			if (instance.compareAndSet(null, pool)) {
				final xThreadPool existing = pools.putIfAbsent(MAIN_POOL_NAME, pool);
				if (existing != null) {
					final xThreadPool_Main main = (xThreadPool_Main) existing;
					instance.set(main);
					return main;
				}
				return pool;
			}
		}
		return instance.get();
	}



	protected xThreadPool_Main() {
		super(MAIN_POOL_NAME);
		this.keepOneAlive.set(true);
	}



	// -------------------------------------------------------------------------------
	// workers



	@Override
	public void unregisterWorker(final xThreadPoolWorker worker) {
		super.unregisterWorker(worker);
		this.log().fine("Main thread pool has stopped");
	}



	// -------------------------------------------------------------------------------
	// state



	public void stopMain() {
		if (this.stopping.compareAndSet(false, true))
			this.stopWorkers();
		Keeper.Remove(this);
	}



	@Override
	public boolean isMainPool() {
		return true;
	}



}
