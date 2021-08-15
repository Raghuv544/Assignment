package com.Automation.Reporting;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.Automation.Utilities.*;


public class Report extends TestListenerAdapter{
	
	WebDriver driver;
	com.Automation.Utilities.AutomationCore AutoCore = new com.Automation.Utilities.AutomationCore();
	String buildNumber = "";
	
	@Override
	public void onTestFailure(ITestResult tr){
		driver = com.Automation.Utilities.SeleniumHelper.getDriver();
		System.out.println("On Test Failure method");
		System.out.println("driver " + driver);
		System.out.println(tr.getThrowable());
		if (!tr.getName().equalsIgnoreCase("getBrowser")) {
			buildNumber = System.getenv("BUILD_NUMBER");
			Reporter.log("current build number is " + buildNumber);
			String errorMessage = tr.getThrowable().toString();
			Reporter.log("<br> Step Result: Failed. <br>"
					+ errorMessage.substring(errorMessage.indexOf(" ") + 1,
							errorMessage.length()));
			Reporter.log("<br> <b>TestCase: </b>" + tr.getName()
					+ " &nbsp <b> Result :</b> Fail &nbsp <b> Start Time: </b>"
					+ new Date(tr.getStartMillis())
					+ " &nbsp <b> End Time: </b>" + new Date(tr.getEndMillis())
					+ " &nbsp <b> Response Time in Seconds: </b>"
					+ (tr.getEndMillis() - tr.getStartMillis()) / 1000
					+ " &nbsp <b> Environment: </b> " + AutomationCore.envName
					+ " &nbsp <b> Exe Machine Name: </b>"
					+ AutoCore.getHostName() + " &nbsp <b> App Url: </b>"
					+ AutomationCore.appURL + "&nbsp");
			String seMinVal = AutoCore.formatDateAndTime(
					AutoCore.getCurrentDateAndTime(), "mmss");
			String imageName = tr.getName() + "Fail" + seMinVal + ".png";
			
			String path = AutoCore.Environment("screenShotsPath")
					+ File.separator + AutoCore.Environment("projectName")
					+ File.separator + AutomationCore.currentBrowser
					+ File.separator + imageName;
			System.out.println(path);
			//SeleniumHelper.takeSnapShot(driver, path);

			if(AutomationCore.platform.equalsIgnoreCase("local"))
			{
				//ExtentUtilities.logScreenshot( System.getProperty("user.dir") + File.separator +path);
				TestNGHelper.logFailedScreenshot(AutomationCore.currentStepName, "Yes", errorMessage
						.substring(errorMessage.indexOf(" ") + 1,
								errorMessage.length()));
			}
			else if(AutomationCore.platform.equalsIgnoreCase("sauce"))
			{
				String jenkinsPath = AutoCore.Environment("jenkinsScreenshotsPath")
						+ imageName;
				ExtentUtilities.logScreenshot(jenkinsPath);
			}
			
//			ExtentUtilities.setParentTestStatus("Fail", errorMessage
//					.substring(errorMessage.indexOf(" ") + 1,
//							errorMessage.length()));
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr){
		driver = SeleniumHelper.getDriver();
		System.out.println("On Test Success method");
		System.out.println("driver " + driver);
		if (!tr.getName().equalsIgnoreCase("getBrowser")) {
			Reporter.log("<br> <b>TestCase: </b>" + tr.getName()
					+ " &nbsp <b> Result :</b> Pass &nbsp <b> Start Time: </b>"
					+ new Date(tr.getStartMillis())
					+ " &nbsp <b> End Time: </b>" + new Date(tr.getEndMillis())
					+ " &nbsp <b> Response Time in Seconds: </b>"
					+ (tr.getEndMillis() - tr.getStartMillis()) / 1000
					+ " &nbsp <b> Environment: </b> " + AutomationCore.envName
					+ " &nbsp <b> Exe Machine Name: </b>"
					+ AutoCore.getHostName() + " &nbsp <b> App Url: </b>"
					+ AutomationCore.appURL + "&nbsp");
			String seMinVal = AutoCore.formatDateAndTime(
					AutoCore.getCurrentDateAndTime(), "mmss");
			String imageName = tr.getName() + "Pass" + seMinVal + ".png";

			String path = AutoCore.Environment("screenShotsPath")
					+ File.separator + AutoCore.Environment("projectName")
					+ File.separator + AutomationCore.currentBrowser
					+ File.separator + imageName;
			System.out.println(path);
			//SeleniumHelper.takeSnapShot(driver, path);
			
			//ExtentUtilities.setParentTestStatus("Pass", SeleniumHelper.extractTestName(tr.getName()) + " Passed");
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr){
		driver = SeleniumHelper.getDriver();
		System.out.println("On Test Skipped method");
		System.out.println("driver " + driver);
		
//		ExtentUtilities.setParentTestStatus("Skip", SeleniumHelper.extractTestName(tr.getName())
//				+ " Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentUtilities.extentFlush();
	}
}