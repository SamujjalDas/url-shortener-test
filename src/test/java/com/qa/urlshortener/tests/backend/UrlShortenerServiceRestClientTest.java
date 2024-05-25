package com.qa.urlshortener.tests.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.qa.urlshortener.base.TestBase;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UrlShortenerServiceRestClientTest extends TestBase {
	String url = prop.getProperty("url");
	private static final String LONG_URL = "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1";


	@SuppressWarnings("unchecked")
	@Test(priority = 2, enabled = true)
	public void urlShortenerTest() throws IOException, UnirestException {

		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("url", LONG_URL);
		String body = json.toJSONString();
		Unirest.setTimeouts(0, 0);
		HttpRequestWithBody request = Unirest.post(url + "/generate").headers(header);
		HttpResponse generateLongUrlResponse = request.body(body).asJson();
		System.out.println(generateLongUrlResponse.getBody().toString());
		int statusCode = generateLongUrlResponse.getStatus();
		Assert.assertEquals(statusCode, 200, "Status code is "+statusCode);
		System.out.println("Response is : " + generateLongUrlResponse.getBody().toString());
		Assert.assertEquals(LONG_URL, getDataFromJson(generateLongUrlResponse.getBody().toString(), "originalUrl"));
		String shortLink = getDataFromJson(generateLongUrlResponse.getBody().toString(), "shortLink");
		Assert.assertNotNull(shortLink, "Short link is empty");
		Assert.assertNotNull(getDataFromJson(generateLongUrlResponse.getBody().toString(), "expirationDate"), "Expiration date is empty"); //add validation on exact date

		Unirest.setHttpClient(org.apache.http.impl.client.HttpClients.custom()
				.disableRedirectHandling()
				.build());
		GetRequest getRequest = Unirest.get(shortLink).headers(header);
		HttpResponse redirectResponse = getRequest.asString();
		System.out.println("Redirect response is : " + redirectResponse.getBody().toString() + "\n and  Status code is " + redirectResponse.getStatus());

		int redirectStatusCode = redirectResponse.getStatus();
		boolean expectedRedirectStatusCode = redirectStatusCode==301 || redirectStatusCode==302;
		Assert.assertTrue(expectedRedirectStatusCode);
	}

	private String getDataFromJson(String data, String key) throws JsonProcessingException, IOException {
		String value;
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.JsonNode jsonNode = mapper.readTree(data);
		value = jsonNode.get(key).asText();
		return value;
	}

}
