package org.ehfg.app.data;

import java.util.List;

import org.ehfg.app.program.ConferenceDayDTO;

public class ConferenceDayForm {
	private List<ConferenceDayDTO> days;

	public ConferenceDayForm() {
		
	}

	public ConferenceDayForm(List<ConferenceDayDTO> days) {
		this.days = days;
	}
	
	public List<ConferenceDayDTO> getDays() {
		return days;
	}
	
	public void setDays(List<ConferenceDayDTO> days) {
		this.days = days;
	}
}
