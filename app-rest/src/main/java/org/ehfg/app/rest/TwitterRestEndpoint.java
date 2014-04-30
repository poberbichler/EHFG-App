package org.ehfg.app.rest;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.ehfg.app.api.facade.TwitterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONWithPadding;

/**
 * @author patrick
 * @since 12.04.2014
 */
@Component
@Path("twitter")
public class TwitterRestEndpoint {
	private final TwitterFacade twitterFacade;

	@Autowired
	public TwitterRestEndpoint(TwitterFacade twitterFacade) {
		super();
		this.twitterFacade = twitterFacade;
	}

	@GET
	@Path("hashtag")
	@Produces(Type.JSONP)
	public JSONWithPadding getHashtag(@QueryParam("callback") String callback) throws JSONException {
		return new JSONWithPadding(new JSONObject().put("hashtag", twitterFacade.findHashtag()), callback);
	}

	@GET
	@Path("update")
	@Produces(Type.JSONP)
	public JSONWithPadding updateTweets(@QueryParam("callback") String callback, @QueryParam("lastTweet") Date lastTweet) {
		return new JSONWithPadding(twitterFacade.findNewerTweetsForCongress(lastTweet), callback);
	}

	@GET
	@Path("tweetpage/{page}")
	@Produces(Type.JSONP)
	public JSONWithPadding findTweetsByPage(@QueryParam("callback") String callback, @PathParam("page") Integer pageId) {
		return new JSONWithPadding(twitterFacade.findTweetPage(pageId), callback);
	}
}
