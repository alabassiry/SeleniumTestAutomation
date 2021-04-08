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

public class APITST_T8 {
	private final String URI = "http://localhost:3030";
	private String fileName;
	private final String newProductName = "New Product";
	private int id;

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
			String response1 = given().log().all().header("Content-Type","application/json").body("{\n" + 
					"  \"name\": \""+newProductName+"\",\n" + 
					"  \"type\": \"New Product Type\",\n" + 
					"  \"price\": 10,\n" + 
					"  \"shipping\": 5,\n" + 
					"  \"upc\": \"N/A\",\n" + 
					"  \"description\": \"New Product All in One\",\n" + 
					"  \"manufacturer\": \"China\",\n" + 
					"  \"model\": \"A1\",\n" + 
					"  \"url\": \"N/A\",\n" + 
					"  \"image\": \"N/A\"\n" + 
					"}").when().post("products").then().log().all().statusCode(201).extract()
					.response().asString();
			JsonPath jp1 = new JsonPath(response1);
			id = jp1.getInt("id");
			
			String response2 = given().log().all().when().param("id", id).get("products").then().log().all().statusCode(200).extract()
					.response().asString();
			JsonPath jp2 = new JsonPath(response2);
			String productName = jp2.getString("data.name[0]");
			Assert.assertEquals(newProductName, productName);
			
			String response3 = given().log().all().when().param("id", id).delete("products").then().log().all().statusCode(200).extract()
					.response().asString();
			JsonPath jp3 = new JsonPath(response3);
			Assert.assertEquals(jp3.getInt("id[0]"), id);
			Assert.assertEquals(jp3.getString("name[0]"), newProductName);
			
			String response4= given().log().all().when().param("id", id).get("products").then().log().all().statusCode(404).extract()
					.response().asString();
			JsonPath jp4 = new JsonPath(response4);
			Assert.assertEquals(jp4.getInt("name"), "NotFound");
			Assert.assertEquals(jp4.getString("message"), "No record found for id '"+id+"'");
			
			String response5= given().log().all().when().param("id", id).delete("products").then().log().all().statusCode(404).extract()
					.response().asString();
			JsonPath jp5 = new JsonPath(response5);
			Assert.assertEquals(jp5.getInt("name"), "NotFound");
			Assert.assertEquals(jp5.getString("message"), "No record found for id '"+id+"'");
		}
	}

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		//toggle logging in the console by removing this method
		fileReader(APILogsDirectory, fileName);
	}

}
