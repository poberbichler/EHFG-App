package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.api.facade.TwitterFacade;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("twitter")
public class TwitterRestService {
	private final TwitterFacade twitterFacade;

	public TwitterRestService(TwitterFacade twitterFacade) {
		super();
		this.twitterFacade = twitterFacade;
	}

	@GET
	@Produces(Type.JSONP)
	public JSONWithPadding findTweets(@QueryParam("callback") String callback) {
		return new JSONWithPadding(twitterFacade.findTweetsForCongress(), callback);
	}
}
