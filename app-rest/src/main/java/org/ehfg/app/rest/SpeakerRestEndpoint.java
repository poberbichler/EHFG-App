package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.program.ProgramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * @author patrick
 * @since 12.04.2014
 */
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
}
