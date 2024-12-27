package com.poixson.tools;

import static com.poixson.tools.BuilderURL.URLBuilder;

import org.junit.Assert;
import org.junit.Test;


public class Test_URLBuilder {



	@Test
	public void testURLBuilder() {
		Assert.assertEquals("",                            URLBuilder()                                        .build());
		Assert.assertEquals("https://example.com/",        URLBuilder().domain("example.com")                  .build());
		Assert.assertEquals("https://example.com/",        URLBuilder().domain("example.com").https()          .build());
		Assert.assertEquals( "http://example.com/",        URLBuilder().domain("example.com").http()           .build());
		Assert.assertEquals("/abc/def",                    URLBuilder().path(                      "abc/def" ) .build());
		Assert.assertEquals("/abc/def/",                   URLBuilder().path(                     "/abc/def/") .build());
		Assert.assertEquals("https://example.com/abc/def", URLBuilder().domain("example.com").path("abc/def" ) .build());
		Assert.assertEquals("/?abc=def",                   URLBuilder().param("abc", "def")                    .build());
		Assert.assertEquals("/?abc=123&def=456",           URLBuilder().param("abc", "123").param("def", "456").build());
		Assert.assertEquals("https://example.com/?abc=123&def=456",
			URLBuilder().domain("example.com").https()
			.param("abc", "123")
			.param("def", "456")
			.build());
	}



}
