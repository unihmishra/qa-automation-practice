package com.learning.testcases;

import static io.restassured.RestAssured.expect;

import org.testng.Assert;

import com.learning.factories.ReporterFactory;
import com.learning.utilities.ConfigReader;
import com.learning.utilities.UtilityMethods;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
/**
 * Author: Harsh Mishra
 * Date: Jan-2018
 */
public class RestAssuredDemoTest {
	
	String baseUrlAudience = "https://" + ConfigReader.getConfig("API_URL") + "/accounts/"
			+ ConfigReader.getConfig("account_id") + "/members";
	String basicAuth = UtilityMethods.getBasicAuth(ConfigReader.getConfig("api_username"), ConfigReader.getConfig("api_password"));
	
	public void test_xml_request_in_rest_assured() {
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Api test case started");
		testReporter.assignCategory("Audience POST API");
		
		
		testReporter.log(LogStatus.INFO, "Trying to add member with invalid mobile number - 8 digit");
		String phoneNumber = "87956704";
		String xmlBody = "<body>\n" + 
				"   <member>\n" + 
				"    <mobilephone>"+phoneNumber+"</mobilephone>\n" + 
				"    <firstname>hello</firstname>\n" + 
				"  </member>\n" + 
				"</body>";
		System.out.println("XML Body is " +xmlBody);
		
		Response r = expect().given().contentType("application/xml")
				.header("Authorization", basicAuth)
				.header("X-Ms-Source", "api")
				.header("X-Ms-Format", "xml").body(xmlBody).log().all().when().post(this.baseUrlAudience);
		Assert.assertEquals(r.statusCode(), 400);
		
		testReporter.log(LogStatus.INFO, "Response code "+r.statusCode());
		
		String body = r.getBody().asString();
		
		XmlPath xmlPath = new XmlPath(body);
		Assert.assertEquals(xmlPath.get("body.results.success.text()").toString(), "0");
		Assert.assertEquals(xmlPath.get("body.results.failure.text()").toString(), "1");
		Assert.assertEquals(xmlPath.get("body.members.member.member_errors.member_error.message.text()").toString(), "Invalid parameter value");
		Assert.assertEquals(xmlPath.get("body.members.member.member_errors.member_error.detail.reason.text()").toString(), "invalid_format");
		Assert.assertEquals(xmlPath.get("body.members.member.member_errors.member_error.detail.parameter.text()").toString(), "MOBILEPHONE");
	}

}
