package com.qa.urlshortener.testcases;

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
	String longUrl = prop.getProperty("longUrl");;

	@SuppressWarnings("unchecked")
	@Test(priority = 0, enabled = true)
	public void generateTest() throws JsonProcessingException, IOException {
		RequestSpecification request = given();
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("url", longUrl);
		request.body(json.toJSONString());
		Response response = request.post(url + "/generate");
		int code = response.getStatusCode();
		System.out.println("Response code is : " + code);
		Assert.assertEquals(code, 200);

	}

	@Test(priority = 1, enabled = true)
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
		String shortUrl = "";
		RequestSpecification request = given();
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("url", longUrl);
		request.body(json.toJSONString());
		Response response = request.post(url + "/generate");
		int code = response.getStatusCode();
		System.out.println("Response code for generate is : " + code);
		System.out.println("Response is : " + response.asString());
		shortUrl = getShortUrl(response.asString());
		System.out.println("Short link is : " + shortUrl);
		RequestSpecification request2 = given();
		request2.header("Content-Type", "application/json");
		request2.redirects().follow(false);
		Response response2 = request2.get(url + "/" + shortUrl);
		String redirectUrl = response2.getHeader("Location");
		int code2 = response2.getStatusCode();
		System.out.println("Response code for redirect is : " + code2);
		System.out.println("Redirect url is : " + redirectUrl);
		Assert.assertEquals(redirectUrl, longUrl);

	}

	private String getShortUrl(String data) throws JsonProcessingException, IOException {
		String shortLink = "";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(data);
		shortLink = jsonNode.get("shortLink").asText();
		return shortLink;
	}

}
