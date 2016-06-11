package org.ehfg.app.rest;

import org.ehfg.app.program.ProgramFacade;
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
@RequestMapping("rest/speakers")
public final class SpeakerRestEndpoint {
	private final ProgramFacade programFacade;

	@Autowired
	public SpeakerRestEndpoint(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends SpeakerRepresentation> findAllSpeakers() {
		return programFacade.findSpeakersWithSession();
	}
}
