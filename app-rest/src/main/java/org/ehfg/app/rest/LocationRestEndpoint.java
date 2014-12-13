package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * @author patrick
 * @since 19.07.2014
 */
@Component
@Path("location")
public final class LocationRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public LocationRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@GET
	@Path("all")
	@Produces(Type.JSONP)
	public JSONWithPadding findAll(@QueryParam("callback") String callback) {
		return new JSONWithPadding(masterDataFacade.findAllLocation(), callback);
	}
	
	@GET
	@Path("name/{locationName}")
	@Produces(Type.JSONP)
	public JSONWithPadding findByName(@PathParam("locationName") String locationName, @QueryParam("callback") String callback) {
		LocationDTO result = null;
		for (final LocationDTO location : masterDataFacade.findAllLocation()) {
			if (location.getName().equalsIgnoreCase(locationName)) {
				result = location;
				break;
			}
		}
		return new JSONWithPadding(result, callback);
	}
}
