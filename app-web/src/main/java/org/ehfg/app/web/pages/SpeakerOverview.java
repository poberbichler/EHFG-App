package org.ehfg.app.web.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Grid;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.web.components.BootstrapLayout;

public class SpeakerOverview {
	@Component
	private BootstrapLayout layout;
	
	@Component(parameters = {"source=speakerList"})
	private Grid speakers;
	
	@Inject
	private ProgramFacade programFacade;
	
	@Cached
	public List<SpeakerDTO> getSpeakerList() {
		return programFacade.findAllSpeakers();
	}
}
