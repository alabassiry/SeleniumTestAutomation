package com.Jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import helper.PropertiesLoader;

public class zephyr {
	public final static String postURL = "https://api.adaptavist.io/tm4j/v2/testexecutions";
	PropertiesLoader properties;

	public int postExecutionResult(String testcaseID, String executionCycle, String tester, String exeStatus,
			int failedStep, String failedMsg, String env) throws Exception {
		System.out.println("::: Posting results to Jira initiated... :::");
		URL url = new URL(postURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(30000);
		con.setReadTimeout(30000);
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer"  + " " +  getTesterToken(tester));
		con.setRequestProperty("Content-Type", "application/json");
		String parent = constructJSON(testcaseID, tester, executionCycle, exeStatus, failedStep, failedMsg, env);
		OutputStream os = con.getOutputStream();
		os.write(parent.getBytes("UTF-8"));
		os.close();
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			System.out.println("::: Test execution saved successfully! :) :::");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
		} else {
			responseCode = 0;
		}
		System.out.println("::: Results posted on Jira successfully! :::");
		return responseCode;
	}

	private String getTesterToken(String tester) throws Exception {
		String token = "";
		properties = new PropertiesLoader(tester);
		token = properties.fetchProperty("zephyr.token");
		return token;
	}

	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private int getTestStepsNumber(String testcasekey, String tester) throws Exception {
		System.out.println("::: Getting test steps count from Jira :::");
		String getURL = "https://api.adaptavist.io/tm4j/v2/testcases/" + testcasekey + "/teststeps";
		URL url = new URL(getURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(30000);
		con.setReadTimeout(30000);
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer" + " " + getTesterToken(tester));
		InputStream response = con.getInputStream();
		JSONObject json = new JSONObject(convertStreamToString(response));
		Object steps = json.get("total");
		String stepsSTR = steps.toString();
		int stepsCount = Integer.valueOf(stepsSTR);
		System.out.println("::: Test steps count (" + stepsCount + ") are fetched successfully from Jira! :::");
		return stepsCount;
	}

	private String constructJSON(String tstcsKey,String tstr, String tstCyclKey, String status, int failedStp, String failedMsg,
			String env) throws Exception {
		System.out.println("::: Constructing JSON file... :::");
		int steps = getTestStepsNumber(tstcsKey, tstr);
		String output = "";
		String comment = "";
		JSONObject json = new JSONObject();
		JSONObject nestedJson1 = new JSONObject();
		JSONObject nestedJson2 = new JSONObject();
		JSONObject nestedJson3 = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		json.put("projectKey", "TST");
		json.put("testCaseKey", tstcsKey);
		json.put("testCycleKey", tstCyclKey);
		json.put("statusName", status);
		if (failedStp != 0) {
			comment = "Test failed! See the html report for more info";
			for (int i = 1; i <= failedStp; i++) {
				if (i == failedStp) {
					nestedJson1.put("statusName", "Fail");
					nestedJson1.put("actualResult", failedMsg);
					jsonArr.put(nestedJson1);
					for (int o = failedStp + 1; o <= steps; o++) {
						nestedJson3.put("statusName", "Blocked");
						nestedJson3.put("actualResult", "Blocked");
						jsonArr.put(nestedJson3);
					}
				} else {
					nestedJson2.put("statusName", "Pass");
					nestedJson2.put("actualResult", "Passed!");
					jsonArr.put(nestedJson2);
				}
			}
			json.put("testScriptResults", jsonArr);
		} else {
			for (int z = 1; z <= steps; z++) {
				nestedJson1.put("statusName", "Pass");
				nestedJson1.put("actualResult", "Passed!");
				jsonArr.put(nestedJson1);
			}
			json.put("testScriptResults", jsonArr);
			comment = "Test Passed! See the html report for more info";
		}
		json.put("environmentName", env);
		json.put("comment", comment);
		output = json.toString();
		System.out.println("::: JSON file constructed successfully! :::");
		return output;
	}
}
