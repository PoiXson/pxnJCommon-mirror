package com.poixson.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class AppProps {

	public static final String PROPS_FILE = "app.properties";

	// property values
	public final String name;
	public final String title;
	public final String version;
	public final String commitHashFull;
	public final String commitHashShort;
	public final String url;
	public final String orgName;
	public final String orgUrl;
	public final String issueName;
	public final String issueUrl;



	public static AppProps LoadFromClassRef(final Class<?> clss) throws IOException {
		final Properties props;
		InputStream in = null;
		try {
			in = clss.getResourceAsStream(
				StringUtils.ForceStarts("/", PROPS_FILE)
			);
			if (in == null) {
				throw new IOException(
					String.format(
						"Failed to load %s resource from jar",
						PROPS_FILE
					)
				);
			}
			props = new Properties();
			props.load(in);
		} finally {
			Utils.SafeClose(in);
		}
		return new AppProps(props);
	}
	// load app.properties file
	protected AppProps(final Properties props) {
		if (props == null) throw new RequiredArgumentException("props");
		this.name      = props.getProperty("name");
		this.title     = props.getProperty("title");
		this.version   = props.getProperty("version");
		this.url       = props.getProperty("url");
		this.orgName   = props.getProperty("org_name");
		this.orgUrl    = props.getProperty("org_url");
		this.issueName = props.getProperty("issue_name");
		this.issueUrl  = props.getProperty("issue_url");
		// commit hash
		{
			final String hash = props.getProperty("commit");
			if (Utils.isEmpty(hash)) {
				this.commitHashFull  = null;
				this.commitHashShort = null;
			} else
			if (hash.startsWith("${")) {
				this.commitHashFull  = null;
				this.commitHashShort = null;
			} else {
				this.commitHashFull  = hash;
				this.commitHashShort = hash.substring(0, 7);
			}
		}
	}



}
