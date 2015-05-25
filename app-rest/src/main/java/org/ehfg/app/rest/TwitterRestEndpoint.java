package org.ehfg.app.rest;

import java.util.Collection;
import java.util.Date;

import org.ehfg.app.twitter.TweetPageDTO;
import org.ehfg.app.twitter.TwitterFacade;
import org.ehfg.app.twitter.TwitterStreamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick
 * @since 04.2014
 */
@RestController
@RequestMapping("rest/twitter")
public final class TwitterRestEndpoint {
	private final TwitterFacade twitterFacade;

	@Autowired
	public TwitterRestEndpoint(TwitterFacade twitterFacade) {
		this.twitterFacade = twitterFacade;
	}

	@RequestMapping(value = "hashtag", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getHashtag() {
		return twitterFacade.findHashtag();
	}

	@RequestMapping(value = "update/{lastTweet}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<? extends TweetRepresentation> updateTweets(@PathVariable("lastTweet") Long timestamp) {
		return twitterFacade.findNewerTweetsForCongress(new Date(timestamp));
	}

	@RequestMapping(value = "page/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TweetPageDTO findTweetsByPage(@PathVariable("page") Integer pageId) {
		return twitterFacade.findTweetPage(pageId);
	}
	
	@RequestMapping(value = "check", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String checkIfRunning() {
		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		
		switch (status) {
			case HAD_TO_RESTART:
				return "stream had to be restarted";
				
			case RUNNING:
				return "stream was already running";
				
			case NOT_RUNNING:
			default:
				return "thats bad, bro";
		}
	}
}
