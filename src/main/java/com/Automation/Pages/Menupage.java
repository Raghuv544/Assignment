package com.Automation.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Menupage {

	private WebDriver driver;
	
	public Menupage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);// TODO Auto-generated constructor stub
	}

	
	
}
