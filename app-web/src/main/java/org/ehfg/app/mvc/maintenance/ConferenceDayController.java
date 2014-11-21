package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.data.ConferenceDayForm;
import org.ehfg.app.program.ProgramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("maintenance/conferencedays")
public class ConferenceDayController {
	private final ProgramFacade programFacade;

	@Autowired
	public ConferenceDayController(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView conferenceDaysPage() {
		return createPage();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveDays(@ModelAttribute("dayForm") ConferenceDayForm form, BindingResult result) {
		programFacade.saveDays(form.getDays());
		return "redirect:/maintenance/conferencedays";
	}
	
	private ModelAndView createPage() {
		ModelAndView view = new ModelAndView("conferenceDays");
		view.addObject("activePage", "conferenceDays");
		view.addObject("dayForm", new ConferenceDayForm(programFacade.findDays()));

		return view;
	}
}
