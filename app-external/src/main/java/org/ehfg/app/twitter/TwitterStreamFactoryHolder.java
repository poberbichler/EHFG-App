package org.ehfg.app.twitter;

import twitter4j.TwitterStream;

/**
 * @author patrick
 * @since 12.2014
 */
interface TwitterStreamFactoryHolder {
	TwitterStream getStream();
}
