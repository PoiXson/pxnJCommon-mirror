/*
package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.ArrayUtils.MatchMaps;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(Assertions.class)
public class Test_xURL {



	@Test
	public void testXURL() {
		AssertEquals("", XURL().build());
		// protocol
		AssertEquals("example.com",          XURL().host("example.com").protocol(null   ).build());
		AssertEquals("example.com",          XURL().host("example.com").protocol(""     ).build());
		AssertEquals( "http://example.com/", XURL().host("example.com").protocol("http" ).build());
		AssertEquals("https://example.com/", XURL().host("example.com").protocol("https").build());
		AssertEquals( "http://example.com/", XURL().host("example.com").http()           .build());
		AssertEquals("https://example.com/", XURL().host("example.com").https()          .build());
		// user
		AssertEquals(        "example.com", XURL().host("example.com").user(null   ).build());
		AssertEquals(        "example.com", XURL().host("example.com").user(""     ).build());
		AssertEquals(  "admin@example.com", XURL().host("example.com").user("admin").build());
		// pass
		AssertEquals(        "example.com", XURL().host("example.com").pass(null    ).build());
		AssertEquals(        "example.com", XURL().host("example.com").pass(""      ).build());
		AssertEquals(":abc123@example.com", XURL().host("example.com").pass("abc123").build());
		// host
		AssertEquals("",                XURL().host(null             ).build());
		AssertEquals("",                XURL().host(""               ).build());
		AssertEquals(    "example.com", XURL().host(    "example.com").build());
		AssertEquals("www.example.com", XURL().host("www.example.com").build());
		// port
		AssertEquals("example.com",      XURL().host("example.com").port(Integer.MIN_VALUE).build());
		AssertEquals("example.com",      XURL().host("example.com").port(  -1).build());
		AssertEquals("example.com",      XURL().host("example.com").port(   0).build());
		AssertEquals("example.com:1",    XURL().host("example.com").port(   1).build());
		AssertEquals("example.com:2",    XURL().host("example.com").port(   2).build());
		AssertEquals("example.com:11",   XURL().host("example.com").port(  11).build());
		AssertEquals("example.com:1142", XURL().host("example.com").port(1142).build());
		AssertEquals("example.com:9999", XURL().host("example.com").port(9999).build());
		// default port
		AssertEquals("http://example.com/",     XURL().http() .host("example.com").port( 80).build());
		AssertEquals("http://example.com:443/", XURL().http() .host("example.com").port(443).build());
		AssertEquals("https://example.com:80/", XURL().https().host("example.com").port( 80).build());
		AssertEquals("https://example.com/",    XURL().https().host("example.com").port(443).build());
		// path
		AssertEquals("/abc",          XURL().path( "abc" )                            .build());
		AssertEquals("/abc/",         XURL().path("/abc/")                            .build());
		AssertEquals("/abc/def/ghi",  XURL().path( "abc" ).path( "def" ).path( "ghi" ).build());
		AssertEquals("/abc/def/ghi/", XURL().path("/abc/").path("/def/").path("/ghi/").build());
		// params
		AssertEquals("?abc=123", XURL().param("abc", 123).build());
		AssertEquals("?abc=123&def=456&ghi=789",
			XURL().param("abc", 123).param("def", 456).param("ghi", 789).build());
		// everything
		AssertEquals("https://admin:abc123@example.com:440/abc/def?num=123&letters=xyz",
			XURL()
				.https().host("example.com")
				.user("admin").pass("abc123")
				.port(440)
				.path("abc").path("def")
				.param("num",     "123")
				.param("letters", "xyz")
				.build()
		);
		{
			final Map<String, String> params = new HashMap<String, String>();
			params.put("num",     "123");
			params.put("letters", "abc");
			final xURL url = XURL("https://admin:abc123@example.com/abc/def?num=123&letters=abc");
			AssertEquals("https",       url.getProtocol());
			AssertEquals("admin",       url.getUser());
			AssertEquals("abc123",      url.getPass());
			AssertEquals("example.com", url.getHost());
			AssertEquals(443,           url.getPort());
			AssertEquals("/abc/def",    url.getPath());
			AssertTrue(MatchMaps(params, url.getParamsMap()));
		}
	}



}
*/
