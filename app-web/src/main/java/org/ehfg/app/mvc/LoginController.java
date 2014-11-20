package org.ehfg.app.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping("login")
public class LoginController {
	@RequestMapping(method = RequestMethod.GET)
	public String getPage() {
		return "login";
	}
	
	@RequestMapping("failed")
	public ModelAndView handleError() {
		ModelAndView view = new ModelAndView("login");
		view.addObject("loginFailed", true);
		
		return view;
	}
}

