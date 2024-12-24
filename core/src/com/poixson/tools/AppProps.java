package com.poixson.tools;

import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.StringUtils;


public class AppProps {

	public static final String PROPS_FILE = "app.properties";

	// property values
	public final String name;
	public final String title;
	public final String version;
	public final String license;
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
			SafeClose(in);
		}
		return new AppProps(props);
	}
	// load app.properties file
	protected AppProps(final Properties props) {
		if (props == null) throw new RequiredArgumentException("props");
		this.name      = props.getProperty("name");
		this.title     = props.getProperty("title");
		this.version   = props.getProperty("version");
		this.license   = props.getProperty("license");
		this.url       = props.getProperty("url");
		this.orgName   = props.getProperty("org_name");
		this.orgUrl    = props.getProperty("org_url");
		this.issueName = props.getProperty("issue_name");
		this.issueUrl  = props.getProperty("issue_url");
		if (IsEmpty(this.name   ) || this.name   .startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
		if (IsEmpty(this.title  ) || this.title  .startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
		if (IsEmpty(this.version) || this.version.startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
		// commit hash
		{
			final String hash = props.getProperty("commit");
			if (IsEmpty(hash)) {
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
