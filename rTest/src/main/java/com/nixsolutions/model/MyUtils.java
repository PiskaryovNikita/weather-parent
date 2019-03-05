package com.nixsolutions.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;
import java.sql.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyUtils {

	public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {
		String jsonFromResponse = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(jsonFromResponse, clazz);
	}

	public static Long getLastRoleId() throws IOException {
		HttpUriRequest request = new HttpGet("http://10.10.103.92:8080/webapi/roles/");
		HttpResponse resp = HttpClientBuilder.create().build().execute(request);
		Role[] roles = retrieveResourceFromResponse(resp, Role[].class);
		return roles[roles.length - 1].getRoleId();
	}

	public static Long getLastUserId() throws IOException {
		HttpUriRequest request = new HttpGet("http://10.10.103.92:8080/webapi/users/");
		HttpResponse resp = HttpClientBuilder.create().build().execute(request);
		User[] users = retrieveResourceFromResponse(resp, User[].class);
		return users[users.length - 1].getUserId();
	}

	public static Role getRandomRole() {
		return new Role(0l, randomAlphabetic(10));
	}

	public static User getRandomUser() {
		return new User(randomAlphabetic(10), randomAlphabetic(10), randomAlphabetic(10), randomAlphabetic(10),
				randomAlphabetic(10), Date.valueOf("1999-12-12"), new Role(1l, "User"));
	}

	public static String convertToJsonString(Object object)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
