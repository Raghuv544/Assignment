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
import io.cucumber.java.en.When;

public class Steps extends Mastersteps{

	public static Scenario scenario;
	
	@Before
	public void setupp(Scenario scenario) {
		currentScenario = scenario.getName().replace(" ", "");
		System.out.println(System.getProperty("user.dir"));
		AutomationCore core = new AutomationCore();
		core.testFileAttachmentPath = System.getProperty("user.dir") + File.separator
				+  core.Environment("testAttachmentFilePath");
		System.out.println(AutomationCore.testFileAttachmentPath);

		ExtentUtilities.initilaizeExtentReport();
		ExtentManager.setExtentReportName();
		ExtentUtilities.createParentTest(scenario.getName());
		startDriver();
		getHomepage();
		getLoginpage();
		getorangeHRMhomepage();
		getOrangeHRM_SystemUsers_page();
		getAdd_user_page();
	

	}
	
	@After
	public void closeDriver() {
		ExtentUtilities.extentFlush();
		testdriver.quit();
	}
	
	
	@Given("Add item {string} to the cart and checkout")
	public void add_item_to_the_cart_and_checkout(String prodctItem) {
	   homepage.CheckOutAnyItem(prodctItem);
	}
	@Then("Verify the price the selected item")
	public void verify_the_price_the_selected_item() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

//steps
}
