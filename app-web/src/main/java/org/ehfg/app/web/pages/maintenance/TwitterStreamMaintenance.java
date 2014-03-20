package org.ehfg.app.web.pages.maintenance;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Loop;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.ehfg.app.api.dto.TweetDTO;
import org.ehfg.app.api.facade.TwitterFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 14.03.2014
 */
public class TwitterStreamMaintenance {
	@Component
	private BootstrapLayout layout;

	@Component
	private Form inputForm;

	@Component(parameters = { "source=streamList", "value=currentStream" })
	private Loop<String> streams;

	@Component(parameters = { "context=currentStream", "zone=streamListZone" })
	private ActionLink deleteStream;

	@Component(parameters = { "update=show" })
	private Zone streamListZone;

	@Component(parameters = { "value=hashtagValue" })
	private TextField hashtag;

	@Component
	private Submit addStream;

	@Component(parameters = { "source=tweetList" })
	private Grid tweets;

	@Component(parameters = { "update=show" })
	private Zone tweetZone;

	@Component(parameters = { "zone=tweetZone" })
	private ActionLink refreshTweets;

	@Inject
	private TwitterFacade twitterFacade;

	@Property
	private String currentStream;

	@Property
	private String hashtagValue;

	@Cached
	public List<String> getStreamList() {
		return twitterFacade.findStreams();
	}

	@Cached
	public List<TweetDTO> getTweetList() {
		return twitterFacade.findAllTweets();
	}

	@OnEvent(component = "refreshTweets", value = EventConstants.ACTION)
	Object onActionFromRefreshTweets() {
		return tweetZone;
	}

	@OnEvent(component = "deleteStream", value = EventConstants.ACTION)
	Object onActionFromDeleteStream(String context) {
		twitterFacade.removeStream(context);
		return streamListZone.getBody();
	}

	@OnEvent(component = "inputForm", value = EventConstants.SUCCESS)
	void onSuccessFromInputForm() {
		twitterFacade.addStream(hashtagValue);
	}

	public Boolean getStreamsNotEmpty() {
		return !getStreamList().isEmpty();
	}

	public Integer getTotalTweets() {
		return getTweetList().size();
	}
}
