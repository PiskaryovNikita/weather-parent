package org.nixsolutions.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class Weather {
    private String cityName;
	private int zipCode;
	private int temperature;
    private String date;
    
    public Weather() {
	}
    
	public Weather(String cityName, int zipCode, int temperature, String date) {
		this.cityName = cityName;
		this.zipCode = zipCode;
		this.temperature = temperature;
		this.date = date;
	}
}
