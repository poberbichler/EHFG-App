package org.ehfg.app.populator;

import org.ehfg.app.MockService;
import org.ehfg.app.base.PointOfInterestDTO;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class PointOfInterestPopulateStrategy extends AbstractPopulateStrategy {
	private static final int NUMBER_OF_POINTS = 6;

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
