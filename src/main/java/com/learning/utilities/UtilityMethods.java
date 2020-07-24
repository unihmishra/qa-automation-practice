package com.learning.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Author: Harsh Mishra
 * Date: Nov-2018
 */
public class UtilityMethods {

	public static synchronized String getScreenShot(WebDriver driver, String ScreenShotName) {
		String fileName = (new Date()).toString().replace(" ", "_").replace(":", "-").trim() + ".png";
		String destinationFilePath = "./ScreenShots/" + ScreenShotName + "_" + fileName;
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File(destinationFilePath));
		} catch (Exception e) {
			System.out.println("Exception while taking screen shot " + e.getMessage());
		}
		System.out.println("Screen shot taken");
		return destinationFilePath;
	}

	public static synchronized String getCsvFilePath(String fileName) {
		return System.getProperty("user.dir") + "/src/test/resources/testData" + "/" + fileName + ".csv";
	}
	
	public static synchronized String getDate_YYYY_MM_DD(int delaydate) {
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DAY_OF_MONTH, delaydate);
		dNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(dNow);
		return strDate;
	}
	
	public static synchronized String getTimeWithDelay(int delayTimeInMinutes) {
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.MINUTE, delayTimeInMinutes);
		dNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String strDate = sdf.format(dNow);
		return strDate;
	}
	
	public static synchronized String getBasicAuth(String username, String password) {
		return "Basic "+ Base64.getEncoder().encodeToString((username +":"+ password).getBytes());
	}
	
	public static synchronized File getTheNewestFile(String filePath, String ext) {
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter("*." + ext);
	    File[] files = dir.listFiles(fileFilter);

	    if (files.length > 0) {
	        /** The newest file comes first **/
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }
	    return theNewestFile;
	}
	
	public static synchronized List<String> getAllCSVColumnsValueByHeaderName(File file, String headerName) throws Exception {
		List<String> values = new ArrayList<String>();
		try (
	            Reader reader = new FileReader(file);
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                    .withFirstRecordAsHeader()
	                    .withIgnoreHeaderCase()
	                    .withTrim());
	        ) {
	            for (CSVRecord csvRecord : csvParser) {
	                // Accessing values by Header names
	                String value = csvRecord.get(headerName).trim().toString();
	                if (!value.isEmpty()) {
	                	values.add(value);
	                }
	            }
	        }
		return values;
	}
	
	public static synchronized void generateCsvFileByGivenHeaderName(String csvFilePath, String campaign_name, String message_name, String message_body, String is_track_click_through, String schedule_on) throws Exception {
		
		try (
	            BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePath));

	            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                    .withHeader("campaign_name", "message_name", "message_body", "is_track_click_through", "schedule_on"));
	        ) {
	            csvPrinter.printRecord(campaign_name, message_name, message_body, is_track_click_through, schedule_on);
	            csvPrinter.flush();            
	        }
	}
	
	public static synchronized String getEmailOTP(WebDriver driver, String email) throws InterruptedException {		
		email = email.substring(0, email.indexOf('@'));
		System.out.println("Email is -- "+email);
		Thread.sleep(3000);
		driver.navigate().to("https://www.mailinator.com/v3/index.jsp?zone=public&query="+email+"#/#inboxpane");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='inboxpane']//table[@class='table table-striped jambo_table']/tbody/tr[1]/td[4]/a[contains(text(), 'One Time Password (OTP) for Safejob Login/SignUp')]")).click();	
		driver.switchTo().frame("msg_body");
		Thread.sleep(3000);
		String token = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[3]/td/b/span")).getText().trim();
		System.out.println("Token on Email is - "+token);
		return token;
	}
}
