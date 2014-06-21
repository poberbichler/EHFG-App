package org.ehfg.app.external.rss.strategy;

import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 21.06.2014
 */
@Service
final class SpeakerRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeaker> {
	SpeakerRetrievalStrategy() {
		super(RssSpeaker.class, "speakers");
	}
}
