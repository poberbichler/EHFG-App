package org.ehfg.app.twitter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class TweetFactoryTest {
	@Mock
	private org.springframework.social.twitter.api.Tweet status;

	@Mock
	private TwitterUser user;

	private static final String ANY_HASHTAG = "#EHFG2014";
	private static final long ANY_ID = 1235234908L;
	private static final String MESSAGE = "OMG, #EHFG2014 was awsome... #EHFG2013; http://t.co/IyExg1bi61 app.ehfg.org/1234 app.ehfg.org/12";

	@Before
	public void initMocks() {
//		URLEntity entity = mock(URLEntity.class);
//		when(entity.getDisplayURL()).thenReturn("app.ehfg.org/123");
//		when(entity.getExpandedURL()).thenReturn("https://app.ehfg.org/123");
//		when(entity.getURL()).thenReturn("http://t.co/IyExg1bi61");

		when(status.getCreatedAt()).thenReturn(new Date());
		when(status.getId()).thenReturn(ANY_ID);
		when(status.getText()).thenReturn(MESSAGE);
		when(status.getUnmodifiedText()).thenReturn(MESSAGE);
	}

	@Test
	@Ignore
	public void shouldUpdateUrlsCorrect() {
		Tweet createdTweet = TweetFactory.create(status, ANY_HASHTAG, user);
		assertEquals(user, createdTweet.getAuthor());
		assertEquals(MESSAGE, createdTweet.getMessage());
		assertEquals(ANY_HASHTAG, createdTweet.getHashtag());
		final String expectedFormattedMessage = "OMG, <span class=\"hashtag\">#EHFG2014</span> was awsome... <span class=\"hashtag\">#EHFG2013;</span> "
				+ "<a href=\"https://app.ehfg.org/123\" target=\"_blank\">app.ehfg.org/123</a> app.ehfg.org/1234 app.ehfg.org/12 ";

		assertEquals(expectedFormattedMessage, createdTweet.getFormattedMesssage());
	}
}
