package org.ehfg.app.external.rss.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.data.speaker.Speaker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
				logger.debug("preparing text for speaker {}", speaker);
				
				String escapedBio = StringEscapeUtils.unescapeHtml4(speaker.getBio().replaceAll("&nbsp;", ""));
				Document document = Jsoup.parse(escapedBio);
				
				// remove stuff overriding our own styles
				document.select("img").remove();
				
				// remove different fonts
				Elements select = document.select("*[style*=font-family]");
				String styleAttribute = select.attr("style");
				String replace = styleAttribute.replaceAll("font-family: '[A-Za-z0-9]*'", "");
				select.attr("style", replace);
				
				// remove empty paragraphs
				for (Element element : document.select("p")) {
					if (element.text().trim().isEmpty()) {
						element.remove();
					}
				}
				
				
				SpeakerDTO speakerDTO = new SpeakerDTO.Builder().id(speaker.getId()).firstName(speaker.getFirstname())
						.lastName(speaker.getLastname()).description(document.html()).imageUrl("").build();

				dataCache.put(speaker.getId(), speakerDTO);
			}
		}

		else {
			logger.error("did not receive data for {}", RssSpeaker.class.getName());
		}
	}
}
