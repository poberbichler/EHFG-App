package org.ehfg.app.web.pages.maintenance;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.AddRowLink;
import org.apache.tapestry5.corelib.components.AjaxFormLoop;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.RemoveRowLink;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.web.components.BootstrapLayout;
import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 04.04.2014
 */
public final class SessionDayMaintenance {
	@Component
	private BootstrapLayout layout;

	@Component
	private Form inputForm;

	@Component
	private Submit saveDays;

	@Component(parameters = { "source=dayList", "value=currentDay", "encoder=dayEncoder", "show=show" })
	private AjaxFormLoop dayLoop;

	@Component
	private AddRowLink addDay;

	@Component
	private RemoveRowLink removeDay;

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

	@Persist
	@Property
	private List<ConferenceDayDTO> dayList;

	@Property
	private ValueEncoder<ConferenceDayDTO> dayEncoder = new ValueEncoder<ConferenceDayDTO>() {
		@Override
		public String toClient(ConferenceDayDTO value) {
			return value.getId().toString();
		}

		@Override
		public ConferenceDayDTO toValue(String clientValue) {
			for (final ConferenceDayDTO day : dayList) {
				if (day.getId().toString().equals(clientValue)) {
					return day;
				}
			}

			return null;
		}
	};

	void beginRender() {
		dayList = programFacade.findDays();
	}

	public ConferenceDayDTO onAddRowFromDayLoop() {
		final ConferenceDayDTO day = new ConferenceDayDTO(new Date().getTime(), LocalDate.now(), "description");
		dayList.add(day);
		return day;
	}

	public void onRemoveRowFromDayLoop(ConferenceDayDTO day) {
		dayList.remove(day);
	}

	void onSuccessFromInputForm() {
		programFacade.saveDays(dayList);
	}
}
