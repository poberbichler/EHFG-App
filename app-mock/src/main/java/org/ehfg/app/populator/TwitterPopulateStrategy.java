package org.ehfg.app.populator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.ehfg.app.ExternalConfig;
import org.ehfg.app.MockService;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class TwitterPopulateStrategy extends AbstractPopulateStrategy {
	private static final int MAX_TWEETS = 5;
	
	@Override
	public void execute() throws Exception {
		final Object tweetUser = createTweetUser();
		insertTwitterUser(tweetUser);
		insertTweets(tweetUser);
	}

	private void insertTweets(Object tweetUser) throws Exception {
		final Object tweetRepository = applicationContext.getBean("tweetRepository");
		final Class<?> tweetRepositoryClass = tweetRepository.getClass();
		final Method saveTweetMethod = tweetRepositoryClass.getMethod("save", Iterable.class);
		
		final List<Object> tweetList = new ArrayList<>(MAX_TWEETS);
		IntStream.range(0, MAX_TWEETS).forEach(i -> tweetList.add(createTweet(i, "Message ".concat(Integer.toString(i)), tweetUser, i)));

		saveTweetMethod.setAccessible(true);
		saveTweetMethod.invoke(tweetRepository, tweetList);
	}

	private void insertTwitterUser(Object tweetUser) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object twitterUserRepository = applicationContext.getBean("twitterUserRepository");
		final Class<?> twitterUserRepositoryClass = twitterUserRepository.getClass();
		final Method saveTwitterUserMethod = twitterUserRepositoryClass.getMethod("save", Object.class);
		
		saveTwitterUserMethod.setAccessible(true);
		saveTwitterUserMethod.invoke(twitterUserRepository, tweetUser);
	}

	private Object createTweetUser() throws Exception {
		final Class<?> twitterUserclass = Class.forName("org.ehfg.app.twitter.TwitterUser");

		final Constructor<?> constructor = twitterUserclass.getDeclaredConstructors()[1];
		constructor.setAccessible(true);

		return constructor.newInstance(123L, "Patrick Oberbichler", "poberbichler",
				"https://abs.twimg.com/sticky/default_profile_images/default_profile_6_normal.png");
	}

	private Object createTweet(long id, String message, Object tweetUser, int timeoffset) {
		try {
			final Class<?> tweetClass = Class.forName("org.ehfg.app.twitter.Tweet");
			final Constructor<?> constructor = tweetClass.getDeclaredConstructors()[1];
			constructor.setAccessible(true);

			return constructor.newInstance(id, message, new Date(System.currentTimeMillis() + timeoffset), "#EHFG2014", message, tweetUser);
		}

		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
