package org.ehfg.app.mvc.maintenance;

import javax.validation.Valid;

import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.base.PointOfInterestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("maintenance/pointofinterest")
public class PointOfInterestController {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public PointOfInterestController(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getPage() {
		ModelAndView view = new ModelAndView("pointOfInterest");
		view.addObject("activePage", "pointOfInterest");
		view.addObject("points", masterDataFacade.findAllPointsOfInterest());
		view.addObject("command", new PointOfInterestDTO());
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addPoint(@Valid @ModelAttribute("command") PointOfInterestDTO point, BindingResult bindingResult) {
		System.out.println(point);
		masterDataFacade.savePointOfInterest(point);
		return "redirect:/mvc/maintenance/pointofinterest";
	}
}
