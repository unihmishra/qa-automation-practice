package com.learning.utilities;

import static io.restassured.RestAssured.expect;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Author: Harsh Mishra
 * Date: Dec-2018
 */
public class API {
	
	private static final String audienceAPIURL = ConfigReader.getConfig("api_url");
	private static final String basicAuth = UtilityMethods.getBasicAuth(ConfigReader.getConfig("api_username"), ConfigReader.getConfig("api_password"));
	
	public static synchronized String createNewMemberAndSub(String phoneNumber, String listId) throws Exception {
		HashMap<String, String> member = new HashMap<String, String>();
		member.put("mobilephone", phoneNumber);
		JSONObject xy = new JSONObject(member);
		String jsonBody = "{\"body\": { \"member\": [ " + xy.toString() + " ], \"listid\": \"" + listId + "\" } }";
		Response r = expect().statusCode(200).given().contentType("application/json")
				.header("Authorization", basicAuth)
				.header("X-Ms-Ignore-Error-For-Existing-Members", "true").header("X-Ms-Format", "json")
				.header("X-Ms-Source", "api")
				.header("X-Ms-Audience-Update", "upsert")
				.header("X-Ms-Update","included_fields_only")
				.body(jsonBody).log().all().when().put(audienceAPIURL);
		String body = r.getBody().asString();
		System.out.println(body);
		Assert.assertEquals(r.header("Content-Type"), "application/json;charset=utf-8");
		Assert.assertEquals(r.statusCode(), 200);
		JsonPath jsonPathEvaluator = r.jsonPath();
		Assert.assertEquals(jsonPathEvaluator.get("body.members[0].member.result").toString(), "success");
		return jsonPathEvaluator.get("body.members[0].member.id.memberid").toString();
	}
	
    public static synchronized JsonPath createCampaign(String jsonBody, int statusCode) {
        RestAssured.baseURI = ConfigReader.getConfig("apollo_program_host_api");

        Response r = expect().given()
                .header("Authorization", ConfigReader.getConfig("apollo_api_key"))
                .header("Content-Type", ConfigReader.getConfig("apollo_content"))
                .body(jsonBody).log().all().when().post();
        System.out.println(r.getBody().asString());
        Assert.assertEquals(r.statusCode(), statusCode);
        Assert.assertEquals(r.header("Content-Type"), "application/json");
        return r.jsonPath();
    }
    
