package org.ehfg.app.populator;

import java.util.HashSet;
import java.util.Set;

import org.ehfg.app.MockService;
import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.program.SessionDTO;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class LocationPopulateStrategy extends AbstractPopulateStrategy {
	@Override
	public void execute() throws Exception {
		final Set<String> locationSet = new HashSet<>();
		for (final SessionDTO session : sessionRepository.findAll()) {
			locationSet.add(session.getLocation());
		}
		
		for (final String location : locationSet) {
			masterDataFacade.saveLocation(new LocationDTO(null, location, generateRandomCoordinate()));
		}
	}
}
