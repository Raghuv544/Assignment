package com.Automation.Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.Automation.Reporting.ExtentUtilities;
import com.Automation.Steps.Mastersteps;

public class TestNGHelper {
	
	static Logger log = Logger.getLogger(TestNGHelper.class);
	static AutomationCore AutoCore = new AutomationCore();
	static WebDriver driver;	
	private static Assertion hardAssert = new Assertion(); 
	private static SoftAssert softAssert = new SoftAssert(); 

  
	public static void assertEqual(Object actual, Object expected, String screenshotStatus, String stepName){	
		
		try
		{
			Assert.assertEquals(actual, expected);		
			//Mastersteps ms= new Mastersteps();
			logScreenshot(stepName, screenshotStatus);
		//	logScreenshot(stepName, screenshotStatus);
		}
		catch(AssertionError error)
		{
			logFailedScreenshot(stepName, screenshotStatus, "Test Failed");
			//SeleniumHelper.quitDriver();
		}		
		
	}	
	
	public static void assertEqual(Object actual, Object expected, String message, String screenshotStatus, String stepName){		
		try
		{
			Assert.assertEquals(actual, expected, message);
			logScreenshot(stepName, screenshotStatus);
		}
		catch(AssertionError error)
		{
			logFailedScreenshot(stepName, screenshotStatus, message + "<br> Expected " + expected + " but found " + actual);
			//logScreenshot(stepName, screenshotStatus);
			//SeleniumHelper.quitDriver();
			//throw new CucumberException(message);
		}
		
	}
	
	public static void fail(String message){
		
		
		
			logFailedScreenshot(AutomationCore.currentStepName, "Yes", message);
			//SeleniumHelper.quitDriver();
			System.out.println("testg called");
			Assert.fail("Failed in Testng helper");
			new SoftAssert().assertAll();
			
	}
	
