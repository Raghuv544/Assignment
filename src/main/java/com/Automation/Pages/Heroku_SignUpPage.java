package com.Automation.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;
import com.Automation.Utilities.TestNGHelper;

public class Heroku_SignUpPage {
	private WebDriver driver;

	public Heroku_SignUpPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	@FindBy(id = "first_name")
	private WebElement FirstName_Txt;

	@FindBy(id = "last_name")
	private WebElement LastName_Txt;

	@FindBy(id = "email")
	private WebElement emailAddress1_Txt;

	@FindBy(id = "company")
	private WebElement CompanyName_Txt;

	@FindBy(id = "role")
	private WebElement Role_DropDown;

	@FindBy(id = "self_declared_country")
	private WebElement CountryName_DropDown;

	@FindBy(id = "main_programming_language")
	private WebElement SelectLanguage_DropDown;

	@FindBy(xpath = "//*[@id=\'recaptcha-anchor\']/div[1]")
	private WebElement NotRobot_CheckBox;

	@FindBy(name = "commit")
	private WebElement CreateAcc_Btn;
	public void Create_Heroku_FreeAccount(String firstName, String lastName, String email, String companyName,
			String role, String countryName, String selectLanguage) {
		SeleniumHelper.enterValueIntoTextField(FirstName_Txt, firstName);
		SeleniumHelper.enterValueIntoTextField(LastName_Txt, lastName);
		SeleniumHelper.enterValueIntoTextField(emailAddress1_Txt, email);
		SeleniumHelper.enterValueIntoTextField(CompanyName_Txt, companyName);
		SeleniumHelper.selectListItemByText(Role_DropDown, role);
		SeleniumHelper.selectListItemByText(CountryName_DropDown, countryName);
		SeleniumHelper.selectListItemByText(SelectLanguage_DropDown, selectLanguage);
		SeleniumHelper.selectCheckBox(NotRobot_CheckBox);
		SeleniumHelper.clickElement(CreateAcc_Btn);

		String Actual_Title = "Sign up for free and experience Heroku today";
		String Expected_Title = driver.getTitle();
		TestNGHelper.assertEqual(Actual_Title, Expected_Title, "Create a Free Account", "Yes");
	
	
	}	
}
