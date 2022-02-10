package com.Automation.Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;

import com.Automation.Utilities.SeleniumHelper;
import com.Automation.Utilities.SeleniumHelper.ExplicitConditions;
import com.Automation.Utilities.TestNGHelper;

import junit.framework.Assert;

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
	
	@FindBy(linkText = "My Info")
	private WebElement My_info_link;
	
	@FindBy(linkText = "Performance")
	private WebElement Performance;
	
	@FindBy(linkText = "Directory")
	private WebElement Directory_link;
	
	@FindBy(linkText = "Maintenance")
	private WebElement Maintenance_link;
	
	@FindBy(linkText = "Buzz")
	private WebElement Buzz_link;
	
	@FindBy(linkText = "Dashboard")
	private WebElement Dashboard_link;
	
	@FindBy(id = "MP_link")
	private WebElement  MarketPlace_link;
	
	@FindBy(linkText = "Purge Records")
	private WebElement Purge_records_link;
	
	@FindBy(linkText = "Employee Records")
	private WebElement Employee_records_link;
	
	@FindBy(linkText ="Candidate Records")
	private WebElement Candidate_records_link;
	
	@FindBy(linkText ="Access Records")
	private WebElement Access_records_link;
	
	@FindBy(className = "arrow")
	private WebElement Configure;
	
	@FindBy(linkText ="KPIs")
	private WebElement KPIs_link;
	
	@FindBy(linkText ="Trackers")
	private WebElement Trackers_link;
		
	@FindBy(id ="menu_performance_searchPerformancReview")
	private WebElement Manage_reviews_link;
	
	@FindBy(linkText = "My Reviews")
	private WebElement My_reviews_link;
	
	@FindBy(linkText ="Review List")
	private WebElement Review_list_link ;
	
	@FindBy(linkText ="My Trackers")
	private WebElement My_trackers_link ;
	
	@FindBy(linkText ="Employee Trackers")
	private WebElement Employee_trackers_link;
	
	@FindBy(id = "welcome")
	private WebElement Welcome_link;
	
	@FindBy(id = "aboutDisplayLink")
	private WebElement About_link;
	
	@FindBy(linkText = "Support")
	private WebElement Support_link;
	
	@FindBy(linkText = "Logout")
	private WebElement Logout_link;
	
		


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
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/index.php/admin/viewSystemUsers","The URL is mismatching");
		
	}
	
	public void navigateTo_Myinfo_link() {
		SeleniumHelper.clickElement(MyInfo_Link);
		//AssertJUnit.assertEquals(driver.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/index.php/pim/viewMyDetails ","mismatched url");
	}

	public void navigateTo_OptionalFieldsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(OptionalFields_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/pim/configurePim","mismatched url");
	}
	

	public void navigateTo_CustomFieldsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(CustomFields_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/pim/listCustomFields","mismatched url");
	}
	
	public void navigateTo_DataImportLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(DataImport_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/admin/pimCsvImport","mismatched url");
	}
	public void navigateTo_ReportingMethodsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(ReportingMethods_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/pim/viewReportingMethods","mismatched url");
		
	}
	
	public void navigateToTerminationReasonsLink() {
		SeleniumHelper.mouseHover(driver,PIM_Link );
		SeleniumHelper.mouseHover(driver,PIM_Configuration_Link );
		SeleniumHelper.clickElement(TerminationReasons_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/pim/viewTerminationReasons","mismatched url");
		
	}
	
	public void navigateToCandidatesLink() {
		SeleniumHelper.mouseHover(driver,Recruitment_Link);		
		SeleniumHelper.clickElement(Candidates_Link);	
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/recruitment/viewCandidates","mismatched url");
	}
	
	public void navigateToApplyLeaveLink() {
		SeleniumHelper.mouseHover(driver,Leave_Link);		
		SeleniumHelper.clickElement(ApplyLeave_Link);	
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave","mismatched url");
	}
	public void navigateToMyLeaveLink() {
		SeleniumHelper.mouseHover(driver,Leave_Link);		
		SeleniumHelper.clickElement(MyLeave_Link);	
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/leave/viewMyLeaveList/reset/1","mismatched url");
		
			}
	public void navigateToMyTimesheetsLink() {
		SeleniumHelper.mouseHover(driver,Time_Link );
		SeleniumHelper.mouseHover(driver,Timesheets_Link );
		SeleniumHelper.clickElement(MyTimesheet_Link);
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/time/viewMyTimesheet","mismatched url");
		
	}
	
	public void navigateToEmployeeTimesheetsLink() {
		SeleniumHelper.mouseHover(driver, Time_Link);
		SeleniumHelper.mouseHover(driver,Timesheets_Link);
		SeleniumHelper.clickElement(EmployeeTimesheet_Link);
		//AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/time/viewEmployeeTimesheet","mismatched url");
		
	}
	
	public void navigateToVacanciesLink() {
		SeleniumHelper.mouseHover(driver,Recruitment_Link);		
		SeleniumHelper.clickElement(Vacancies_Link);	
	//	AssertJUnit.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/index.php/recruitment/viewJobVacancy","mismatched url");
	}
	
	public void logout() {
		SeleniumHelper.clickElement(Welcome_link);
		SeleniumHelper.clickElement(Logout_link);	
	}
	
	public void navigate_to_KPIs() {
		SeleniumHelper.mouseHover(driver,Performance);
		SeleniumHelper.mouseHover(driver,Configure);
		SeleniumHelper.mouseHoverClick(driver,KPIs_link );
}
	public void navigate_to_trackers() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHover(driver,Configure);
		SeleniumHelper.mouseHoverClick(driver,Trackers_link );
}
    public void navigate_to_Manage_Reviewes() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHoverClick(driver,Manage_reviews_link );
}
	public void navigate_to_My_reviews() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHover(driver, Manage_reviews_link);
		SeleniumHelper.mouseHoverClick(driver,My_reviews_link );
}

	public void navigate_to_Review_List() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHover(driver, Review_list_link);
		SeleniumHelper.mouseHoverClick(driver,Review_list_link );
		}
	public void navigate_to_My_Trackers() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHoverClick(driver,My_trackers_link );
}
	
	public void navigate_to_Emplpyee_trackers() {
		SeleniumHelper.mouseHover(driver, Performance);
		SeleniumHelper.mouseHoverClick(driver,Employee_trackers_link );
}
	public void navigate_to_Maintenance() {
		SeleniumHelper.mouseHover(driver, Maintenance_link);
		SeleniumHelper.mouseHover(driver, Purge_records_link);
		SeleniumHelper.mouseHoverClick(driver,Employee_records_link );
}
	public void navigate_to_Purge_Records() {
		SeleniumHelper.mouseHover(driver, Maintenance_link);
		SeleniumHelper.mouseHover(driver, Purge_records_link);
		SeleniumHelper.mouseHoverClick(driver,Candidate_records_link );
}
	public void navigate_to_Access_Records() {
		SeleniumHelper.mouseHover(driver, Maintenance_link);
		SeleniumHelper.mouseHoverClick(driver,Access_records_link );

	}
}

