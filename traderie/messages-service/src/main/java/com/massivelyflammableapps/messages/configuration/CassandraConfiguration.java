package com.massivelyflammableapps.messages.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
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
		return new String[] { "com.massivelyflammableapps.messages.model" };
	}

	@Override
	public String getKeyspaceName() {
		return "traderie_cassandra";
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
				.createKeyspace("traderie_cassandra").ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
		return Arrays.asList(specification);
	}
}
