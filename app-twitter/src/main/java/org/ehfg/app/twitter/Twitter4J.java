package org.ehfg.app.twitter;

import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4J {
	public static void main(String[] args) throws TwitterException,
			InstantiationException, IllegalAccessException, IOException {
		ConfigurationBuilder builder = new ConfigurationBuilder()
				.setDebugEnabled(true)
				.setOAuthConsumerKey("L0P1ot1tpW8YRQ246GUog")
				.setOAuthConsumerSecret("Dunfvc7L2AYAnd57Mx4iWSUWcLNg8W96vPZM95muY")
				.setOAuthAccessToken("215640018-XKpgfqh3gqMTAbqxEgquCJ5FL2cOSsliZ7YnhPzv")
				.setOAuthAccessTokenSecret("1Oec9b8ichpWOhPExBLfvvIQyXCBrQtIKWArfFk3WIF7R")
				.setUseSSL(true);

		Configuration build = builder.build();

		Twitter twitter = new TwitterFactory(build).getInstance();
		Query searchQuery = new Query("#baghipups");
		searchQuery.setSince("2014-02-25");
		searchQuery.setUntil("2014-03-02");
		searchQuery.setResultType(Query.RECENT);
		for (Status status : twitter.search(searchQuery).getTweets()) {
			System.out.println(status);
		}

		System.out.println("done getting");

		TwitterStream stream = new TwitterStreamFactory(build).getInstance();
		stream.addListener(new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatus(Status arg0) {
				System.out.println(arg0);
				System.out.println("@" + arg0.getUser().getScreenName() + " "
						+ arg0.getText());

			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}
		});

		FilterQuery query = new FilterQuery();
		query.track(new String[] { "#baghipups" });
		stream.filter(query);
	}
}
