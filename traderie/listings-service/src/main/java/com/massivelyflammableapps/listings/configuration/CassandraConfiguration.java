package com.massivelyflammableapps.listings.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.massivelyflammableapps")
public class CassandraConfiguration extends AbstractCassandraConfiguration {
	@Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

	@Override
	public String[] getEntityBasePackages() {
		return new String[]{"com.massivelyflammableapps"};
	}

	@Override
	public String getKeyspaceName() {
		return "traderie_cassandra";
	}
}
