package org.ehfg.app.program.strategy;

import org.ehfg.app.program.data.speaker.RssSpeaker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
@Profile({ "!mock" })
final class SpeakerRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeaker> {
	SpeakerRetrievalStrategy() {
		super(RssSpeaker.class, "speakers");
	}
}
