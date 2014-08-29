package org.ehfg.app.program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.program.SpeakerRepository;
import org.ehfg.app.program.data.speaker.RssSpeaker;
import org.ehfg.app.program.data.speaker.Speaker;
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

			int filterCounter = 0;
			List<SpeakerDTO> speakerList = new ArrayList<>(speakers.size());
			for (final Speaker speaker : speakers) {
				logger.debug("preparing text for speaker {}", speaker);

				if ((StringUtils.isEmpty(speaker.getFirstname()) && StringUtils.isEmpty(speaker.getLastname()))
						|| StringUtils.isEmpty(speaker.getBio())) {
					filterCounter++;
					continue;
				}

				// TODO: superawesome regexp would be better
				String description = EscapeUtils.escapeText(speaker.getBio());
				description = StringUtils.remove(description.trim(), "<strong>");
				description = StringUtils.remove(description.trim(), "<div>");
				description = StringUtils.remove(description.trim(), "\n");
				description = StringUtils.removeStart(description.trim(), speaker.getFirstname());
				description = StringUtils.removeStart(description.trim(), speaker.getLastname());
				description = StringUtils.removeStart(description.trim(), speaker.getFirstname());
				description = StringUtils.removeStart(description.trim(), "</div>");
				description = StringUtils.removeStart(description.trim(), "</strong>");
				description = StringUtils.removeStart(description.trim(), ";");
				description = StringUtils.removeStart(description.trim(), ",");

				final String imagePath = speaker.getImagePath() == null ? "" : speaker.getImagePath().trim();
				speakerList.add(new SpeakerDTO.Builder().id(speaker.getId()).firstName(speaker.getFirstname())
						.lastName(speaker.getLastname()).description(description).imageUrl(imagePath).build());

			}

			logger.info("filtered {} speakers", filterCounter);

			Collections.sort(speakerList);
			for (SpeakerDTO speaker : speakerList) {
				dataCache.put(speaker.getId(), speaker);
			}
		}

		else {
			logger.error("did not receive data for {}", RssSpeaker.class.getName());
		}
	}

	@Override
	public SpeakerDTO findById(Long speakerId) {
		return dataCache.get(speakerId);
	}

	@Override
	public List<SpeakerDTO> findAll() {
		return new ArrayList<>(dataCache.values());
	}

}
