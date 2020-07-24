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
public class UserHomePage {
	
	WebDriver driver;
	ExtentTest testReporter;
	WebDriverWait wait;
	
	@FindBy(xpath = "//div[@class='user-name sm-text']")
	private WebElement userName;
	
	@FindBy(xpath ="//button[contains(@id, 'cancel') and text()='Ask me Later']")
	private WebElement askMeLater;
	
	@FindBy(xpath ="//button[contains(@id, 'cancel') and text()='Ask me Later']")
	private WebElement yesIamonBoard;

	public UserHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		testReporter = ReporterFactory.getTest();
		wait = new WebDriverWait(driver, 20);
	}
	
	public boolean isUserNamePresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.userName)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public String getDispalyUserName() {
		if (this.isUserNamePresent()) {
			return this.userName.getText();
		} else {
			return null;
		}
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
