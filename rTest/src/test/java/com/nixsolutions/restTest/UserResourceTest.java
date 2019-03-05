package com.nixsolutions.restTest;

import static com.nixsolutions.model.MyUtils.convertToJsonString;
import static com.nixsolutions.model.MyUtils.getRandomUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.nixsolutions.model.MyUtils;
import com.nixsolutions.model.Role;
import com.nixsolutions.model.User;

public class UserResourceTest {
	private final User userExp = new User(1l, "login1", "qwerty", "email1", "fn1", "ln1", Date.valueOf("1999-12-12"),
			new Role(2l, "Admin"));
	private final String expMimeType = "application/json";
	private final String resourceUrl = "http://10.10.103.92:8080/webapi/users/";
	private final String diffAcceptMimeType = "application/xml";

	// GET
	@Test
	public void testGetStatusCodeNotFound() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(resourceUrl + 13);
		HttpResponse resp = HttpClientBuilder.create().build().execute(request);
		String actualMimeType = ContentType.getOrDefault(resp.getEntity()).getMimeType();
		assertEquals(HttpStatus.SC_NOT_FOUND, resp.getStatusLine().getStatusCode());
		assertEquals(expMimeType, actualMimeType);
	}

	@Test
	public void testGetStatusCodeFound() throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(resourceUrl + 1);
		HttpResponse resp = HttpClientBuilder.create().build().execute(request);
		String actualMimeType = ContentType.getOrDefault(resp.getEntity()).getMimeType();
		assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());
		assertEquals(expMimeType, actualMimeType);
		User actual = MyUtils.retrieveResourceFromResponse(resp, User.class);
		assertEquals(userExp, actual);
	}

	// POST
	@Test
	public void testPostNotacceptable406() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(resourceUrl);
		//responded when checking headers
		post.setHeader("Accept", diffAcceptMimeType);
		post.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(post);
		assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPostBadreq400() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(resourceUrl);
		post.setHeader("Accept", expMimeType);
		post.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(post);
		assertEquals(HttpStatus.SC_BAD_REQUEST, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPost409() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(resourceUrl);
		String json = convertToJsonString(userExp);
		post.setEntity(new StringEntity(json));
		post.setHeader("Accept", expMimeType);
		post.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(post);
		assertEquals(expMimeType, resp.getHeaders("Content-type")[0].getValue());
		assertEquals(HttpStatus.SC_CONFLICT, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPostOk() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(resourceUrl);
		String json = convertToJsonString(getRandomUser());
		post.setEntity(new StringEntity(json));
		post.setHeader("Accept", expMimeType);
		post.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(post);
		assertNotNull(resp.getHeaders("Location")[0].getValue());
		assertEquals(expMimeType, resp.getHeaders("Content-type")[0].getValue());
		assertEquals(HttpStatus.SC_CREATED, resp.getStatusLine().getStatusCode());
	}

	// PUT
	@Test
	public void testPutNotacceptable() throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(resourceUrl + 3);
		put.setHeader("Accept", diffAcceptMimeType);
		put.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(put);
		assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPutBadReq() throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(resourceUrl + 2);
		put.setHeader("Accept", expMimeType);
		put.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(put);
		assertEquals(HttpStatus.SC_BAD_REQUEST, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPut404() throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(resourceUrl + 13);
		String json = convertToJsonString(getRandomUser());
		put.setEntity(new StringEntity(json));
		put.setHeader("Accept", expMimeType);
		put.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(put);
		// when datanotfoundexc then send json with 404 & error message
		assertEquals(expMimeType, resp.getHeaders("Content-type")[0].getValue());
		assertEquals(HttpStatus.SC_NOT_FOUND, resp.getStatusLine().getStatusCode());
	}

	@Test
	public void testPutOk() throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(resourceUrl + 41);
		String json = convertToJsonString(getRandomUser());
		put.setEntity(new StringEntity(json));
		put.setHeader("Accept", expMimeType);
		put.setHeader("Content-type", expMimeType);
		HttpResponse resp = HttpClientBuilder.create().build().execute(put);
		assertEquals(expMimeType, resp.getHeaders("Content-type")[0].getValue());
		assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());
	}

	// DELETE
	@Test
	public void testDeleteNotFound() throws ClientProtocolException, IOException {
		Long roleId = 13l;
		HttpUriRequest request = new HttpDelete(resourceUrl + roleId);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void testDeleteFound() throws ClientProtocolException, IOException {
		Long roleId = MyUtils.getLastUserId();
		HttpUriRequest request = new HttpDelete(resourceUrl + roleId);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		assertEquals(HttpStatus.SC_NO_CONTENT, httpResponse.getStatusLine().getStatusCode());
	}
}
