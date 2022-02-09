package com.Automation.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.Automation.Utilities.SeleniumHelper;

public class OrangeHRM_SystemUsers_page {

	private WebDriver driver;

	public OrangeHRM_SystemUsers_page (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	

	@FindBy(id = "searchSystemUser_userName")
	private WebElement SystemUser_Username_Txt;

	@FindBy(id = "searchSystemUser_userType")
	private WebElement SystemUser_UserRole_List;

	@FindBy(id = "searchSystemUser_employeeName_empName")
	private WebElement SystemUser_employeeName_Txt;

	@FindBy(id = "searchSystemUser_status")
	private WebElement SystemUser_Status_List;
	
	@FindBy(id = "searchBtn")
	private WebElement SystemUser_Search_Btn;
		
	@FindBy(id = "resetBtn")
	private WebElement SystemUser_Reset_Btn;
	
	@FindBy(id = "btnAdd")
	private WebElement SystemUser_Add_Btn;
	
	@FindBy(xpath="//*[@id='btnDelete']")
	private WebElement SystemUser_Delete_Btn;
	
	public void enterSystemUsers_Details(String username ,String Userrole,String empName,String status)
	{
		SeleniumHelper.enterValueIntoTextField(SystemUser_Username_Txt, username);
		SeleniumHelper.selectListItemByText(SystemUser_UserRole_List, Userrole);
		SeleniumHelper.enterValueIntoTextField(SystemUser_employeeName_Txt, empName);
		SeleniumHelper.selectListItemByText(SystemUser_Status_List, status);
		
		
	}
	public void ClickOnSearchButton() {
		SeleniumHelper.clickElement(SystemUser_Search_Btn);
	}
	public void ClickOnResetButton() {
		SeleniumHelper.clickElement(SystemUser_Reset_Btn);
	}
	public void ClickOnAddButton() {
		SeleniumHelper.clickElement(SystemUser_Add_Btn);
	}
	public void ClickOnDeleteButton() {
		SeleniumHelper.clickElement(SystemUser_Delete_Btn);
	}
}

//pom
