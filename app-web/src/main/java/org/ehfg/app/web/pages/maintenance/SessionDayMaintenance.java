package org.ehfg.app.web.pages.maintenance;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Loop;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 04.04.2014
 */
public class SessionDayMaintenance {
	@Component
	private BootstrapLayout layout;

	@Component
	private Form inputForm;

	@Component
	private Submit saveDays;

	@Component(parameters = { "zone=dayZone", "context=dayList" })
	private ActionLink addDay;

	@Component(parameters = { "zone=dayZone", "context=currentDay.id" })
	private ActionLink removeDay;

	@Property
	@Component(parameters = { "source=dayList", "value=currentDay"})
	private Loop<ConferenceDayDTO> dayLoop;

	@Component(parameters = { "value=currentDay.description" })
	private TextField description;

	@Component(parameters = { "value=currentDay.day" })
	private DateField day;

	@Component
	private Zone dayZone;

	@Inject
	private ProgramFacade programFacade;
	
	@Property
	private ConferenceDayDTO currentDay;
	
	@Property
	private List<ConferenceDayDTO> dayList;
	
	void onPrepare() {
		dayList = programFacade.findDays();
	}
	
	@OnEvent(component = "removeDay", value = EventConstants.ACTION)
	Object onActionFromRemoveDay(Long dayId) {
		programFacade.removeDay(dayId);
		return dayZone.getBody();
	}
	
	@OnEvent(component = "addDay", value = EventConstants.ACTION)
	Object onActionFromAddDay() {
		dayList.add(programFacade.addDay());
		return dayZone.getBody();
	}
}
