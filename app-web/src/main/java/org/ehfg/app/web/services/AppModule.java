package org.ehfg.app.web.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.javascript.JavaScriptStack;

public class AppModule {
	public static void contributeJavaScriptStackSource(
			MappedConfiguration<String, JavaScriptStack> configuration) {
		configuration.addInstance("bootstrap", BootstrapStack.class);
	}
	
	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
		configuration.add("/rest/.*");
	}
}
