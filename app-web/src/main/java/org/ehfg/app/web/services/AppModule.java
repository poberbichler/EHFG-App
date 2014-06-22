package org.ehfg.app.web.services;

import java.util.Date;

import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.MetaDataConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.joda.time.LocalDate;

public class AppModule {
	public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration) {
		configuration.addInstance("bootstrap", BootstrapStack.class);
	}
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "gjkfg!98gdkljYX__jf909kjKLDG3kjdfADSF");
		configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "show");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "FALSE");
	}
	
	public void contributeMetaDataLocator(MappedConfiguration<String,String> configuration) {
	    configuration.add(MetaDataConstants.SECURE_PAGE, "true");
	}
	
	public static void contributeTypeCoercer(Configuration<CoercionTuple<?, ?>> configuration) {
        configuration.add(new CoercionTuple<>(Date.class, LocalDate.class, new Coercion<Date, LocalDate>() {
			@Override
			public LocalDate coerce(Date input) {
				if (input == null) {
					return LocalDate.now();
				}
				
				return LocalDate.fromDateFields(input);
			}
		}));
        
        configuration.add(new CoercionTuple<>(LocalDate.class, Date.class, new Coercion<LocalDate, Date>() {
			@Override
			public Date coerce(LocalDate input) {
				if (input == null) {
					return new Date(); 
				}
				
				return input.toDate();
			}
		}));
	}
	
	public static void contributeIgnoredPathsFilter(Configuration<String> configuration) {
		configuration.add("/rest/.*");
		configuration.add("/ws/.*");
	}
}
