package org.ehfg.app.program;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author patrick
 * @since 06.2015
 */
@Document
class ConferenceDay implements Comparable<ConferenceDay> {
	@Id
	private String id;

	@NotNull
	private String description;
	
	@NotNull
	private LocalDate date;

	public ConferenceDay() {

	}

	public ConferenceDay(String description, LocalDate date) {
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that);
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
