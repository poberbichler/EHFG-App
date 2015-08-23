package org.ehfg.app.program.data.speakerevents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Channel {
	@XmlElement
	private String title;

	@XmlElement
	private String description;

	@XmlElement
	private String language;

	@XmlElement
	private String link;

	@XmlElement(name = "item")
	private List<SpeakerEvent> speakerEvents;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<SpeakerEvent> getSpeakerEvents() {
		return speakerEvents;
	}

	public void setSpeakerEvents(List<SpeakerEvent> speakerEvents) {
		this.speakerEvents = speakerEvents;
	}

}
