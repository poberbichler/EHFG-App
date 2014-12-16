package org.ehfg.app.populator;

import java.util.HashSet;
import java.util.Set;

import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.AbstractSessionRepository;
import org.ehfg.app.program.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 12.2014
 */
@Service
class LocationPopulateStrategy extends AbstractPopulateStrategy {
	private final MasterDataFacade masterDataFacade;
	private final AbstractSessionRepository sessionRepository;

	@Autowired
	public LocationPopulateStrategy(MasterDataFacade masterDataFacade, AbstractSessionRepository sessionRepository) {
		this.masterDataFacade = masterDataFacade;
		this.sessionRepository = sessionRepository;
	}

	@Override
	public void execute() throws Exception {
		final Set<String> locationSet = new HashSet<>();
		for (final SessionDTO session : sessionRepository.findAll()) {
			locationSet.add(session.getLocationId());
		}
		
		for (final String location : locationSet) {
			masterDataFacade.saveLocation(new LocationDTO(null, location, generateRandomCoordinate()));
		}
	}
}
