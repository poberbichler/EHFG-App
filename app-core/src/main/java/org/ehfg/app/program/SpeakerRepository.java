package org.ehfg.app.program;

import java.util.List;

public interface SpeakerRepository {
	SpeakerDTO findById(Long speakerId);
	List<SpeakerDTO> findAll();
}
