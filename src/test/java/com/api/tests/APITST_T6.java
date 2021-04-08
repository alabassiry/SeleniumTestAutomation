package com.api.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static helper.Commons.*;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import org.apache.commons.io.output.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class APITST_T6 {
	private final String URI = "http://localhost:3030";
	private String response;
	private String fileName;

	@BeforeTest
	public void setup() {
		RestAssured.baseURI = URI;
		String checkResponse = given().when().get("products").then().statusCode(200).extract()
				.response().asString();
		Assert.assertNotEquals(null, checkResponse);
	}

	@Test
	public void test() throws IOException {
		fileName = this.getClass().getSimpleName() + joiningChar() + getRandomFilePrefix(5) + fileType("log");
		try (FileWriter fileWriter = new FileWriter(APILogsDirectory + fileName);
				PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true)) {
			RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
			response = given().log().all().queryParam("$limit", "10").when().get("products").then().log().all().statusCode(200).extract()
					.response().asString();
		}
		JsonPath jp = new JsonPath(response);
		int count = jp.getInt("data.size()");
		Assert.assertEquals(10, count);
	}

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		//toggle logging in the console by removing this method
		fileReader(APILogsDirectory, fileName);
	}

}
