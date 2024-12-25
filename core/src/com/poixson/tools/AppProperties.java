package com.poixson.tools;

import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.StringUtils;


public interface AppProperties {

	public static final String PROPS_FILE = "app.properties";



	public AppPropsDAO getProps();



	public class AppPropsDAO {

		public final String artifact;
		public final String group;
		public final String slug;
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

		public static AppPropsDAO LoadSafe() {
			try {
				return LoadFromClassRef(AppProperties.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		public static AppPropsDAO LoadFromClassRef(final Class<?> clss) throws IOException {
			final Properties props;
			InputStream in = null;
			try {
				in = clss.getResourceAsStream(
					StringUtils.ForceStarts("/", AppProperties.PROPS_FILE)
				);
				if (in == null) {
					throw new IOException(
						String.format(
							"Failed to load %s resource from jar",
							AppProperties.PROPS_FILE
						)
					);
				}
				props = new Properties();
				props.load(in);
			} finally {
				SafeClose(in);
			}
			return new AppPropsDAO(props);
		}

		// load app.properties file
		protected AppPropsDAO(final Properties props) {
			if (props == null) throw new RequiredArgumentException("props");
			this.artifact  = props.getProperty("artifact"  );
			this.group     = props.getProperty("group"     );
			if (IsEmpty(this.artifact) || this.artifact.startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
			if (IsEmpty(this.group   ) || this.group   .startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
			{
				final String slug  = props.getProperty("slug" );
				final String title = props.getProperty("title");
				String             name = slug;
				if (IsEmpty(name)) name = title;
				if (IsEmpty(name)) name = this.artifact;
				if (IsEmpty(name)) throw new NullPointerException("Invalid build; something went wrong");
				this.slug  = (IsEmpty(slug)  ? name : slug );
				this.title = (IsEmpty(title) ? name : title);
			}
			this.version   = props.getProperty("version"   );
			this.license   = props.getProperty("license"   );
			this.url       = props.getProperty("url"       );
			this.orgName   = props.getProperty("org_name"  );
			this.orgUrl    = props.getProperty("org_url"   );
			this.issueName = props.getProperty("issue_name");
			this.issueUrl  = props.getProperty("issue_url" );
			if (IsEmpty(this.slug    ) || this.slug    .startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
			if (IsEmpty(this.version ) || this.version .startsWith("$")) throw new RuntimeException("Invalid build; something went wrong");
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



	// -------------------------------------------------------------------------------
	// properties



	public default String getArtifact() {
		return this.getProps().artifact;
	}
	public default String getGroup() {
		return this.getProps().group;
	}
	public default String getSlug() {
		return this.getProps().slug;
	}
	public default String getTitle() {
		return this.getProps().title;
	}
	public default String getVersion() {
		return this.getProps().version;
	}
	public default String getLicense() {
		return this.getProps().license;
	}
	public default String getCommitHashFull() {
		return this.getProps().commitHashFull;
	}
	public default String getCommitHashShort() {
		return this.getProps().commitHashShort;
	}
	public default String getURL() {
		return this.getProps().url;
	}
	public default String getOrgName() {
		return this.getProps().orgName;
	}
	public default String getOrgURL() {
		return this.getProps().orgUrl;
	}
	public default String getIssueName() {
		return this.getProps().issueName;
	}
	public default String getIssueURL() {
		return this.getProps().issueUrl;
	}



}
