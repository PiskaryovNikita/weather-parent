package com.nixsolutions.wsxx.resource.jsonAdapter;

import java.sql.Date;
import java.time.LocalDate;

import javax.json.bind.adapter.JsonbAdapter;

public class DateJsonbAdapter implements JsonbAdapter<Date, LocalDate>  {

	@Override
	public LocalDate adaptToJson(Date obj) throws Exception {
		return obj.toLocalDate();
	}

	@Override
	public Date adaptFromJson(LocalDate obj) throws Exception {
		return Date.valueOf(obj);
	}
}
