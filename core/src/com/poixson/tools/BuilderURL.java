package com.poixson.tools;

import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.StringUtils.SafeString;
import static com.poixson.utils.StringUtils.ToString;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.poixson.exceptions.RequiredArgumentException;


public class BuilderURL {

	protected boolean is_tls = true;
	protected String  domain = null;
	protected String  path   = null;
	protected Map<String, String> params = new HashMap<String, String>();



	public static BuilderURL URLBuilder() {
		return new BuilderURL();
	}

	public BuilderURL() {
	}



	public String build() {
		if (IsEmpty(this.domain)
		&&  IsEmpty(this.path  )
		&&  IsEmpty(this.params))
			return "";
		final StringBuilder url = new StringBuilder();
		if (!IsEmpty(this.domain))
			url.append(this.is_tls ? "https://" : "http://").append(this.domain);
		url.append(ForceStarts('/', SafeString(this.path)));
		if (!IsEmpty(this.params)) {
			boolean first = true;
			for (final Entry<String, String> entry : this.params.entrySet()) {
				url.append(first ? '?' : '&');
				url.append(entry.getKey())
					.append('=').append(entry.getValue());
				first = false;
			}
		}
		return url.toString();
	}



	public BuilderURL http() {
		this.is_tls = false;
		return this;
	}
	public BuilderURL https() {
		this.is_tls = true;
		return this;
	}
	public boolean isTLS() {
		return this.is_tls;
	}



	public BuilderURL domain(final String domain) {
		this.domain = domain;
		return this;
	}
	public String getDomain() {
		return this.domain;
	}



	public BuilderURL path(final String path) {
		this.path = path;
		return this;
	}
	public String getPath() {
		return this.path;
	}



	public BuilderURL param(final String key, final Object value) {
		final String val = ToString(value);
		if (IsEmpty(key)) throw new RequiredArgumentException("key");
		if (IsEmpty(val)) throw new RequiredArgumentException("val");
		this.params.put(key, val);
		return this;
	}
	public String getParam(final String key) {
		return this.params.get(key);
	}

	public BuilderURL params(final Map<String, String> params) {
		for(final Entry<String, String> entry : params.entrySet())
			this.params.put(entry.getKey(), entry.getValue());
		return this;
	}
	public Map<String, String> getParamsMap() {
		return this.params;
	}



}
