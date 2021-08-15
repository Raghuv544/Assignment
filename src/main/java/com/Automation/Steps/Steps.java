package com.Automation.Steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps extends Mastersteps{

	@Before
	public void setupp() {
		startDriver();
		getHomepage();
		getCheckoutpage();
		getLetstalkteapage();
		getMenupage();
		getOurpassionpage();
		getWelcomepage();

	}
	
	@After
	public void closeDriver() {
		testdriver.quit();
	}
	
	
	@Then("Verify CheckOut link navigation")
	public void verify_check_out_link_navigation() {
	  
		homepage.homepage_checkout_click();
	}
	@When("Enter CheckOut information")
	public void enter_check_out_information() {
	    checkoutpage.enter_customerinfo("abc@gmail.com", "xyz", "chennai");
	    checkoutpage.enter_paymentdetails("Visa", "123455", "abc", "1234");
	}
	@Then("Submit the CheckOut")
	public void submit_the_check_out() {
		checkoutpage.click_placeorderbutton();
	}

	@When("Enter CheckOut Customer information")
	public void enter_check_out_customer_information() {
		checkoutpage.enter_customerinfo("abc@gmail.com", "xyz", "chennai");
	}
	@When("Enter Checkout Payment information")
	public void enter_checkout_payment_information() {
		checkoutpage.enter_paymentdetails("Visa", "123455", "abc", "1234");
		
	}
	
	@When("Enter {string},{string},{string} CheckOut Customer information")
	public void enter_check_out_customer_information(String email, String name, String address) {
	   checkoutpage.enter_customerinfo(email, name, address);
	}
	
	@When("Enter {string},{string},{string} CheckOutPage Customer information")
	public void enter_check_out_page_customer_information(String email, String name, String address) {
		checkoutpage.enter_customerinfo(email, name, address);
	}
	


	

}
