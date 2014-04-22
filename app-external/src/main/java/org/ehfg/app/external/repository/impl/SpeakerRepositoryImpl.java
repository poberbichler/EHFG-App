package org.ehfg.app.external.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SpeakerRepositoryImpl implements SpeakerRepository {
	private final static Map<Long, SpeakerDTO> speakerMap = new HashMap<Long, SpeakerDTO>() {
		private static final long serialVersionUID = 2853113529863078141L;
	{
		put(0L, new SpeakerDTO(0L, "Kim", "Jong-Il", "dead north korean 'terrorist'", "http://www.asdf.org/avatar.png"));
		put(1L, new SpeakerDTO(1L, "Vladimir", "Putin", "loves to kill bears\nrules russia during his free time", "http://www.asdf.org/anotheravatar.png"));
	}};
	

	@Override
	public SpeakerDTO findById(Long speakerId) {
		return speakerMap.get(speakerId);
	}

	@Override
	public List<SpeakerDTO> findAll() {
		return new ArrayList<SpeakerDTO>(speakerMap.values());
	}

}
