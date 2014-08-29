package org.ehfg.app.program;

import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 04.04.2014
 */
public final class ConferenceDayDTO implements Comparable<ConferenceDayDTO> {
	private Long id;
	private LocalDate day;
	private String description;

	public ConferenceDayDTO() {

	}

	public ConferenceDayDTO(Long id, LocalDate day, String description) {
		this.id = id;
		this.day = day;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConferenceDayDTO other = (ConferenceDayDTO) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(ConferenceDayDTO that) {
		if (this.day.equals(that.day)) {
			return this.description.compareTo(that.description);
		}

		return this.day.compareTo(that.day);
	}
}
