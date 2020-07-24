package com.learning.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.learning.listeners.WebActionListeners;
import com.learning.utilities.ConfigReader;

/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class BrowserFactory {

	private BrowserFactory() {}
	private static final String browser = ConfigReader.getBrowserName();
	private static final String chromedriverPath = getChromedriverPath();
	private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getBrowser(){

		if (threadDriver.get() == null) {
			WebDriver driver = createWebDriver();
			threadDriver.set(driver);
			return threadDriver.get();
		}
		else {
			return threadDriver.get();
		}
	}
	
	public static synchronized void closeWebDriver() {
		if (threadDriver.get() != null) {
			threadDriver.get().close();
			threadDriver.get().quit();
			threadDriver.remove();
			System.out.println("Closing WebDriver Connection for threadId - "+Thread.currentThread().getId());
		}
	}
	
	private static synchronized WebDriver createWebDriver() {
		System.setProperty("webdriver.chrome.driver", chromedriverPath);
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriver chromedriver = new ChromeDriver(getChromeCapabilities(false));
			chromedriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			chromedriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			chromedriver.manage().window().maximize();
			EventFiringWebDriver eventDriver = new EventFiringWebDriver(chromedriver);
			WebActionListeners webListeners = new WebActionListeners();
			eventDriver.register(webListeners);
			chromedriver = eventDriver;
			return chromedriver;
		}
		if(browser.equalsIgnoreCase("chrome-Headless")) {
			WebDriver chromedriver = new ChromeDriver(getChromeCapabilities(true));
			chromedriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			chromedriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			chromedriver.manage().window().maximize();
			EventFiringWebDriver eventDriver = new EventFiringWebDriver(chromedriver);
			WebActionListeners webListeners = new WebActionListeners();
			eventDriver.register(webListeners);
			chromedriver = eventDriver;
			return chromedriver;
		}
		
		if(browser.equalsIgnoreCase("chrome-mobile")) {			
			WebDriver chromedriver = new ChromeDriver(getChromeCapabilitiesMobile(false));
			chromedriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			chromedriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			chromedriver.manage().window().maximize();
			EventFiringWebDriver eventDriver = new EventFiringWebDriver(chromedriver);
			WebActionListeners webListeners = new WebActionListeners();
			eventDriver.register(webListeners);
			chromedriver = eventDriver;
			return chromedriver;
		}
		
		if(browser.equalsIgnoreCase("chrome-mobile-headless")) {			
			WebDriver chromedriver = new ChromeDriver(getChromeCapabilitiesMobile(true));
			chromedriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			chromedriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			chromedriver.manage().window().maximize();
			EventFiringWebDriver eventDriver = new EventFiringWebDriver(chromedriver);
			WebActionListeners webListeners = new WebActionListeners();
			eventDriver.register(webListeners);
			chromedriver = eventDriver;
			return chromedriver;
		}
		
		if(browser.equalsIgnoreCase("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", ProxyType.AUTODETECT.ordinal());
			WebDriver firefoxdriver = new FirefoxDriver();
			firefoxdriver.manage().window().maximize();
			firefoxdriver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
			firefoxdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			EventFiringWebDriver eventDriver = new EventFiringWebDriver(firefoxdriver);
			WebActionListeners webListeners = new WebActionListeners();
			eventDriver.register(webListeners);
			firefoxdriver = eventDriver;
			return firefoxdriver;
		}
		return null;
	}
	
	public synchronized static String getChromedriverPath() {
		String os = System.getProperty("os.name");
		if(os.toLowerCase().contains("linux")) {
			return System.getProperty("user.dir") + "/src/test/resources/chromedriver_linux";
		}
		else if (os.toLowerCase().contains("mac")) {
			return System.getProperty("user.dir") + "/src/test/resources/chromedriver_mac";
		}
		else if (os.toLowerCase().contains("window")) {
			return System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe";
		}
		else {
			System.out.println("ERROR: Operating System is not in the defined range - "+os);
		}
		return "";
	}
	
	private synchronized static DesiredCapabilities getChromeCapabilities(boolean isHeadless){
		String downloadFilepath = System.getProperty("user.dir") + "/downloads";
		HashMap <String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();		
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--no-proxy-server=socks5");
		options.addArguments("--whitelisted-ips");
		if (isHeadless) {
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--start-maximized");
		}
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return cap;
	}
	
	private synchronized static DesiredCapabilities getChromeCapabilitiesMobile(boolean isHeadless){
		String downloadFilepath = System.getProperty("user.dir") + "/downloads";
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Nexus 5");		
		HashMap <String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--no-proxy-server=socks5");
		options.addArguments("--whitelisted-ips");
		if (isHeadless) {
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--start-maximized");
		}
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return cap;
	}
}
