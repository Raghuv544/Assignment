package com.Automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;

public class Letstalkteapage {

	private WebDriver driver;
	
	public Letstalkteapage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);
	}

	@FindBy(name = "name")
	private WebElement name_textbox;
	
	@FindBy(name = "email")
	private WebElement email_textbox;
	
	@FindBy(name = "subject")
	private WebElement subject_textbox;
	
	@FindBy(name = "message")
	private WebElement message_textbox;
	
	
	@FindBy(xpath = "//input[@type='submit']")
	private WebElement submit_button;
	
	public void Send_us_an_email(String name,String email,String subject,String message) {
		
		SeleniumHelper.enterValueIntoTextField(name_textbox, name);
		//name_textbox.sendKeys(name);
		
		SeleniumHelper.enterValueIntoTextField(email_textbox, email);
		//email_textbox.sendKeys(email);
		SeleniumHelper.enterValueIntoTextField(subject_textbox, subject);
//		subject_textbox.sendKeys(subject);
		SeleniumHelper.enterValueIntoTextField(message_textbox, message);
//		message_textbox.sendKeys(message);
		//submit_button.click();
		
		SeleniumHelper.clickElement(submit_button);
	}
	
}

//It is test for my update

