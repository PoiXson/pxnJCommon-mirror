package com.poixson.tools;

import static com.poixson.utils.MathUtils.MAX_PORT;
import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.StringUtils.NullNorm;
import static com.poixson.utils.StringUtils.SafeString;
import static com.poixson.utils.StringUtils.ToLower;
import static com.poixson.utils.StringUtils.ToString;
import static com.poixson.utils.StringUtils.ceTrim;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.poixson.exceptions.RequiredArgumentException;


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
//TODO
		return this;
	}
/*
	public xURL setURI(final String uri) {
		String buf = uri;
		int pos;
		this.reset();
		// protocol://
		{
			pos = buf.indexOf("//");
			if (pos != -1) {
				this.protocol =
					ceTrim(
						buf.substring(0, pos),
						':'
					);
				buf = buf.substring(pos + 2);
			}
		}
		// user:pass
		{
			pos = buf.indexOf('@');
			if (pos != -1) {
				final String str = buf.substring(0, pos);
				buf = buf.substring(pos + 1);
				pos = str.indexOf(':');
				if (pos == -1) {
					this.user = str;
				} else {
					this.user = str.substring(0, pos);
					this.pass = str.substring(pos + 1);
				}
			}
		}
		// host:port
		{
			pos = buf.indexOf('/');
			if (pos != -1) {
				final String str = buf.substring(0, pos);
				buf = buf.substring(pos);
				pos = str.indexOf(':');
				if (pos != -1) {
					this.host = str.substring(0, pos);
					final Integer i =
						CastInteger(
							str.substring(pos + 1)
						);
					this.port = (
						i == null
						? -1
						: i.intValue()
					);
				}
			}
		}
		// path
		{
			if (!IsEmpty(buf))
				this.path = buf;
		}
		return this;
	}
*/



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
