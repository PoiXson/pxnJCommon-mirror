package com.poixson.tools;

import static com.poixson.tools.xURL.XURL;
import static com.poixson.utils.ArrayUtils.MatchMaps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


public class Test_xURL {



	@Test
	public void testXURL() {
		Assert.assertEquals("", XURL().build());
		// protocol
		Assert.assertEquals("example.com",          XURL().host("example.com").protocol(null   ).build());
		Assert.assertEquals("example.com",          XURL().host("example.com").protocol(""     ).build());
		Assert.assertEquals( "http://example.com/", XURL().host("example.com").protocol("http" ).build());
		Assert.assertEquals("https://example.com/", XURL().host("example.com").protocol("https").build());
		Assert.assertEquals( "http://example.com/", XURL().host("example.com").http()           .build());
		Assert.assertEquals("https://example.com/", XURL().host("example.com").https()          .build());
		// user
		Assert.assertEquals(        "example.com", XURL().host("example.com").user(null   ).build());
		Assert.assertEquals(        "example.com", XURL().host("example.com").user(""     ).build());
		Assert.assertEquals(  "admin@example.com", XURL().host("example.com").user("admin").build());
		// pass
		Assert.assertEquals(        "example.com", XURL().host("example.com").pass(null    ).build());
		Assert.assertEquals(        "example.com", XURL().host("example.com").pass(""      ).build());
		Assert.assertEquals(":abc123@example.com", XURL().host("example.com").pass("abc123").build());
		// host
		Assert.assertEquals("",                XURL().host(null             ).build());
		Assert.assertEquals("",                XURL().host(""               ).build());
		Assert.assertEquals(    "example.com", XURL().host(    "example.com").build());
		Assert.assertEquals("www.example.com", XURL().host("www.example.com").build());
		// port
		Assert.assertEquals("example.com",      XURL().host("example.com").port(Integer.MIN_VALUE).build());
		Assert.assertEquals("example.com",      XURL().host("example.com").port(  -1).build());
		Assert.assertEquals("example.com",      XURL().host("example.com").port(   0).build());
		Assert.assertEquals("example.com:1",    XURL().host("example.com").port(   1).build());
		Assert.assertEquals("example.com:2",    XURL().host("example.com").port(   2).build());
		Assert.assertEquals("example.com:11",   XURL().host("example.com").port(  11).build());
		Assert.assertEquals("example.com:1142", XURL().host("example.com").port(1142).build());
		Assert.assertEquals("example.com:9999", XURL().host("example.com").port(9999).build());
		// default port
		Assert.assertEquals("http://example.com/",     XURL().http() .host("example.com").port( 80).build());
		Assert.assertEquals("http://example.com:443/", XURL().http() .host("example.com").port(443).build());
		Assert.assertEquals("https://example.com:80/", XURL().https().host("example.com").port( 80).build());
		Assert.assertEquals("https://example.com/",    XURL().https().host("example.com").port(443).build());
		// path
		Assert.assertEquals("/abc",          XURL().path( "abc" )                            .build());
		Assert.assertEquals("/abc/",         XURL().path("/abc/")                            .build());
		Assert.assertEquals("/abc/def/ghi",  XURL().path( "abc" ).path( "def" ).path( "ghi" ).build());
		Assert.assertEquals("/abc/def/ghi/", XURL().path("/abc/").path("/def/").path("/ghi/").build());
		// params
		Assert.assertEquals("?abc=123", XURL().param("abc", 123).build());
		Assert.assertEquals("?abc=123&def=456&ghi=789",
			XURL().param("abc", 123).param("def", 456).param("ghi", 789).build());
		// everything
		Assert.assertEquals("https://admin:abc123@example.com:440/abc/def?num=123&letters=xyz",
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
			Assert.assertEquals("https",       url.getProtocol());
			Assert.assertEquals("admin",       url.getUser());
			Assert.assertEquals("abc123",      url.getPass());
			Assert.assertEquals("example.com", url.getHost());
			Assert.assertEquals(443,           url.getPort());
			Assert.assertEquals("/abc/def",    url.getPath());
			Assert.assertTrue(MatchMaps(params, url.getParamsMap()));
		}
	}



}
