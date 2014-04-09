package org.ehfg.app.rest.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.api.dto.SessionDTO;

/**
 * @author patrick
 * @since 06.04.2014
 */
@XmlRootElement
public class SessionWrapper {
//	public final JSONObject blup;
//
//	public SessionWrapper(Map<ConferenceDayDTO, List<SessionDTO>> data) throws JSONException {
//		blup = new JSONObject();
//		
//		for (final Entry<ConferenceDayDTO, List<SessionDTO>> entry : data.entrySet()) {
//			JSONArray sessionList = new JSONArray();
//			
//			for (final SessionDTO session : entry.getValue()) {
//				sessionList.put(session);
//			}
//			
//			blup.put(entry.getKey().getDescription(), sessionList);
//		}
//	}
	public final Map<ConferenceDayDTO, List<SessionDTO>> data;
	
	public SessionWrapper(Map<ConferenceDayDTO, List<SessionDTO>> data) {
		this.data = data;
	}
}
