package org.ehfg.app.external.rss.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.data.speaker.Speaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
class SpeakerRepositoryImpl implements SpeakerRepository, ApplicationListener<DataUpdatedEvent> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, SpeakerDTO> dataCache = new HashMap<>();

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
		final RssSpeaker data = event.getDataForClass(RssSpeaker.class);
		if (data != null) {
			List<Speaker> speakers = data.getChannel().getSpeakers();
			
			logger.info("received {} speakers", speakers.size());
			dataCache.clear();
			for (final Speaker speaker : speakers) {
				dataCache.put(speaker.getId(), new SpeakerDTO(speaker.getId(), speaker.getFirstname(), speaker.getLastname(), speaker.getBio(), ""));
			}
		}
		
		else {
			logger.error("did not receive data for {}", RssSpeaker.class.getName());
		}
	}
}
