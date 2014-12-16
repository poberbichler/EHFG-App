package org.ehfg.app.populator;

import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.base.PointOfInterestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 12.2014
 */
@Service
class PointOfInterestPopulateStrategy extends AbstractPopulateStrategy {
	private static final int NUMBER_OF_POINTS = 6;
	private MasterDataFacade masterDataFacade;

	@Autowired
	public PointOfInterestPopulateStrategy(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@Override
	public void execute() throws Exception {
		for (int i = 0; i < NUMBER_OF_POINTS; i++) {
			masterDataFacade.savePointOfInterest(createRandomPoint());
		}
	}

	private PointOfInterestDTO createRandomPoint() {
		return new PointOfInterestDTO(null, "name", "address", "description", "contact", "website", generateRandomCoordinate());
	}
}
