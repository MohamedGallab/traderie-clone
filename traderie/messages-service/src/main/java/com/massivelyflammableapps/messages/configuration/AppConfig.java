package com.massivelyflammableapps.messages.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class AppConfig extends AbstractCassandraConfiguration {
	@Override
	public String getKeyspaceName() {
		return "traderie_cassandra";
	}

	// not working
	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
}