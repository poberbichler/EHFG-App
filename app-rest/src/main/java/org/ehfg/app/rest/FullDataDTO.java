package org.ehfg.app.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("completedata")
public class FullDataDTO {
	@Produces(Type.JSONP)
	public JSONWithPadding completeData(@QueryParam("callback") String callBack) {
		FullDataDTO result = new FullDataDTO();
		return new JSONWithPadding(result, callBack);
	}
}
