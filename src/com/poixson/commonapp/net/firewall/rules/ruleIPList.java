package com.poixson.commonapp.net.firewall.rules;

import com.poixson.commonapp.net.firewall.NetFirewallRule;
import com.poixson.commonapp.net.firewall.RuleType;


public class ruleIPList extends NetFirewallRule {



	public ruleIPList(final RuleType type) {
		super(type);
	}



	@Override
	public Boolean check(
			final String localHost,  final int localPort,
			final String remoteHost, final int remotePort) {

		return Boolean.FALSE;
	}



	@Override
	public String toString() {
		return null;
	}



}
