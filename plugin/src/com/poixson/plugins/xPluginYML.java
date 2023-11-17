package com.poixson.plugins;

import java.util.Map;

import com.poixson.tools.config.xConfig;


public class xPluginYML extends xConfig {

	// plugin.yml keys
	public static final String PLUGIN_YML_NAME        = "Plugin Name";
	public static final String PLUGIN_YML_VERSION     = "Plugin Version";
	public static final String PLUGIN_YML_APP_VERSION = "App Version";
	public static final String PLUGIN_YML_COMMIT      = "Commit";
	public static final String PLUGIN_YML_AUTHOR      = "Author";
	public static final String PLUGIN_YML_WEBSITE     = "Website";

	public final String plugin_name;

	public final String plugin_version;
	public final String app_version;

	public final String commit;
	public final String author;
	public final String website;

	public final String class_main;



	public xPluginYML(final Map<String, Object> datamap) {
		this(datamap, null);
	}
	public xPluginYML(final Map<String, Object> datamap,
			final String keyClassMain) {
		super(datamap);
		this.plugin_name    = this.getString(PLUGIN_YML_NAME       );
		this.plugin_version = this.getString(PLUGIN_YML_VERSION    );
		this.app_version    = this.getString(PLUGIN_YML_APP_VERSION);
		this.commit         = this.getString(PLUGIN_YML_COMMIT     );
		this.author         = this.getString(PLUGIN_YML_AUTHOR     );
		this.website        = this.getString(PLUGIN_YML_WEBSITE    );
		this.class_main     = this.getStr(keyClassMain, null);
	}



	// -------------------------------------------------------------------------------



	public String getPluginTitle() {
		return
			(new StringBuilder())
				.append(this.plugin_name)
				.append('-')
				.append(this.plugin_version)
				.toString();
	}
	public String getPluginName() {
		return this.plugin_name;
	}



	public String getPluginVersion() {
		return this.plugin_version;
	}
	public String getRequiredAppVersion() {
		return this.app_version;
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
			this.class_main.endsWith(".class")
			? this.class_main.substring(0, this.class_main.length() - 6)
			: this.class_main
		);
	}



}
