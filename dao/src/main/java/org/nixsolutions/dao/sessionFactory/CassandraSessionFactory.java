package org.nixsolutions.dao.sessionFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import lombok.Getter;
import lombok.Setter;

public class CassandraSessionFactory {
	@Getter
	@Setter
	private String keyspace = "weather";
	@Getter
	@Setter
	private String host = "127.0.0.1";
	private Cluster cluster;

	private CassandraSessionFactory() {
		configure(host, keyspace);
	}

	private CassandraSessionFactory(String host, String keyspace) {
		this.host = host;
		this.keyspace = keyspace;
		configure(host, keyspace);
	}

	public static CassandraSessionFactory getInstance() {
		return new CassandraSessionFactory();
	}

	public static CassandraSessionFactory getInstance(String host, String keyspace) {
		return new CassandraSessionFactory(host, keyspace);
	}

	public Session getSession() {
		return cluster.connect(keyspace);
	}

	private void configure(String host, String keyspace) {
		cluster = Cluster.builder().addContactPoint(host).build();
		cluster.connect().execute(String.format(
				"CREATE KEYSPACE IF NOT EXISTS %s WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };",
				keyspace));
		cluster.connect().execute(
				"CREATE TABLE if not exists weather.weather(cityName varchar PRIMARY KEY, zipCode int, temperature int, date varchar);");
	}
}
