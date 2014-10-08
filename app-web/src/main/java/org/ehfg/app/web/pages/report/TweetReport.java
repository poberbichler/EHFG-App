package org.ehfg.app.web.pages.report;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.twitter.TweetDTO;
import org.ehfg.app.twitter.TwitterFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 08.10.2014
 */
public class TweetReport {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. hh:mm");

	@Component(parameters = { "hideSidebar=true" })
	private BootstrapLayout layout;

	@Component(parameters = { "source=tweetList", "row=currentTweet", "exclude=fullNameForScreen",
			"reorder=profileImage,fullName,nickName,timestamp,message", "rowsPerPage=100" })
	private Grid tweets;

	@Property
	private TweetDTO currentTweet;

	@Inject
	private MasterDataFacade masterDataFacade;

	@Inject
	private TwitterFacade twitterFacade;

	@Cached
	public List<TweetDTO> getTweetList() {
		return twitterFacade.findAllTweets();
	}

	public String getCurrentHashtag() {
		return masterDataFacade.getAppConfiguration().getHashtag();
	}

	public String getFormattedTimestamp() {
		return sdf.format(currentTweet.timestamp);
	}
}
