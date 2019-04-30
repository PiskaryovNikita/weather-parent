package org.nixsolutions.camel;

import org.apache.camel.builder.RouteBuilder;

public class CustomRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("http://localhost:8282/api/weather").id("mail")
		.setHeader("subject", simple("Weather"))
		.to("system:out")
		.to("log:start");
	}
}
