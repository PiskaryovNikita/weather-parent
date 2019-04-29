package org.nixsolutions.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.nixsolutions.dao.contract.WeatherDao;
import org.nixsolutions.dao.sessionFactory.CassandraSessionFactory;
import org.nixsolutions.model.Weather;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import lombok.Setter;

public class WeatherDaoImpl implements WeatherDao {
	@Setter
	private CassandraSessionFactory sessionFactory;
	@Setter
	private String table = "WEATHER";// default

	public WeatherDaoImpl() {
	}

	public WeatherDaoImpl(CassandraSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Weather save(Weather entity) {
		Session session = sessionFactory.getSession();
		try {
			String query = String.format(
					"INSERT INTO %s (cityName, zipCode, temperature, date) values ('%s', %d, %d, '%s');", table,
					entity.getCityName(), entity.getZipCode(), entity.getTemperature(), entity.getDate().toString());
			session.execute(query);
			return entity;
		} finally {
			session.close();
		}
	}

	@Override
	public Iterable<Weather> getAll() {
		Session session = sessionFactory.getSession();
		try {
			String query = String.format("SELECT * FROM %s ;", table);
			ResultSet rs = session.execute(query);
			List<Weather> list = new ArrayList<Weather>();
			rs.forEach(r -> {
				Weather weather = new Weather();
				weather.setCityName(r.getString("cityName"));
				weather.setDate((r.getString("date")));
				weather.setTemperature(r.getInt("temperature"));
				weather.setZipCode(r.getInt("zipCode"));
				list.add(weather);
			});
			return list;
		} finally {
			session.close();
		}
	}

	@Override
	public Weather get(String city) {
		Session session = sessionFactory.getSession();
		try {
			String query = String.format("SELECT * FROM %s WHERE cityName = '%s';", table, city);
			ResultSet rs = session.execute(query);
			Row row = rs.one();
			if (row != null) {
				Weather weather = new Weather();
				weather.setCityName(row.getString("cityName"));
				weather.setDate(row.getString("date"));
				weather.setTemperature(row.getInt("temperature"));
				weather.setZipCode(row.getInt("zipCode"));
				return weather;
			}
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public Weather update(String city, Weather entity) {
		Session session = sessionFactory.getSession();
		try {
			entity.setCityName(city);
			String query = String.format(
					"UPDATE %s SET zipCode = %d, temperature = %d, date = '%s' WHERE cityName = '%s';", table,
					entity.getZipCode(), entity.getTemperature(), entity.getDate().toString(), entity.getCityName());
			session.execute(query);
			return entity;
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(String city) {
		String query = String.format("DELETE FROM %s WHERE cityName = '%s';", table, city);
		Session session = sessionFactory.getSession();
		try {
			session.execute(query);
		} finally {
			session.close();
		}
	}

}
