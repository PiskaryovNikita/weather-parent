package org.nixsolutions.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.nixsolutions.dao.impl.WeatherDaoImpl;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;

public class WeatherServiceStarter {

	public void startRestService() {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setAddress("http://localhost:8282/api");
		factory.setResourceClasses(WeatherServiceImpl.class);
		WeatherDaoImpl weatherDao = new WeatherDaoImpl();
		weatherDao.setSessionFactory(CassandraSessionFactory.getInstance());
		WeatherServiceImpl serviceImpl = new WeatherServiceImpl(weatherDao);
		factory.setResourceProvider(new SingletonResourceProvider(serviceImpl));
		factory.setProviders(new ArrayList<>(Arrays.asList(new JAXBElementProvider<>())));
		Server server = factory.create();
		server.start();
	}

	public static void main(String[] args) throws IOException {
		try {
			new WeatherServiceStarter().startRestService();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
