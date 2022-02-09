package com.Automation.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Automation.Utilities.SeleniumHelper;
import com.Automation.Utilities.SeleniumHelper.ExplicitConditions;
import com.Automation.Utilities.TestNGHelper;

public class OrangeHRM_Home_page {
	private WebDriver driver;

	public OrangeHRM_Home_page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(id = "menu_admin_viewAdminModule")
	private WebElement AdminModule_Link;

	@FindBy(id = "menu_admin_UserManagement")
	private WebElement UserManagement_Link;

	@FindBy(id = "menu_admin_viewSystemUsers")
	private WebElement Users_Link;

	@FindBy(id = "menu_pim_viewPimModule")
	private WebElement PIM_Link;

	@FindBy(id = "menu_pim_Configuration")
	private WebElement PIM_Configuration_Link;
	
	@FindBy(id = "menu_pim_configurePim")
	private WebElement OptionalFields_Link;
	
	@FindBy(id = "menu_pim_listCustomFields")
	private WebElement CustomFields_Link;
	
	@FindBy(id = "menu_admin_pimCsvImport")
	private WebElement DataImport_Link;

	@FindBy(id = "menu_pim_viewReportingMethods")
	private WebElement ReportingMethods_Link;
	
	@FindBy(id = "menu_pim_viewTerminationReasons")
	private WebElement TerminationReasons_Link;
	
	@FindBy(id = "menu_leave_viewLeaveModule")
	private WebElement Leave_Link;	

	@FindBy(id = "menu_leave_applyLeave")
	private WebElement ApplyLeave_Link;
	
	@FindBy(id = "menu_leave_viewMyLeaveList")
	private WebElement MyLeave_Link ;
	
	@FindBy(id = "menu_time_viewTimeModule")
	private WebElement Time_Link;
	
	@FindBy(id = "menu_time_Timesheets")
	private WebElement Timesheets_Link ;
	
	@FindBy(id = "menu_time_viewMyTimesheet")
	private WebElement MyTimesheet_Link;
	
	@FindBy(id = "menu_time_viewEmployeeTimesheet")
	private WebElement EmployeeTimesheet_Link;	
	
	@FindBy(id = "menu_recruitment_viewRecruitmentModule")
	private WebElement Recruitment_Link;
	
	@FindBy(id = "menu_recruitment_viewCandidates")
	private WebElement Candidates_Link;
	
	@FindBy(id = "menu_recruitment_viewJobVacancy")
	private WebElement Vacancies_Link;
	
	@FindBy(id = "menu_pim_viewMyDetails")
	private WebElement MyInfo_Link;
	@FindBy(id = "menu_admin_Job")
	private WebElement menu_admin_job;
	
	@FindBy(id = "menu_admin_viewJobTitleList")
	private WebElement menu_admin_viewjobTitlelist;
	
	@FindBy(id = "btnAdd")
	private WebElement menu_admin_jobTitles;
	
	@FindBy(id = "menu_admin_viewPayGrades")
	private WebElement menu_admin_viewPayGrades;
	
	@FindBy(id = "menu_admin_employmentStatus")
	private WebElement menu_admin_employmentStatus;
	
	@FindBy(id = "menu_admin_jobCategory")
	private WebElement menu_admin_jobCategory;
	
	@FindBy(id = "menu_admin_workShift")
	private WebElement menu_admin_workShift;
		


	 /* public void CheckOutAnyItem(String searchItem) {
	 * SeleniumHelper.enterValueIntoTextField(searchBox_Txt, "Shirt");
	 * TestNGHelper.assertEqual(driver.getTitle(), "My Store", "Yes",
	 * "HomePage Navigation"); SeleniumHelper.clickElement(search_Btn);
	 * TestNGHelper.assertEqual(driver.getTitle(), "Search - My Store", "Yes",
	 * "SearchResultPage Navigation");
	 * SeleniumHelper.clickElement(firstLinkFromResults_Link);
	 * SeleniumHelper.ScrollToElement(driver, addToCart_btn);
	 * SeleniumHelper.clickElement(addToCart_btn);
	 * SeleniumHelper.waitForPageLoad(driver);
	 * SeleniumHelper.waitForWebElementToBePresent(driver, proceedToCheckout_Btn);
	 * TestNGHelper.assertEqual(driver.getTitle(),
	 * "Printed Chiffon Dress - My Stores", "Yes",
	 * "Proceed to checkout Navigation");
	 * SeleniumHelper.clickElement(proceedToCheckout_Btn); String totalPrice =
	 * SeleniumHelper.getElementText(totalPrice_Label);
	 * 
	 * 
	 * }*/
	

	public void navigateTo_UsersLink() {
		SeleniumHelper.mouseHover(driver, AdminModule_Link);
		SeleniumHelper.mouseHover(driver, UserManagement_Link);
		SeleniumHelper.clickElement(Users_Link);
	}
	
	public void navigateTo_Myinfo_link() {
		SeleniumHelper.clickElement(MyInfo_Link);
	}

	public void navigateTo_OptionalFieldsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(OptionalFields_Link);
	}
	

	public void navigateTo_CustomFieldsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(CustomFields_Link);
	}
	
	public void navigateTo_DataImportLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(DataImport_Link);
		
	}
	public void navigateTo_ReportingMethodsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(ReportingMethods_Link);
		
	}
	
	public void navigateToTerminationReasonsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(TerminationReasons_Link);
		
	}
	
	public void navigateToCandidatesLink() {
		SeleniumHelper.mouseHover(driver,Recruitment_Link);		
		SeleniumHelper.clickElement(Candidates_Link);		
	}
	
	public void navigateToApplyLeaveLink() {
		SeleniumHelper.mouseHover(driver,Leave_Link);		
		SeleniumHelper.clickElement(ApplyLeave_Link);		
	}
	public void navigateToMyLeaveLink() {
		SeleniumHelper.mouseHover(driver,Leave_Link);		
		SeleniumHelper.clickElement(MyLeave_Link);		
	}
	public void navigateToMyTimesheetsLink() {
		SeleniumHelper.mouseHover(driver,Time_Link );
		SeleniumHelper.mouseHover(driver,Timesheets_Link );
		SeleniumHelper.clickElement(MyTimesheet_Link);
		
	}
	
	public void navigateToEmployeeTimesheetsLink() {
		SeleniumHelper.mouseHover(driver, Time_Link);
		SeleniumHelper.mouseHover(driver,Timesheets_Link);
		SeleniumHelper.clickElement(EmployeeTimesheet_Link);
		
	}
	
	public void navigateToVacanciesLink() {
		SeleniumHelper.mouseHover(driver,Recruitment_Link);		
		SeleniumHelper.clickElement(Vacancies_Link);		
	}
	public void navigateTo_jobs() {
		SeleniumHelper.mouseHover(driver, menu_admin_job);
		SeleniumHelper.mouseHover(driver, menu_admin_viewjobTitlelist);
		SeleniumHelper.clickElement(menu_admin_jobTitles);
		SeleniumHelper.mouseHover(driver, menu_admin_viewPayGrades);
		SeleniumHelper.mouseHover(driver, menu_admin_employmentStatus);
		SeleniumHelper.mouseHover(driver, menu_admin_jobCategory);
		SeleniumHelper.mouseHover(driver, menu_admin_workShift);
	}
}
//pom
