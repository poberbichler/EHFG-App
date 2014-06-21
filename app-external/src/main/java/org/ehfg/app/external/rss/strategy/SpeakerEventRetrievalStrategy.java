package org.ehfg.app.external.rss.strategy;

import org.ehfg.app.external.rss.data.speakerevents.RssSpeakerEvents;
import org.springframework.stereotype.Service;

@Service
class SpeakerEventRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeakerEvents> {
	SpeakerEventRetrievalStrategy() {
		super(RssSpeakerEvents.class, "speakerevents");
	}
}
