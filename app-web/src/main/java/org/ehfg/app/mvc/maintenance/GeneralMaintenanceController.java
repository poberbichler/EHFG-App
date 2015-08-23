package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("maintenance/general")
public class GeneralMaintenanceController {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public GeneralMaintenanceController(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView generalMaintenancePage() {
		return createGeneralView(masterDataFacade.getAppConfiguration());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleSubmittedForm(@Valid @ModelAttribute("config") ConfigurationDTO configurationDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return createGeneralView(configurationDTO);
		}

		return createGeneralView(masterDataFacade.saveAppConfiguration(configurationDTO));
	}

	private ModelAndView createGeneralView(final ConfigurationDTO config) {
		ModelAndView view = new ModelAndView("generalMaintenance");
		view.addObject("activePage", "generalMaintenance");
		view.addObject("config", config);

		return view;
	}
}
