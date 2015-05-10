package org.ehfg.app.program.data.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.program.adapter.LocalDateAdapter;
import org.ehfg.app.program.adapter.LocalTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Event {
	@XmlElement
	private String id;

	@XmlElement
	private String event;

	@XmlElement
	private String details;

	@XmlElement
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate day;

	@XmlElement
	@XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
	private LocalTime start;

	@XmlElement
	@XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
	private LocalTime end;

	@XmlElement
	private String room;

	@XmlElement
	private String categoryid;

	@XmlElement
	private String category;

	@XmlElement
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
