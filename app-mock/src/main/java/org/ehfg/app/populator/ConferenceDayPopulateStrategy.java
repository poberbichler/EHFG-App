package org.ehfg.app.populator;

import java.util.Arrays;

import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 12.2014
 */
@Service
class ConferenceDayPopulateStrategy extends AbstractPopulateStrategy {
	private final ProgramFacade programFacade;

	@Autowired
	public ConferenceDayPopulateStrategy(ProgramFacade programFacade) {
		this.programFacade = programFacade;
	}

	@Override
	public void execute() throws Exception {
		programFacade.saveDays(Arrays.asList(
				new ConferenceDayDTO(null, LocalDate.parse("01.10.2014", FORMAT), "Day 1"),
				new ConferenceDayDTO(null, LocalDate.parse("02.10.2014", FORMAT), "Day 2"),
				new ConferenceDayDTO(null, LocalDate.parse("03.10.2014", FORMAT), "Day 3")));
	}

}
