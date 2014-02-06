package org.ehfg.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "org.ehfg.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String getHelloWorldAsString(String name) {
		return String.format("Hello %s!", name == null ? "" : name);
	}

}
