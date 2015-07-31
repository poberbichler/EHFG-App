package org.ehfg.app.populator;

import org.ehfg.app.InMemoryService;
import org.ehfg.app.base.PointOfInterestDTO;

import java.util.stream.IntStream;

/**
 * @author patrick
 * @since 12.2014
 */
@InMemoryService
class PointOfInterestPopulateStrategy extends AbstractPopulateStrategy {
	private static final int NUMBER_OF_POINTS = 6;

	@Override
	public void execute() throws Exception {
		IntStream.range(0, NUMBER_OF_POINTS).forEach(index -> masterDataFacade.savePointOfInterest(createRandomPoint()));
	}

	private PointOfInterestDTO createRandomPoint() {
		return new PointOfInterestDTO(null, "name", "address", "description, which is pretty damn long. like srsly long",
				"contact", "website", super.generateRandomCoordinate());
	}
}
