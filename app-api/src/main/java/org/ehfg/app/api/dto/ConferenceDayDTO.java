package org.ehfg.app.api.dto;

import java.util.Date;

/**
 * @author patrick
 * @since 04.04.2014
 */
public class ConferenceDayDTO {
	private Long id;
	private Date day;
	private String description;

	public ConferenceDayDTO() {

	}

	public ConferenceDayDTO(Long id, Date day, String description) {
		super();
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

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
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

	// @Override
	// public String toString() {
	// final StringBuilder builder = new StringBuilder();
	// builder.append("{");
	// builder.append("description: ").append(description).append(", timestamp: ").append(day.getTime());
	// builder.append("}");
	//
	// return builder.toString();
	// }
}
