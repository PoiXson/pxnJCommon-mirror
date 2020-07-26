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
	public void publish(final String line);
	public void publish(final String[] lines);
	public void publish(final xLogRecord record);


	public void flush();
	public void clearScreen();
	public void beep();


	public void trace  (final Throwable e);
	public void trace  (final Throwable e, final String line, final Object...args);

	public void stdout(final String...lines);
	public void stderr(final String...lines);

	public void title  (final String line, final Object...args);
	public void detail (final String line, final Object...args);
	public void finest (final String line, final Object...args);
	public void finer  (final String line, final Object...args);
	public void fine   (final String line, final Object...args);
	public void stats  (final String line, final Object...args);
	public void info   (final String line, final Object...args);
	public void warning(final String line, final Object...args);
	public void notice (final String line, final Object...args);
	public void severe (final String line, final Object...args);
	public void fatal  (final String line, final Object...args);

	public void title  (final String[] lines, final Object...args);
	public void detail (final String[] lines, final Object...args);
	public void finest (final String[] lines, final Object...args);
	public void finer  (final String[] lines, final Object...args);
	public void fine   (final String[] lines, final Object...args);
	public void stats  (final String[] lines, final Object...args);
	public void info   (final String[] lines, final Object...args);
	public void warning(final String[] lines, final Object...args);
	public void notice (final String[] lines, final Object...args);
	public void severe (final String[] lines, final Object...args);
	public void fatal  (final String[] lines, final Object...args);


}
