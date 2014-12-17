package org.ehfg.app.populator;

import java.util.Arrays;

import org.ehfg.app.MockService;
import org.ehfg.app.program.ConferenceDayDTO;
import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class ConferenceDayPopulateStrategy extends AbstractPopulateStrategy {
	@Override
	public void execute() throws Exception {
		programFacade.saveDays(Arrays.asList(
				new ConferenceDayDTO(null, LocalDate.parse("01.10.2014", FORMAT), "Day 1"),
				new ConferenceDayDTO(null, LocalDate.parse("02.10.2014", FORMAT), "Day 2"),
				new ConferenceDayDTO(null, LocalDate.parse("03.10.2014", FORMAT), "Day 3")));
	}

}
