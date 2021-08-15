package com.Automation.Steps;

import org.openqa.selenium.WebDriver;

import com.Automation.Pages.BaseDriverPage;
import com.Automation.Pages.Checkoutpage;
import com.Automation.Pages.Homepage;
import com.Automation.Pages.Letstalkteapage;
import com.Automation.Pages.Menupage;
import com.Automation.Pages.Ourpassionpage;
import com.Automation.Pages.Welcomepage;

public class Mastersteps {

	public  WebDriver testdriver;
	
	public Homepage homepage =null;
	public Checkoutpage checkoutpage =null;
	public Letstalkteapage letstalkteapage=null;
	public Menupage menupage=null;
	public Ourpassionpage ourpassionpage=null;
	public Welcomepage welcomepage=null;
	
	public void getHomepage() {
		homepage =new Homepage(testdriver);
		
	}
	public void getCheckoutpage() {
		checkoutpage=new Checkoutpage(testdriver);
	}
	public void getLetstalkteapage() {
		letstalkteapage=new Letstalkteapage(testdriver);
		
	}
	public void getMenupage() {
		menupage=new Menupage(testdriver);
	}
	public void getOurpassionpage() {
		ourpassionpage=new Ourpassionpage(testdriver);
	}
	public void getWelcomepage() {
		welcomepage=new Welcomepage(testdriver);
	}
	
	public void startDriver() {
		BaseDriverPage bpage = new BaseDriverPage();
		testdriver = bpage.setup();
	}
}
