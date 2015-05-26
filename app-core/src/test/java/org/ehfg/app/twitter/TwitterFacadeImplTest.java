package org.ehfg.app.twitter;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterFacadeImplTest {
	@InjectMocks
	private TwitterFacadeImpl twitterFacade;

	@Mock
	private MasterDataFacade masterDataFacade;

	@Mock
	private TwitterStreamingFacade streamingFacade;

	private static final String CURRENT_HASHTAG = "#EHFG2014";

	@Before
	public void initMocks() {
		ConfigurationDTO mockedConfig = mock(ConfigurationDTO.class);
		when(mockedConfig.getHashtag()).thenReturn(CURRENT_HASHTAG);

		when(masterDataFacade.getAppConfiguration()).thenReturn(mockedConfig);
	}

	@Test
	public void shouldDoNothingIfCurrentStreamIsRunning() {
		when(streamingFacade.findAllListeners()).thenReturn(Arrays.asList(CURRENT_HASHTAG));

		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		assertEquals(TwitterStreamStatus.RUNNING, status);

		verify(streamingFacade).findAllListeners();
		verifyNoMoreInteractions(streamingFacade);
	}

	@Test
	public void shouldRestartStreamIfNoOneIsActive() {
		when(streamingFacade.findAllListeners()).thenReturn(Collections.<String> emptyList());

		callAndVerifyRestartBehaviour();
	}

	@Test
	public void shouldRestartStreamIfWrongStreamWasFound() {
		when(streamingFacade.findAllListeners()).thenReturn(
				Arrays.asList("asdfasdf", "", "umpalumpa", CURRENT_HASHTAG.concat("a"), CURRENT_HASHTAG.substring(1)));
		
		callAndVerifyRestartBehaviour();
	}
	
	/**
	 * calls the tested class, and verifies the behaviour in case the hashtag was not found
	 */
	private void callAndVerifyRestartBehaviour() {
		TwitterStreamStatus status = twitterFacade.checkIfRelevantStreamIsRunning();
		assertEquals(TwitterStreamStatus.HAD_TO_RESTART, status);

		verify(streamingFacade).findAllListeners();
		verify(streamingFacade).addListener(Hashtag.valueOf(CURRENT_HASHTAG));
	}
}
