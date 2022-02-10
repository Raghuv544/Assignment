package com.Automation.Steps;

import java.io.File;

import com.Automation.Reporting.ExtentManager;
import com.Automation.Reporting.ExtentUtilities;
import com.Automation.Utilities.AutomationCore;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HerokuSteps extends MasterStepsForHeroku {
	public static Scenario scenario;

	@Before
	public void setupp(Scenario scenario) {
		currentScenario = scenario.getName().replace(" ", "");
		System.out.println(System.getProperty("user.dir"));
		AutomationCore core = new AutomationCore();
		core.testFileAttachmentPath = System.getProperty("user.dir") + File.separator
				+ core.Environment("testAttachmentFilePath");
		System.out.println(AutomationCore.testFileAttachmentPath);

		ExtentUtilities.initilaizeExtentReport();
		ExtentManager.setExtentReportName();
		ExtentUtilities.createParentTest(scenario.getName());
		startDriver();
		getherokuhomepage();
		getsignupPage();
	}

	@After
	public void closeDriver() {
		ExtentUtilities.extentFlush();
		testdriver.quit();
	}

	@Given("search the element in search box")
	public void search_the_element_in_search_box() {
	   herokuhomepage.Heroku_Search("covid-19");
	}

	@Given("click the signup page link")
	public void click_the_signup_page_link() {
	   
		herokuhomepage.Click_SignUp_link();
	}
	@Then("enter the details in the box")
	public void enter_the_details_in_the_box() {
	  herokusignuppage.Create_Heroku_FreeAccount("senthil", "kumar","ahsjl.@gmail.com", "abc","emp", "india", "Python");
	}


}
