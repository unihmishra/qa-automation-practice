package com.learning.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.learning.factories.ReporterFactory;
/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class CoursePage {
	WebDriver driver;
	ExtentTest testReporter;
	WebDriverWait wait;
		
	@FindBy(xpath="//div[@class='dashboard']/header//button[@color='#ffffff']")
	private WebElement getStartedButton;
	
	@FindBy(xpath="//div[@class='new-logo-contnr']/span/img")
	private WebElement safeJobLogo;
	
	@FindBy(xpath="//div[@class='lp-detail-container']/div[@class='lp-detail__info']/h1[@class='lp-detail__title']")
	private WebElement courseNameText;
	
	@FindBy(xpath="//div[@class='lp-detail-container']/p[@class='lp-detail__description']")
	private WebElement programDescriptionText;
	
	@FindBy(xpath="//div[@class='lp-detail-container']/button")
	private WebElement programGetStartedButton;
	
	@FindBy(xpath="//div[@class='lp-detail-container']/button/span")
	private WebElement programGetStartedButtonText;
	
	@FindBy(xpath="//div[@class='lp-detail-container']/div[@class='lp-detail__share__btn']")
	private WebElement shareThisCourseButton;
	
	public CoursePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
		wait = new WebDriverWait(driver, 20);
	}
	
	public boolean isGetStartedButtonPresent() {
		try {
			if (this.getStartedButton.isDisplayed()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isSafeJobLogoPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.safeJobLogo)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isCourseNameTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.courseNameText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isProgramDescriptionTextPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.programDescriptionText)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isProgramGetStartedButtonPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.programGetStartedButton)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isShareThisCourseButtonPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.shareThisCourseButton)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public UserLoginPage clickOnGetStartedButton() {
		if (this.isGetStartedButtonPresent()) {
			this.getStartedButton.click();
			return new UserLoginPage(this.driver);
		} else {
			System.out.println("Error: Get Started Button is not Present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public String getCourseNameText() {
		if (this.isCourseNameTextPresent()) {
			return this.courseNameText.getText();
		} else {
			System.out.println("Error: Course name is not Present on page");
		}
		return null;
	}
	
	public String getProgramDescriptionText() {
		if (this.isProgramDescriptionTextPresent()) {
			return this.programDescriptionText.getText();
		} else {
			System.out.println("Error: Program Description Text is not Present on page");
		}
		return null;
	}
	
	public UserLoginPage clickOnProgramGetStartedButton() {
		if (this.isProgramGetStartedButtonPresent()) {
			this.programGetStartedButton.click();
			return new UserLoginPage(this.driver);
		} else {
			System.out.println("Error: Program Get Started Button is not Present on page");
		}
		return new UserLoginPage(this.driver);
	}
}
