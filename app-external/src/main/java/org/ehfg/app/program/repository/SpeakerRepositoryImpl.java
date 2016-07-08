package org.ehfg.app.program.repository;

import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.program.SpeakerRepository;
import org.ehfg.app.program.data.speaker.RssSpeaker;
import org.ehfg.app.program.data.speaker.Speaker;
import org.ehfg.app.program.strategy.AbstractDataRetrievalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 01.2015
 */
@Repository
class SpeakerRepositoryImpl implements SpeakerRepository {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final AbstractDataRetrievalStrategy<RssSpeaker> retrievalStrategy;

	@Autowired
	public SpeakerRepositoryImpl(AbstractDataRetrievalStrategy<RssSpeaker> retrievalStrategy) {
		this.retrievalStrategy = retrievalStrategy;
	}

	@Override
	@Cacheable("speaker")
	public Collection<SpeakerDTO> findAll() {
		List<Speaker> speakers = retrievalStrategy.fetchData().getChannel().getSpeakers();
		logger.info("received {} speakers", speakers.size());

		return speakers.stream()
				.filter(speaker -> !(StringUtils.isEmpty(speaker.getFirstname()) && StringUtils.isEmpty(speaker.getLastname())))
				.map(speaker -> {
					logger.trace("preparing text for speaker {}", speaker);
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
					description = EscapeUtils.escapeLinks(description);

					final String imagePath = speaker.getImagePath() == null ? "" : speaker.getImagePath().trim();
					return new SpeakerDTO.Builder().id(speaker.getId()).firstName(speaker.getFirstname())
							.lastName(speaker.getLastname()).description(description).imageUrl(imagePath).build();
				}).sorted().collect(Collectors.toList());
	}
}
