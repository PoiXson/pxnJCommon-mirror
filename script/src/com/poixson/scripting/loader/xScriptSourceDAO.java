package com.poixson.scripting.loader;

import static com.poixson.utils.FileUtils.GetLastModified;
import static com.poixson.utils.FileUtils.ReadInputStream;
import static com.poixson.utils.Utils.GetMS;
import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class xScriptSourceDAO {

	public final String path_local;
	public final String path_resource;

	public final String filename;
	public final boolean isReal;

	public final String code;
	public final long timestamp;



	public static xScriptSourceDAO Find(
			final String path_local, final String path_resource,
			final String filename)
			throws FileNotFoundException {
		InputStream in = null;
		boolean isReal = false;
		// local file
		if (path_local != null) {
			final File file = new File(path_local, filename);
			if (file.isFile()) {
				in = new FileInputStream(file);
				isReal = true;
			}
		}
		// resource file
		if (path_resource != null) {
			final StringBuilder resFile = new StringBuilder();
			if (!IsEmpty(path_resource))
				resFile.append(path_resource).append('/');
			resFile.append(filename);
//TODO: load resource from reference
//			if (in == null) in = plugin.getResource(resFile.toString());
			if (in == null) throw new FileNotFoundException(filename);
			final String code = ReadInputStream(in);
			SafeClose(in);
			final xScriptSourceDAO dao =
				new xScriptSourceDAO(
					isReal,
					path_local,
					path_resource,
					filename,
					code
				);
//TODO
//Log().info(String.format("%sLoaded %s script: %s", LOG_PREFIX, isReal?"local":"resource", filename));
			return dao;
		}
		throw new FileNotFoundException(filename);
	}



	public xScriptSourceDAO(final boolean isReal,
			final String path_local, final String path_resource,
			final String filename, final String code) {
		this.isReal        = isReal;
		this.path_local    = path_local;
		this.path_resource = path_resource;
		this.filename      = filename;
		this.code          = code;
		this.timestamp = GetMS();
	}



	public boolean hasFileChanged() {
		if (this.isReal) {
			final File file = new File(this.path_local, this.filename);
			try {
				final long last = GetLastModified(file);
				return (last > 0L) && (last*1000L > this.timestamp);
			} catch (IOException ignore) {}
		}
		return false;
	}



}
