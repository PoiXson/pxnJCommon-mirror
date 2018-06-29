package com.poixson.threadpool.types;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xVars;
import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.threadpool.xThreadPool;
import com.poixson.threadpool.xThreadPoolWorker;
import com.poixson.utils.ThreadUtils;


public class xThreadPool_Main extends xThreadPool_SingleWorker {

	public static final String MAIN_POOL_NAME = "main";

	private final static AtomicReference<xThreadPool_Main> instance =
			new AtomicReference<xThreadPool_Main>(null);



	public static xThreadPool_Main get() {
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
				final xThreadPool existing =
					pools.putIfAbsent(MAIN_POOL_NAME, pool);
				if (existing != null) {
					instance.set( (xThreadPool_Main) existing );
					return (xThreadPool_Main) existing;
				}
				return pool;
			}
			return instance.get();
		}
	}



	protected xThreadPool_Main() {
		super(MAIN_POOL_NAME);
		this.keepOneAlive.set(true);
		this.imposeMain.set(false);
	}



	// ------------------------------------------------------------------------------- //
	// start/stop



	// start blocking
	@Override
	public void run() {
		if (stoppingAll.get()) return;
		this.start();
		{
			final xThreadPoolWorker worker =
				new xThreadPoolWorker(
					this,
					Thread.currentThread(),
					MAIN_POOL_NAME
				);
			this.registerWorker(worker);
			if (worker.isRunning())
				throw new RuntimeException("Single thread pool worker is already active");
			worker.run();
		}
		xLogRoot.Get()
			.publish(
				( xVars.isDebug() ? xLevel.WARNING : xLevel.INFO ),
				"Main thread returning.."
			);
		ThreadUtils.Sleep(100L);
		if (this.isMainPool()) {
			System.exit(1);
		}
	}



	@Override
	protected void startNewWorkerIfNeededAndAble() {
	}



	// ------------------------------------------------------------------------------- //
	// config



	@Override
	public boolean keepOneAlive() {
		return true;
	}
	@Override
	public void setKeepOneAlive(final boolean keepOne) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean isImposeMainPool() {
		return false;
	}
	@Override
	public void setImposeMainPool(final boolean impose) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean isMainPool() {
		return true;
	}



	// ------------------------------------------------------------------------------- //
	// logger



	protected xLog _log() {
		return xLogRoot.Get( "thpool-"+MAIN_POOL_NAME );
	}



}
