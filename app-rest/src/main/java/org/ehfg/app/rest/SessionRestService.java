package org.ehfg.app.rest;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.facade.ProgramFacade;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("session")
public class SessionRestService {
	private final ProgramFacade programFacade;

	public SessionRestService(ProgramFacade programFacade) {
		super();
		this.programFacade = programFacade;
	}

	@GET
	@Path("all")
	@Produces(Type.JSONP)
	public JSONWithPadding test(@QueryParam("callback") String callback) throws JSONException {
		final JSONArray result = new JSONArray();
		for (final Entry<ConferenceDayDTO, List<SessionDTO>> entry : programFacade.findAllSessions().entrySet()) {
			final ConferenceDayDTO day = entry.getKey();
			
			final JSONObject jsonDay = new JSONObject();
			jsonDay.put("description", day.getDescription());
			jsonDay.put("timestamp", day.getDay().getTime());
			
			final JSONArray sessions = new JSONArray();
			for (final SessionDTO session : entry.getValue()) {
				final JSONObject jsonSession = new JSONObject();
				jsonSession.put("id", session.getId());
				jsonSession.put("description", session.getDescription());
				jsonSession.put("start", session.getStartTime().getTime());
				jsonSession.put("end", session.getEndTime().getTime());
				jsonSession.put("name", session.getName());
				jsonSession.put("location", session.getLocationId());
				jsonSession.put("speakers", session.getSpeakers());
				
				sessions.put(jsonSession);
			}
			
			jsonDay.put("sessions", sessions);
			result.put(jsonDay);
		}
		
		return new JSONWithPadding(result, callback);
	}

	@GET
	@Path("single/{id}")
	@Produces(Type.JSONP)
	public SessionDTO findSessionById(@PathParam("id") Long sessionId) {
		return programFacade.findSessionById(sessionId);
	}

	@GET
	@Path("day/{day}")
	@Produces(Type.JSONP)
	public List<SessionDTO> findSessionByDay(@PathParam("day") Long day) {
		return programFacade.findSessionByDay();
	}

	@GET
	@Path("speaker/{id}")
	@Produces(Type.JSONP)
	public List<SessionDTO> findSessionBySpeaker(@PathParam("id") Long speakerId) {
		return programFacade.findSessionsBySpeaker(speakerId);
	}
}
