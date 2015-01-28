package org.ehfg.app.program.strategy;

import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 06.2014
 */
@Service
@Profile({ "!mock" })
final class SpeakerEventRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeakerEvents> {
	SpeakerEventRetrievalStrategy() {
		super(RssSpeakerEvents.class, "speakerevents");
	}
}
