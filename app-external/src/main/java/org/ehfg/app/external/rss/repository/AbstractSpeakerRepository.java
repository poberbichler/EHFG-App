package org.ehfg.app.external.rss.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.data.speaker.Speaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 * @since 12.07.2014
 */
public abstract class AbstractSpeakerRepository implements SpeakerRepository {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final Map<String, SpeakerDTO> dataCache = new LinkedHashMap<>();

	protected void fillCache(RssSpeaker data) {
		if (data != null) {
			List<Speaker> speakers = data.getChannel().getSpeakers();

			logger.info("received {} speakers", speakers.size());
			dataCache.clear();

			List<SpeakerDTO> speakerList = new ArrayList<>(speakers.size());
			for (final Speaker speaker : speakers) {
				logger.debug("preparing text for speaker {}", speaker);

				if (StringUtils.isEmpty(speaker.getFirstname()) && StringUtils.isEmpty(speaker.getLastname())) {
					continue;
				}

				speakerList.add(new SpeakerDTO.Builder().id(speaker.getId()).firstName(speaker.getFirstname())
						.lastName(speaker.getLastname()).description(EscapeUtils.escapeText(speaker.getBio())).imageUrl("").build());

			}

			Collections.sort(speakerList);
			for (SpeakerDTO speaker : speakerList) {
				dataCache.put(speaker.getId(), speaker);
			}
		}

		else {
			logger.error("did not receive data for {}", RssSpeaker.class.getName());
		}
	}
}
