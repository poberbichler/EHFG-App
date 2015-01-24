package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * @author patrick
 * @since 01.2015
 */
@Component
@Path("backdoor")
public final class BackdoorRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public BackdoorRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}
	
	@GET
	@Produces(Type.JSONP)
	public JSONWithPadding getBackdoor(@QueryParam("callback") String callback) {
		return new JSONWithPadding(masterDataFacade.getAppConfiguration().getBackdoorScript(), callback);
	}

}
