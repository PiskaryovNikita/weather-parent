package org.nixsolutions.dao.contract;

import org.nixsolutions.model.Weather;

public interface WeatherDao {
	Weather save(Weather entity);
	Iterable<Weather> getAll();
	Weather get(String city);
	Weather update(String city, Weather entity);
	void delete(String city);
}
