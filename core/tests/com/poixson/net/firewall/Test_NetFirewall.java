package com.poixson.commonapp.net.firewall;

import java.net.InetSocketAddress;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.commonapp.net.firewall.rules.ruleHostname;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.xLogger.xLogTest;


public class NetFirewallTest {
	static final String TEST_NAME_HOSTNAME = "NetFirewall Hostname";
	static final String TEST_NAME_IPLIST   = "NetFirewall IP List";
	static final String TEST_NAME_IPRANGE  = "NetFirewall IP Range";

	static final String LOCAL_HOST      = "local.hostname";
	static final String REMOTE_HOST     = "remote.hostname";
	static final String REMOTE_HOST_BAD = "bad.host";
	static final int    LOCAL_PORT  = 1111;
	static final int    REMOTE_PORT = 2222;

	static final InetSocketAddress LOCAL      = new InetSocketAddress(LOCAL_HOST,      LOCAL_PORT);
	static final InetSocketAddress REMOTE     = new InetSocketAddress(REMOTE_HOST,     REMOTE_PORT);
	static final InetSocketAddress REMOTE_BAD = new InetSocketAddress(REMOTE_HOST_BAD, REMOTE_PORT);



	// hostname rules
	@Test
	public void testRuleHostname() {
		xLogTest.testStart(TEST_NAME_HOSTNAME);
		try {
			// expect success
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*",           LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*hostname",   LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*.hostname",  LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "local.host*", LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "local.*",     LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*:1111",      LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*:1110-1112", LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*:*-1112",    LOCAL, REMOTE);
			this.checkHost(Boolean.TRUE, RuleType.ALLOW_LOCAL, "*:1110-*",    LOCAL, REMOTE);
			// expect fail
			this.checkHost(null, RuleType.ALLOW_LOCAL, null,       LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "invalid*", LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*invalid", LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*:2222",   LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*:0-1110", LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*:*-1110", LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*:1112-"+Integer.toString(utilsNumbers.MAX_PORT), LOCAL, REMOTE);
			this.checkHost(null, RuleType.ALLOW_LOCAL, "*:1112-*", LOCAL, REMOTE);
			// deny host
			this.checkHost(Boolean.FALSE, RuleType.DENY_REMOTE, "bad.*", LOCAL, REMOTE_BAD);
		} catch (Exception e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		} catch (Error e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		}
		xLogTest.testPassed(TEST_NAME_HOSTNAME);
	}
	void checkHost(final Boolean expected, final RuleType type, final String pattern,
			final InetSocketAddress local, final InetSocketAddress remote) {
		final NetFirewall firewall = new NetFirewall();
		firewall.addRule(new ruleHostname(type, pattern));
		final Boolean result = firewall.check(local, remote);
		Assert.assertEquals("Pattern didn't return expected result: "+pattern, expected, result);
	}



	// ip list
	@Test
	public void testRuleIPList() {
		xLogTest.testStart(TEST_NAME_IPLIST);
		try {


		} catch (Exception e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		} catch (Error e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		}
		xLogTest.testPassed(TEST_NAME_IPLIST);
	}



	// ip range
	@Test
	public void testRuleIPRange() {
		xLogTest.testStart(TEST_NAME_IPRANGE);
		try {


		} catch (Exception e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		} catch (Error e) {
			xLogTest.trace(e);
			throw new RuntimeException(e);
		}
		xLogTest.testPassed(TEST_NAME_IPRANGE);
	}



}
