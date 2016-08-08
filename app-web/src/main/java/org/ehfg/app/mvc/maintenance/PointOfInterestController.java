package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.base.PointOfInterestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("maintenance/pointofinterest")
public class PointOfInterestController {
	private static final String TO_BASE_PAGE = "redirect:/maintenance/pointofinterest";
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
		view.addObject("categories", masterDataFacade.findAllMapCategories());
		view.addObject("editPoint", new PointOfInterestDTO());
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addPoint(@Valid @ModelAttribute("editPoint") PointOfInterestDTO point, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			masterDataFacade.savePointOfInterest(point);
		}
		return TO_BASE_PAGE;
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView editPointOfInterest(@PathVariable("id") final String pointId) {
		final List<PointOfInterestDTO> allPoints = masterDataFacade.findAllPointsOfInterest();
		
		ModelAndView view = new ModelAndView("pointOfInterest");
		view.addObject("editPoint", findPointInList(allPoints, pointId));
		view.addObject("points", allPoints);
		view.addObject("activePage", "pointOfInterest");
		view.addObject("categories", masterDataFacade.findAllMapCategories());
		
		return view;
	}
	
	@RequestMapping(value = "delete/{pointId}", method = RequestMethod.GET)
	public String deletePointOfInterest(@PathVariable("pointId") String pointId) {
		masterDataFacade.removePoint(pointId);
		return TO_BASE_PAGE;
	}
	
	private PointOfInterestDTO findPointInList(final Collection<PointOfInterestDTO> points, final String pointId) {
		return points.stream().filter(p -> p.getId().equals(pointId)).findFirst().get();
	}
}
