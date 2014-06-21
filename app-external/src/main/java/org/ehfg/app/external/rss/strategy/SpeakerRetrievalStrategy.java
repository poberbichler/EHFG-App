package org.ehfg.app.external.rss.strategy;

import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.springframework.stereotype.Service;

@Service
final class SpeakerRetrievalStrategy extends AbstractDataRetrievalStrategy<RssSpeaker> {
	SpeakerRetrievalStrategy() {
		super(RssSpeaker.class, "speakers");
	}
}