    public static synchronized HashMap<String, String> eventUploadAPI_JSON_V2(Map<String, String> eventDataInMap,
			String eventName, String listId, String scheduledOn, String timeZone, String evaluationScope,
			String verbosity, int expectedStatusCode) {
		eventDataInMap.put("scheduled_on", scheduledOn);
		eventDataInMap.put("timezone", timeZone);
		eventDataInMap.put("evaluation_scope", evaluationScope);

		JSONObject xy = new JSONObject(eventDataInMap);

		String jsonBody = "{ \"body\": { \"events\": { \"name\": \"" + eventName + "\", \"event\": " + xy.toString()
				+ " }, \"listid\": \"" + listId + "\" } }";
		if (verbosity.equalsIgnoreCase("full")) {
			Response r = expect().given().contentType("application/json")
					.header("Authorization", UtilityMethods.getBasicAuth(ConfigReader.getConfig("api_username"), ConfigReader.getConfig("api_password")))
					.header("X-Ms-API-Version", "2.0")
					.header("X-Ms-Format", "json").header("X-Ms-Source", "api").header("X-Ms-Verbosity", "full")
					.body(jsonBody).log().all().when().post(ConfigReader.getConfig("eventUploadApiUrl"));
			String body = r.getBody().asString();
			System.out.println(body);
			if (r.statusCode() == 400) {
				Assert.assertEquals(r.statusCode(), expectedStatusCode);
				Assert.assertEquals(r.header("Content-Type"), "application/json;charset=utf-8");
				JsonPath jsonPathEvaluator = r.jsonPath();
				HashMap<String, String> resultset = new HashMap<String, String>();
				resultset.put("processed_event_instances",
						jsonPathEvaluator.getString("body.results.processed_event_instances"));
				resultset.put("scheduled_messages", jsonPathEvaluator.getString("body.results.scheduled_messages"));
				resultset.put("errors", jsonPathEvaluator.getString("body.results.errors"));
				resultset.put("statusCode", String.valueOf(r.statusCode()));
				if (jsonPathEvaluator.getInt("body.results.processed_event_instances") == 0) {
					resultset.put("error_message",
							jsonPathEvaluator.getString("body.results.details.events_error.error_message"));
				} else {
					resultset.put("error_message",
							jsonPathEvaluator.getString("body.results.details.events.event.errors.error.error_message"));
					resultset.put("memberid", jsonPathEvaluator.getString("body.results.details.events.event.memberid"));
					resultset.put("event_definition_name",
							jsonPathEvaluator.getString("body.results.details.events.event_definition_name"));
					if (eventDataInMap.containsKey("mobilephone")) {
						resultset.put("mobilephone", jsonPathEvaluator
								.getString("body.results.details.events.event.event_instance_body.body.event.mobilephone"));
					}
					if (eventDataInMap.containsKey("email")) {
						resultset.put("email", jsonPathEvaluator
								.getString("body.results.details.events.event.event_instance_body.body.event.email"));
					}
					if (eventDataInMap.containsKey("correlationid")) {
						resultset.put("email", jsonPathEvaluator
								.getString("body.results.details.events.event.event_instance_body.body.event.correlationid"));
					}
				}
				return resultset;
			} else {
				Assert.assertEquals(r.statusCode(), expectedStatusCode);
				Assert.assertEquals(r.header("Content-Type"), "application/json;charset=utf-8");
				JsonPath jsonPathEvaluator = r.jsonPath();
				Assert.assertEquals(jsonPathEvaluator.get("body.results.details.listid").toString(),
						String.valueOf(listId));
				HashMap<String, String> resultset = new HashMap<String, String>();
				resultset.put("processed_event_instances",
						jsonPathEvaluator.getString("body.results.processed_event_instances"));
				resultset.put("scheduled_messages", jsonPathEvaluator.getString("body.results.scheduled_messages"));
				resultset.put("errors", jsonPathEvaluator.getString("body.results.errors"));
				resultset.put("event_definition_name",
						jsonPathEvaluator.getString("body.results.details.events.event_definition_name"));
				resultset.put("memberid", jsonPathEvaluator.getString("body.results.details.events.event.memberid"));
				if (jsonPathEvaluator.getString(
						"body.results.details.events.event.scheduled_messages") != null) {
					resultset.put("message_id", jsonPathEvaluator.getString(
							"body.results.details.events.event.scheduled_messages.scheduled_message.message_id"));
				}
				
				if (jsonPathEvaluator.getString(
						"body.results.details.events.event.errors") != null) {
					resultset.put("error_message", jsonPathEvaluator.getString(
							"body.results.details.events.event.errors.error.error_message"));
				}
				
				resultset.put("scheduled_on", jsonPathEvaluator
						.getString("body.results.details.events.event.event_instance_body.body.event.scheduled_on"));
				resultset.put("evaluation_scope", jsonPathEvaluator.getString(
						"body.results.details.events.event.event_instance_body.body.event.evaluation_scope"));
				resultset.put("statusCode", String.valueOf(r.statusCode()));
				if (eventDataInMap.containsKey("mobilephone")) {
					resultset.put("mobilephone", jsonPathEvaluator
							.getString("body.results.details.events.event.event_instance_body.body.event.mobilephone"));
				}
				if (eventDataInMap.containsKey("email")) {
					resultset.put("email", jsonPathEvaluator
							.getString("body.results.details.events.event.event_instance_body.body.event.email"));
				}
				if (eventDataInMap.containsKey("correlationid")) {
					resultset.put("email", jsonPathEvaluator
							.getString("body.results.details.events.event.event_instance_body.body.event.correlationid"));
				}
				return resultset;
			}
		} else {
			Response r = expect().given().contentType("application/json")
					.header("Authorization", UtilityMethods.getBasicAuth(ConfigReader.getConfig("api_username"), ConfigReader.getConfig("api_password")))
					.header("X-Ms-API-Version", "2.0")
					.header("X-Ms-Format", "json").header("X-Ms-Source", "api").body(jsonBody).log().all().when()
					.post(ConfigReader.getConfig("eventUploadApiUrl"));
			String body = r.getBody().asString();
			System.out.println(body);
			if (r.statusCode() == 400) {
				Assert.assertEquals(r.statusCode(), expectedStatusCode);
				Assert.assertEquals(r.header("Content-Type"), "application/json;charset=utf-8");
				JsonPath jsonPathEvaluator = r.jsonPath();
				HashMap<String, String> resultset = new HashMap<String, String>();
				resultset.put("processed_event_instances",
						jsonPathEvaluator.getString("body.results.processed_event_instances"));
				resultset.put("scheduled_messages", jsonPathEvaluator.getString("body.results.scheduled_messages"));
				resultset.put("errors", jsonPathEvaluator.getString("body.results.errors"));
				resultset.put("statusCode", String.valueOf(r.statusCode()));
				if (jsonPathEvaluator.getInt("body.results.processed_event_instances") == 0) {
					resultset.put("error_message",
							jsonPathEvaluator.getString("body.results.details.events_error.error_message"));
				} else {
					resultset.put("error_message",
							jsonPathEvaluator.getString("body.results.details.events.event.errors.error.error_message"));
				}
				return resultset;
			} else {
				Assert.assertEquals(r.header("Content-Type"), "application/json;charset=utf-8");
				Assert.assertEquals(r.statusCode(), expectedStatusCode);
				JsonPath jsonPathEvaluator = r.jsonPath();
				Assert.assertEquals(jsonPathEvaluator.get("body.results.details.listid").toString(),
						String.valueOf(listId));
				HashMap<String, String> resultset = new HashMap<String, String>();
				resultset.put("processed_event_instances",
						jsonPathEvaluator.getString("body.results.processed_event_instances"));
				resultset.put("scheduled_messages", jsonPathEvaluator.getString("body.results.scheduled_messages"));
				resultset.put("errors", jsonPathEvaluator.getString("body.results.errors"));
				resultset.put("event_definition_name",
						jsonPathEvaluator.getString("body.results.details.events.event_definition_name"));
				resultset.put("memberid", jsonPathEvaluator.getString("body.results.details.events.event.memberid"));
				if (jsonPathEvaluator.getString(
						"body.results.details.events.event.scheduled_messages") != null) {
					resultset.put("message_id", jsonPathEvaluator.getString(
							"body.results.details.events.event.scheduled_messages.scheduled_message.message_id"));
				}
				if (jsonPathEvaluator.getString(
						"body.results.details.events.event.errors") != null) {
					resultset.put("error_message", jsonPathEvaluator.getString(
							"body.results.details.events.event.errors.error.error_message"));
				}
				resultset.put("statusCode", String.valueOf(r.statusCode()));
				return resultset;
			}
		}
	}
}
