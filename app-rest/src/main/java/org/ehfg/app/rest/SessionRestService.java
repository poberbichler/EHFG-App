package org.ehfg.app.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.facade.ProgramFacade;

@Path("session")
public class SessionRestService {
	private final ProgramFacade programFacade;

	public SessionRestService(ProgramFacade programFacade) {
		super();
		this.programFacade = programFacade;
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SessionDTO> findAllSessions() {
		return programFacade.findAllSessions();
	}

	@GET
	@Path("single/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SessionDTO findSessionById(@PathParam("id") Long sessionId) {
		return programFacade.findSessionById(sessionId);
	}

	@GET
	@Path("day/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SessionDTO> findSessionByDay(@PathParam("day") Long day) {
		return programFacade.findSessionByDay();
	}

	@GET
	@Path("speaker/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SessionDTO> findSessionBySpeaker(@PathParam("id") Long speakerId) {
		return programFacade.findSessionsBySpeaker(speakerId);
	}
}
