package com.poixson.tools.scheduler.trigger;

import static com.poixson.logger.xLog.XLog;

import com.poixson.logger.xLog;


public abstract class TriggerFactory<T extends Trigger> {



	public abstract T build();



	public xLog log() {
		return XLog();
	}



}
