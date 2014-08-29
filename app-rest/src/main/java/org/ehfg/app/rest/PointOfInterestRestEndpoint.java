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
 * @since 12.04.2014
 */
@Component
@Path("points")
public final class PointOfInterestRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public PointOfInterestRestEndpoint(MasterDataFacade masterDataFacade) {
		super();
		this.masterDataFacade = masterDataFacade;
	}
	
	@GET
	@Path("all")
	@Produces(Type.JSONP)
	public JSONWithPadding findAllPoints(@QueryParam("callback") String callback) {
		return new JSONWithPadding(masterDataFacade.findAllPointsOfInterest(), callback);
	}

}
