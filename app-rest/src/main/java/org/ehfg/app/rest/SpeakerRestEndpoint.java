package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

@Component
@Path("speaker")
public class SpeakerRestEndpoint {
	private final ProgramFacade programFacade;

	@Autowired
	public SpeakerRestEndpoint(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@GET
	@Path("all")
	@Produces(Type.JSONP)
	public JSONWithPadding findAllSpeakers(@QueryParam("callback") String callback) {
		return new JSONWithPadding(programFacade.findAllSpeakers(), callback);
	}

	@GET
	@Path("single/{id}")
	@Produces(Type.JSONP)
	public SpeakerDTO findById(@PathParam("id") Long id) {
		return programFacade.findSpeakerById(id);
	}
}
