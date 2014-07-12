package org.ehfg.app.external.rss.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.repository.AbstractSpeakerRepository;
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
	public SpeakerDTO findById(Long speakerId) {
		return dataCache.get(speakerId.toString());
	}

	@Override
	public List<SpeakerDTO> findAll() {
		return new ArrayList<>(dataCache.values());
	}

	@Override
	public void onApplicationEvent(DataUpdatedEvent event) {
		super.fillCache(event.getDataForClass(RssSpeaker.class));
	}
}
