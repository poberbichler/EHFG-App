package org.ehfg.app.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.ConnectionLifeCycleListener;
import twitter4j.FilterQuery;
import twitter4j.RateLimitStatusListener;
import twitter4j.RawStreamListener;
import twitter4j.SiteStreamsListener;
import twitter4j.StatusListener;
import twitter4j.StatusStream;
import twitter4j.StreamController;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.UserStream;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

/**
 * Factory used for creating {@link TwitterStream} mock instances, where every
 * method throws an {@link UnsupportedOperationException}<br>
 * The only exceptions are {@link TwitterStream#cleanUp()},
 * {@link TwitterStream#filter(FilterQuery)} and
 * {@link TwitterStream#addListener(StatusListener)}, due to the fact that these
 * are used directly
 * 
 * @author patrick
 * @since 03.11.2014
 */
final class MockStreamFactory {
	private MockStreamFactory() {
		// do not allow instantiation
	}

	public static TwitterStream getInstance() {
		return INSTANCE;
	}

	private static final TwitterStream INSTANCE = new TwitterStream() {
		private final Logger LOG = LoggerFactory.getLogger(getClass());

		@Override
		public void cleanUp() {
			LOG.info("cleaning up mock stream");
		}

		@Override
		public void filter(FilterQuery query) {
			LOG.info("adding filter for query {}", query);
		}

		@Override
		public void addListener(StatusListener listener) {
			LOG.info("adding listener for {}", listener);
		}

		@Override
		public String getScreenName() throws TwitterException, IllegalStateException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public long getId() throws TwitterException, IllegalStateException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public Configuration getConfiguration() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public Authorization getAuthorization() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void addRateLimitStatusListener(RateLimitStatusListener listener) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void setOAuthConsumer(String consumerKey, String consumerSecret) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void setOAuthAccessToken(AccessToken accessToken) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public RequestToken getOAuthRequestToken() throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public AccessToken getOAuthAccessToken() throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void user(String[] track) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void user() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StreamController site(boolean withFollowings, long[] follow) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void shutdown() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void sample() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void retweet() {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void links(int count) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public UserStream getUserStream(String[] track) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public UserStream getUserStream() throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StatusStream getSampleStream() throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StatusStream getRetweetStream() throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StatusStream getLinksStream(int count) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StatusStream getFirehoseStream(int count) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public StatusStream getFilterStream(FilterQuery query) throws TwitterException {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void firehose(int count) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void addListener(RawStreamListener listener) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void addListener(SiteStreamsListener listener) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void addListener(UserStreamListener listener) {
			throw new UnsupportedOperationException("mock - should not be called");
		}

		@Override
		public void addConnectionLifeCycleListener(ConnectionLifeCycleListener listener) {
			throw new UnsupportedOperationException("mock - should not be called");
		}
	};
}
