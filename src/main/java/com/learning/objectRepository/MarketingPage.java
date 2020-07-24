package com.learning.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.learning.factories.ReporterFactory;
import com.learning.utilities.ConfigReader;

/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class MarketingPage {
	WebDriver driver;
	ExtentTest testReporter;
	WebDriverWait wait;
	String title = "Safejob - Your dream jobs are waiting";
	String appUrl=ConfigReader.getConfig("appUrl");
	
	@FindBy(xpath = "//div[@id='root']//header[@class='home']//div[@class='new-logo-contnr']/span/img[contains(@src, 'white-logo')]")
	private WebElement safeJobLogo;
	
	@FindBy(xpath = "//div[@class='contact-gutter']/header/img[contains(@src, 'logo')]")
	private WebElement safeJobFooterLogo;
	
	@FindBy(xpath = "//div[@id='root']//ul[@class='dashboard-list']/following-sibling::button[@color='#ffffff']")
	private WebElement getStartedLinkButton;
	
	@FindBy(xpath = "//a[@href='/admin-login' and contains(text(),'Admin')]")
	private WebElement adminLoginLink;
	
	@FindBy(xpath = "//a[@href='/teacher-login' and contains(text(),'Teacher')]")
	private WebElement teacherLoginLink;
	
	@FindBy(xpath = "//ul[@class='dashboard-list']/a[contains(text(), 'Community')]")
	private WebElement communityLink;
	
	@FindBy(xpath = "//ul[@class='dashboard-list']/a[contains(text(), 'Jobs')]")
	private WebElement jobsLink;
	
	@FindBy(xpath = "//ul[@class='dashboard-list']/a[contains(text(), 'Learning')]")
	private WebElement learningLink;
	
	@FindBy(xpath = "//ul[@class='dashboard-list']/a[contains(text(), 'Blog')]")
	private WebElement blogLink;
	
	@FindBy(xpath ="//button[contains(@id, 'cancel') and text()='Ask me Later']")
	private WebElement askMeLater;
	
	@FindBy(xpath ="//button[contains(@id, 'cancel') and text()='Ask me Later']")
	private WebElement yesIamonBoard;
	
	public MarketingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
		wait = new WebDriverWait(driver, 20);
	}
	
	public boolean isSafeJobLogoPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.safeJobLogo)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isSafeJobFooterLogoPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.safeJobFooterLogo)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isGetStartedLinkButtonPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.getStartedLinkButton)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isAdminLoginLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.adminLoginLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isTeacherLoginLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.teacherLoginLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isCommunityLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.communityLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isJobsLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.jobsLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isLearningLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.learningLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isBlogLinkPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.blogLink)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isAlertBoxPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.yesIamonBoard)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public UserLoginPage clickOnGetStartedLink() {
		if (this.isGetStartedLinkButtonPresent()) {
			this.clickOnAskMeLaterIfAlertPresent();
			this.getStartedLinkButton.click();
		} else {
			System.out.println("Error: get started link button not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public void clickOnAskMeLaterIfAlertPresent() {
		try {
			if (this.askMeLater.isDisplayed()) {
				this.askMeLater.click();
			}
			} catch (Exception e) {
				
			}
	}
}
