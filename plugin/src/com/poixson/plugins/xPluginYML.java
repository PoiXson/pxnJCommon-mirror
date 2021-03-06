/*
package com.poixson.plugins;

import java.util.Map;

import com.poixson.tools.config.xConfig;


public class xPluginYML extends xConfig {

	public final String pluginName;

	public final String pluginVersion;
	public final String appVersion;

	public final String commit;
	public final String author;
	public final String website;

	public final String mainClass;



	public xPluginYML(final Map<String, Object> datamap) {
		this(datamap, null);
	}
	public xPluginYML(final Map<String, Object> datamap,
			final String mainClassKey) {
		super(datamap);
		this.pluginName    = this.getString(xPluginDefines.PLUGIN_YML_NAME);
		this.pluginVersion = this.getString(xPluginDefines.PLUGIN_YML_VERSION);
		this.appVersion    = this.getString(xPluginDefines.PLUGIN_YML_APP_VERSION);
		this.commit        = this.getString(xPluginDefines.PLUGIN_YML_COMMIT);
		this.author        = this.getString(xPluginDefines.PLUGIN_YML_AUTHOR);
		this.website       = this.getString(xPluginDefines.PLUGIN_YML_WEBSITE);
		this.mainClass     = this.getStr(mainClassKey, null);
	}



	// -------------------------------------------------------------------------------



	public String getPluginTitle() {
		return
			(new StringBuilder())
				.append(this.pluginName)
				.append('-')
				.append(this.pluginVersion)
				.toString();
	}
	public String getPluginName() {
		return this.pluginName;
	}



	public String getPluginVersion() {
		return this.pluginVersion;
	}
	public String getRequiredAppVersion() {
		return this.appVersion;
	}



	public String getCommit() {
		return this.commit;
	}
	public String getCommitShort() {
		return this.getCommitShort(7);
	}
	public String getCommitShort(final int size) {
		if (size <= 0)
			return "";
		if (size >= this.commit.length())
			return this.commit;
		return this.commit.substring(0, size);
	}



	public String getAuthor() {
		return this.author;
	}
	public String getWebsite() {
		return this.website;
	}



	public String getMainClass() {
		return (
			this.mainClass.endsWith(".class")
			? this.mainClass.substring(0, this.mainClass.length() - 6)
			: this.mainClass
		);
	}



}
*/
