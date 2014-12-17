package org.ehfg.app.populator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.ehfg.app.MockService;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class TwitterPopulateStrategy extends AbstractPopulateStrategy {
	@Override
	public void execute() throws Exception {
		final Object tweetUser = createTweetUser();
		insertTwitterUser(tweetUser);
		insertTweets(tweetUser);
	}

	private void insertTweets(Object tweetUser) throws Exception {
		final Object tweetRepository = applicationContext.getBean("tweetRepository");
		final Class<?> tweetRepositoryClass = tweetRepository.getClass();
		final Method saveTweetMethod = tweetRepositoryClass.getMethod("save", Object.class);

		saveTweetMethod.invoke(tweetRepository, createTweet(1L, "Message1", tweetUser));
		saveTweetMethod.invoke(tweetRepository, createTweet(2L, "Message2", tweetUser));
	}

	private void insertTwitterUser(Object tweetUser) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object twitterUserRepository = applicationContext.getBean("twitterUserRepository");
		final Class<?> twitterUserRepositoryClass = twitterUserRepository.getClass();
		final Method saveTwitterUserMethod = twitterUserRepositoryClass.getMethod("save", Object.class);

		saveTwitterUserMethod.invoke(twitterUserRepository, tweetUser);
	}

	private Object createTweetUser() throws Exception {

		final Class<?> twitterUserclass = Class.forName("org.ehfg.app.twitter.TwitterUser");
		final Constructor<?> constructor = twitterUserclass.getDeclaredConstructors()[1];
		constructor.setAccessible(true);

		return constructor.newInstance(123L, "poberbichler", "Patrick Oberbichler",
				"https://pbs.twimg.com/profile_images/2441790961/b1nxj0dyy72d4gt17ylz.png");
	}

	private Object createTweet(long id, String message, Object tweetUser) throws Exception {
		final Class<?> tweetClass = Class.forName("org.ehfg.app.twitter.Tweet");
		final Constructor<?> constructor = tweetClass.getDeclaredConstructors()[1];
		constructor.setAccessible(true);

		return constructor.newInstance(id, message, new Date(), "#EHFG2014", message, tweetUser);
	}
}
