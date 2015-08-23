package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ProgramFacade;
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
import java.util.Collections;
import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("maintenance/location")
public class LocationController {
	private final MasterDataFacade masterDataFacade;
	private final ProgramFacade programFacade;

	@Autowired
	public LocationController(MasterDataFacade masterDataFacade,
			ProgramFacade programFacade) {
		this.masterDataFacade = masterDataFacade;
		this.programFacade = programFacade;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getPage() {
		ModelAndView view = new ModelAndView("location");
		
		final List<LocationDTO> alreadyAssignedLocations = masterDataFacade.findAllLocation();
		
		view.addObject("activePage", "location");
		view.addObject("locations", alreadyAssignedLocations);
		view.addObject("availableLocations", resolveLocations(alreadyAssignedLocations));
		view.addObject("editLocation", new LocationDTO());
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveLocation(@Valid @ModelAttribute("editLocation") LocationDTO location, BindingResult result) {
		if (!result.hasErrors()) {
			masterDataFacade.saveLocation(location);
		}
		
		return "redirect:/maintenance/location";
	}

	@RequestMapping("delete/{id}")
	public String deleteLocation(@PathVariable("id") String id) {
		masterDataFacade.deleteLocation(id);
		return "redirect:/maintenance/location";
	}
	
	private Collection<String> resolveLocations(final Collection<LocationDTO> alreadyAssignedLocations) {
		List<String> availableLocations = programFacade.findAvailableLocations();
		for (LocationDTO location : alreadyAssignedLocations) {
			availableLocations.remove(location.getName());
		}
		
		Collections.sort(availableLocations);
		return availableLocations;
	}
}
