package org.ehfg.app.mvc.overview;

import org.ehfg.app.program.ProgramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("speaker/overview")
public class SpeakerOverviewController {
	private final ProgramFacade programFacade;

	@Autowired
	public SpeakerOverviewController(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}
	
	@RequestMapping
	public ModelAndView showPage() {
		ModelAndView view = new ModelAndView("speakerOverview");
		view.addObject("speakers", programFacade.findAllSpeakers());
		view.addObject("activePage", "speaker");
		
		return view;
	}
}
