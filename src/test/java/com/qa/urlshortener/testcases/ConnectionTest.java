package com.qa.urlshortener.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.urlshortener.base.TestBase;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class ConnectionTest extends TestBase {
	String url = prop.getProperty("url");

	@Test
	public void testResponseCode() {

		/*
		 * Response res = get("http://127.0.0.1:8080/test"); int code =
		 * res.getStatusCode();
		 */
		int code = get(url + "/test").getStatusCode();
		System.out.println("Status Code is : " + code);
		Assert.assertEquals(code, 200);
	}

	@Test
	public void testBody() {

		String data = get(url + "/test").asString();
		String expectedData = "Service is Up";
		System.out.println("Body is : " + data);
		// System.out.println("Response Time is : " + res.getTime());
		Assert.assertEquals(data, expectedData);
	}
}