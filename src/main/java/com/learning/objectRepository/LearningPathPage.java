package com.learning.objectRepository;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.learning.factories.ReporterFactory;

/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class LearningPathPage {
	WebDriver driver;
	ExtentTest testReporter;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor js;
	
	@FindBy(xpath="//div[text()='Free Courses']")
	private WebElement freeCourseText;
	
	@FindBy(xpath="//div[text()='Fasttrack Courses']")
	private WebElement fasttrackText;
	
	@FindBy(xpath="//div[text()='Trending Courses']")
	private WebElement trendingCourseText;
	
	@FindBy(xpath="//div[text()='Masterclass Courses']")
	private WebElement masterclassCourseText;
	
	@FindBy(xpath="//div[@class='dashboard']/header//button[@color='#ffffff']")
	private WebElement getStartedButtonMobile;
	
	@FindBy(xpath="//div[@class='head-container ']/p[@class='head-text']")
	private WebElement headText;
	
	@FindBy(id="tab-id-Assessment")
	private WebElement assessmentTab;
	
	@FindBy(id="tab-id-Live Class")
	private WebElement liveClassTab;
	
	@FindBys({@FindBy(xpath="//div[text()='Free Courses']/following-sibling::div[@class='card-container']/span[@class='scroll-card']")})
	private List<WebElement> allFreeCourses;
	
	@FindBys({@FindBy(xpath="//div[text()='Free Courses']/following-sibling::div[@class='card-container']/span//div[@class='content-title']")})
	private List<WebElement> allFreeCoursesName;
	
	@FindBys({@FindBy(xpath="//div[text()='Fasttrack Courses']/following-sibling::div[@class='card-container']/span")})
	private List<WebElement> allFasttrackCourses;
	
	@FindBys({@FindBy(xpath="//div[text()='Fasttrack Courses']/following-sibling::div[@class='card-container']/span//div[@class='content-title']")})
	private List<WebElement> allFasttrackCoursesName;
	
	@FindBys({@FindBy(xpath="//div[text()='Trending Courses']/following-sibling::div[@class='card-container']/span")})
	private List<WebElement> allTrendingCourses;
	
	@FindBys({@FindBy(xpath="//div[text()='Trending Courses']/following-sibling::div[@class='card-container']/span//div[@class='content-title']")})
	private List<WebElement> allTrendingCoursesName;
	
	@FindBys({@FindBy(xpath="//div[text()='Masterclass Courses']/following-sibling::div[@class='card-container']/span")})
	private List<WebElement> allMasterclassCourses;
	
	@FindBys({@FindBy(xpath="//div[text()='Masterclass Courses']/following-sibling::div[@class='card-container']/span//div[@class='content-title']")})
	private List<WebElement> allMasterclassCoursesName;
	
	public LearningPathPage(WebDriver driver) {
		this.driver = driver;
		testReporter = ReporterFactory.getTest();
		wait = new WebDriverWait(driver, 20);
		act = new Actions(driver);
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isGetStartedButtonPresent() {
		try {
			if (this.getStartedButtonMobile.isDisplayed()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isHeadTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.headText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isAssessmentTabPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.assessmentTab)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isLiveClassTabPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.liveClassTab)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isFreeCourseTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.freeCourseText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isFasttrackTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.fasttrackText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isTrendingCourseTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.trendingCourseText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isMasterclassCourseTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.masterclassCourseText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isFreeCourseDisplayByName(String courseName) {
		String xpath = "//div[text()='Free Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isFasttrackCourseDisplayByName(String courseName) {
		String xpath = "//div[text()='Fasttrack Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isTrendingCourseDisplayByName(String courseName) {
		String xpath = "//div[text()='Trending Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isMasterclassCourseDisplayByName(String courseName) {
		String xpath = "//div[text()='Masterclass Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public UserLoginPage clickOnGetStartedButton() {
		if (this.isGetStartedButtonPresent()) {
//			this.js.executeScript("arguments[0].scrollIntoView()", this.getStartedButtonMobile);
			this.js.executeScript("arguments[0].click()", this.getStartedButtonMobile);
			return new UserLoginPage(this.driver);
		} else {
			System.out.println("Error: Get Started Button is not Present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public CoursePage clickOnFreeCourseByName(String courseName) {
		String xpath = "//div[text()='Free Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			this.act.moveToElement(course).build().perform();
//			this.js.executeScript("arguments[0].scrollIntoView()", course);
			this.js.executeScript("arguments[0].click()", course);
			return new CoursePage(this.driver);
		} else {
			System.out.println("Error: Free Course is not display by name - "+courseName);
		}
		return new CoursePage(this.driver);
	}
	
	public CoursePage clickOnFasttrackCourseByName(String courseName) {
		String xpath = "//div[text()='Fasttrack Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			this.act.moveToElement(course).build().perform();
//			this.js.executeScript("arguments[0].scrollIntoView()", course);
			this.js.executeScript("arguments[0].click()", course);
			return new CoursePage(this.driver);
		} else {
			System.out.println("Error: Fasttrack Course is not display by name - "+courseName);
		}
		return new CoursePage(this.driver);
	}
	
	public CoursePage clickOnTrendingCourseByName(String courseName) {
		String xpath = "//div[text()='Trending Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			this.act.moveToElement(course).build().perform();
			this.js.executeScript("arguments[0].click()", course);
			return new CoursePage(this.driver);
		} else {
			System.out.println("Error: Free Course is not display by name - "+courseName);
		}
		return new CoursePage(this.driver);
	}
	
	public CoursePage clickOnMasterclassCourseByName(String courseName) {
		String xpath = "//div[text()='Masterclass Courses']/following-sibling::div[@class='card-container']"
				+ "/span//div[@class='content-title' and contains(text(), '"+courseName+"')]";
		WebElement course = this.driver.findElement(By.xpath(xpath));
		if (this.wait.until(ExpectedConditions.visibilityOf(course)).isDisplayed()) {
			this.act.moveToElement(course).build().perform();
//			this.js.executeScript("arguments[0].scrollIntoView()", course);
			this.js.executeScript("arguments[0].click()", course);
			return new CoursePage(this.driver);
		} else {
			System.out.println("Error: Free Course is not display by name - "+courseName);
		}
		return new CoursePage(this.driver);
	}
	
	public String getHeadText() {
		return this.headText.getText();
	}
	
	public int getTotalFreeCourseCount() {
		this.js.executeScript("arguments[0].scrollIntoView()", this.freeCourseText);
		return this.allFreeCourses.size();
	}
	
	public int getTotalFasttrackCourseCount() {
		return this.allFasttrackCourses.size();
	}
	
	public int getTotalTrendingCourseCount() {
		return this.allTrendingCourses.size();
	}
	
	public int getTotalMasterclassCourseCount() {
		return this.allMasterclassCourses.size();
	}
	
	public ArrayList<String> getAllDisplayFreeCoursesName() {
		ArrayList<String> courses = new ArrayList<String>();
		for(int i=0; i<this.allFreeCoursesName.size(); i++) {
			courses.add(this.allFreeCoursesName.get(i).getText());
		}
		return courses;
	}
	
	public ArrayList<String> getAllFasttrackCoursesName() {
		ArrayList<String> courses = new ArrayList<String>();
		for(int i=0; i<this.allFasttrackCoursesName.size(); i++) {
			courses.add(this.allFasttrackCoursesName.get(i).getText());
		}
		return courses;
	}
	
	public ArrayList<String> getAllTrendingCoursesName() {
		ArrayList<String> courses = new ArrayList<String>();
		for(int i=0; i<this.allTrendingCoursesName.size(); i++) {
			courses.add(this.allTrendingCoursesName.get(i).getText());
		}
		return courses;
	}
	
	public ArrayList<String> getAllMasterclassCoursesName() {
		ArrayList<String> courses = new ArrayList<String>();
		for(int i=0; i<this.allMasterclassCoursesName.size(); i++) {
			courses.add(this.allMasterclassCoursesName.get(i).getText());
		}
		return courses;
	}
}
