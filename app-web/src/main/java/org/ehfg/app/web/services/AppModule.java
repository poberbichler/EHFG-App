package org.ehfg.app.web.services;

import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.javascript.JavaScriptStack;

public class AppModule {
	public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration) {
		configuration.addInstance("bootstrap", BootstrapStack.class);
	}
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "gjkfg!98gdkljYX__jf909kjKLDG3kjdfADSF");
		configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "show");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "FALSE");
	}
	
	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
		configuration.add("/rest/.*");
		configuration.add("/ws/.*");
	}
}
