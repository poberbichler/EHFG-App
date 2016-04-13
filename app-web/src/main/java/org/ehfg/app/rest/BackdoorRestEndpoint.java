package org.ehfg.app.rest;

import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick
 * @since 01.2015
 */
@RestController
@RequestMapping("rest/backdoor")
public final class BackdoorRestEndpoint {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public BackdoorRestEndpoint(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String getBackdoor() {
		return masterDataFacade.getAppConfiguration().getBackdoorScript();
	}

}
