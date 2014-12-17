package org.ehfg.app.populator;

import java.util.Random;

import org.ehfg.app.MockService;
import org.ehfg.app.base.CoordinateDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.AbstractSessionRepository;
import org.ehfg.app.program.ProgramFacade;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
abstract class AbstractPopulateStrategy implements DatabasePopulateStrategy, ApplicationContextAware {
	protected static final DateTimeFormatter FORMAT = new DateTimeFormatterFactory("dd.MM.yyyy").createDateTimeFormatter();
	
	@Autowired
	protected MasterDataFacade masterDataFacade;
	
	@Autowired
	protected ProgramFacade programFacade;
	
	@Autowired
	protected AbstractSessionRepository sessionRepository;
	
	protected ApplicationContext applicationContext;
	
	/**
	 * @return a random {@link CoordinateDTO}. latitude and longitude lie somewhere between the centre of Bad Hofgastein
	 */
	protected final CoordinateDTO generateRandomCoordinate() {
		final Random random = new Random();
		
		final double xBaseValue 	= 47.170323d;
		final double yBaseValue 	= 13.102228d;
		final double randomOffset 	= 00.003000d;
		
		return new CoordinateDTO(
				xBaseValue + random.nextDouble() % randomOffset, 
				yBaseValue + random.nextDouble() % randomOffset);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
