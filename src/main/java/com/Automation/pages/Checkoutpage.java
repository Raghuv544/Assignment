package com.Automation.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;

public class Checkoutpage {

	private WebDriver driver;
	
	public Checkoutpage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);
	}
	
	
	
	  @FindBy(id = "email") 	  
	  private WebElement email_textbox;
	  @FindBy(id = "name")
	  private WebElement name_textbox;
	  @FindBy(id = "address")
	  private WebElement address_textbox;
	  @FindBy(id = "card_type")
	  private WebElement cardType_dropdown;
	  @FindBy(id = "card_number")
	  private WebElement cardNumber_textox;
	  @FindBy(id = "cardholder_name")
	  private WebElement cardHolderName_textbox;
	  @FindBy(id = "verification_code")
	  private WebElement verificationCode_textbox;
	  @FindBy(xpath = "//button[text()='Place Order']")
	  private WebElement placeOrder_button;
	  @FindBy(linkText = "Cancel")
	  private WebElement cancel_button;
	  
	  public void enter_customerinfo(String email,String name,String address) {
		 // email_textbox.sendKeys(email); 
		  SeleniumHelper.enterValueIntoTextField(email_textbox, email);
		  SeleniumHelper.enterValueIntoTextField(name_textbox,name);
		  //address_textbox.sendKeys(address);
		  SeleniumHelper.enterValueIntoTextField(address_textbox, address);
	  }
//need to check  int 
	  public void enter_paymentdetails(String cardtype,String cardnumber,String cardholdername,String verifycode) {
		  
		  //cardType_dropdown.sendKeys(cardtype);
		  SeleniumHelper.selectListItemByText(cardType_dropdown, cardtype);
		  //cardNumber_textox.sendKeys(cardnumber);
		  SeleniumHelper.enterValueIntoTextField(cardNumber_textox, cardnumber);
		  //cardHolderName_textbox.sendKeys(cardholdername);
		  SeleniumHelper.enterValueIntoTextField(cardHolderName_textbox, cardholdername);
		  //verificationCode_textbox.sendKeys(verifycode);
		  SeleniumHelper.enterValueIntoTextField(verificationCode_textbox, verifycode);
	  }
	  public void click_placeorderbutton() {
		  //placeOrder_button.click();
		  SeleniumHelper.clickElement(placeOrder_button);
	  }
	  public void click_cancel_button() {
		  //cancel_button.click();
		  SeleniumHelper.clickElement(cancel_button);
	  }
	 
	}


