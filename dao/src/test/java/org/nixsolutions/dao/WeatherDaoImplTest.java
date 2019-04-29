package org.nixsolutions.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nixsolutions.dao.impl.WeatherDaoImpl;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;
import org.nixsolutions.model.Weather;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class WeatherDaoImplTest {
	private static final String HOST = "127.0.0.1";
	private static final String KEYSPACE = "weather";
	private static final String TABLE = "weathertest";

	private static WeatherDaoImpl dao;
	private static Cluster cluster;
	private Weather weather;

	@BeforeClass
	public static void executeScript() {
		cluster = Cluster.builder().addContactPoint(HOST).build();
		Session session = cluster.connect(KEYSPACE);
		try {
			session.execute("create table if not exists " + TABLE
					+ "(cityName varchar PRIMARY KEY, zipCode int, temperature int, date varchar);");
			session.execute("truncate table " + TABLE + ";");
		} finally {
			session.close();
		}
		dao = new WeatherDaoImpl();
		dao.setSessionFactory(CassandraSessionFactory.getInstance(HOST, KEYSPACE));
		dao.setTable(TABLE);
	}

	@Before
	public void setUp() {
		weather = new Weather();
		weather.setCityName("Shanhai");
		weather.setDate("1998-08-06");
		weather.setTemperature(13);
		weather.setZipCode(12345);
		dao.save(weather);
	}

	@Test
	public void testCreate() {
		ResultSet set = cluster.connect(KEYSPACE)
				.execute(String.format("select count(*) from %s WHERE cityName = '%s';", TABLE, weather.getCityName()));
		assertTrue(set.iterator().hasNext());
	}

	@Test
	public void testGet() {
		weather = dao.get(weather.getCityName());
		assertNotNull(weather);
	}

	@Test
	public void testGetAll() {
		Iterable<Weather> iterable = dao.getAll();
		assertTrue(iterable.iterator().hasNext());
	}

	@Test
	public void testUpdate() {
		weather.setDate("1999-12-12");
		weather.setTemperature(987);
		weather.setZipCode(98765);
		dao.update(weather.getCityName(), weather);
		ResultSet set = cluster.connect(KEYSPACE)
				.execute(String.format("select zipCode from %s WHERE cityName = '%s';", TABLE, weather.getCityName()));
		assertEquals(98765, set.one().getInt("zipCode"));
	}

	@Test
	public void testDelete() {
		weather = new Weather("Moscow", 123, 123, "1999-12-12");
		dao.save(weather);
		dao.delete(weather.getCityName());
		ResultSet set = cluster.connect(KEYSPACE)
				.execute(String.format("select * from %s WHERE cityName = '%s';", TABLE, weather.getCityName()));
		assertNull(set.one());
	}

	@AfterClass
	public static void release() {
		cluster = Cluster.builder().addContactPoint(HOST).build();
		Session session = cluster.connect(KEYSPACE);
		try {
			session.execute("truncate table " + TABLE + ";");
		} finally {
			session.close();
		}
	}
}
