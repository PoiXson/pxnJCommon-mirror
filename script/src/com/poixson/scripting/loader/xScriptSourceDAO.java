package com.poixson.scripting.loader;

import static com.poixson.utils.FileUtils.GetLastModified;
import static com.poixson.utils.FileUtils.ReadInputStream;
import static com.poixson.utils.StringUtils.ForceEnds;
import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.Utils.GetMS;
import static com.poixson.utils.Utils.SafeClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.poixson.utils.FileUtils;


public class xScriptSourceDAO {

	public final String path_local;
	public final String path_resource;

	public final String filename;
	public final boolean isLocal;

	public final String code;
	public final long timestamp;



	public static xScriptSourceDAO Find(final Class<?> clss,
			final String path_local, final String path_resource,
			final String filename)
			throws FileNotFoundException {
		InputStream in = null;
		boolean isLocal = false;
		try {
			// local file
			if (path_local != null) {
				final File file = new File(path_local, filename);
				if (file.isFile()) {
					in = new FileInputStream(file);
					isLocal = true;
				}
			}
			// resource file
			String resFile = null;
			if (path_resource != null) {
				resFile = ForceStarts('/', ForceEnds('/', path_resource)) + filename;
				if (in == null)
					in = FileUtils.OpenResource(clss, resFile);
			}
			if (in == null) throw new FileNotFoundException(filename);
			final String code = ReadInputStream(in);
			return
				new xScriptSourceDAO(
					isLocal,
					path_local,
					path_resource,
					filename,
					code
				);
		} finally {
			SafeClose(in);
//TODO
//Log().info(String.format("Loaded %s script: %s", isReal?"local":"resource", filename));
		}
	}



	public xScriptSourceDAO(final boolean isLocal,
			final String path_local, final String path_resource,
			final String filename, final String code) {
		this.isLocal       = isLocal;
		this.path_local    = path_local;
		this.path_resource = path_resource;
		this.filename      = filename;
		this.code          = code;
		this.timestamp = GetMS();
	}



	public boolean hasFileChanged() {
		if (this.isLocal) {
			final File file = new File(this.path_local, this.filename);
			try {
				final long last = GetLastModified(file);
				return (last > 0L) && (last*1000L > this.timestamp);
			} catch (IOException ignore) {}
		}
		return false;
	}



}
