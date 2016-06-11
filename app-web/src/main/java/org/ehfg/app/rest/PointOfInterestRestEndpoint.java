package org.ehfg.app.rest;

import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author patrick
 * @since 04.2014
 */
@RestController
@RequestMapping("rest/points")
public final class PointOfInterestRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public PointOfInterestRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends PointOfInterestRepresentation> findAllPoints() {
		return masterDataFacade.findAllPointsOfInterest();
	}

}
