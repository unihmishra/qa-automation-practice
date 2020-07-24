package com.learning.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.learning.factories.BrowserFactory;
import com.learning.factories.ReporterFactory;
import com.learning.objectRepository.CoursePage;
import com.learning.objectRepository.LearningPathPage;
import com.learning.utilities.ConfigReader;
/**
 * Author: Harsh Mishra
 * Date: Dec-2018
 */
public class LearningPathTest extends BaseTest{
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=0)
	public void Verify_that_non_logged_in_user_can_open_learning_path_url() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		if(ConfigReader.getBrowserName().contains("mobile")) {
			Assert.assertTrue(learn.isGetStartedButtonPresent(), "Error: Get Started Button not Present for non logged in user");
		}
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=1)
	public void Verify_that_non_logged_in_user_can_see_some_free_courses() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		LearningPathPage learn = learningPath(driver);

		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalFreeCourseCount()>=1, "Error: non of the free coure display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=1)
	public void Verify_that_non_logged_in_user_can_see_some_Trending_courses() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		LearningPathPage learn = learningPath(driver);

		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalTrendingCourseCount()>=1, "Error: non of the Trending coure display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=1)
	public void Verify_that_non_logged_in_user_can_see_some_Fasttrack_courses() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		LearningPathPage learn = learningPath(driver);

		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalFasttrackCourseCount()>=1, "Error: non of the Fasttrack coure display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=2)
	public void Verify_that_given_free_course_must_be_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_free_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFreeCourseDisplayByName(courseName), 
				"Error: Free coure '"+courseName+"' not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=2)
	public void Verify_that_given_Trending_course_must_be_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_trending_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isTrendingCourseDisplayByName(courseName), 
				"Error: Trending coure '"+courseName+"' not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=2)
	public void Verify_that_given_fasttrack_course_must_be_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_fasttrack_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFasttrackCourseDisplayByName(courseName), 
				"Error: Fasttrack coure '"+courseName+"' not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=3)
	public void Verify_that_non_logged_in_user_can_click_on_given_free_course_by_name() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_free_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFreeCourseDisplayByName(courseName), 
				"Error: Free coure '"+courseName+"' not display on Learning page for non logged in user");
		CoursePage course = learn.clickOnFreeCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=3)
	public void Verify_that_non_logged_in_user_can_click_on_given_Trending_course_by_name() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_trending_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isTrendingCourseDisplayByName(courseName), 
				"Error: Trending coure '"+courseName+"' not display on Learning page for non logged in user");
		CoursePage course = learn.clickOnTrendingCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=3)
	public void Verify_that_non_logged_in_user_can_click_on_given_fasttrack_course_by_name() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_fasttrack_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFasttrackCourseDisplayByName(courseName), 
				"Error: Fasttrack coure '"+courseName+"' not display on Learning page for non logged in user");
		CoursePage course = learn.clickOnFasttrackCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=4)
	public void Verify_minimum_number_of_free_courses_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		int courseCount = Integer.parseInt(ConfigReader.getConfig("minimum_free_course_count"));
		
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalFreeCourseCount()>=courseCount, "Error: minimum "+courseCount+" number of free coures not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=4)
	public void Verify_minimum_number_of_Trending_courses_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		int courseCount = Integer.parseInt(ConfigReader.getConfig("minimum_trending_course_count"));
		
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalTrendingCourseCount()>=courseCount, "Error: minimum "+courseCount+" number of trending coures not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "smoke_test", "learning_path_test"}, priority=4)
	public void Verify_minimum_number_of_fasttrack_courses_on_learning_path_page_for_non_logged_in_user() {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		int courseCount = Integer.parseInt(ConfigReader.getConfig("minimum_fasttrack_course_count"));
		
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.getTotalFasttrackCourseCount()>=courseCount, "Error: minimum "+courseCount+" number of fasttrack coures not display on Learning page for non logged in user");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=5)
	public void Verify_that_non_logged_in_user_can_see_details_of_given_free_course() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_free_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFreeCourseDisplayByName(courseName), 
				"Error: Free coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnFreeCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName.trim());
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=5)
	public void Verify_that_non_logged_in_user_can_see_details_of_given_trending_course() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_trending_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isTrendingCourseDisplayByName(courseName), 
				"Error: Trending coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnTrendingCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName);
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=5)
	public void Verify_that_non_logged_in_user_can_see_details_of_given_fasttrack_course() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_fasttrack_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFasttrackCourseDisplayByName(courseName), 
				"Error: Fasttrack coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnFasttrackCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName);
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=6)
	public void Verify_that_for_given_Free_course_name_non_logged_in_user_can_see_login_page_when_click_to_enroll_button_on_course_detail_page() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_free_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFreeCourseTextPresent(), "Error: Free Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFreeCourseDisplayByName(courseName), 
				"Error: Free coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnFreeCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName.trim());
		Assert.assertTrue(course.clickOnProgramGetStartedButton().isLoginUserDetailInputPresent(), "Error: user login input text box not present when non logged in user clicked to enroll in a course");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=6)
	public void Verify_that_for_given_Trending_course_name_non_logged_in_user_can_see_login_page_when_click_to_enroll_button_on_course_detail_page() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_trending_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isTrendingCourseTextPresent(), "Error: Trending Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isTrendingCourseDisplayByName(courseName), 
				"Error: Trending coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnTrendingCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName);
		Assert.assertTrue(course.clickOnProgramGetStartedButton().isLoginUserDetailInputPresent(), "Error: user login input text box not present when non logged in user clicked to enroll in a course");
	}
	
	@Test(groups = { "sanity", "learning_path_test"}, priority=6)
	public void Verify_that_for_given_Fasttrack_course_name_non_logged_in_user_can_see_login_page_when_click_to_enroll_button_on_course_detail_page() throws InterruptedException {
		WebDriver driver = BrowserFactory.getBrowser();
		ExtentTest testReporter = ReporterFactory.getTest();
		testReporter.log(LogStatus.INFO, "Test method started");
		testReporter.assignCategory("Learning Path Test");
		
		LearningPathPage learn = learningPath(driver);
		String courseName = ConfigReader.getConfig("must_display_fasttrack_course_name");
		Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getConfig("learning_path_url"));
		
		Assert.assertTrue(learn.isFasttrackTextPresent(), "Error: Fasttrack Course Text not Present for non logged in user");
		Assert.assertTrue(learn.isFasttrackCourseDisplayByName(courseName), 
				"Error: Fasttrack coure '"+courseName+"' not display on Learning page for non logged in user");
		
		CoursePage course = learn.clickOnFasttrackCourseByName(courseName);
		Thread.sleep(3000);
		Assert.assertTrue(course.isSafeJobLogoPresent(), "Error: Safe job logo not present on Course detail page");
		Assert.assertTrue(course.isCourseNameTextPresent(), "Error: Course name text element not present on Course detail page");
		
		Assert.assertEquals(course.getCourseNameText(), courseName);
		Assert.assertTrue(course.clickOnProgramGetStartedButton().isLoginUserDetailInputPresent(), "Error: user login input text box not present when non logged in user clicked to enroll in a course");
	}
}
