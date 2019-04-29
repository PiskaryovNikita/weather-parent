package org.nixsolutions.model;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/weather")
@Produces(value = MediaType.APPLICATION_XML)
@Consumes(value = MediaType.APPLICATION_XML)
public interface WeatherService {
	@GET
	Iterable<Weather> getAll();
	@GET
	@Path(value = "/{city}")
	Weather get(@PathParam("city") String date);
	@POST
	Weather create(Weather entity);
	@PUT
	@Path(value = "/{city}")
	Weather update(@PathParam("city") String date, Weather entity);
	@DELETE
	@Path(value = "/{city}")
	void delete(@PathParam("city") String date);
}
