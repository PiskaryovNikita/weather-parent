package org.nixsolutions.impl.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nixsolutions.dao.impl.WeatherDaoImpl;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;
import org.nixsolutions.impl.WeatherServiceImpl;
import org.nixsolutions.model.Weather;
import org.nixsolutions.model.WeatherService;

public class WeatherServiceRestWebTest {
	private static final String SERVER_URL = "http://localhost:8282";
	private static final String ENDPOINT_URL = SERVER_URL + "/weather";
	private static Server server;

	@BeforeClass
	public static void startServer() {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setAddress(SERVER_URL);
		factory.setResourceClasses(WeatherServiceImpl.class);
		
		WeatherDaoImpl weatherDao = new WeatherDaoImpl();
		weatherDao.setSessionFactory(CassandraSessionFactory.getInstance());
		WeatherService service = new WeatherServiceImpl(weatherDao);
		
		factory.setResourceProvider(new SingletonResourceProvider(service));
		factory.setProviders(new ArrayList<>(Arrays.asList(new JAXBElementProvider<>())));
		server = factory.create();
		server.start();

		WebClient client = WebClient.create(ENDPOINT_URL);
		Weather weather = new Weather("Kharkov", 1, 1, "2000-12-2");
		client.post(weather);
		weather = new Weather("Yantai", 1, 1, "2000-12-2");
		client.post(weather);
		weather = new Weather("Moscow", 1, 1, "2000-12-2");
		client.post(weather);
	}

	@Test
	public void testGet() {
		WebClient client = WebClient.create(ENDPOINT_URL + "/Kharkov");
		Weather returned = client.get(Weather.class);
		assertEquals("Kharkov", returned.getCityName());
	}

	@Test
	public void testGetAll() {
		WebClient client = WebClient.create(ENDPOINT_URL);
		Collection<? extends Weather> collection = client.getCollection(Weather.class);
		long count = collection.stream()
				.filter(i -> i.getCityName().equalsIgnoreCase("Yantai") || i.getCityName().equalsIgnoreCase("Kharkov"))
				.count();
		assertEquals(2, count);
	}

	@Test
	public void testPost() {
		Weather weather = new Weather("Chindao", 123, 123, "1998-12-12");
		WebClient client = WebClient.create(ENDPOINT_URL);
		weather = client.post(weather, Weather.class);
		assertEquals("Chindao", weather.getCityName());
	}

	@Test
	public void testPut() {
		WebClient client = WebClient.create(ENDPOINT_URL + "/Kharkov");
		Weather weather = new Weather("Kharkov", 123, 123, "1998-12-12");
		weather = client.put(weather, Weather.class);
		assertEquals(123, weather.getZipCode());
	}

	@Test
	public void testDelete() {
		WebClient client = WebClient.create(ENDPOINT_URL + "/Moscow");
		client.delete();
		client = WebClient.create(ENDPOINT_URL + "/Moscow");
		Weather weather = client.get(Weather.class);
		assertNull(weather);
	}

	@AfterClass
	public static void stopServer() {
		server.stop();
	}
}
