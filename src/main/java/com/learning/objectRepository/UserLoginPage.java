package com.learning.objectRepository;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.learning.factories.ReporterFactory;
import com.learning.utilities.UtilityMethods;

/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class UserLoginPage {

	WebDriver driver;
	ExtentTest testReporter;
	WebDriverWait wait;
	
	@FindBy(xpath = "//div[@class='left']/div/header")
	private WebElement safeJobLogo;
	
	@FindBy(name = "userDetail")
	private WebElement loginUserDetailInput;
	
	@FindBy(id= "next")
	private WebElement nextButton;
	
	@FindBy(name = "firstName")
	private WebElement firstNameInputBox;
	
	@FindBy(name = "lastName")
	private WebElement lastNameInputBox;
	
	@FindBy(name = "otpValue")
	private WebElement otpValueInputBox;
	
	@FindBy(name = "//button[@type='button' and contains(text(), 'Resend OTP')]")
	private WebElement resendOTPButton;

	public UserLoginPage(WebDriver driver) {
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
	
	public boolean isLoginUserDetailInputPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.loginUserDetailInput)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isNextButtonPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.nextButton)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isFirstNameInputBoxPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.firstNameInputBox)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isLastNameInputBoxPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.lastNameInputBox)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isOtpValueInputBoxPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.otpValueInputBox)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public boolean isResendOTPButtonPresent() {
		if (this.wait.until(ExpectedConditions.visibilityOf(this.resendOTPButton)).isDisplayed()) {
			return true;
		} else
			return false;
	}
	
	public UserLoginPage enterUserPhoneOrEmail(String value) {
		if (this.isLoginUserDetailInputPresent()) {
			this.loginUserDetailInput.clear();
			this.loginUserDetailInput.sendKeys(value);
		} else {
			System.out.println("Error: User input phone or email not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserLoginPage clickOnNexButtonAndStayOnPage() {
		if (this.isNextButtonPresent()) {
			this.nextButton.click();
		} else {
			System.out.println("Error: next button not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserHomePage clickOnNexButton() {
		if (this.isNextButtonPresent()) {
			this.nextButton.click();
		} else {
			System.out.println("Error: next button not present on page");
		}
		return new UserHomePage(this.driver);
	}
	
	public UserLoginPage enterFirstName(String value) {
		if (this.isFirstNameInputBoxPresent()) {
			this.firstNameInputBox.clear();
			this.firstNameInputBox.sendKeys(value);
		} else {
			System.out.println("Error: first name input box not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserLoginPage enterLastName(String value) {
		if (this.isLastNameInputBoxPresent()) {
			this.lastNameInputBox.clear();
			this.lastNameInputBox.sendKeys(value);
		} else {
			System.out.println("Error: last name input box not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserLoginPage enterOTP(String otp) {
		if (this.isOtpValueInputBoxPresent()) {
			this.otpValueInputBox.clear();
			this.otpValueInputBox.sendKeys(otp);
		} else {
			System.out.println("Error: OTP input box not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserLoginPage getOTPFromEmailAndEnter(String email) throws InterruptedException {
		if (this.isOtpValueInputBoxPresent()) {
			this.otpValueInputBox.sendKeys(this.getTokenFromEmail(email));
		} else {
			System.out.println("Error: OTP input box not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	public UserLoginPage clickResendOTP() {
		if (this.isResendOTPButtonPresent()) {
			this.resendOTPButton.click();
		} else {
			System.out.println("Error: Resend OTP button not present on page");
		}
		return new UserLoginPage(this.driver);
	}
	
	private String getTokenFromEmail(String emailId) throws InterruptedException {
		((JavascriptExecutor)this.driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Total current running tabs "+tabs.size());
		this.driver.switchTo().window(tabs.get(1));
		String token = UtilityMethods.getEmailOTP(this.driver, emailId);
//		((JavascriptExecutor)this.driver).executeScript("window.close()");
		this.driver.close();
		this.driver.switchTo().window(tabs.get(0));
		return token;
	}
}
