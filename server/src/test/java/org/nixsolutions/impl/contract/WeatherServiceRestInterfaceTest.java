package org.nixsolutions.impl.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nixsolutions.dao.impl.WeatherDaoImpl;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;
import org.nixsolutions.impl.WeatherServiceImpl;
import org.nixsolutions.model.Weather;
import org.nixsolutions.model.WeatherService;

public class WeatherServiceRestInterfaceTest {
	private static WeatherService service;

	@BeforeClass
	public static void startServer() {
		CassandraSessionFactory sessionFactory = CassandraSessionFactory.getInstance();
		WeatherDaoImpl weatherDaoImpl = new WeatherDaoImpl();
		weatherDaoImpl.setSessionFactory(sessionFactory);
		service = new WeatherServiceImpl(weatherDaoImpl);

		Weather weather = new Weather("Kharkov", 1, 1, "2000-12-2");
		service.create(weather);
		weather = new Weather("Yantai", 1, 1, "2000-12-2");
		service.create(weather);
		weather = new Weather("Moscow", 1, 1, "2000-12-2");
		service.create(weather);
	}

	@Test
	public void testGet() {
		assertEquals("Kharkov", service.get("Kharkov").getCityName());
	}

	@Test
	public void testGetAll() {
		Collection<? extends Weather> collection = (Collection<? extends Weather>) service.getAll();
		long count = collection.stream()
				.filter(i -> i.getCityName().equalsIgnoreCase("Yantai") || i.getCityName().equalsIgnoreCase("Kharkov"))
				.count();
		assertEquals(2, count);
	}

	@Test
	public void testPost() {
		Weather weather = new Weather("Chindao", 123, 123, "1998-12-12");
		weather = service.create(weather);
		assertEquals("Chindao", weather.getCityName());
	}

	@Test
	public void testPut() {
		Weather weather = new Weather("Kharkov", 123, 123, "1998-12-12");
		weather = service.update(weather.getCityName(), weather);
		assertEquals(123, weather.getZipCode());
	}

	@Test
	public void testDelete() {
		String city = "Moscow";
		service.delete(city);
		assertNull(service.get(city));
	}
}
