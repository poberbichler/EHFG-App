package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
	@Path("tweets")
	@Produces(Type.JSONP)
	public JSONWithPadding findTweets(@QueryParam("callback") String callback) {
		return new JSONWithPadding(twitterFacade.findTweetsForCongress(), callback);
	}
	
	@GET
	@Path("hashtag")
	@Produces(Type.JSONP)
	public JSONWithPadding getHashtag(@QueryParam("callback") String callback) throws JSONException {
		return new JSONWithPadding(new JSONObject().put("hashtag", twitterFacade.findHashtag()), callback);
	}
}
