package com.Automation.Steps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.Automation.Pages.Add_user_page;
import com.Automation.Pages.BaseDriverPage;
import com.Automation.Pages.Homepage;
import com.Automation.Pages.Login_page;
import com.Automation.Pages.OrangeHRM_Home_page;
import com.Automation.Pages.OrangeHRM_SystemUsers_page;
import com.Automation.Reporting.ExtentUtilities;
import com.Automation.Utilities.AutomationCore;

public class Mastersteps {

	public  static WebDriver testdriver;
	public static String currentScenario = "";
	public Homepage homepage =null;
	public Login_page loginpage=null;
	public OrangeHRM_Home_page orangeHRMhomepage=null;
	public OrangeHRM_SystemUsers_page orangeHRMSystemUsers_page=null;
	public Add_user_page add_user_page=null;

	public WebDriver startDriver() {
		BaseDriverPage bpage = new BaseDriverPage();
		return testdriver = bpage.setup();
	}
	
	public void getHomepage() {
		homepage =new Homepage(testdriver);
		
	}
	public void getLoginpage() {
		loginpage=new Login_page(testdriver);
	}
	public void getorangeHRMhomepage() {
		orangeHRMhomepage=new OrangeHRM_Home_page(testdriver);
	}
	public void getOrangeHRM_SystemUsers_page() {
		orangeHRMSystemUsers_page=new OrangeHRM_SystemUsers_page(testdriver);
	}
	public void getAdd_user_page() {
		add_user_page=new Add_user_page(testdriver);
	}
	
	public static void logScreenshot(String stepName, String screenshotStatus){		
		Reporter.log("<br> Step Result: " +stepName+ " :Passed");
		AutomationCore core = new AutomationCore();
		stepName = stepName.trim();
		 String path = "";
		if(screenshotStatus.equalsIgnoreCase("yes")){
				
				//driver = SeleniumHelper.getDriver();
				String seMinVal = core.formatDateAndTime(core.getCurrentDateAndTime(), "mmss");
				 path = System.getProperty("user.dir") + File.separator+core.Environment("screenShotsPath") + "\\" + stepName+ "Pass" + seMinVal + ".png";

				System.out.println(path);
				Mastersteps ms = new Mastersteps();
				System.out.println(testdriver.getCurrentUrl()+" =======");
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
	
}
