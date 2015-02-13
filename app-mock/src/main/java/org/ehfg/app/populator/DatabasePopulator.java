package org.ehfg.app.populator;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.ehfg.app.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Populates the in-memory db for the mock profile<br>
 * Tweets will be initiated via reflection and also added to the database
 * 
 * @author patrick
 * @since 11.2014
 */
@MockService
public class DatabasePopulator {
	final Collection<DatabasePopulateStrategy> strategies;

	@Autowired
	public DatabasePopulator(Collection<DatabasePopulateStrategy> strategies) {
		this.strategies = strategies;
	}

	@PostConstruct
	@Transactional(readOnly = false)
	public void afterPropertiesSet() throws Exception {
		for (final DatabasePopulateStrategy strategy : strategies) {
			strategy.execute();
		}
	}
}
