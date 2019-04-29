package org.nixsolutions.impl;

import org.nixsolutions.dao.contract.WeatherDao;
import org.nixsolutions.model.Weather;
import org.nixsolutions.model.WeatherService;

public class WeatherServiceImpl implements WeatherService {
	private WeatherDao weatherDao;

	public WeatherServiceImpl() {
	}

	public WeatherServiceImpl(WeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}

	@Override
	public Iterable<Weather> getAll() {
		return weatherDao.getAll();
	}

	@Override
	public Weather get(String city) {
		return weatherDao.get(city);
	}

	@Override
	public Weather create(Weather entity) {
		return weatherDao.save(entity);
	}

	@Override
	public Weather update(String city, Weather entity) {
		return weatherDao.update(city, entity);
	}

	@Override
	public void delete(String city) {
		weatherDao.delete(city);
	}
}
