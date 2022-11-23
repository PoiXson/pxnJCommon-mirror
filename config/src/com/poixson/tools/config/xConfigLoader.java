package com.poixson.tools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.exceptions.CreateDefaultYmlFileException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.utils.FileUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public final class xConfigLoader {
	private xConfigLoader() {}



	// new xConfig child instance
	public static <T extends xConfig> T NewConfig(
			final Map<String, Object> datamap, final Class<T> cfgClass) {
		if (datamap == null)  throw new RequiredArgumentException("datamap");
		if (cfgClass == null) throw new RequiredArgumentException("cfgClass");
		// get construct
		final Constructor<? extends xConfig> construct;
		try {
			construct = cfgClass.getDeclaredConstructor(Map.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		if (construct == null)
			throw new RuntimeException("xConfig constructor not found!");
		// get new instance
		try {
			return CastConfig(
				construct.newInstance(datamap)
			);
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



	/ **
	 * Load and parse yaml data from an input stream. 
	 * @param in InputStream to read from.
	 * @return Map<String, Object> datamap contents of yml file.
	 * /
	@SuppressWarnings("unchecked")
	public static Map<String, Object> LoadYamlFromStream(final InputStream in) {
		if (in == null) throw new RequiredArgumentException("in");
		final Yaml yml = new Yaml();
		final Map<String, Object> datamap = yml.loadAs(in, Map.class);
		return datamap;
	}



	// load file
	public static <T extends xConfig> T FromFile(final String filePath, final Class<T> clss) {
		if (Utils.isEmpty(filePath)) throw new RequiredArgumentException("filePath");
		if (clss == null)            throw new RequiredArgumentException("clss");
		final String fileStr = StringUtils.ForceEnds(".yml", filePath);
		final File file = new File(fileStr);
		if (!file.isFile()) return null;
		InputStream in = null;
		try {
			in = new FileInputStream(fileStr);
			final Map<String, Object> datamap = LoadYamlFromStream(in);
			if (datamap == null)
				return null;
			final T cfg = NewConfig(datamap, clss);
			return cfg;
		} catch (FileNotFoundException ignore) {
		} finally {
			Utils.safeClose(in);
		}
		return null;
	}



	// load jar resource
	public static <T extends xConfig> T FromJar(final String filePath, final Class<T> clss) {
		if (Utils.isEmpty(filePath)) throw new RequiredArgumentException("filePath");
		if (clss == null)            throw new RequiredArgumentException("clss");
		final String fileStr = StringUtils.ForceEnds(".yml", filePath);
		InputStream in = null;
		try {
			in = FileUtils.OpenResource(clss, fileStr);
			if (in == null) return null;
			final Map<String, Object> datamap = LoadYamlFromStream(in);
			if (datamap == null) return null;
			final T cfg = NewConfig(datamap, clss);
			return cfg;
		} finally {
			Utils.safeClose(in);
		}
	}



	// load file or jar (copy from jar to filesystem if doesn't exist)
	public static <T extends xConfig> T FromFileOrJar(final String filePath, final Class<T> clss)
			throws CreateDefaultYmlFileException {
		if (Utils.isEmpty(filePath)) throw new RequiredArgumentException("filePath");
		if (clss == null)            throw new RequiredArgumentException("clss");
		final String fileStr = StringUtils.ForceEnds(".yml", filePath);
		try {
			// attempt loading from file
			{
				final T cfg = FromFile(fileStr, clss);
				if (cfg != null) return cfg;
			}
			// attempt loading from resource
			{
				final T cfg = FromJar(fileStr, clss);
				if (cfg != null) {
					// copy default file
					try {
						xLog.GetRoot().info("Creating default file:", fileStr);
						FileUtils.ExportResource(fileStr, FileUtils.OpenResource(clss, fileStr));
					} catch (Exception e) {
						throw new CreateDefaultYmlFileException(fileStr, e);
					}
					return cfg;
				}
			}
		} catch (Exception e) {
			xLog.GetRoot().trace(e);
		}
		return null;
	}



}
