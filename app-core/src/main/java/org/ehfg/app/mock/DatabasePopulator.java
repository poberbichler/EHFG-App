package org.ehfg.app.mock;

import java.util.Arrays;
import java.util.List;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ConferenceDayDTO;
import org.ehfg.app.program.ProgramFacade;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * populates the in-memory db for the mock profile
 * 
 * @author patrick
 * @since 17.11.2014
 */
public class DatabasePopulator implements InitializingBean {
	private static final ConfigurationDTO CONFIG;
	
	private static final List<ConferenceDayDTO> DAY_LIST;
	private static final DateTimeFormatter FORMAT = new DateTimeFormatterFactory("dd.MM.yyyy").createDateTimeFormatter();
	
	static {
		CONFIG = new ConfigurationDTO("EHFG2014", 10);
		DAY_LIST = Arrays.asList(
				new ConferenceDayDTO(null, LocalDate.parse("01.10.2014", FORMAT), "Day 1"),
				new ConferenceDayDTO(null, LocalDate.parse("02.10.2014", FORMAT), "Day 2"),
				new ConferenceDayDTO(null, LocalDate.parse("03.10.2014", FORMAT), "Day 3"));
	}
	
	
	private final MasterDataFacade masterDataFacade;
	private final ProgramFacade programFacade;

	public DatabasePopulator(MasterDataFacade masterDataFacade,
			ProgramFacade programFacade) {
		this.masterDataFacade = masterDataFacade;
		this.programFacade = programFacade;
	}

	@Override
	@Transactional(readOnly = false)
	public void afterPropertiesSet() throws Exception {
		masterDataFacade.saveAppConfiguration(CONFIG);
		programFacade.saveDays(DAY_LIST);
	}
}
