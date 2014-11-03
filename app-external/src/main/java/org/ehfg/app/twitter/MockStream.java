package org.ehfg.app.twitter;

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

final class MockStream {
	private MockStream() {
		
	}
	
	static TwitterStream getInstance() {
		return INSTANCE;
	}
	
	private static final TwitterStream INSTANCE = new TwitterStream() {
		@Override
		public String getScreenName() throws TwitterException, IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getId() throws TwitterException, IllegalStateException {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Configuration getConfiguration() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Authorization getAuthorization() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void addRateLimitStatusListener(RateLimitStatusListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOAuthConsumer(String consumerKey, String consumerSecret) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOAuthAccessToken(AccessToken accessToken) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public RequestToken getOAuthRequestToken() throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessToken getOAuthAccessToken() throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void user(String[] track) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void user() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public StreamController site(boolean withFollowings, long[] follow) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void shutdown() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void sample() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void retweet() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void links(int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public UserStream getUserStream(String[] track) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UserStream getUserStream() throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StatusStream getSampleStream() throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StatusStream getRetweetStream() throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StatusStream getLinksStream(int count) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StatusStream getFirehoseStream(int count) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public StatusStream getFilterStream(FilterQuery query) throws TwitterException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void firehose(int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void filter(FilterQuery query) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void cleanUp() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addListener(RawStreamListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addListener(SiteStreamsListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addListener(StatusListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addListener(UserStreamListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addConnectionLifeCycleListener(ConnectionLifeCycleListener listener) {
			// TODO Auto-generated method stub
			
		}
	};
}
