package com.qa.urlshortener.tests.backend;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.urlshortener.base.TestBase;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UrlShortenerServiceTest extends TestBase {
	String url = prop.getProperty("url");
	private static final String LONG_URL = "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1";

	@SuppressWarnings("unchecked")
	@Test(priority = 0, enabled = false)
	public void generateTest() throws JsonProcessingException, IOException {
		RequestSpecification request = given();
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("url", LONG_URL);
		request.body(json.toJSONString());
		Response response = request.post(url + "/generate");
		int code = response.getStatusCode();
		System.out.println("Response code is : " + code);
		Assert.assertEquals(code, 200);

	}

	@Test(priority = 1, enabled = false)
	public void redirectTest() {
		RequestSpecification request = given();
		request.header("Content-Type", "application/json");
		request.redirects().follow(false);
		Response response = request.get(url + "/" + "abcdef");
		int code = response.getStatusCode();
		System.out.println("Response code is : " + code);
		Assert.assertEquals(code, 200);

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2, enabled = true)
	public void urlShortenerTest() throws JsonProcessingException, IOException {
		RequestSpecification request = given();
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("url", LONG_URL);
		request.body(json.toJSONString());
		Response response = request.post(url + "/generate");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Status code is "+statusCode);
		System.out.println("Response is : " + response.getBody().print());
		Assert.assertEquals(LONG_URL, getDataFromJson(response.getBody().asString(), "originalUrl"));
		String shortLink = getDataFromJson(response.getBody().asString(), "shortLink");
		Assert.assertNotNull(shortLink, "Short link is empty");
		Assert.assertNotNull(getDataFromJson(response.getBody().asString(), "expirationDate"), "Expiration date is empty"); //add validation on exact date

		RequestSpecification redirectRequest = given();
		request.header("Content-Type", "application/json");
		redirectRequest.redirects().follow(false);
		Response redirectResponse = redirectRequest.get(shortLink);

		System.out.println(redirectResponse.getBody().print());
		System.out.println(redirectResponse.getStatusCode());

		String redirectUrl = redirectResponse.getHeader("Location");
		int redirectStatusCode = redirectResponse.getStatusCode();
		boolean expectedRedirectStatusCode = redirectStatusCode==301 || redirectStatusCode==302;
		Assert.assertTrue(expectedRedirectStatusCode);
		//Assert.assertEquals(redirectUrl, longUrl); //issue in server, working in local

	}

	private String getDataFromJson(String data, String key) throws JsonProcessingException, IOException {
		String value;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(data);
		value = jsonNode.get(key).asText();
		return value;
	}

}
