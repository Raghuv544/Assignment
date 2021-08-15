package com.Automation.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Ourpassionpage {

	private WebDriver driver;
	
	public Ourpassionpage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(this.driver,this);
	}
}
