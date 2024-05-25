package com.qa.urlshortener.tests.backend;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.urlshortener.base.TestBase;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class ConnectionTest extends TestBase {
	String url = prop.getProperty("url");

	/*@Test(enabled = false)
	public void testResponseCode() {
		int code = get(url + "/test").getStatusCode();
		System.out.println("Status Code is : " + code);
		Assert.assertEquals(code, 200);
	}

	@Test(enabled = false)
	public void testBody() {

		String data = get(url + "/test").asString();
		String expectedData = "Service is Up";
		System.out.println("Body is : " + data);
		Assert.assertEquals(data, expectedData);
	}
*/
	@Test(description = "Test connection for service")
	public void testConn() {
		RequestSpecification request = RestAssured.given();
		Response response = request.get(url+"/test");
		int statusCode = response.getStatusCode();
		String body = response.getBody().print();
		Assert.assertEquals(statusCode, 200, "Status code is : "+ statusCode);
		Assert.assertNotNull(body, "Response is null");
	}
}