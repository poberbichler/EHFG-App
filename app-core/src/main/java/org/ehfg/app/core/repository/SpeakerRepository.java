package org.ehfg.app.core.repository;

import java.util.List;

import org.ehfg.app.api.dto.SpeakerDTO;

public interface SpeakerRepository {
	SpeakerDTO findById(Long speakerId);
	List<SpeakerDTO> findAll();
}
