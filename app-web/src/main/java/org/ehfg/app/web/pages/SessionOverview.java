package org.ehfg.app.web.pages;

import java.text.SimpleDateFormat;
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
	@Component
	private BootstrapLayout layout;
	
	@Component(parameters = {"source=sessionList", "row=currentSession", "add=detail", "exclude=description"})
	private Grid sessions;
	
	@Component(parameters = {"page=prop:sessionDetailPage", "context=currentSession.id"})
	private PageLink sessionDetails;
	
	@Property
	private SessionDTO currentSession;
	
	@Inject
	private ComponentClassResolver resolver;
	
	@Inject
	private ProgramFacade programFacade;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM hh:mm");
	
	@Cached
	public List<SessionDTO> getSessionList() {
		return programFacade.findAllSessionsWithoutDayInformation();
	}
	
	public String getFormattedStartDate() {
		return sdf.format(currentSession.getStartTime());
	}
	
	public String getFormattedEndDate() {
		return sdf.format(currentSession.getEndTime());
	}
	
	public Object getSessionDetailPage() {
		return resolver.resolvePageClassNameToPageName(SessionDetails.class.getName());
	}
}
