package com.poixson.tools.config;

import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.exceptions.CreateDefaultYmlFileException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.utils.FileUtils;
import com.poixson.utils.StringUtils;


public final class xConfigLoader {
	private xConfigLoader() {}



	// new xConfig child instance
	public static <T extends xConfig> T NewConfig(
			final Map<String, Object> datamap, final Class<T> clss) {
		if (datamap == null) {
			return NewConfig(
				new HashMap<String, Object>(),
				clss
			);
		}
		if (clss == null)
			return CastConfig(NewConfig(datamap, xConfig.class));
		// get construct
		final Constructor<? extends xConfig> construct;
		try {
			construct = clss.getDeclaredConstructor(Map.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		if (construct == null)
			throw new RuntimeException("xConfig constructor not found!");
		// get new instance
		try {
			return CastConfig( construct.newInstance(datamap) );
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	private static <T extends xConfig> T CastConfig(final Object obj) {
		if (obj == null) return null;
		return (T) obj;
	}



	/**
	 * Load and parse yaml data from an input stream. 
	 * @param in InputStream to read from.
	 * @return Map<String, Object> datamap contents of yml file.
	 */
	public static Map<String, Object> LoadYamlFromStream(final InputStream in) {
		if (in == null) throw new RequiredArgumentException("in");
		final Yaml yml = new Yaml();
		final Map<String, Object> datamap = yml.loadAs(in, Map.class);
		return datamap;
	}



	// load file
	public static <T extends xConfig> T FromFile(final String filepath, final Class<T> clss) {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filepath");
		if (clss == null)
			return CastConfig(FromFile(filepath, xConfig.class));
		final String file = StringUtils.ForceEnds(".yml", filepath);
		final File f = new File(file);

		Map<String, Object> datamap = null;
		if (f.isFile()) {
			InputStream in = null;
			try {
				in = new FileInputStream(f);
				datamap = LoadYamlFromStream(in);
			} catch (FileNotFoundException ignore) {
			} finally {
				SafeClose(in);
			}
		}
		return NewConfig(datamap, clss);
	}



	// load jar resource
	public static <T extends xConfig> T FromJar(final String filepath, final Class<T> clss) {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filePath");
		if (clss == null)
			return CastConfig(FromJar(filepath, xConfig.class));
		final String file = StringUtils.ForceEnds(".yml", filepath);
		InputStream in = null;
		try {
			in = FileUtils.OpenResource(clss, file);
			if (in == null) return null;
			final Map<String, Object> datamap = LoadYamlFromStream(in);
			if (datamap == null) return null;
			return NewConfig(datamap, clss);
		} finally {
			SafeClose(in);
		}
	}



	// load file or jar (copy from jar to filesystem if doesn't exist)
	public static <T extends xConfig> T FromFileOrJar(final String filepath, final Class<T> clss)
			throws CreateDefaultYmlFileException {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filepath");
		final String file = StringUtils.ForceEnds(".yml", filepath);
		if (clss == null)
			return CastConfig(FromFileOrJar(filepath, xConfig.class));
		try {
			// attempt loading from file
			{
				final T cfg = FromFile(file, clss);
				if (cfg != null) return cfg;
			}
			// attempt loading from resource
			{
				final T cfg = FromJar(file, clss);
				if (cfg != null) {
					// copy default file
					try {
						xLog.Get().info("Creating default file:", file);
						FileUtils.ExportResource(file, FileUtils.OpenResource(clss, file));
					} catch (Exception e) {
						throw new CreateDefaultYmlFileException(file, e);
					}
					return cfg;
				}
			}
		} catch (Exception e) {
			xLog.Get().trace(e);
		}
		return null;
	}



}
