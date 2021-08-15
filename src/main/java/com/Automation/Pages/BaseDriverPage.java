package com.Automation.Pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.Automation.Utilities.AutomationCore;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseDriverPage {
	

	public  WebDriver driver;

	public WebDriver setup() {
		AutomationCore core = new AutomationCore();
		String executionenvironment = core.Environment("executionenvironment");
		String browsername = core.Environment("browsername").toLowerCase();
		String url = core.Environment("url");
		System.out.println("execution environment: " + executionenvironment);
		System.out.println("Browser: "+browsername);
		System.out.println("url: "+url);

		switch (executionenvironment) {

		case "local":
			if(browsername.equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
			if(browsername.equals("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			
			
			break;

		case "saucelab":
			String sauceUserName = "srinivasanr841";
			String sauceAccessKey = "6ea77d4e-1d44-47e1-ad8b-63a722555d12";
			DesiredCapabilities capabilities = null;
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("username", sauceUserName);
			capabilities.setCapability("accessKey", sauceAccessKey);
			capabilities.setCapability("build", "Cucumber POM Scenario's");
			capabilities.setCapability("name", "Cucumber Scenario's");
			
			switch (browsername.toLowerCase()) {
			case "firefox":
				capabilities.setCapability("browserName", "Firefox");
				capabilities.setCapability("platform", "Windows 10");
				capabilities.setCapability("version", "55.0");
				break;
			case "chrome":
				capabilities.setCapability("browserName", "Chrome");
				capabilities.setCapability("platform", "Windows 10");
				capabilities.setCapability("version", "89.0");
				break;
			case "edge":
				capabilities.setCapability("browserName", "Edge");
				capabilities.setCapability("platform", "Windows 10");
				capabilities.setCapability("version", "18.17763");
				break;
			default:
                 System.out.println("browser name not matching");
			}

			try {
				driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		return driver;



	}

	public void closebrowser() {
		// driver.close();
		driver.quit();
	}
}
