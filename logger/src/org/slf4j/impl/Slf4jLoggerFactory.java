/*
package org.slf4j.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.poixson.logger.xLog;
import com.poixson.logger.proxies.LoggerToXLog;
import com.poixson.utils.Utils;


public class Slf4jLoggerFactory implements ILoggerFactory {
	public static final String LOG_NAME = "slf4j";

	private final ConcurrentHashMap<String, Logger> loggers = new ConcurrentHashMap<String, Logger>();



	public Slf4jLoggerFactory() {
	}



	@Override
	public Logger getLogger(final String name) {
		// use existing logger instance
		{
			final Logger logger = this.loggers.get(name);
			if (logger != null)
				return logger;
		}
		// new logger instance
		{
			// wrap the logger
			final Logger newlogger = new Slf4jLoggerAdapter(name, GetXLog(name));
			// cache wrapped logger
			final Logger existing = this.loggers.putIfAbsent(name, newlogger);
			if (existing != null)
				return existing;
			return newlogger;
		}
	}



	public static xLog GetXLog(final String name) {
		return xLog.Get(
			Utils.ifEmpty(LoggerToXLog.AliasFor(name), LOG_NAME)
		);
	}



}
*/
