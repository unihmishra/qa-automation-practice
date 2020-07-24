package com.learning.testcases;

import com.learning.factories.BrowserFactory;
import com.learning.factories.DatabaseFactory;
import com.learning.factories.ReporterFactory;
import com.learning.objectRepository.LearningPathPage;
import com.learning.objectRepository.MarketingPage;
import com.learning.utilities.ConfigReader;
import com.learning.utilities.EmailSendGrid;
import com.learning.utilities.UtilityMethods;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
/**
 * Author: Harsh Mishra
 * Date: Dec-2018
 */
public class BaseTest {
	
	Logger logger = Logger.getLogger(BaseTest.class);
	private int passedTestsCounter;
	private int failedTestsCounter;
	private int totalSuiteRun;
	
	@BeforeSuite(alwaysRun = true)
	public synchronized void startSuite() throws Exception {
		System.out.println("**************************************************************************************");
		System.out.println(" ##### Test Suite Started On "+ConfigReader.getENVConfigValue().toUpperCase()+" ##### ");
		System.out.println("Inside BeforeSuite");
	}

	@AfterSuite(alwaysRun = true)
	public synchronized void endSuite(ITestContext context) throws IOException {

		System.out.println(" ##### Test Suite Ended ##### ");
		ReporterFactory.closeReport();
		String result = getTestSuiteResult(context.getSuite());
		StringBuilder str = new StringBuilder("Hi Team,\n");
		str.append("\n");
		str.append("Please find Automation suite run report!\n");
		str.append("\n");
		str.append("Note:- This is Computer generated Email message");
		str.append("\n");
		str.append("*******************************************");
		str.append("\nTotal number of test suite run - "+this.totalSuiteRun);
		str.append("\nTotal Passed test cases - "+(this.passedTestsCounter));
		str.append("\nTotal Failed test cases - "+(this.failedTestsCounter));
		str.append("\n");
		str.append("*******************************************");
		str.append("\n"+result);
		String textMessage = str.toString();
		System.out.println(textMessage);
		DateFormat df = new SimpleDateFormat("dd-MMM-YYYY");
	    Date date = new Date();
		String subject = "mPulse Automation Suite Report on "+ConfigReader.getENVConfigValue()+" Environment dated "+df.format(date);
		String filePath = System.getProperty("user.dir")+"/testReports";
		String fileName = UtilityMethods.getTheNewestFile(filePath, "html").getName();
		filePath = filePath +"/"+fileName;
		if(ConfigReader.allowToSendEmail().equalsIgnoreCase("true")) {
			EmailSendGrid.sendEmailMessage(subject, textMessage, filePath, fileName);
		}
	}

	@BeforeTest(alwaysRun = true)
	public synchronized void startTest(final ITestContext testContext) {
		System.out.println("----- Test '" + testContext.getName() + "' Started -----");
	}

	@AfterTest(alwaysRun = true)
	public synchronized void endTest(final ITestContext testContext) {
		System.out.println("----- Test '" + testContext.getName() + "' Ended -----");
		BrowserFactory.closeWebDriver();
		DatabaseFactory.closeConnection();
	}

	@BeforeMethod(alwaysRun = true)
	public synchronized void startMethod(Method method) {
		ReporterFactory.getTest(method.getName(), "Test Method '" + method.getName() + "' Started");
		System.out.println("**** Test Method '" + method.getName() + "' Started ****");
	}

	@AfterMethod(alwaysRun = true)
	public synchronized void endMethod(ITestResult result) {
		ExtentTest testReporter = ReporterFactory.getTest();
		if (ITestResult.FAILURE == result.getStatus()) {
			System.out.println(">>>>>>>> Error :- Test Method '" + result.getName() + "' Failed <<<<<<<<");
			testReporter.log(LogStatus.FAIL, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Failed ****");
			StringWriter sw = new StringWriter(); 
			result.getThrowable().printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString(); 
			// Write the stack trace to extent reports 
			testReporter.log(LogStatus.FAIL, "<span class='label failure'>" + result.getName() + "</span>", "<pre>Stacktrace:\n" + stacktrace + "</pre>");
			WebDriver driver = BrowserFactory.getBrowser();
			String screenshot_path = UtilityMethods.getScreenShot(driver, "Method_" + result.getName() + "_onFailure");
			String image = testReporter.addScreenCapture(screenshot_path);
			testReporter.log(LogStatus.FAIL, result.getMethod().getMethodName(), image);
			System.out.println("ScreenShort taken");
		} if(ITestResult.SUCCESS == result.getStatus()){
			testReporter.log(LogStatus.PASS, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Passed ****");
		}if(ITestResult.SKIP == result.getStatus()) {
			testReporter.log(LogStatus.INFO, "**** Test Method '" + result.getMethod().getMethodName().replace("_", " ") + "' Skipped ****");
		}
		ReporterFactory.closeTest(result.getMethod().getMethodName());
		System.out.println("**** Test Method '" + result.getName() + "' Ended ****");
	}
	
	private String getTestSuiteResult(ISuite suite) {
        Collection<ISuiteResult> suiteResults = suite.getResults().values();
        this.totalSuiteRun = suiteResults.size();
        StringBuilder str = new StringBuilder();
        String failedTestPrepend = "\nFailed Test Cases -\n"+"*******************************************"+"\n";
        for (ISuiteResult suiteResult : suiteResults) {
        	for (ITestResult result : suiteResult.getTestContext().getFailedTests().getAllResults()) {
        		failedTestsCounter++;
        		if(failedTestsCounter==1) {
    				str.append(failedTestPrepend);
    			}
        		if(failedTestsCounter<51) {
    				str.append(getFormatedResult(result, failedTestsCounter));
    			}
        	}
        }
        if(failedTestsCounter>0) {
        	str.append("*******************************************");
        }
        if(failedTestsCounter>50) {
        	str.append("\n");
        	str.append("Note- For further Failed Test Cases and complete Automation Report, please download the attached file and open in your browser.");
        }
        str.append("\n");
        
        String passedTestPrepend = "\nPassed Test Cases -\n"+"*******************************************"+"\n";
        for (ISuiteResult suiteResult : suiteResults) {
        	for (ITestResult result : suiteResult.getTestContext().getPassedTests().getAllResults()) {
        		passedTestsCounter++;
        		if(passedTestsCounter==1) {
    				str.append(passedTestPrepend);
    			}
        		if(passedTestsCounter<51) {
    				str.append(getFormatedResult(result, passedTestsCounter));
    			}
        	}
        }
        if(passedTestsCounter>0) {
        	str.append("*******************************************");
        }
        if(passedTestsCounter>50) {
        	str.append("\n");
        	str.append("Note- For further Passed Test Cases and complete Automation Report, please download the attached file and open in your browser.");
        }
        return str.toString();
    }

    private String getFormatedResult(ITestResult result, int count) {
        return count+": " + result.getMethod().getMethodName() + ".\n";
    }
    
    public MarketingPage app(WebDriver driver){
    	String appUrl=ConfigReader.getConfig("appUrl");
    	System.out.println("Logging");
    	
    	driver.navigate().to(appUrl);
    	return new MarketingPage(driver);
    }
    
    public LearningPathPage learningPath(WebDriver driver){
    	String appUrl=ConfigReader.getConfig("learning_path_url");
    	driver.navigate().to(appUrl);
    	return new LearningPathPage(driver);
    }
}
