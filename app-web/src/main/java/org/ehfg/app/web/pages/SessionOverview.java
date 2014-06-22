package org.ehfg.app.web.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.PageLink;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.ehfg.app.web.components.BootstrapLayout;

public class SessionOverview {
	private static final String DATE_PATTERN = "dd.MM. HH:mm";

	@Component
	private BootstrapLayout layout;

	@Component(parameters = { "source=sessionList", "row=currentSession", "add=startTime,endTime", "exclude=description" })
	private Grid sessions;

	@Component(parameters = { "page=prop:sessionDetailPage", "context=currentSession.id" })
	private PageLink sessionDetails;

	@Property
	private SessionDTO currentSession;

	@Inject
	private ComponentClassResolver resolver;

	@Inject
	private ProgramFacade programFacade;

	@Cached
	public List<SessionDTO> getSessionList() {
		List<SessionDTO> findAllSessionsWithoutDayInformation = programFacade.findAllSessionsWithoutDayInformation();
		return findAllSessionsWithoutDayInformation;
	}

	public String getFormattedStartDate() {
		return currentSession.getStartTime().toString(DATE_PATTERN);
	}

	public String getFormattedEndDate() {
		return currentSession.getEndTime().toString(DATE_PATTERN);
	}

	public Object getSessionDetailPage() {
		return resolver.resolvePageClassNameToPageName(SessionDetails.class.getName());
	}
}
