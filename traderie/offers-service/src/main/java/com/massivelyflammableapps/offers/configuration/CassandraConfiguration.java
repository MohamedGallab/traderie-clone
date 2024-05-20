package com.massivelyflammableapps.offers.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration {
	@Value("${spring.cassandra.keyspace-name}")
	private String keyspaceName;

	@Value("${spring.cassandra.contact-points}")
	private String contactPoints;
	
	@Value("${spring.cassandra.port}")
	private int port;

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints + ":" + port;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.massivelyflammableapps.offers.model" };
	}

	@Override
	public String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
				.createKeyspace(keyspaceName).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
		return Arrays.asList(specification);
	}
}
