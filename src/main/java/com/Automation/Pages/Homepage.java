package com.Automation.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;
import com.Automation.Utilities.SeleniumHelper.ExplicitConditions;
import com.Automation.Utilities.TestNGHelper;

public class Homepage {

	private WebDriver driver;
	
	public Homepage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);
	}
	
	
	@FindBy(id = "search_query_top")
	private WebElement searchBox_Txt;
   
	@FindBy(name = "submit_search")
	private WebElement search_Btn;
	
	@FindBy(xpath = "//*[@id=\"best-sellers_block_right\"]/div/ul/li[1]/a/img")
	private WebElement firstLinkFromResults_Link;
	
	@FindBy(xpath = "//*[@id='add_to_cart']/button/span")
	private WebElement addToCart_btn;
	
	@FindBy(xpath = "//*[contains(text(),'Proceed to checkout')]")
	private WebElement proceedToCheckout_Btn;
	
	@FindBy(xpath = "//*[@id='total_price']")
	private WebElement totalPrice_Label;
	

	public void CheckOutAnyItem(String searchItem) {
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
		 

	}
	

}
