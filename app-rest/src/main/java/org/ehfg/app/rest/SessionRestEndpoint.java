package org.ehfg.app.rest;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * @author patrick
 * @since 12.04.2014
 */
@Component
@Path("session")
public final class SessionRestEndpoint {
	private final ProgramFacade programFacade;

	@Autowired
	public SessionRestEndpoint(ProgramFacade programFacade) {
		super();
		this.programFacade = programFacade;
	}

	@GET
	@Path("all")
	@Produces(Type.JSONP)
	public JSONWithPadding findAllSessions(@QueryParam("callback") String callback) throws JSONException {
		final JSONArray result = new JSONArray();
		for (final Entry<ConferenceDayDTO, List<SessionDTO>> entry : programFacade.findAllSessions().entrySet()) {
			final ConferenceDayDTO day = entry.getKey();

			final JSONObject jsonDay = new JSONObject();
			jsonDay.put("description", day.getDescription());
			jsonDay.put("timestamp", day.getDay().toDate().getTime());

			final JSONArray sessions = new JSONArray();
			for (final SessionDTO session : entry.getValue()) {
				final JSONObject jsonSession = new JSONObject();
				jsonSession.put("id", session.getId());
				jsonSession.put("description", session.getDescription());
				jsonSession.put("start", session.getStartTime().getMillis());
				jsonSession.put("end", session.getEndTime().getMillis());
				jsonSession.put("name", session.getName());
				jsonSession.put("location", session.getLocationId());
				jsonSession.put("speakers", session.getSpeakers());
				jsonSession.put("code", session.getSessionCode());

				sessions.put(jsonSession);
			}

			jsonDay.put("sessions", sessions);
			result.put(jsonDay);
		}

		return new JSONWithPadding(result, callback);
	}
}
