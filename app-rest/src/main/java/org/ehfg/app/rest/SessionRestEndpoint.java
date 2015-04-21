package org.ehfg.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public Map<ConferenceDayRepresentation, List<? extends SessionRepresentation>> findAllSessions() {
		Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions = programFacade.findAllSessions();
		
		Map<ConferenceDayRepresentation, List<? extends SessionRepresentation>> result = new HashMap<>();
		for (Entry<ConferenceDayDTO, List<SessionDTO>> entry : findAllSessions.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
}
