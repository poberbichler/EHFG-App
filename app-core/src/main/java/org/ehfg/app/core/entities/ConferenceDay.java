package org.ehfg.app.core.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 04.04.2014
 */
@Entity
public class ConferenceDay implements Comparable<ConferenceDay> {
	@Id
	@GeneratedValue
	private Long id;

	private String description;
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(ConferenceDay that) {
		if (this.date.equals(that.date)) {
			return this.description.compareTo(that.description);
		}
		
		return this.date.compareTo(that.date);
	}
}
