package org.ehfg.app;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author patrick
 * @since 11.2014
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(queryLookupStrategy = Key.CREATE_IF_NOT_FOUND, 
	basePackages = { "org.ehfg.app.base", "org.ehfg.app.program", "org.ehfg.app.twitter" })
public class PersistenceConfig {
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPersistenceUnitName("ehfgAppPersistenceUnit");
		entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
		entityManagerFactory.setJpaProperties(jpaProperties());
		
		return entityManagerFactory;
	}
	
	private Properties jpaProperties() {
		final Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "false");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
		return transactionManager;
	}
	
	@Profile("in-memory-db")
	public static class InMemoryDatabaseConfig {
		@Bean
		public DataSource dataSource() {
			final EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
			factory.setDatabaseName("ehfgApp");
			factory.setDatabaseType(EmbeddedDatabaseType.H2);
			
			return factory.getDatabase();
		}
	}
	
	@Profile("!in-memory-db")
	public static class DefaultDatabaseConfig {
		@Value("${db.url}")
		private String url;

		@Value("${db.username}")
		private String username;

		@Value("${db.password}")
		private String password;

		@Bean
		public DataSource dataSource() {
			return new SingleConnectionDataSource(url, username, password, false);
		}
	}
}
