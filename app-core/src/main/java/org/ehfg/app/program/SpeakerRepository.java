package org.ehfg.app.program;

import java.util.Collection;

/**
 * @author patrick
 * @since 06.2014
 */
public interface SpeakerRepository {
	/**
	 * @return collection of {@link SpeakerDTO} (never null)
	 */
	Collection<SpeakerDTO> findAll();
}
