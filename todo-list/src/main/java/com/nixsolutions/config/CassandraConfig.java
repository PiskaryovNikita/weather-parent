package com.nixsolutions.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PlainTextAuthProvider;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
	@Value("${cassandra.entity.package}")
	private String ENTITY_PACKAGE;
	@Value("${spring.data.cassandra.keyspace-name}")
	private String KEYSPACE;
	@Value("${spring.data.cassandra.username}")
	private String USERNAME;
	@Value("${spring.data.cassandra.password}")
	private String PASSWORD;
	

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected String getKeyspaceName() {
		return KEYSPACE;
	}
	
	@Override
	protected AuthProvider getAuthProvider() {
		return new PlainTextAuthProvider(USERNAME, PASSWORD);
	}
	
	@Override
	public String[] getEntityBasePackages() {
		return new String[] { ENTITY_PACKAGE };
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(KEYSPACE).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication());
	}

	//exec scripts on initialization
	
//	@Override
//	protected List<String> getStartupScripts() {
//		return Arrays.asList("USE " + KEYSPACE + ";",
//				"INSERT INTO todo (id, name) VALUES (now(), 'springEurekaServer" + (int)(Math.random() * 1000_1000)
//						+ "');");
//	}
}
