package com.Automation.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;

public class Add_user_page {
	private WebDriver driver;

	public Add_user_page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(id="systemUser_userType")
	private WebElement user_role;
	
	@FindBy(id="systemUser_employeeName_empName")
	private WebElement employee_name;
	
	@FindBy(id="systemUser_userName")
	private WebElement username;
	
	@FindBy(id="systemUser_status")
	private WebElement status;
	
	@FindBy(id="systemUser_password")
	private WebElement password;
	
	@FindBy(id="systemUser_confirmPassword")
	private WebElement confirm_password;
	
	public void select_userrole(String role) {
		SeleniumHelper.selectListItemByText(user_role, role);
	}
	
	public void enter_employee_name(String emp_name) {
		employee_name.sendKeys(emp_name);
	}
	
	public void enter_username(String uname) {
		username.sendKeys(uname);
	}
	
	public void select_status(String enter_status) {
		SeleniumHelper.selectListItemByText(status,enter_status);
	}
	
	public void enter_password(String pwd) {
		password.sendKeys(pwd);	
	}
	public void enter_confirmPassword(String confirm_pwd) {
		password.sendKeys(confirm_pwd);	
	}
	
}


