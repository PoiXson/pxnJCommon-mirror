package com.poixson.plugins;

import java.util.Map;

import com.poixson.tools.config.xConfig;
import com.poixson.utils.Utils;


public class xPluginYML extends xConfig {

	private final String name;
	private final String appVersion;
	private final String author;
	private final String website;
	private final String mainClass;



	public xPluginYML(final Map<String, Object> datamap) {
		this(datamap, null);
	}
	public xPluginYML(final Map<String, Object> datamap, final String mainClassKey) {
		super(datamap);
		this.name       = this.getString(xPluginDefines.PLUGIN_YML_NAME);
		this.appVersion = this.getString(xPluginDefines.PLUGIN_YML_VERSION);
		this.author     = this.getString(xPluginDefines.PLUGIN_YML_AUTHOR);
		this.website    = this.getString(xPluginDefines.PLUGIN_YML_WEBSITE);
		{
			final String key = (
				Utils.isEmpty(mainClassKey)
				? xPluginDefines.DEFAULT_PLUGIN_CLASS_KEY
				: mainClassKey
			);
			this.mainClass = this.getStr(key, null);
		}
	}
	@Override
	public xPluginYML clone() {
		return new xPluginYML(super.datamap);
	}



	// plugin name
	public String getPluginName() {
		return this.name;
	}
	// plugin version
	public String getAppVersion() {
		return this.appVersion;
	}
	// plugin author
	public String getPluginAuthor() {
		return this.author;
	}
	// plugin website
	public String getPluginWebsite() {
		return this.website;
	}
	// plugin main class
	public String getMainClass() {
		return this.mainClass;
	}



}