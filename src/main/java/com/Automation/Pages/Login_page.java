package com.Automation.Pages;


	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;

	import com.Automation.Utilities.SeleniumHelper;
	import com.Automation.Utilities.SeleniumHelper.ExplicitConditions;
	import com.Automation.Utilities.TestNGHelper;

	public class Login_page {

		public WebDriver driver;
		
		public Login_page(WebDriver driver) {
			this.driver=driver;
			PageFactory.initElements(this.driver,this);
		}
		
		
		@FindBy(id = "txtUsername")
		private WebElement Username_Txt;
	   
		@FindBy(id = "txtPassword")
		private WebElement Password_Txt;
		
		@FindBy(id = "btnLogin")
		private WebElement Login_Btn;
		
		@FindBy(linkText="Forgot your password?")
		private WebElement ForgotPassword_Link;
			
		

		/*public void CheckOutAnyItem(String searchItem) {
			SeleniumHelper.enterValueIntoTextField(searchBox_Txt, "Shirt");
			TestNGHelper.assertEqual(driver.getTitle(), "My Store", "Yes", "HomePage Navigation");
			SeleniumHelper.clickElement(search_Btn);
			TestNGHelper.assertEqual(driver.getTitle(), "Search - My Store", "Yes", "SearchResultPage Navigation");
			SeleniumHelper.clickElement(firstLinkFromResults_Link);
			 SeleniumHelper.ScrollToElement(driver, addToCart_btn);
			 SeleniumHelper.clickElement(addToCart_btn);
			 SeleniumHelper.waitForPageLoad(driver);
			 SeleniumHelper.waitForWebElementToBePresent(driver, proceedToCheckout_Btn);
			 TestNGHelper.assertEqual(driver.getTitle(), "Printed Chiffon Dress - My Stores", "Yes", "Proceed to checkout Navigation");
			 SeleniumHelper.clickElement(proceedToCheckout_Btn); 
			 String totalPrice = SeleniumHelper.getElementText(totalPrice_Label);
			 

		}*/
		
	public void orangeHRM_Login(String username,String password)
	{
		SeleniumHelper.enterValueIntoTextField(Username_Txt, username);
		SeleniumHelper.enterValueIntoTextField(Password_Txt, password);
		SeleniumHelper.clickElement(Login_Btn);
		
	}
	
	}

//pom
