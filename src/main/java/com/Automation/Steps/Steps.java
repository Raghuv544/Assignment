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
	@Given("verify orangeHRM login")
	public void verify_orange_hrm_login() {
	  loginpage.orangeHRM_Login("Admin", "admin123");
	}

	@Then("navigate to systemUsers")
	public void navigate_to_system_users() {
	    orangeHRMhomepage.navigateTo_UsersLink();
	}
	@Then("navigate to My Info link")
	public void navigate_to_my_info_link() {
	   orangeHRMhomepage.navigateTo_Myinfo_link();
	}
	@Then("navigate to Trackers link")
	public void navigate_to_trackers_link() {
	   orangeHRMhomepage.navigate_to_My_Trackers();
	}
	@Then("navigate to  EmployeeTimeSheet  link")
	public void navigate_to_employee_time_sheet_link() {
	    orangeHRMhomepage.navigateToEmployeeTimesheetsLink();
	}
	@Then("navigate to ReportingMethodsLink")
	public void navigate_to_reporting_methods_link() {
	   orangeHRMhomepage.navigateTo_ReportingMethodsLink();
	}
	@Then("click on add button and navigate to add user page")
	public void click_on_add_button_and_navigate_to_add_user_page() {
	   orangeHRMSystemUsers_page.ClickOnAddButton();
	   
	}
	@Then("enter the details and click on save button")
	public void enter_the_details_and_click_on_save_button() {
	   add_user_page.enter_AddUser_details("ESS", "Akshay", "kumar", "Enabled","asdf","asdf");
	   add_user_page.click_Save_btn();
	}

}
