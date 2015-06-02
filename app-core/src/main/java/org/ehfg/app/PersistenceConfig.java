package org.ehfg.app;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
@Configuration
@EnableMongoRepositories(queryLookupStrategy = Key.CREATE_IF_NOT_FOUND,
	basePackages = { "org.ehfg.app.base", "org.ehfg.app.program", "org.ehfg.app.twitter" })
public class PersistenceConfig extends AbstractMongoConfiguration {
	@Value("${db.url}")
	private String url;

	@Value("${db.name}")
	private String databaseName;

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public Mongo mongo() throws Exception {
		final List<MongoCredential> credentials;
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			credentials = Collections.emptyList();
		} else {
			credentials = Arrays.asList(MongoCredential.createCredential(username, databaseName, password.toCharArray()));
		}

		final MongoClient client = new MongoClient(new ServerAddress(url), credentials);
		client.setWriteConcern(WriteConcern.SAFE);
		return client;
	}
}
