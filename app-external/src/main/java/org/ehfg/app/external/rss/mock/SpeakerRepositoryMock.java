package org.ehfg.app.external.rss.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 * @since 24.03.2014
 */
@Repository
@Profile("mock")
class SpeakerRepositoryMock implements SpeakerRepository {
	private final static Map<Long, SpeakerDTO> speakerMap = new HashMap<>();

	static {
		speakerMap.put(0L, new SpeakerDTO(0L, "Nelson", "Mandela", "Politiker aus Suedafrika",
				"http://upload.wikimedia.org/wikipedia/commons/1/14/Nelson_Mandela-2008_%28edit%29.jpg"));

		speakerMap.put(1L, new SpeakerDTO(1L, "Alfred", "Nobel", "Erfindet gern Preise",
				"http://upload.wikimedia.org/wikipedia/commons/6/6e/AlfredNobel_adjusted.jpg"));
	}

	@Override
	public SpeakerDTO findById(Long speakerId) {
		return speakerMap.get(speakerId);
	}

	@Override
	public List<SpeakerDTO> findAll() {
		return new ArrayList<SpeakerDTO>(speakerMap.values());
	}
}
