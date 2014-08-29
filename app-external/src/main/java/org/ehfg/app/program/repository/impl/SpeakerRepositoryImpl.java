package org.ehfg.app.program.repository.impl;

import org.ehfg.app.program.AbstractSpeakerRepository;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.program.data.speaker.RssSpeaker;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * takes care of transforming the data from {@link RssSpeaker} into usable
 * {@link SpeakerDTO}, and caches the given data
 * 
 * @author patrick
 * @since 21.06.2014
 */
@Repository
@Profile({ "!mock" })
class SpeakerRepositoryImpl extends AbstractSpeakerRepository implements ApplicationListener<DataUpdatedEvent> {
	@Override
	public void onApplicationEvent(DataUpdatedEvent event) {
		super.fillCache(event.getDataForClass(RssSpeaker.class));
	}
}
