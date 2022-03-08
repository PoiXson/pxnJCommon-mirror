package com.poixson.logger;

import com.poixson.logger.records.xLogRecord;


public interface xLogInterface {


	public xLevel getLevel();
	public void    setLevel(   final xLevel level);
	public boolean isLoggable( final xLevel level);
	public boolean notLoggable(final xLevel level);
	public boolean isRoot();
	public String[] getNameTree();


	public void publish();
	public void publish(final String msg);
	public void publish(final xLogRecord record);


	public void flush();
	public void clearScreen();
	public void beep();


	public void trace  (final Throwable e);
	public void trace  (final Throwable e, final String msg, final Object...args);

	public void stdout(final String msg);
	public void stderr(final String msg);

	public void title  (final String msg, final Object...args);
	public void detail (final String msg, final Object...args);
	public void finest (final String msg, final Object...args);
	public void finer  (final String msg, final Object...args);
	public void fine   (final String msg, final Object...args);
	public void stats  (final String msg, final Object...args);
	public void info   (final String msg, final Object...args);
	public void warning(final String msg, final Object...args);
	public void notice (final String msg, final Object...args);
	public void severe (final String msg, final Object...args);
	public void fatal  (final String msg, final Object...args);


}
