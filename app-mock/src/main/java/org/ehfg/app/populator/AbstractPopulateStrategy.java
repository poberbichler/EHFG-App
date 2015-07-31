package org.ehfg.app.populator;

import org.ehfg.app.InMemoryService;
import org.ehfg.app.base.CoordinateDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SessionRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author patrick
 * @since 12.2014
 */
@InMemoryService
abstract class AbstractPopulateStrategy implements DatabasePopulateStrategy, ApplicationContextAware {
	static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	@Autowired
	protected MasterDataFacade masterDataFacade;
	
	@Autowired
	protected ProgramFacade programFacade;
	
	@Autowired
	protected SessionRepository sessionRepository;
	
	ApplicationContext applicationContext;
	
	/**
	 * @return a random {@link CoordinateDTO}. latitude and longitude lie somewhere between the centre of Bad Hofgastein
	 */
	final CoordinateDTO generateRandomCoordinate() {
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
