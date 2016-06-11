package org.ehfg.app.rest;

import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author patrick
 * @since 07.2014
 */
@RestController
@RequestMapping("rest/locations")
public final class LocationRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public LocationRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends LocationRepresentation> findAll() {
		return masterDataFacade.findAllLocation();
	}
	
	@RequestMapping(value = "{locationName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public LocationRepresentation findByName(@PathVariable("locationName") String locationName) {
		for (final LocationDTO location : masterDataFacade.findAllLocation()) {
			if (location.getName().equalsIgnoreCase(locationName)) {
				return location;
			}
		}
		
		return null;
	}
}
