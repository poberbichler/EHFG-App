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
@RequestMapping({"/", "session/overview"})
public class SessionOverviewController {
	private final ProgramFacade programFacade;
	
	@Autowired
	public SessionOverviewController(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}
	
	@RequestMapping
	public ModelAndView findOtherStuff() {
		ModelAndView view = new ModelAndView("sessionOverview");
		view.addObject("sessions", programFacade.findAllSessionsWithoutDayInformation());
		view.addObject("activePage", "session");

		return view;
	}
}