	public static void assertNotEqual(Object actual, Object expected, String screenshotStatus, String stepName){
		
		Assert.assertNotEquals(actual, expected);		
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNotEqual(Object actual, Object expected, String message, String screenshotStatus, String stepName){		
		
		Assert.assertNotEquals(actual, expected, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertTrue(boolean condition, String screenshotStatus, String stepName){
		Assert.assertTrue(condition);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertTrue(boolean condition, String message, String screenshotStatus, String stepName){
		hardAssert.assertTrue(condition, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void isTrue(boolean condition, String message, String screenshotStatus, String stepName){
		softAssert.assertTrue(condition);
		logFailedScreenshot(stepName, screenshotStatus, "Assert is true failed");
	}
	
	public static void assertFalse(boolean condition, String screenshotStatus, String stepName){
		Assert.assertFalse(condition);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertFalse(boolean condition, String message, String screenshotStatus, String stepName){
		Assert.assertFalse(condition, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNotNull(Object object, String screenshotStatus, String stepName){
		hardAssert.assertNotNull(object);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNotNull_NoFailure(Object object, String screenshotStatus, String stepName){
		softAssert.assertNotNull(object);
		logScreenshot(stepName, screenshotStatus);
	}
	
	
	public static void assertNotNull(Object object, String message, String screenshotStatus, String stepName){
		hardAssert.assertNotNull(object, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void isNotNull(Object object, String message, String screenshotStatus, String stepName){
		softAssert.assertNotNull(object, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNotSame(Object actual, Object expected, String screenshotStatus, String stepName){
		
		try{
			Assert.assertNotSame(actual, expected);
		}catch(AssertionError e){
			logFailedScreenshot(stepName, screenshotStatus, e);
			throw new AssertionError(e.getMessage());
		}		
	}
	
	public static void isNotSame(Object actual, Object expected, String screenshotStatus, String stepName){
		softAssert.assertNotSame(actual, expected);
		logScreenshot(stepName, screenshotStatus);	
	}
	
	public static void assertNotSame(Object actual, Object expected, String message, String screenshotStatus, String stepName){
		Assert.assertNotSame(actual, expected, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNull(Object object, String screenshotStatus, String stepName){
		hardAssert.assertNull(object);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNull_NoFailure(Object object, String screenshotStatus, String stepName){
		softAssert.assertNull(object);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNull(Object object, String message, String screenshotStatus, String stepName){
		hardAssert.assertNull(object, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertNull_NoFailure(Object object, String message, String screenshotStatus, String stepName){
		softAssert.assertNull(object, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertSame(Object actual, Object expected, String screenshotStatus, String stepName){
		Assert.assertSame(actual, expected);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertSame(Object actual, Object expected, String message, String screenshotStatus, String stepName){
		Assert.assertSame(actual, expected, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	/*public static void assertThrows(ThrowingRunnable runnable, String screenshotStatus, String stepName){
		Assert.assertThrows(runnable);
		logScreenshot(stepName, screenshotStatus);
	}*/
	
	/*@SuppressWarnings("unchecked")
	public static void assertThrows(@SuppressWarnings("rawtypes") Class throwable, ThrowingRunnable runnable, String screenshotStatus, String stepName){
		Assert.assertThrows(throwable, runnable);
		logScreenshot(stepName, screenshotStatus);
	}
	*/
	public static void assertEqualsNoOrder(Object actual[], Object expected[], String screenshotStatus, String stepName){
		Assert.assertEqualsNoOrder(actual, expected);
		logScreenshot(stepName, screenshotStatus);
	}
	
	public static void assertEqualsNoOrder(Object actual[], Object expected[], String message, String screenshotStatus, String stepName){
		Assert.assertEqualsNoOrder(actual, expected, message);
		logScreenshot(stepName, screenshotStatus);
	}
	
	
	
	/*@SuppressWarnings("unchecked")
	public static void expectThrows(@SuppressWarnings("rawtypes") Class throwable, ThrowingRunnable runnable, String screenshotStatus, String stepName){
		Assert.expectThrows(throwable, runnable);
		logScreenshot(stepName, screenshotStatus);
	}	*/
	
	public static void logScreenshot(String stepName, String screenshotStatus){		
		AutomationCore core = new AutomationCore();
		stepName = stepName.trim();
		 String path = "";
		if(screenshotStatus.equalsIgnoreCase("yes")){
			log.info("Screenshot Status is yes and hence taking screenshot");
				
				//driver = SeleniumHelper.getDriver();
				String seMinVal = core.formatDateAndTime(core.getCurrentDateAndTime(), "mmss");
				 path = System.getProperty("user.dir") + File.separator+core.Environment("screenShotsPath") + "\\" + stepName+ "Pass" + seMinVal + ".png";

				System.out.println(path);
				Mastersteps ms = new Mastersteps();
				System.out.println(ms.testdriver.getCurrentUrl()+" =======");
				TakesScreenshot scrShot = ((TakesScreenshot) ms.testdriver);
				File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
				File DestFile = new File(path);
				try {
					FileUtils.copyFile(SrcFile, DestFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//SeleniumHelper.takeSnapShot(ms.testdriver, path);
				//Reporter.log("<br> <a href=file:" + path + " target='blank'> <img src=" +path + " target='blank' height='300' width='500' /> </a></br>");
				//SeleniumHelper.takeSnapShot(driver, path);
				if(core.Environment("executionenvironment").equalsIgnoreCase("Local"))
				{
					ExtentUtilities.logScreenshotWithTitle(path, stepName);
					//ExtentUtilities.logScreenshotWithTitle(path, stepName);
				}
				else 
				{					
					//String jenkinsPath = AutoCore.Environment("jenkinsScreenshotsPath")+imageName;
					ExtentUtilities.logScreenshotWithTitle("", stepName);
				}				
				
			}
		
	}
	
	public static void logFailedScreenshot(String stepName, String screenshotStatus, String errorDetails){		
		AutomationCore core = new AutomationCore();
		stepName = stepName.trim();
		 String path = "";
				String seMinVal = core.formatDateAndTime(core.getCurrentDateAndTime(), "mmss");
				 path = System.getProperty("user.dir") + File.separator+core.Environment("screenShotsPath") + "\\" + stepName+ "Pass" + seMinVal + ".png";

				System.out.println(path);
				Mastersteps ms = new Mastersteps();
				System.out.println(ms.testdriver.getCurrentUrl()+" =======");
				TakesScreenshot scrShot = ((TakesScreenshot) ms.testdriver);
				File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
				File DestFile = new File(path);
				try {
					FileUtils.copyFile(SrcFile, DestFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//SeleniumHelper.takeSnapShot(ms.testdriver, path);
				//Reporter.log("<br> <a href=file:" + path + " target='blank'> <img src=" +path + " target='blank' height='300' width='500' /> </a></br>");
				//SeleniumHelper.takeSnapShot(driver, path);
				if(core.Environment("executionenvironment").equalsIgnoreCase("Local"))
				{
					ExtentUtilities.logFailedScreenshotWithTitle(path, stepName);
					//ExtentUtilities.logScreenshotWithTitle(path, stepName);
				}
				else 
				{					
					ExtentUtilities.logFailedScreenshotWithTitle(path, stepName);
				}				
				
	}
	
/*	public static void logFailedScreenshot(String stepName, String screenshotStatus, String errorDetails){		
		Reporter.log("<br> Step Result: " +stepName+ " :Failed");
		log.info("Step: " + stepName+ " Failed");
		stepName = stepName.trim();
		if(screenshotStatus.equalsIgnoreCase("yes")){
			log.info("Screenshot Status is yes and hence taking screenshot");
			
		
			WebDriver tempDriver = SeleniumHelper.getDriver();
				//driver = SeleniumHelper.getDriver();
				System.out.println("Driver is" +tempDriver);
				
				String seMinVal = AutoCore.formatDateAndTime(
						AutoCore.getCurrentDateAndTime(), "mmss");				
				
				String path = AutoCore.Environment("screenShotsPath") + File.separatorChar
						+ AutoCore.Environment("projectName") + File.separatorChar
						+ AutomationCore.currentBrowser + File.separatorChar + stepName
						+ "Fail" + seMinVal + ".png";
				
//				File file =new File(path);
//				path = file.getAbsolutePath().toString();
				System.out.println(path);
				SeleniumHelper.takeSnapShot(tempDriver, path);
				//Reporter.log("<br> <a href=file:" + path + " target='blank'> <img src=" +path + " target='blank' height='300' width='500' /> </a></br>");				
				ExtentUtilities.logFailedScreenshotWithTitle(path, AutomationCore.currentStepName+" -Failed <br> Failure Reason: " +errorDetails.substring(errorDetails.indexOf(":")+1, errorDetails.length()));
				//ExtentUtilities.setParentTestStatus("Fail", errorDetails);			
		}
	}*/
	
	public static void logFailedScreenshot(String stepName, String screenshotStatus, Throwable errorMessage){
		String erMsg = errorMessage.getMessage();
				erMsg = erMsg.substring(erMsg.indexOf(" ") + 1,
				erMsg.length());
		logFailedScreenshot(stepName,  screenshotStatus, erMsg);
	}
}
