package org.nixsolutions.camel;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.nixsolutions.dao.impl.WeatherDaoImpl;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;
import org.nixsolutions.impl.WeatherServiceImpl;

public class Starter {

	public void run() throws Exception {
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

		JndiContext jndi = new JndiContext();
		jndi.bind("server", serviceImpl);
		CamelContext context = new DefaultCamelContext(jndi);
		context.addComponent("system", );
		context.addRoutes(new CustomRoute());
		context.start();
		System.in.read();
		context.stop();
	}

	public static void main(String[] args) throws Exception {
		try {
			new Starter().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
