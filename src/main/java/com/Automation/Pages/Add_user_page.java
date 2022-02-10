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
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(id = "systemUser_userType")
	private WebElement AddUser_UserRole_list;

	@FindBy(id = "systemUser_employeeName_empName")
	private WebElement AddUser_employee_name_txt;

	@FindBy(id = "systemUser_userName")
	private WebElement AddUser_username_txt;

	@FindBy(id = "systemUser_status")
	private WebElement AddUser_status_list;

	@FindBy(id = "systemUser_password")
	private WebElement AddUser_password_txt;

	@FindBy(id = "systemUser_confirmPassword")
	private WebElement AddUser_confirm_password_txt;

	@FindBy(name = "btnSave")
	private WebElement AddUser_Save_btn;

	@FindBy(name = "btnCancel")
	private WebElement AddUser_Cancel_btn;

	public void enter_AddUser_details(String userRole, String employeeName, String userName, String status,
			String password, String confirm_password) {
		SeleniumHelper.selectListItemByText(AddUser_UserRole_list, userRole);
		SeleniumHelper.enterValueIntoTextField(AddUser_employee_name_txt, employeeName);
		SeleniumHelper.enterValueIntoTextField(AddUser_username_txt, userName);
		SeleniumHelper.selectListItemByText(AddUser_status_list, status);
		SeleniumHelper.enterValueIntoTextField(AddUser_password_txt, password);
		SeleniumHelper.enterValueIntoTextField(AddUser_confirm_password_txt, confirm_password);
	}

	public void click_Save_btn() {
		AddUser_Save_btn.click();

	}

	public void click_Cancel_btn() {
		AddUser_Cancel_btn.click();
	}

}
