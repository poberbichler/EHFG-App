package org.ehfg.app.data;

import org.ehfg.app.program.ConferenceDayDTO;

import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
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
