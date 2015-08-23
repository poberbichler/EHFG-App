package org.ehfg.app.program;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * @author patrick
 * @since 04.2014
 */
public final class ConferenceDayDTO implements Comparable<ConferenceDayDTO> {
	private String id;
	private LocalDate day;
	private String description;
	private boolean deleted;

	public ConferenceDayDTO() {

	}

	public ConferenceDayDTO(String id, LocalDate day, String description) {
		this.id = id;
		this.day = day;
		this.description = description;
		deleted = false;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int compareTo(ConferenceDayDTO that) {
		if (this.day.equals(that.day)) {
			return this.description.compareTo(that.description);
		}

		return this.day.compareTo(that.day);
	}

	@Override
	public boolean equals(Object thatObject) {
		if (this == thatObject) {
			return true;
		}

		if (!(thatObject instanceof ConferenceDayDTO)) {
			return false;
		}

		ConferenceDayDTO that = (ConferenceDayDTO) thatObject;
		return new EqualsBuilder().append(this.id, that.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
