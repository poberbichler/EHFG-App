package org.ehfg.app.web.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Grid;
import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.ehfg.app.web.components.BootstrapLayout;

public class SessionOverview {
	@Component
	private BootstrapLayout layout;
	
	@Component(parameters = {"source=sessionList"})
	private Grid sessions;
	
	@Inject
	private ProgramFacade programFacade;
	
	@Cached
	public List<SessionDTO> getSessionList() {
		return programFacade.findAllSessions();
	}
}
