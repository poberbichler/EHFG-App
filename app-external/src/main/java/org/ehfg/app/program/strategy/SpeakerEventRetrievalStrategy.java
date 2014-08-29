package org.ehfg.app.program.strategy;

import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 21.06.2014
 */
@Service
final class SpeakerEventRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeakerEvents> {
	SpeakerEventRetrievalStrategy() {
		super(RssSpeakerEvents.class, "speakerevents");
	}
}
