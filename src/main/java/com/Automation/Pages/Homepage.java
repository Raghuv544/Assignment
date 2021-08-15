package com.Automation.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;

public class Homepage {

	private WebDriver driver;
	
	public Homepage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);
	}
	
	
	@FindBy(linkText = "Welcome")
	private WebElement welcome_link;
   
	@FindBy(linkText = "Our Passion")
	private WebElement ourPassion_link;
	
	@FindBy(linkText = "Menu")
	private WebElement menu_link;
	
	@FindBy(linkText = "Let's Talk Tea")
	private WebElement letsTalkTea_link;
	
	@FindBy(linkText = "Check Out")
	private WebElement checkOut_link;
	
	@FindBy(linkText = "seleniumframework.com")
	private WebElement seleniumFramwork_link;
	 
	public void homepage_welcome_click() {
		//welcome_link.click();
		SeleniumHelper.clickElement(welcome_link);

	}
	public void homepage_ourPassion_click() {
	//	ourPassion_link.click();
		SeleniumHelper.clickElement(ourPassion_link);
	}
	public void homepage_menu_click() {
		//menu_link.click();
		SeleniumHelper.clickElement(menu_link);
	}
	public void homepage_letsTalkTea_click() {
		//letsTalkTea_link.click();
		SeleniumHelper.clickElement(letsTalkTea_link);
	}
	public void homepage_checkout_click() {
		//checkOut_link.click();
		SeleniumHelper.clickElement(checkOut_link);
	}
	public void homepage_seleniumFramwork_click() {
		//seleniumFramwork_link.click();
		SeleniumHelper.clickElement(seleniumFramwork_link);
	}
	

}
