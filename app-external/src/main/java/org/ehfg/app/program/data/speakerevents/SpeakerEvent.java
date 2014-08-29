package org.ehfg.app.program.data.speakerevents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class SpeakerEvent {
	@XmlElement
	private String speakerid;

	@XmlElement
	private String lastname;

	@XmlElement
	private String firstname;

	@XmlElement
	private String eventid;

	@XmlElement
	private String event;

	public String getSpeakerid() {
		return speakerid;
	}

	public void setSpeakerid(String speakerid) {
		this.speakerid = speakerid;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}
