package org.ehfg.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.ehfg.app.api.dto.FullTweetDTO;

import com.sun.jersey.api.json.JSONWithPadding;

@Path("twitter")
public class TwitterRestService {
	@GET
	@Produces(Type.JSONP)
	public JSONWithPadding findTweets(@QueryParam("callback") String callback) {
		FullTweetDTO tweet = new FullTweetDTO("Patrick Oberbichler", "keldris", "https://pbs.twimg.com/profile_images/2441790961/b1nxj0dyy72d4gt17ylz_bigger.png", "kldsjfglksdjfglk #baghipups");
		
		return new JSONWithPadding(tweet, callback);
	}
}

/*
{"statuses":[{"metadata":{"result_type":"recent","iso_language_code":"tr"},"created_at":"Sun Mar 09 10:30:37 +0000 2014","id":442608389206835200,"id_str":"442608389206835200","text":"asdflkjasdlfk #baghipups","source":"web","truncated":false,"in_reply_to_status_id":null,"in_reply_to_status_id_str":null,"in_reply_to_user_id":null,"in_reply_to_user_id_str":null,"in_reply_to_screen_name":null,"user":{"id":215640018,"id_str":"215640018","name":"Patrick Oberbichler","screen_name":"keldris","location":"","description":"","url":null,"entities":{"description":{"urls":[]}},"protected":false,"followers_count":3,"friends_count":68,"listed_count":0,"created_at":"Sun Nov 14 14:20:03 +0000 2010","favourites_count":0,"utc_offset":null,"time_zone":null,"geo_enabled":false,"verified":false,"statuses_count":10,"lang":"en","contributors_enabled":false,"is_translator":false,"is_translation_enabled":false,"profile_background_color":"C0DEED","profile_background_image_url":"http:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png","profile_background_image_url_https":"https:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png","profile_background_tile":false,"profile_image_url":"http:\/\/abs.twimg.com\/sticky\/default_profile_images\/default_profile_6_normal.png","profile_image_url_https":"https:\/\/abs.twimg.com\/sticky\/default_profile_images\/default_profile_6_normal.png","profile_link_color":"0084B4","profile_sidebar_border_color":"C0DEED","profile_sidebar_fill_color":"DDEEF6","profile_text_color":"333333","profile_use_background_image":true,"default_profile":true,"default_profile_image":true,"following":false,"follow_request_sent":false,"notifications":false},"geo":null,"coordinates":null,"place":null,"contributors":null,"retweet_count":0,"favorite_count":0,"entities":{"hashtags":[{"text":"baghipups","indices":[14,24]}],"symbols":[],"urls":[],"user_mentions":[]},"favorited":false,"retweeted":false,"lang":"tr"}],"search_metadata":{"completed_in":0.025,"max_id":442608389206835200,"max_id_str":"442608389206835200","query":"%23baghipups","refresh_url":"?since_id=442608389206835200&q=%23baghipups&include_entities=1","count":15,"since_id":0,"since_id_str":"0"}}
*/