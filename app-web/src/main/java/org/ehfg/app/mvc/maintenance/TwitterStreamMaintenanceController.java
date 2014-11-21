package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.twitter.TwitterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 11.2014
 */
@Controller
@RequestMapping(value = "maintenance/twitterstreams")
public class TwitterStreamMaintenanceController {
	private static final String TO_VIEW = "redirect:/maintenance/twitterstreams";
	private final TwitterFacade twitterFacade;
	
	@Autowired
	public TwitterStreamMaintenanceController(TwitterFacade twitterFacade) {
		this.twitterFacade = twitterFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView twitterStreamPage() {
		ModelAndView view = new ModelAndView("twitterStreams");
		view.addObject("activePage", "twitterStreams");
		view.addObject("streams", twitterFacade.findStreams());
		
		return view;
	}
	
	@RequestMapping(value = "delete/{stream}", method = RequestMethod.GET)
	public String deleteStream(@PathVariable("stream") String hashtag) {
		twitterFacade.removeStream(hashtag);
		return TO_VIEW;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addStream(@RequestParam("streamName") String hashtag) {
		twitterFacade.addStream(hashtag);
		return TO_VIEW;
	}
}
