package org.ehfg.app.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Populates the in-memory db for the mock profile<br>
 * Tweets will be initiated via reflection and also added to the database
 * 
 * @author patrick
 * @since 11.2014
 */
@Component
@Profile("in-memory-db")
public class DatabasePopulator implements InitializingBean, ApplicationContextAware {
	private static final ConfigurationDTO CONFIG;

	private static final Object TWEET_USER;
	private static final List<ConferenceDayDTO> DAY_LIST;
	private static final DateTimeFormatter FORMAT = new DateTimeFormatterFactory("dd.MM.yyyy").createDateTimeFormatter();


	static {
		TWEET_USER = createTweetUser();
		CONFIG = new ConfigurationDTO("EHFG2014", 10);
		DAY_LIST = Arrays.asList(
				new ConferenceDayDTO(null, LocalDate.parse("01.10.2014", FORMAT), "Day 1"),
				new ConferenceDayDTO(null, LocalDate.parse("02.10.2014", FORMAT), "Day 2"), 
				new ConferenceDayDTO(null, LocalDate.parse("03.10.2014", FORMAT), "Day 3"));
	}

	private final MasterDataFacade masterDataFacade;
	private final ProgramFacade programFacade;

	private ApplicationContext applicationContext;

	@Autowired
	public DatabasePopulator(MasterDataFacade masterDataFacade, ProgramFacade programFacade) {
		this.masterDataFacade = masterDataFacade;
		this.programFacade = programFacade;
	}

	@Override
	@Transactional(readOnly = false)
	public void afterPropertiesSet() throws Exception {
		masterDataFacade.saveAppConfiguration(CONFIG);
		programFacade.saveDays(DAY_LIST);

		insertTwitterUser();
		insertTweets();
	}

	private void insertTweets() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object tweetRepository = applicationContext.getBean("tweetRepository");
		final Class<?> tweetRepositoryClass = tweetRepository.getClass();
		final Method saveTweetMethod = tweetRepositoryClass.getMethod("save", Object.class);

		saveTweetMethod.invoke(tweetRepository, createTweet(1L, "Message1"));
		saveTweetMethod.invoke(tweetRepository, createTweet(2L, "Message2"));
	}

	private void insertTwitterUser() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Object twitterUserRepository = applicationContext.getBean("twitterUserRepository");
		final Class<?> twitterUserRepositoryClass = twitterUserRepository.getClass();
		final Method saveTwitterUserMethod = twitterUserRepositoryClass.getMethod("save", Object.class);

		saveTwitterUserMethod.invoke(twitterUserRepository, TWEET_USER);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private static Object createTweetUser() {
		try {
			final Class<?> twitterUserclass = Class.forName("org.ehfg.app.twitter.TwitterUser");
			final Constructor<?> constructor = twitterUserclass.getDeclaredConstructors()[1];
			constructor.setAccessible(true);
			
			return constructor.newInstance(123L, "poberbichler", "Patrick Oberbichler", "https://pbs.twimg.com/profile_images/2441790961/b1nxj0dyy72d4gt17ylz.png");
		}
		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object createTweet(long id, String message) {
		try {
			final Class<?> tweetClass = Class.forName("org.ehfg.app.twitter.Tweet");
			final Constructor<?> constructor = tweetClass.getDeclaredConstructors()[1];
			constructor.setAccessible(true);
			
			return constructor.newInstance(id, message, new Date(), "#EHFG2014", message, TWEET_USER);
		}
		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
