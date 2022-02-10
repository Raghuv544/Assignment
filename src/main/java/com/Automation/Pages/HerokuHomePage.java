package com.Automation.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;
import com.Automation.Utilities.TestNGHelper;

public class HerokuHomePage {
	private WebDriver driver;

	public HerokuHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(name = "q")
	private WebElement SearchBox_Txt;
	@FindBy(xpath = "//*[@id=\'site-search\']/div/button")
	private WebElement SearchButton_Btn;
	@FindBy(linkText = "Log in")
	private WebElement LoginLink_Link;
	@FindBy(xpath= "//*[@id=\"navigation\"]/div/div[2]/ul[2]/li/a[2]")
	private WebElement SignUp_Link;

	
	public void Heroku_Search(String Search) {
		SeleniumHelper.enterValueIntoTextField(SearchBox_Txt, Search);
		SeleniumHelper.clickElement(SearchButton_Btn);
	}

	public void Heroku_LoginClick() {
		SeleniumHelper.clickElement(LoginLink_Link);
	}	

	public void Click_SignUp_link() {
		SeleniumHelper.clickElement(SignUp_Link);
	}
}
