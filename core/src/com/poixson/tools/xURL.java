package com.poixson.tools;

import static com.poixson.utils.MathUtils.MAX_PORT;
import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.StringUtils.NullNorm;
import static com.poixson.utils.StringUtils.SafeString;
import static com.poixson.utils.StringUtils.ToLower;
import static com.poixson.utils.StringUtils.ToString;
import static com.poixson.utils.StringUtils.ceTrim;
import static com.poixson.utils.StringUtils.cfTrim;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.MathUtils;


// protocol:[//[user[:password]@]host[:port]][/path][?key=val[&key=val]..]
public class xURL {

	public String prot = null;
	public String user = null;
	public String pass = null;
	public String host = null;
	public int    port = -1;
	public String path = null;
	public Map<String, String> params = new HashMap<String, String>();



	public static xURL XURL() {
		return new xURL();
	}
	public static xURL XURL(final String url) {
		return XURL().set(url);
	}

	public xURL() {
	}

	@Override
	public xURL clone() {
		return XURL().set(this);
	}



	public xURL reset() {
		this.prot = null;
		this.user = null;
		this.pass = null;
		this.host = null;
		this.port = -1;
		this.path = null;
		this.params.clear();
		return this;
	}



	public String build() {
		if (IsEmpty(this.host)
		&&  IsEmpty(this.path)
		&&  IsEmpty(this.params))
			return "";
		final StringBuilder url = new StringBuilder();
		// host
		if (!IsEmpty(this.host)) {
			if (!IsEmpty(this.prot))
				url.append(this.prot).append("://");
			if (!IsEmpty(this.user) || !IsEmpty(this.pass)) {
				if (!IsEmpty(this.user)) url.append(this.user);
				if (!IsEmpty(this.pass)) url.append(':').append(this.pass);
				url.append('@');
			}
			url.append(this.host);
			if (this.port > 0) {
				boolean is_default = false;
				if (!IsEmpty(this.prot)) {
					SWITCH_PROT:
					switch (this.prot) {
					case "http":  if (this.port ==  80) is_default = true; break SWITCH_PROT;
					case "https": if (this.port == 443) is_default = true; break SWITCH_PROT;
					default: break SWITCH_PROT;
					}
				}
				if (!is_default)
					url.append(':').append(this.port);
			}
		}
		// path
		if (!IsEmpty(this.path)
		|| (!IsEmpty(this.prot) && !IsEmpty(this.host)))
			url.append( ForceStarts('/', SafeString(this.path)) );
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
	@Override
	public String toString() {
		return this.build();
	}



	// -------------------------------------------------------------------------------
	// set url



	public xURL set(final xURL url) {
		this.prot = url.prot;
		this.user = url.user;
		this.pass = url.pass;
		this.host = url.host;
		this.port = url.port;
		this.path = url.path;
		this.params.clear();
		for (final Entry<String, String> entry : url.params.entrySet())
			this.params.put(entry.getKey(), entry.getValue());
		return this;
	}



	public xURL set(final String url) {
		if (IsEmpty(url)) return null;
		String str = url;
		// protocol
		if (str.contains("://")) {
			final String[] parts = str.split("\\:\\/\\/", 2);
			str = parts[1];
			this.protocol(parts[0]);
		}
		// user:pass
		if (str.contains("@")) {
			final String[] parts = str.split("\\@", 2);
			str = parts[1];
			if (parts[0].contains(":")) {
				final String[] pts = parts[0].split("\\:", 2);
				this.user(pts[0]);
				this.pass(pts[1]);
			} else {
				this.user(parts[0]);
			}
		}
		// host:port
		if (str.contains("/")) {
			final String[] parts = str.split("\\/", 2);
			str = parts[1];
			if (parts[0].contains(":")) {
				final String[] pts = parts[0].split("\\:");
				this.host(pts[0]);
				this.port(MathUtils.ToInteger(pts[1], -1));
			} else {
				this.host(parts[0]);
			}
		}
		// path?params
		if (str.contains("?")) {
			final String[] parts = str.split("\\?");
			this.path = ""; this.path(parts[0]);
			this.setParams(parts[1]);
		} else {
			this.path = ""; this.path(str);
		}
		return this;
	}



	// -------------------------------------------------------------------------------
	// set parameters



	// protocol
	public xURL protocol(final String protocol) {
		this.prot = ToLower(NullNorm(protocol));
		return this;
	}
	public String getProtocol() {
		return this.prot;
	}
	public boolean hasProtocol() {
		return !IsEmpty(this.prot);
	}

	public xURL http() {
		return this.protocol("http");
	}
	public xURL https() {
		return this.protocol("https");
	}

	public boolean isHTTP() {
		return "http".equals(this.prot);
	}
	public boolean isHTTPS() {
		return "https".equals(this.prot);
	}



	// user
	public xURL user(final String user) {
		this.user = NullNorm(user);
		return this;
	}
	public String getUser() {
		return this.user;
	}
	public boolean hasUser() {
		return !IsEmpty(this.user);
	}



	// pass
	public xURL pass(final String pass) {
		this.pass = NullNorm(pass);
		return this;
	}
	public String getPass() {
		return this.pass;
	}
	public boolean hasPass() {
		return !IsEmpty(this.pass);
	}



	// host
	public xURL host(final String host) {
		this.host = NullNorm(host);
		return this;
	}
	public String getHost() {
		return this.host;
	}
	public boolean hasHost() {
		return !IsEmpty(this.host);
	}



	// port
	public xURL port(final int port) {
		if (port < 1       ) this.port = -1; else
		if (port > MAX_PORT) throw new IllegalArgumentException("Invalid port number: "+Integer.toString(port));
		else                 this.port = port;
		return this;
	}
	public int getPort() {
		{
			final int port = this.port;
			if (port > 0) return port;
		}
		{
			final String protocol = this.getProtocol();
			if (!IsEmpty(protocol)) {
				SWITCH_PROTOCOL:
				switch (protocol) {
				case "http":  this.port( 80); break SWITCH_PROTOCOL;
				case "https": this.port(443); break SWITCH_PROTOCOL;
				default: break SWITCH_PROTOCOL;
				}
			}
		}
		return this.port;
	}
	public boolean hasPort() {
		return (this.port > 0);
	}



	// path
	public xURL path(final String path) {
		if (!IsEmpty(path))
			this.path = ceTrim(SafeString(this.path), '/') + ForceStarts('/', path);
		return this;
	}
	public String getPath() {
		return this.path;
	}
	public boolean hasPath() {
		return !IsEmpty(this.path);
	}



	// params
	public xURL param(final String key, final String val) {
		if (IsEmpty(key)) throw new RequiredArgumentException("key");
		if (IsEmpty(val)) throw new RequiredArgumentException("val");
		this.params.put(key, val);
		return this;
	}
	public xURL param(final String key, final Object val) {
		return this.param(key, ToString(val));
	}
	public String getParam(final String key) {
		return this.params.get(key);
	}
	public xURL setParams(final String params) {
		final String[] parts = cfTrim(params, '?').split("\\&");
		for (final String part : parts) {
			final String [] pts = part.split("\\=", 2);
			this.param(pts[0], pts[1]);
		}
		return this;
	}
	public xURL setParams(final Map<String, String> params) {
		this.params = params;
		return this;
	}
	public Map<String, String> getParamsMap() {
		return this.params;
	}
	public boolean hasParams() {
		return !IsEmpty(this.params);
	}



}
