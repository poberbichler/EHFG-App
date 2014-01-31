package org.ehfg.app.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.api.facade.ProgramFacade;


@Path("speaker")
public class SpeakerRestService {
	private final ProgramFacade programFacade;
	
	public SpeakerRestService(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpeakerDTO> findAllSpeakers() {
		return programFacade.findAllSpeakers();
	}
	
	@GET
	@Path("single/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SpeakerDTO findById(@PathParam("id") Long id) {
		return programFacade.findSpeakerById(id);
	}
}
