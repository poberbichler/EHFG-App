package org.ehfg.app.rest;

import org.ehfg.app.program.ProgramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author patrick
 * @since 04.2014
 */
@RestController
@RequestMapping("rest/sessions")
public final class SessionRestEndpoint {
	private final ProgramFacade programFacade;

	@Autowired
	public SessionRestEndpoint(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<LocalDate, ? extends ConferenceDayRepresentation> findAllSessions() {
		return programFacade.findAllSessions();
	}
}
