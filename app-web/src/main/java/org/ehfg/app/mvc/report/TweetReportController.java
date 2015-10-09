package org.ehfg.app.mvc.report;

import org.ehfg.app.twitter.TwitterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 12.2014
 */
@Controller
@RequestMapping("report")
public class TweetReportController {
	private static final int PAGE_SIZE = 100;
	
	private final TwitterFacade twitterFacade;

	@Autowired
	public TweetReportController(TwitterFacade twitterFacade) {
		this.twitterFacade = twitterFacade;
	}

	@RequestMapping("tweet")
	public String tweetReportPage() {
		return "redirect:tweet/0";
	}
	
	@RequestMapping("tweet/{pageId}")
	public ModelAndView tweetReportPage(@PathVariable("pageId") Integer pageId) {
		final ModelAndView view = new ModelAndView("report/tweetReport");
		view.addObject("tweetPage", twitterFacade.findTweetPageWithSize(pageId, PAGE_SIZE));
		view.addObject("currentHashtag", twitterFacade.findHashtag());
		
		return view;
	}

	@RequestMapping("tweet/stats")
	public ModelAndView twitterStats() {
		final ModelAndView view = new ModelAndView("report/tweetStats");
		view.addObject("reportLines", twitterFacade.findStats());
		return view;
    }
}
