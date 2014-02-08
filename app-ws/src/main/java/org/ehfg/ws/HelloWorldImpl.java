package org.ehfg.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "org.ehfg.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
	private String blup = "blup";

	@Override
	public String getHelloWorldAsString(String name) {
		return String.format("Hello %s!", name == null ? blup : name);
	}

	public String getBlup() {
		return blup;
	}

	public void setBlup(String blup) {
		this.blup = blup;
	}
}
