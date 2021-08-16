package com.Automation.Utilities;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.cucumber.core.exception.CucumberException;


public class SeleniumHelper {
	static Logger log = Logger.getLogger(SeleniumHelper.class);
	static AutomationCore AutoCore = new AutomationCore();

	/**
	 * This is a private filed of wedDriver type of SeleniumHelper class
	 */
	// private static WebDriver currentDriver;
	private static RemoteWebDriver currentDriver = null;
	/**
	 * This is a private filed of integer type of SeleniumHelper class
	 */
	private static int pId = 1;
	private static long synTimeVal;
	private static int count = 0;

	// private WebDriverWait wait;
	// private static String browserType;
	/**
	 * This method is used to launch the webDriver based on browser type
	 * 
	 * @param browserName
	 *            The name of the browser to be launched
	 * @param driverPath
	 *            The Driver.exe file path form IE/Chrome.. etc browsers
	 * @param syncTime
	 *            The global sync time for browser waiting period
	 * @return Returns the webDriver of browser Ex:-
	 *         SeleniumHelper.launchDriver("ff","c"\chromeDriver.exe",20
	 *         seconds);
	 */
	public static WebDriver launchDriver(String ieFocus, long syncTime) {
		try {
			synTimeVal = syncTime;
			// browserType=browserName;
			// Need to add switch statement once java7 is available since switch
			// case with string is not available in java 6
			if (AutomationCore.currentBrowser.equals("ff")) {
				FirefoxProfile ffProfile = new FirefoxProfile();
				ffProfile.setAcceptUntrustedCertificates(true);
				//ffProfile.setEnableNativeEvents(true);
				//currentDriver = new FirefoxDriver(ffProfile);
			} else if (AutomationCore.currentBrowser.equals("ie")) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				/*
				 * capabilities.setCapability(InternetExplorerDriver.
				 * ENABLE_PERSISTENT_HOVERING, false);
				 * if(ieFocus.equalsIgnoreCase("Yes")) {
				 * capabilities.setCapability(InternetExplorerDriver.
				 * REQUIRE_WINDOW_FOCUS, true); }
				 */

				System.out.println(AutomationCore.getDriverPath());
				File file = new File(AutomationCore.getDriverPath());
				System.out.println(file.getAbsolutePath());
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				currentDriver = new InternetExplorerDriver(capabilities);
			} else if (AutomationCore.currentBrowser.equals("chrome")) {
				DesiredCapabilities capabilities  = DesiredCapabilities.chrome();
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");
				options.addArguments("--enable-precise-memory-info");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("--enable-automation");
				options.addArguments("test-type=browser");
				options.addArguments("disable-infobars");
				options.addArguments("disable-extensions");
				options.setBinary("/path/to/other/chrome/binary");
				options.setExperimentalOption("useAutomationExtension", false);
		                
		        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				
				File file =new File(AutomationCore.getDriverPath());
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				currentDriver = new ChromeDriver(capabilities);
			} else if (AutomationCore.currentBrowser.equals("safari")) {
				currentDriver = new SafariDriver();
			}
			// currentDriver.manage().timeouts().implicitlyWait(syncTime,TimeUnit.SECONDS);
			currentDriver.manage().deleteAllCookies();
			currentDriver.manage().window().maximize();
			// currentDriver.manage().timeouts().pageLoadTimeout(syncTime,
			// TimeUnit.SECONDS);
		} catch (Exception e) {
			e.getMessage();
			currentDriver = null;
		}

		return currentDriver;
	}

	/**
	 * Creates a new instance of the driver object with desired capabilities
	 * running in a sauce labs environment.
	 * 
	 * This requires that the runtime environment have two variables defined:
	 * SAUCE_USERNAME: Your sauce Labs login username SAUCE_ACCESS_KEY: Your
	 * sauce labs login access key (obtained from "Account")
	 * 
	 * @param browser:
	 *            Browser to instantiate (can be "firefox", "chrome" or "ie"
	 * @param context:
	 *            Method context allowing for extraction of test method name for
	 *            Sauce job name.
	 * @return New RemoteWebDriver instance for a local browser
	 * 
	 * @author
	 */
	public static RemoteWebDriver createWebDriverSauce(String browser) {

		DesiredCapabilities capabilities = null;
		String USERNAME = "aaggar1";
		String ACCESS_KEY = "d2175a13-1c3b-4849-a2db-5d1adacc0c46";
		//String USERNAME = System.getenv("SAUCE_USERNAME");
		//String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
		String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

		if (USERNAME == null || ACCESS_KEY == null) {
			TestNGHelper.fail(
					"Missing value for environment variable(s) SAUCE_USERNAME or SAUCE_ACCESS_KEY.  Check environment configuration and try again");
		}

		switch (browser) {
		/**************** chrome ****************************/
		case "chrome":
			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			capabilities.setCapability(CapabilityType.VERSION, "47");
			capabilities.setCapability(CapabilityType.PLATFORM, "Windows 7");
			capabilities.setCapability("screenResolution", "1280x1024");
			break;

		/**************** FireFox ****************************/
		case "firefox":
			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
			capabilities.setCapability(CapabilityType.VERSION, "37");
			capabilities.setCapability(CapabilityType.PLATFORM, "Windows 7");
			capabilities.setCapability("screenResolution", "1280x1024");
			break;

		/**************** Internet Explorer 11 ****************************/
		case "ie":
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("parentTunnel", "sauce_admin");
			capabilities.setCapability("tunnelIdentifier", "OptumSharedTunnel-Stg");
			//capabilities.setCapability("seleniumVersion", "3.0.0");
			//capabilities.setCapability("iedriverVersion", "3.0.0");
			capabilities.setCapability("platform", "Windows 7");
			capabilities.setCapability("version", "11.0");
			capabilities.setCapability("screenResolution", "1280x1024");
			break;

		default:
			TestNGHelper.fail("Invalid sauceLab browser parameter [" + browser + "]");
		}

		// String jobName = "EES Test Execution of [" +
		// this.getClass().getSimpleName() + ":" + context.getName()
		// + "] - Using " + capabilities.getBrowserName() + " in environment " +
		// profile;
		// capabilities.setCapability("name", jobName);

		try {
			currentDriver = new RemoteWebDriver(new URL(URL), capabilities);
		} catch (MalformedURLException e) {
			TestNGHelper.fail("Invalid SauceLabs URL: [" + URL + "]" + " <br> Exception Message: " + e.getStackTrace());
		}

		// log.info("Sucessfully configured connection to Sauce Labs");
		// currentDriver.setFileDetector(new LocalFileDetector());
		return currentDriver;
	}

	/**
	 * This method is for loading Property file Before starting the bactch
	 * execution
	 */
	/*
	 * @BeforeClass(alwaysRun = true) public void beforeClass() { log.info(
	 * "DEBUG: Starting beforeClass"); // Load Element Map properties elementMap
	 * = new java.util.Properties(); InputStream in =
	 * getClass().getResourceAsStream("/ees_ElementMap.properties"); try {
	 * elementMap.load(in); in.close(); log.info(
	 * "DEBUG: SUCCESSFULLY LOADED elementMap"); } catch (IOException e) {
	 * TestNGHelper.fail("Exception loading element map", e); } }
	 */

	/**
	 * Creates a new instance of the driver object with desired capabilities
	 * running a local browser.
	 * 
	 * @param browser:
	 *            Browser to instantiate (can be "firefox", "chrome" or "ie"
	 * @return New RemoteWebDriver instance for a local browser
	 * 
	 * @author
	 */
	public static WebDriver createWebDriverLocal(String browser) {
		DesiredCapabilities capabilities = null;
		File file = null;
		switch (browser.toLowerCase()) {
		case "firefox":
			ProfilesIni myProfile = new ProfilesIni();
			FirefoxProfile ffprofile = myProfile.getProfile("default");

			if (ffprofile == null) {
				ffprofile = new FirefoxProfile();
			}
			// Configuration to use FF tabs to view PDFs even if Acrobat
			// installed
			ffprofile.setPreference("browser.helperApps.neverAsk.openFile", "application/pdf");
			ffprofile.setPreference("plugin.disable_full_page_plugin_for_types", "");

			currentDriver = new FirefoxDriver((Capabilities) ffprofile);
			break;
		case "chrome":
			// Assumes web driver executable is in 'Drivers' directory under
			// project.
			//DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities = DesiredCapabilities.chrome();
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			options.addArguments("--js-flags=--expose-gc");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("--enable-automation");
			options.addArguments("test-type=browser");
			options.addArguments("disable-infobars");
			options.addArguments("disable-extensions");
			options.setExperimentalOption("useAutomationExtension", false);
	                
	        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			 file =new File(AutomationCore.getDriverPath());
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			currentDriver = new ChromeDriver(capabilities);
			break;
		case "ie":
			// Assumes web driver executable is in 'Drivers' directory under
			// project.
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			capabilities.setCapability(CapabilityType.VERSION, "11");
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, AutoCore.Environment("URL"));
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			file = new File(AutomationCore.getDriverPath());
			System.out.println(file.getAbsolutePath());
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			currentDriver = new InternetExplorerDriver(capabilities);
			break;
		default:
			//TestNGHelper.fail("Invalid browser parameter passed");
		}

		return currentDriver;
	}

	public static WebDriver getDriver() {
		return currentDriver;
	}

	public static void browserResize(WebDriver driver) {
		Dimension d = new Dimension(800, 480);
		driver.manage().window().setSize(d);
	}

	public static void browserMaximize(WebDriver driver) {
		driver.manage().window().maximize();
	}

	/**
	 * This method is used to find an element with LinkText element locator
	 * 
	 * @param driver
	 * @param linkText
	 * @return Returns an element or null based on element availability status
	 *         <p>
	 *         Ex:- SeleniumHelper.findElementByLinkText(loginPageDriver,
	 *         "//[@id='login']"
	 */
	public static WebElement findElementByTagName(WebElement parelement, String tagName) {
		try {
			WebElement element = parelement.findElement(By.tagName(tagName));
			if (element != null) {
				log.info(AutomationCore.testName + ": Element with tagName " + tagName + " is found");
				return element;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parelement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				findElementByTagName(staleElement, tagName);
			} else {
				log.info(AutomationCore.testName + ": Element with tagName " + tagName + " is not found");
				TestNGHelper.fail("Element with tagName " + tagName + " is not found");
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parelement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				findElementByTagName(webElement, tagName);
			} else {
				log.info(AutomationCore.testName + ": Element with tagName " + tagName + " is not found");
				TestNGHelper.fail("Element with tagName " + tagName + " is not found");
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with tagName " + tagName + " is not found");
			TestNGHelper.fail("Element with tagName " + tagName + " is not found");
		} finally {
			count = 0;
		}
		return null;
	}

	/**
	 * This method is used to find an element by element Id locator
	 * 
	 * @param driver
	 *            pageDriver of webDriver type
	 * @param elementID
	 *            element id property value to identify an element
	 * @return Returns an element or null
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.findElementById(loginPageDriver,"log_in");
	 */
	public static WebElement findElementById(WebElement parentElement, String elementID) {
		try {
			WebElement element = parentElement.findElement(By.id(elementID));
			if (element != null) {
				log.info(AutomationCore.testName + ": Element with id " + elementID + " is found");
				return element;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				findElementById(staleElement, elementID);
			} else {
				log.info(AutomationCore.testName + ": Element with id " + elementID + " is not found");
				TestNGHelper.fail("Element with id " + elementID + " is not found");
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				findElementById(webElement, elementID);
			} else {
				log.info(AutomationCore.testName + ": Element with id " + elementID + " is not found");
				TestNGHelper.fail("Element with id " + elementID + " is not found");
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with id " + elementID + " is not found");
			TestNGHelper.fail("Element with id " + elementID + " is not found");
		} finally {
			count = 0;
		}
		return null;
	}

	/**
	 * This method is used to retrieve the collections of elements by tagName
	 * 
	 * @param parentElement
	 *            Parent element of webElement type from which child elements to
	 *            be collected
	 * @param tagName
	 *            tagName element locator to identify the collections of child
	 *            elements
	 * @return Returns the collection of Elements
	 *         <p>
	 *         SeleniumHelper.getElementsByTagName(searchBox, "a"); This returns
	 *         collection of child elements that contains tag element a
	 */
	public static List<WebElement> findElementsByTagName(WebElement parentElement, String tagName) {
		try {
			List<WebElement> elements = parentElement.findElements(By.tagName(tagName));
			if (elements.size() != 0) {
				log.info(AutomationCore.testName + ": List of elements with tag name " + tagName + " is found");
				return elements;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				findElementsByTagName(staleElement, tagName);
			} else {
				log.info(AutomationCore.testName + ": List of elements with tag name " + tagName + " is not found");
				TestNGHelper.fail("List of elements with tag name " + tagName + " is not found");
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				findElementsByTagName(webElement, tagName);
			} else {
				log.info(AutomationCore.testName + ": List of elements with tag name " + tagName + " is not found");
				TestNGHelper.fail("List of elements with tag name " + tagName + " is not found");
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of elements with tag name " + tagName + " is not found");
			TestNGHelper.fail("List of elements with tag name " + tagName + " is not found");
		} finally {
			count = 0;
		}
		return null;
	}

	/**
	 * This method is used to retrieve the collections of elements by tagName
	 * 
	 * @param parentElement
	 *            Parent element of webElement type from which child elements to
	 *            be collected
	 * @param tagName
	 *            tagName element locator to identify the collections of child
	 *            elements
	 * @return Returns the collection of Elements
	 *         <p>
	 *         SeleniumHelper.getElementsByTagName(searchBox, "a"); This returns
	 *         collection of child elements that contains tag element a
	 */
	public static List<WebElement> findElementsByTagName(WebDriver driver, String tagName) {
		try {
			List<WebElement> elements = driver.findElements(By.tagName(tagName));
			if (elements.size() != 0) {
				return elements;
			}
		} catch (Exception e) {
			e.getMessage();

		}
		return null;
	}

	/**
	 * This method is used to retrieve the collections of elements by tagName
	 * 
	 * @param parentElement
	 *            Parent element of webElement type from which child elements to
	 *            be collected
	 * @param xpath
	 *            tagName element locator to identify the collections of child
	 *            elements
	 * @return Returns the collection of Elements
	 *         <p>
	 *         SeleniumHelper.getElementsByTagName(searchBox, "a"); This returns
	 *         collection of child elements that contains tag element a
	 */
	public static List<WebElement> findElementsByXpath(WebElement parentElement, String xpath) {
		try {
			List<WebElement> elements = parentElement.findElements(By.xpath(xpath));
			if (elements.size() != 0) {
				log.info(AutomationCore.testName + ": List of elements with xpath " + xpath + " is found");
				return elements;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				findElementsByXpath(staleElement, xpath);
			} else {
				log.info(AutomationCore.testName + ": List of elements with tag name " + xpath + " is not found");
				TestNGHelper.fail("List of elements with tag name " + xpath + " is not found");
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(parentElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				findElementsByXpath(webElement, xpath);
			} else {
				log.info(AutomationCore.testName + ": List of elements with tag name " + xpath + " is not found");
				TestNGHelper.fail("List of elements with tag name " + xpath + " is not found");
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of elements with tag name " + xpath + " is not found");
			TestNGHelper.fail("List of elements with tag name " + xpath + " is not found");
		} finally {
			count = 0;
		}
		return null;
	}

	/**
	 * This method is used to enter value into text field
	 * 
	 * @param textElement
	 *            textElement of webElement type
	 * @param valueToEnter
	 *            The string type of value to enter
	 * @return Returns boolean true/false based on entering text value into
	 *         textField
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.enterValueIntoTextField(userNameTextFiled,"abc");
	 */
	public static boolean enterValueIntoTextField(WebElement textElement, String valueToEnter) {
		try {
			if (textElement != null && textElement.isEnabled()) {
				textElement.clear();
				textElement.sendKeys(valueToEnter);
				log.info(AutomationCore.testName + ": Entered the text " + valueToEnter + " in the textbox "
						+ textElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(textElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				enterValueIntoTextField(staleElement, valueToEnter);
			} else {
				log.info(AutomationCore.testName + ": failed to enter the text " + valueToEnter + " in the textbox "
						+ textElement);
				TestNGHelper.fail("failed to enter the text " + valueToEnter + " in the textbox " + textElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(textElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				enterValueIntoTextField(webElement, valueToEnter);
			} else {
				log.info(AutomationCore.testName + ": failed to enter the text " + valueToEnter + " in the textbox "
						+ textElement);
				TestNGHelper.fail("failed to enter the text " + valueToEnter + " in the textbox " + textElement);
				return false;
			}
		}

		catch (Exception e) {
			log.info(AutomationCore.testName + ": failed to enter the text " + valueToEnter + " in the textbox "
					+ textElement);
			TestNGHelper.fail("failed to enter the text " + valueToEnter + " in the textbox " + textElement);
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to click an element
	 * 
	 * @param buttonElement
	 *            element of type webElement to be clicked on
	 * @return Returns boolean true/false based on clicking of an element
	 *         <p>
	 *         Example:- SeleniumHelper.clickElement(loginBtn);
	 */
	public static boolean clickOnElement(WebElement buttonElement) {
		try {
			threadSleep(5000);
			if (buttonElement != null && buttonElement.isEnabled()) {
				buttonElement.click();
				;
				log.info(AutomationCore.testName + ": Clicked on element " + buttonElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				clickOnElement(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
				TestNGHelper.fail("Failed to click on the element " + buttonElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				clickOnElement(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
				TestNGHelper.fail("Failed to click on the element " + buttonElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
			TestNGHelper.fail("Failed to click on the element " + buttonElement);
		} finally {
			count = 0;
		}

		return false;

	}

	public static boolean clickElement(WebElement buttonElement) {
		try {
			if (buttonElement != null && buttonElement.isEnabled()) {
				buttonElement.click();
				;
				log.info(AutomationCore.testName + ":   Clicked on element " + buttonElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				clickElement(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
				TestNGHelper.fail("Failed to click on the element " + buttonElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				clickElement(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
				TestNGHelper.fail("Failed to click on the element " + buttonElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to click on the element " + buttonElement);
			TestNGHelper.fail("Failed to click on the element " + buttonElement);
		} finally {
			count = 0;
		}

		return false;

	}

	public static boolean selectCheckBox(WebElement checkBoxElement) {
		try {
			if (checkBoxElement != null && checkBoxElement.isEnabled()) {
				if (checkBoxElement.isSelected()) {
					log.info(AutomationCore.testName + ": Checkbox " + checkBoxElement + " is already selected");
					return true;
				} else {
					checkBoxElement.click();
					;
					log.info(AutomationCore.testName + ": Selected the checkbox " + checkBoxElement);
					return true;
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(checkBoxElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectCheckBox(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to select the checkbox " + checkBoxElement);
				TestNGHelper.fail("Failed to select the checkbox " + checkBoxElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(checkBoxElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectCheckBox(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to select the checkbox " + checkBoxElement);
				TestNGHelper.fail("Failed to select the checkbox " + checkBoxElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to click on the element " + checkBoxElement);
			TestNGHelper.fail("Failed to click on the element " + checkBoxElement);
		} finally {
			count = 0;
		}

		return false;

	}

	public static boolean unSelectCheckBox(WebElement checkBoxElement) {
		try {
			if (checkBoxElement != null && checkBoxElement.isEnabled()) {
				if (checkBoxElement.isSelected()) {

					checkBoxElement.click();
					;
					log.info(AutomationCore.testName + ": Unchecked the checkbox " + checkBoxElement);
					return true;

				} else {
					log.info(AutomationCore.testName + ": Checkbox " + checkBoxElement + " is already unchecked");
					return true;
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(checkBoxElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectCheckBox(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to select the checkbox " + checkBoxElement);
				TestNGHelper.fail("Failed to select the checkbox " + checkBoxElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(checkBoxElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectCheckBox(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to select the checkbox " + checkBoxElement);
				TestNGHelper.fail("Failed to select the checkbox " + checkBoxElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to click on the element " + checkBoxElement);
			TestNGHelper.fail("Failed to click on the element " + checkBoxElement);
		} finally {
			count = 0;
		}

		return false;

	}

	public static boolean DoubleclickElement(WebElement buttonElement, WebDriver driver) {
		Actions act = new Actions(driver);
		try {
			if (buttonElement != null && buttonElement.isEnabled()) {
				act.doubleClick(buttonElement).build().perform();
				log.info(AutomationCore.testName + ": Double Clicked on element " + buttonElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				DoubleclickElement(staleElement, getDriver());
			} else {
				log.info(AutomationCore.testName + ": Failed to double click on the element " + buttonElement);
				TestNGHelper.fail("Failed to double click on the element " + buttonElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				DoubleclickElement(webElement, getDriver());
			} else {
				log.info(AutomationCore.testName + ": Failed to double click on the element " + buttonElement);
				TestNGHelper.fail("Failed to double click on the element " + buttonElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to double click on the element " + buttonElement);
			TestNGHelper.fail("Failed to double click on the element " + buttonElement);
		} finally {
			count = 0;
		}

		return false;

	}

	public static boolean DoubleclickPegaRow(WebElement buttonElement, WebDriver driver) {
		Actions act = new Actions(driver);
		try {
			if (buttonElement != null && buttonElement.isEnabled()) {
				act.moveToElement(buttonElement).build().perform();
				act.doubleClick(buttonElement).build().perform();
				log.info(AutomationCore.testName + ": Double Clicked on Pega Row element " + buttonElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				DoubleclickPegaRow(staleElement, getDriver());
			} else {
				log.info(AutomationCore.testName + ": Failed to double click on Pega Row element " + buttonElement);
				TestNGHelper.fail("Failed to double click on Pega Row element " + buttonElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(buttonElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				DoubleclickPegaRow(webElement, getDriver());
			} else {
				log.info(AutomationCore.testName + ": Failed to double click on Pega Row element " + buttonElement);
				TestNGHelper.fail("Failed to double click on Pega Row element " + buttonElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to double click on Pega Row element " + buttonElement);
			TestNGHelper.fail("Failed to double click on Pega Row element " + buttonElement);
		} finally {
			count = 0;
		}

		return false;

	}

	/**
	 * This method is used to clear text fields
	 * 
	 * @param textElement
	 *            textElement of webElement type
	 * @return Returns boolean true/false based on clearing the text field
	 *         status
	 *         <p>
	 *         Example:- SeleniumHelper.clearTextField(userNameTextField);
	 */
	public static boolean clearTextrField(WebElement textElement) {
		try {
			if (textElement != null && textElement.getText() != null) {
				textElement.clear();
				log.info(AutomationCore.testName + ": Cleared text in element " + textElement);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(textElement);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				clearTextrField(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Unable to clear text in element " + textElement);
				TestNGHelper.fail("Unable to clear text in element " + textElement);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(textElement);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				clearTextrField(webElement);
			} else {
				log.info(AutomationCore.testName + ": Unable to clear text in element " + textElement);
				TestNGHelper.fail("Unable to clear text in element " + textElement);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Unable to clear text in element " + textElement);
			TestNGHelper.fail("Unable to clear text in element " + textElement);
		} finally {
			count = 0;
		}

		return false;
	}

	/**
	 * This method is used to check the existence of an element on webpage
	 * 
	 * @param element
	 *            element of webElement type
	 * @return Returns true or false based on element existence Example:-
	 *         SeleniumHelper.isElementExist(loginButton)
	 */
	public static boolean isElementExist(WebElement element) {
		try {
			if (element.isDisplayed()) {
				log.info(AutomationCore.testName + ": Element " + element + " exist");
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				isElementExist(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " does not exist");
				TestNGHelper.fail("Element " + element + " does not exist");
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				isElementExist(webElement);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " does not exist");
				TestNGHelper.fail("Element " + element + " does not exist");
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " does not exist");
			TestNGHelper.fail("Element " + element + " does not exist");
		} finally {
			count = 0;
		}
		return false;
	}

	public static boolean isElementExist_NoFail(WebElement element) {
		try {
			if (element.isDisplayed()) {
				log.info(AutomationCore.testName + ": Element " + element + " exist");
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				isElementExist(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " does not exist");
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				isElementExist(webElement);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " does not exist");
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " does not exist");
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to validate element text
	 * 
	 * @param element
	 *            element of webElement type
	 * @param validateText
	 *            The text value of an element to be validated
	 * @return Returns true/false based on validation of element text result
	 *         <p>
	 *         Example:- SeleniumHelper.validateElementText(userName,"abc");
	 */
	public static boolean validateElementText(WebElement element, String validateText) {
		try {
			if (element != null && element.getText().trim().toUpperCase().equals(validateText.trim().toUpperCase())) {
				log.info(AutomationCore.testName + ": Element " + element + " has text " + validateText);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				validateElementText(staleElement, validateText);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " doesn't have text " + validateText);
				TestNGHelper.fail("Element " + element + " doesn't have text " + validateText);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				validateElementText(webElement, validateText);
			} else {
				log.info(AutomationCore.testName + ": Element " + element + " doesn't have text " + validateText);
				TestNGHelper.fail("Element " + element + " doesn't have text " + validateText);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " doesn't have text " + validateText);
			TestNGHelper.fail("Element " + element + " doesn't have text " + validateText);
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to validate element text partially
	 * 
	 * @param element
	 *            element of webElement type
	 * @param validateText
	 *            The text value of an element to be validated
	 * @return Returns true/false based on validation of element text result
	 *         <p>
	 *         Example:- SeleniumHelper.validateElementText(userName,"abc");
	 */
	public static boolean validateElementTextContains(WebElement element, String validateText) {
		try {
			if (element != null && element.getText().trim().toUpperCase().contains(validateText.trim().toUpperCase())) {
				log.info(AutomationCore.testName + ": Element " + element + " contains the text " + validateText);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				validateElementTextContains(staleElement, validateText);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't contain the text " + validateText);
				TestNGHelper.fail("Element " + element + " doesn't contain the text " + validateText);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				validateElementTextContains(webElement, validateText);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't contain the text " + validateText);
				TestNGHelper.fail("Element " + element + " doesn't contain the text " + validateText);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " doesn't contain the text " + validateText);
			TestNGHelper.fail("Element " + element + " doesn't contain the text " + validateText);
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to validate element attribute values
	 * 
	 * @param element
	 *            element of webElement type that to be validated
	 * @param attrbiuteName
	 *            the name of the attribute to read
	 * @param attributeValue
	 *            the expected attribute value
	 * @return Returns true/false based on validation
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.validateElementAttribute(userName,"text","XYZ");
	 */
	public static boolean validateElementAttribute(WebElement element, String attrbiuteName, String attributeValue) {
		try {
			if (element != null && element.getAttribute(attrbiuteName).trim().toUpperCase()
					.equals(attributeValue.trim().toUpperCase())) {
				log.info(AutomationCore.testName + ": Element " + element + " has attribute " + attributeValue);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				validateElementAttribute(staleElement, attrbiuteName, attributeValue);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
				TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				validateElementAttribute(webElement, attrbiuteName, attributeValue);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
				TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
			TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to validate element attribute partially
	 * 
	 * @param element
	 *            element of webElement type that is to be validated
	 * @param attrbiuteName
	 *            Name of the attribute for which the actual value to be read
	 * @param attributeValue
	 *            The expected attribute value of the element
	 * @return Returns true/false based on the validation
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.validateElementAttributeContains(userName,"text",
	 *         "xyz");
	 */
	public static boolean validateElementAttributeContains(WebElement element, String attrbiuteName,
			String attributeValue) {
		try {
			if (element != null && element.getAttribute(attrbiuteName).trim().toUpperCase()
					.contains(attributeValue.trim().toUpperCase())) {
				log.info(AutomationCore.testName + ": Element " + element + " contains attribute " + attributeValue);
				return true;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				validateElementAttributeContains(staleElement, attrbiuteName, attributeValue);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
				TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
				return false;
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				validateElementAttributeContains(webElement, attrbiuteName, attributeValue);
			} else {
				log.info(
						AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
				TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
				return false;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element " + element + " doesn't have attribute " + attributeValue);
			TestNGHelper.fail("Element " + element + " doesn't have attribute " + attributeValue);
		} finally {
			count = 0;
		}
		return false;
	}

	/**
	 * This method is used to retrieve the attribute value of an element
	 * 
	 * @param element
	 *            An element for which property to be retrieved
	 * @param attrbiuteName
	 *            Expected attribute name for which value to be retrieved
	 * @return Returns an elements attribute value
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.getElementAttributeValue(userName,"text");
	 */
	public static String getElementAttributeValue(WebElement element, String attrbiuteName) {
		try {
			if (element != null) {
				String value = element.getAttribute(attrbiuteName).trim();
				log.info(AutomationCore.testName + ": Found attribute value " + value + " of " + attrbiuteName
						+ " of element " + element);
				return value;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				getElementAttributeValue(staleElement, attrbiuteName);
			} else {
				log.info(AutomationCore.testName + ": Failed to get Attribute value of " + attrbiuteName
						+ " of Element " + element);
				TestNGHelper.fail("Failed to get Attribute value of " + attrbiuteName + " of Element " + element);
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				getElementAttributeValue(webElement, attrbiuteName);
			} else {
				log.info(AutomationCore.testName + ": Failed to get Attribute value of " + attrbiuteName
						+ " of Element " + element);
				TestNGHelper.fail("Failed to get Attribute value of " + attrbiuteName + " of Element " + element);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get Attribute value of " + attrbiuteName + " of Element "
					+ element);
			TestNGHelper.fail("Failed to get Attribute value of " + attrbiuteName + " of Element " + element);
		} finally {
			count = 0;
		}
		return null;
	}

	/**
	 * This method is used to get the text of webElement
	 * 
	 * @param element
	 *            Element for which text to be retrieved
	 * @return Returns text of an element
	 *         <p>
	 *         Example:- SeleniumHelper.getElementText(userName)
	 */
	public static String getElementText(WebElement element) {
		try {
			if (element != null) {
				String text = element.getText().trim();
				log.info(AutomationCore.testName + ": Text " + text + " of " + element + " retrieved successfully");
				return text;
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				getElementText(staleElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to retrieve text of element " + element);
				TestNGHelper.fail("Failed to retrieve text of element " + element);
			}
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				getElementText(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to retrieve text of element " + element);
				TestNGHelper.fail("Failed to retrieve text of element " + element);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve text of element " + element);
			TestNGHelper.fail("Failed to retrieve text of element " + element);
		} finally {
			count = 0;
		}
		return "";
	}

	/**
	 * This method is used to get column index from table
	 * 
	 * @param table
	 *            table Element
	 * @param colTagName
	 *            column element tag name value
	 * @param columnText
	 *            column text
	 * @return Returns column index from specified table Example:-
	 *         SeleniumHelper.getColumnIndexFromTable(table,"td","Reply")
	 */
	public static int getColumnIndexFromTable(WebElement table, String colTagName, String columnText) {
		int colIndex = -1;
		try {
			List<WebElement> columnElements = new ArrayList<WebElement>();
			if (table == null) {
				return -1;
			}
			columnElements = table.findElements(By.tagName(colTagName));
			;
			for (int index = 0; index < columnElements.size(); index++) {
				if ((columnElements.get(index)).getText().trim().equals(columnText)) {
					colIndex = index;
					log.info(AutomationCore.testName + ": Retrieved column index " + colIndex + "from table " + table);
					return colIndex;
				}
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve column index from table " + table);
			TestNGHelper.fail("Failed to retrieve column index from table " + table);
		}

		return colIndex;
	}

	/**
	 * This method is used to get column index from table
	 * 
	 * @param table
	 *            table Element
	 * @param colTagName
	 *            column element tag name value
	 * @param columnText
	 *            column text
	 * @return Returns column index from specified table Example:-
	 *         SeleniumHelper.getColumnIndexFromTable(table,"td","Reply")
	 */
	public static int getColumnIndexFromPegaTable(WebElement table, String colTagName, String columnText) {
		int colIndex = -1;
		try {
			List<WebElement> columnElements = new ArrayList<WebElement>();
			columnElements = table.findElements(By.tagName(colTagName));
			;
			for (int index = 1; index < columnElements.size(); index++) {
				if ((columnElements.get(index)).getText().trim().equals(columnText)) {
					colIndex = index;
					log.info(AutomationCore.testName + ": Retrieved column index " + colIndex + "from Pega table "
							+ table);
					return colIndex;
				}
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve column index from Pega table " + table);
			TestNGHelper.fail("Failed to retrieve column index from Pega table " + table);
		}

		return colIndex;
	}

	/**
	 * This method is used to get row index from table
	 * 
	 * @param table
	 *            table element from which the row index to be retrieved
	 * @param rowTagName
	 *            rowTagName of string type
	 * @param colTagName
	 *            colTagName of string type
	 * @param rowUniqueText
	 *            unique text to be searched in table to identify the row
	 * @return Returns row index from table
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.getRowIndexFromTable(searchGrid,"tr","td",
	 *         "Proivder");
	 */
	public static int getRowIndexFromTable(WebElement table, String rowTagName, String colTagName,
			String rowUniqueText) {
		int rowIndex = -1;
		try {
			List<WebElement> rowElements = new ArrayList<WebElement>();
			rowElements = SeleniumHelper.findElementsByTagName(table, rowTagName);
			for (int index = 0; index < rowElements.size(); index++) {
				List<WebElement> cells = SeleniumHelper.findElementsByTagName(rowElements.get(index), colTagName);
				for (int cindex = 0; cindex < cells.size(); cindex++) {
					if ((cells.get(cindex)).getText().equals(rowUniqueText.toString())) {
						rowIndex = index;
						log.info(AutomationCore.testName + ": Retrieved row index " + rowIndex + "from table " + table);
						return rowIndex;
					}
				}

			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve row index from table " + table);
			TestNGHelper.fail("Failed to retrieve row index from table " + table);
		}

		return rowIndex;
	}

	/**
	 * This method is used to get row index from table
	 * 
	 * @param table
	 *            table element from which the row index to be retrieved
	 * @param rowTagName
	 *            rowTagName of string type
	 * @param colTagName
	 *            colTagName of string type
	 * @param rowUniqueText
	 *            unique text to be searched in table to identify the row
	 * @return Returns row index from table
	 *         <p>
	 *         Example:-
	 *         SeleniumHelper.getRowIndexFromTable(searchGrid,"tr","td",
	 *         "Proivder");
	 */
	public static int getRowIndexFromPegaTable(WebElement table, String rowTagName, String colTagName,
			String rowUniqueText) {
		int rowIndex = -1;
		try {
			List<WebElement> rowElements = new ArrayList<WebElement>();
			if (table == null) {
				return -1;
			}
			rowElements = findElementsByTagName(table, rowTagName);
			for (int index = 2; index < rowElements.size(); index++) {
				List<WebElement> cells = findElementsByTagName(rowElements.get(index), colTagName);
				for (int cindex = 0; cindex < cells.size(); cindex++) {
					if ((cells.get(cindex)).getText().trim().equals(rowUniqueText.trim().toString())) {
						rowIndex = index;
						log.info(AutomationCore.testName + ": Retrieved row index " + rowIndex + "from Pega table "
								+ table);
						return rowIndex;
					}
				}

			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve row index from Pega table " + table);
			TestNGHelper.fail("Failed to retrieve row index from Pega table " + table);
		}

		return rowIndex;
	}

	/**
	 * This method is used to get row number from webtable if the table contains
	 * different pages
	 * 
	 * @param driver
	 *            pageDriver of webdriver type
	 * @param tableId
	 *            Id element locator of webtable of string type
	 * @param rowTagName
	 *            rowtagName of String type
	 * @param colTagName
	 *            colTagName of String type
	 * @param rowUniqueText
	 *            rowUnique value of String type to identify the record uniquely
	 * @param pageAttribute
	 *            pageAttribute of String type to identify the page Number
	 *            element
	 * @return Returns row number from web table
	 * 
	 *         Example:- int rowNum=
	 *         SeleniumHelper.getRowIndexFromTableWithPageNationById(
	 *         searchPageDriver,"tableId","tr","td","Selenium","a");
	 */

	public static int getRowIndexFromTableWithPageNationById(WebDriver driver, String tableId, String rowTagName,
			String colTagName, String rowUniqueText, String pageAttribute) {
		int rowIndex = -1;
		int rowCount = -1;
		List<WebElement> rowElements = null;
		WebElement table = null;
		try {
			rowElements = new ArrayList<WebElement>();
			table = findElement(driver, new String[] { "Id", tableId }, 30, ExplicitConditions.CLICK);
			clickElement(table);
			rowElements = table.findElements(By.tagName(rowTagName));
			rowCount = rowElements.size();
			for (int index = 0; index < rowElements.size(); index++) {
				List<WebElement> cells = (rowElements.get(index)).findElements(By.tagName(colTagName));
				for (int cindex = 0; cindex < cells.size(); cindex++) {
					if ((cells.get(cindex)).getText().equals(rowUniqueText.toString())) {
						rowIndex = index;
						log.info(AutomationCore.testName + ": Retrieved row index " + rowIndex
								+ " from TableWithPageNationById " + tableId);
						return rowIndex;
					}
				}

			}
		} catch (Exception e) {
			log.info(
					AutomationCore.testName + ": Failed to retrieve row index from TableWithPageNationById " + tableId);
			TestNGHelper.fail("Failed to retrieve row index from TableWithPageNationById " + tableId);
			return -1;
		}

		if (rowIndex == -1 && rowElements != null) {
			WebElement pageELement = rowElements.get(rowCount - 1);
			if (pageELement == null) {
				return rowIndex;
			}
			List<WebElement> pages = pageELement.findElements(By.tagName(colTagName));
			if (pId <= pages.size()) {
				if (pages.get(pId).findElement(By.tagName(pageAttribute)) != null) {
					pages.get(pId).findElement(By.tagName(pageAttribute)).click();
					pId = pId + 1;
					rowIndex = getRowIndexFromTableWithPageNationById(driver, tableId, rowTagName, colTagName,
							rowUniqueText, pageAttribute);
				}

			}
		}
		return rowIndex;
	}

	/**
	 * This method is used to get row number from webtable based on following
	 * parameters
	 * 
	 * @param table
	 *            table of webelemt type value
	 * @param rowTagName
	 *            rowTagName of String type value
	 * @param colTagName
	 *            colTagName of String type value
	 * @param rowUniqueText
	 *            rowUniqueText of String type Value
	 * @return a row number or index of integer type from webtable Example:- int
	 *         rowNum =
	 *         SeleniumHelper.getRowIndexFromTableContains(questionTable,"tr",
	 *         "td","1234");
	 * 
	 */
	public static int getRowIndexFromTableContains(WebElement table, String rowTagName, String colTagName,
			String rowUniqueText) {
		int rowIndex = -1;
		try {
			List<WebElement> rowElements = new ArrayList<WebElement>();
			rowElements = table.findElements(By.tagName(rowTagName));
			for (int index = 0; index < rowElements.size(); index++) {
				List<WebElement> cells = (rowElements.get(index)).findElements(By.tagName(colTagName));
				for (int cindex = 0; cindex < cells.size(); cindex++) {
					if ((cells.get(cindex)).getText().contains(rowUniqueText.toString())) {
						rowIndex = index;
						log.info(AutomationCore.testName + ": Retrieved row index" + rowIndex + " from table " + table);
						return rowIndex;
					}
				}

			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve row index from table " + table);
			TestNGHelper.fail("Failed to retrieve row index from table " + table);
		}

		return rowIndex;
	}

	/**
	 * This method is used to get cell data from specified table
	 * 
	 * @param table
	 *            table of webelemt type value
	 * @param rowTagName
	 *            rowTagName of String type value
	 * @param colTagName
	 *            colTagName of String type value
	 * @param rowIndex
	 *            rowIndex of Int type value
	 * @param colIndex
	 *            colIndex of Int type value
	 * @return Returns the cell data from table based on row and coulmn numbers
	 *         <p>
	 *         Example: SeleniumHelper.getCellData(searchGrid,"tr","td",1,1);
	 */
	public static String getCellData(WebElement table, String rowTagName, String colTagName, int rowIndex,
			int colIndex) {
		try {
			List<WebElement> row = table.findElements(By.tagName(rowTagName));
			List<WebElement> cells = (row.get(rowIndex)).findElements(By.tagName(colTagName));
			String cellData = cells.get(colIndex).getText().toString();
			log.info(AutomationCore.testName + ": Retrieved cell data" + cellData + " from table " + table
					+ " with rowIndex " + rowIndex + " colunIndex " + colIndex);
			return cellData;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve cell data from table " + table + " with rowIndex "
					+ rowIndex + " colunIndex " + colIndex);
			TestNGHelper.fail("Failed to retrieve row index from table " + table);
		}
		return null;
	}

	/**
	 * This method is used to get Row Element
	 * 
	 * @param table
	 *            table of webelemt type value
	 * @param rowTagName
	 *            rowTagName of String type value
	 * @param rowIndex
	 *            rowIndex of Int type value
	 * @return Returns element in the row based on rowtagname and rowindex
	 *         Example : SeleniumHelper.getRow(Table , "tr" ,3)
	 */

	public static WebElement getRow(WebElement table, String rowTagName, int rowIndex) {
		WebElement element = null;
		try {
			if (table == null) {
				return null;
			}
			List<WebElement> rows = table.findElements(By.tagName(rowTagName));
			element = rows.get(rowIndex);
			log.info("row element " + element + " in table " + table + " found");
			return element;
		} catch (Exception e) {
			log.info("row element in table " + table + " not found");
			TestNGHelper.fail("row element in table " + table + " not found");
		}
		return element;
	}

	/**
	 * This method used to get cell element from a specific row element
	 * 
	 * @param row
	 *            row of String type value
	 * @param colTagName
	 *            colTagName of String type value
	 * @param colIndex
	 *            colIndex of Int type value
	 * @return Returns element in the column based on row, colTagName and
	 *         colIndex Example : SeleniumHelper.getColumnFromRow("abc" , "td"
	 *         ,3)
	 */
	public static WebElement getColumnFromRow(WebElement row, String colTagName, int colIndex) {
		try {
			List<WebElement> cols = row.findElements(By.tagName(colTagName));
			return cols.get(colIndex);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * This method is used to get the cell element from grid
	 * 
	 * @param table
	 *            table grid element
	 * @param rowTagName
	 *            rowTagName of String type value
	 * @param colTagName
	 *            colTagName of String type value
	 * @param rowIndex
	 *            rowIndex of Int type value
	 * @param colIndex
	 *            colIndex of Int type value
	 * @param elementTagName
	 *            elementTagName of String type value
	 * @return Return a webElement Example:-
	 *         SeleniumHelper.getCellEleemnt(searchGrid,"tr","td",1,1,"a");
	 */
	public static WebElement getCellElement(WebElement table, String rowTagName, String colTagName, int rowIndex,
			int colIndex, String elementTagName) {
		try {
			List<WebElement> row = table.findElements(By.tagName(rowTagName));
			List<WebElement> cells = (row.get(rowIndex)).findElements(By.tagName(colTagName));
			return cells.get(colIndex).findElement(By.tagName(elementTagName));
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * This method is used to switch frame
	 * 
	 * @param driver
	 *            PageDriver of webDriver type
	 * @param frameId
	 *            frameId of String type value
	 * @return Switched to New Frame Example:- SeleniumHelper.switchFrame(
	 *         WindowPageDriver ,"Win_log")
	 */
	public static boolean switchFrame(WebDriver driver, String frameId) {
		try {
			driver.switchTo().frame(frameId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method is used to switch frame
	 * 
	 * @param driver
	 *            PageDriver of webDriver type
	 * @param frameIndex
	 *            frameIndex of Int type value
	 * @return Switched to New Frame Example:-
	 *         SeleniumHelper.switchFrame(WindowPageDriver,4)
	 */
	public static boolean switchFrame(WebDriver driver, int frameIndex) {
		try {
			driver.switchTo().frame(frameIndex);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean switchFrame(WebDriver driver, WebElement frame) {
		try {
			driver.switchTo().frame(frame);
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Switch to frame failed " + frame);
			TestNGHelper.fail("Switch to frame failed " + frame);
			return false;
		} finally {
			count = 0;
		}
	}

	/**
	 * This method is used to switch the default content/default page of
	 * application
	 * 
	 * @param driver
	 *            Page driver of webDriver type
	 * @return Returns true/false based on switch status
	 *         <p>
	 *         Example :SeleniumHelper.switchDefaultContent("searchDriver");
	 */
	public static boolean switchDefaultContent(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			log.info(AutomationCore.testName + ": Successfully switched to default content");
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to switch to default content");
			TestNGHelper.fail("Failed to switch to default content");
			return false;
		}
	}

	/**
	 * This method is used to take screenshot of pages
	 * 
	 * @param webdriver
	 *            pageDriver of respective screen
	 * @param fileWithPath
	 *            filepath to save the screenshot
	 *            <p>
	 *            Example:- SeleniumHelper.takeSnapShot(DashBoardPageDriver,
	 *            "C:\Polaris Automation")
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(fileWithPath);
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("NOT captured Screenshot");
			e.getMessage();
		}

	}

	/**
	 * This Method is used to switch to new window
	 * 
	 * @param driver
	 *            driver of webDriver type
	 * @return returns true or false of boolean type
	 */
	public static boolean switchToWindow(WebDriver driver) {
		try {
			String currentWindowhandle = driver.getWindowHandle();
			System.out.println(currentWindowhandle);
			ArrayList<String> handles = new ArrayList<String>(driver.getWindowHandles());
			for (String handle : handles) {
				if (!handle.equals(currentWindowhandle)) {
					System.out.println("true");
					driver.switchTo().window(handle);
					System.out.println("yes");
					System.out.println(driver.getTitle());
					return true;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return false;
	}

	/**
	 * This Method is used to switch to new window
	 * 
	 * @param driver
	 *            driver of webDriver type
	 * @return returns true or false of boolean type
	 */
	public static boolean switchWindow(WebDriver driver) {
		try {
			String currentWindowhandle = driver.getWindowHandle();
			Set<String> windowHandles = driver.getWindowHandles();
			Iterator<String> handles = windowHandles.iterator();
			while (handles.hasNext()) {
				String subHandle = handles.next();
				if (!currentWindowhandle.equals(subHandle)) {
					driver.switchTo().window(subHandle);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return false;
	}

	/**
	 * This method is used to set wait the screen untill presence of element
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param timeOutInSeconds
	 *            timeOutInSeconds of Int type value
	 * @param elementLocator
	 *            elementLocator of String type value
	 * @param elementLocatorValue
	 *            of String type value Example :
	 *            SeleniumHelper.setExplicitWaitForPresenceOfElement(
	 *            DashBoardPageDriver,10,Constants.elemantLocator_id,
	 *            readORProperties(currentScreenName, "table_RequestsId")))
	 */
	// public static void setExplicitWaitForPresenceOfElement(WebDriver
	// driver,long timeOutInSeconds,String elementLocator,String
	// elementLocatorValue)
	// {
	// WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
	// switch (elementLocator.toLowerCase())
	// {
	// case "xpath":
	// waitDriver.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementLocatorValue)));
	// break;
	// case "name":
	// waitDriver.until(ExpectedConditions.presenceOfElementLocated(By.name(elementLocatorValue)));
	// break;
	// case "id":
	// waitDriver.until(ExpectedConditions.presenceOfElementLocated(By.id(elementLocatorValue)));
	// break;
	// default:
	// waitDriver.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementLocatorValue)));
	// break;
	// }
	//
	// }

	public static boolean setExplicitWaitForFrameAndSwitch(WebDriver driver, long timeOutInSeconds,
			String elementLocator, String elementLocatorValue) {
		try {
			WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
			switch (elementLocator.toLowerCase()) {
			case "xpath":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame with xpath locator " + elementLocatorValue);
				break;
			case "name":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame with name locator " + elementLocatorValue);
				break;
			case "id":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame with id locator " + elementLocatorValue);
				break;
			default:
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame with xpath locator " + elementLocatorValue);
				break;
			}
			return true;

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Unable to switch to frame with locator " + elementLocatorValue);
			TestNGHelper.fail("Unable to switch to frame with locator " + elementLocatorValue);
			return false;
		}

	}

	/*public static boolean setExplicitWaitForFrameAndSwitch_poc(WebDriver driver, long timeOutInSeconds,
			String elementLocator, String elementLocatorValue, String elementDescription) {
		try {
			WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
			switch (elementLocator.toLowerCase()) {
			case "xpath":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame " + elementDescription + " with xpath locator "
						+ elementLocatorValue);
				break;
			case "name":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame " + elementDescription + " with xpath locator "
						+ elementLocatorValue);
				break;
			case "id":
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame " + elementDescription + " with xpath locator "
						+ elementLocatorValue);
				break;
			default:
				waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(elementLocatorValue)));
				log.info(AutomationCore.testName + ": Switched to frame " + elementDescription + " with xpath locator "
						+ elementLocatorValue);
				break;
			}
			return true;

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Unable to switch to frame " + elementDescription + " with locator "
					+ elementLocatorValue);
			TestNGHelper
					.fail("Unable to switch to frame " + elementDescription + " with locator " + elementLocatorValue);
			return false;
		}

	}*/

	/**
	 * This method is used to set wait the screen untill presence of Alert
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param timeOutInSeconds
	 *            timeOutInSeconds of Int type value Example :
	 *            SeleniumHelper.setExplicitWaitForPresenceOfAlert(
	 *            DashBoardPageDriver,30)
	 */
	public static boolean setExplicitWaitForPresenceOfAlert(WebDriver driver, long timeOutInSeconds) {
		try {
			WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
			waitDriver.until(ExpectedConditions.alertIsPresent());
			log.info(AutomationCore.testName + ": Alert found");
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Alert Not found");
			TestNGHelper.fail("Alert Not found");
			return false;
		}

	}

	public static boolean setExplicitWaitForPresenceOfAlert_NoFail(WebDriver driver, long timeOutInSeconds) {
		try {
			WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
			waitDriver.until(ExpectedConditions.alertIsPresent());
			log.info(AutomationCore.testName + ": Alert found");
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Alert Not found");
			return false;
		}

	}

	/**
	 * This method is used to set wait for the element to click
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param timeOutInSeconds
	 *            timeOutInSeconds of Int type value
	 * @param elementLocator
	 *            elementLocator of String type value
	 * @param elementLocatorValue
	 *            of String type value Example :
	 *            SeleniumHelper.setExplicitWaitForElementToBeClickable(
	 *            DashBoardPageDriver,10,Constants.elemantLocator_id,
	 *            readORProperties(currentScreenName, "table_RequestsId")))
	 */
	// public static void setExplicitWaitForElementToBeClickable(WebDriver
	// driver,long timeOutInSeconds,String elementLocator,String
	// elementLocatorValue)
	// {
	// WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
	// switch (elementLocator.toLowerCase())
	// {
	// case "xpath":
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.xpath(elementLocatorValue)));
	// break;
	// case "name":
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.name(elementLocatorValue)));
	// break;
	// case "id":
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.id(elementLocatorValue)));
	// break;
	// case "classname":
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.className(elementLocatorValue)));
	// break;
	// case "linktext":
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.linkText(elementLocatorValue)));
	// break;
	// default:
	// waitDriver.until(ExpectedConditions.elementToBeClickable(By.xpath(elementLocatorValue)));
	// break;
	// }
	// }

	/**
	 * This method is used to set wait for the element to click
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param timeOutInSeconds
	 *            timeOutInSeconds of Int type value
	 * @param elementLocator
	 *            elementLocator of String type value
	 * @param elementLocatorValue
	 *            of String type value Example :
	 *            SeleniumHelper.setExplicitWaitForElementToBeClickable(
	 *            DashBoardPageDriver,10,Constants.elemantLocator_id,
	 *            readORProperties(currentScreenName, "table_RequestsId")))
	 */
	// public static void setExplicitwaitForallElementsVisibility(WebDriver
	// driver,long timeOutInSeconds,String elementLocator,
	// String elementLocatorValue)
	// {
	// WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
	// switch (elementLocator.toLowerCase())
	// {
	//
	// case "tagName":
	// waitDriver.until(ExpectedConditions.visibilityOfAllElements(findElementsByTagName(driver,
	// elementLocatorValue)));
	// break;
	// default:
	// waitDriver.until(ExpectedConditions.visibilityOfAllElements(findElementsByTagName(driver,
	// elementLocatorValue)));
	// break;
	// }
	// }

	/**
	 * This method is used to set wait for until Visibility Of Element
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param timeOutInSeconds
	 *            timeOutInSeconds of Int type value
	 * @param elementLocator
	 *            elementLocator of String type value
	 * @param elementLocatorValue
	 *            of String type value Example :
	 *            SeleniumHelper.setExplicitWaitForVisibilityOfElementLocated(
	 *            DashBoardPageDriver,10,Constants.elemantLocator_id,
	 *            readORProperties(currentScreenName, "table_RequestsId")))
	 */
	// public static void setExplicitWaitForVisibilityOfElementLocated(WebDriver
	// driver,long timeOutInSeconds,String elementLocator,String
	// elementLocatorValue)
	// {
	// WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
	// switch (elementLocator.toLowerCase())
	// {
	// case "xpath":
	// waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementLocatorValue)));
	// break;
	// case "name":
	// waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.name(elementLocatorValue)));
	// break;
	// case "id":
	// waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementLocatorValue)));
	// break;
	// default:
	// waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementLocatorValue)));
	// break;
	// }
	// }

	public static boolean checkForPageReadyState(WebDriver driver) {
		int count = 0;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			while (!js.executeScript("return document.readyState").toString().equals("complete")) {
				threadSleep(1000);
				if (count == 20) {
					return false;
				}
				count++;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Timeout (20 seconds) waiting for Page Load Request to complete");
			TestNGHelper.fail("Timeout (20 seconds) waiting for Page Load Request to complete");
		} finally {
			count = 0;
		}

		return true;
	}

	/**
	 * This method used to suspend execution for a specified period
	 * 
	 * @param milliseconds
	 *            of Int type value Example: SeleniumHelper.threadSleep(1000)
	 */
	public static void threadSleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * This method is used to mouse click on webelement
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param elementToBeHover
	 *            Webelement to click Example :
	 *            SeleniumHelper.mouseHoverClick(DashBoardPageDriver,search_btn)
	 */
	public static void mouseHoverClick(WebDriver driver, WebElement elementToBeHover) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(elementToBeHover).click().build().perform();
			actions.sendKeys(Keys.ENTER);
			log.info(AutomationCore.testName + ": Successfully hovered over the element " + elementToBeHover
					+ " and clicked on it");
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				mouseHoverClick(getDriver(), staleElement);
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
						+ " and click on it");
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and click on it");
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				mouseHoverClick(getDriver(), webElement);
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
						+ " and click on it");
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and click on it");
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
					+ " and click on it");
			TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and click on it");
		} finally {
			count = 0;
		}

	}

	public static void pressEnter(WebDriver driver) {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(Keys.ENTER);
			log.info(AutomationCore.testName + ": Successfully pressed on Enter button");
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": failed press on Enter button");
			TestNGHelper.fail("failed press on Enter button");
		}

	}

	public static void mouseHover(WebDriver driver, WebElement elementToBeHover) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(elementToBeHover).build().perform();
			log.info(AutomationCore.testName + ": Successfully hovered over the element " + elementToBeHover);
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				mouseHover(getDriver(), staleElement);
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover);
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				mouseHover(getDriver(), webElement);
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover);
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover);
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover);
			TestNGHelper.fail("failed to hover over the element " + elementToBeHover);
		} finally {
			count = 0;
		}

	}

	public static boolean mouseHoverClickAndHold(WebDriver driver, WebElement elementToBeHover) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(elementToBeHover).clickAndHold(elementToBeHover).build().perform();
			log.info(AutomationCore.testName + ": Successfully hovered over the element " + elementToBeHover
					+ " and holded it");
			return true;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				mouseHoverClickAndHold(getDriver(), staleElement);
				return true;
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
						+ " and hold it");
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and hold it");
				return false;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(elementToBeHover);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				mouseHoverClickAndHold(getDriver(), webElement);
				return true;
			} else {
				log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
						+ " and hold it");
				TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and hold it");
				return false;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": failed to hover over the element " + elementToBeHover
					+ " and hold it");
			TestNGHelper.fail("failed to hover over the element " + elementToBeHover + " and hold it");
			return false;
		} finally {
			count = 0;
		}

	}

	/**
	 * This method is used to select a element from a list
	 * 
	 * @param element
	 *            Webelement to select
	 * @param Option
	 *            of String type value
	 * @return Selecting a item from the List Example
	 *         :SeleniumHelper.selectListItem(select_States(),strStates)
	 */
	public static boolean selectListItem(WebElement element, String Option) {
		try {
			Select sel = new Select(element);
			sel.selectByValue(Option);
			log.info(AutomationCore.testName + ": Selected option " + Option + " from dropdown list " + element);
			return true;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectListItem(staleElement, Option);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select option " + Option + " from dropdown list "
						+ element);
				se.printStackTrace();
				TestNGHelper.fail("Failed to select option " + Option + " from dropdown list " + element);
				return false;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectListItem(webElement, Option);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select option " + Option + " from dropdown list "
						+ element);
				we.printStackTrace();
				TestNGHelper.fail("Failed to select option " + Option + " from dropdown list " + element);
				return false;
			}

		} catch (Exception e) {
			log.info(
					AutomationCore.testName + ": Failed to select option " + Option + " from dropdown list " + element);
			e.printStackTrace();
			TestNGHelper.fail("Failed to select option " + Option + " from dropdown list " + element);
			return false;
		} finally {
			count = 0;
		}

	}

	/**
	 * This method is used to select a element from a list by Text
	 * 
	 * @param element
	 *            Webelement to select
	 * @param text
	 *            of String type value
	 * @return Selecting a item from the List
	 *         SeleniumHelper.selectListItemByText(select_States(),strStates)
	 */
	public static boolean selectListItemByText(WebElement element, String text) {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
			log.info(AutomationCore.testName + ": Selected text " + text + " from the list element " + element);
			/*
			 * //TestNGHelper.assertTrue(waitUntilDropdownValuePopulated(element),
			 * "Waited until dropdown element get refreshed but the text selected is also refreshed "
			 * , AutomationCore.currentStepName);
			 */
			return true;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectListItemByText(staleElement, text);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element "
						+ element);
				TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
				return false;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectListItemByText(webElement, text);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element "
						+ element);
				TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
				return false;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element " + element);
			TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
			return false;
		} finally {
			count = 0;
		}

	}

	/**
	 * This method is used to select a element from a list by Text
	 * 
	 * @param element
	 *            Webelement to select
	 * @param text
	 *            of String type value
	 * @return Selecting a item from the List
	 *         SeleniumHelper.selectListItemByText(select_States(),strStates)
	 */
	public static boolean selectListItemByText(WebElement element, String text, long time) {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
			sel.wait(time);
			log.info(AutomationCore.testName + ": Selected text " + text + " from the list element " + element);
			return true;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectListItemByText(staleElement, text, time);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element "
						+ element);
				TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
				return false;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectListItemByText(webElement, text, time);
				return false;
			} else {
				log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element "
						+ element);
				TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
				return false;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select text " + text + " from the list element " + element);
			TestNGHelper.fail("Failed to select text " + text + " from the list element " + element);
			return false;
		} finally {
			count = 0;
		}

	}

	public static String getFirstSelectedItemTextInList(WebElement element) {
		String firstSelectedItemText = "";
		try {
			Select sel = new Select(element);
			firstSelectedItemText = sel.getFirstSelectedOption().getText();
			log.info(AutomationCore.testName + ": Unable to get text of first selected item from the list element "
					+ element);
			return firstSelectedItemText;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				getFirstSelectedItemTextInList(staleElement);
				return firstSelectedItemText;
			} else {
				log.info(AutomationCore.testName + ": Unable to get text of first selected item from the list element "
						+ element);
				TestNGHelper.fail("Unable to get text of first selected item from the list element " + element);
				return firstSelectedItemText;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				getFirstSelectedItemTextInList(webElement);
				return firstSelectedItemText;
			} else {
				log.info(AutomationCore.testName + ": Unable to get text of first selected item from the list element "
						+ element);
				TestNGHelper.fail("Unable to get text of first selected item from the list element " + element);
				return firstSelectedItemText;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Unable to get text of first selected item from the list element "
					+ element);
			TestNGHelper.fail("Unable to get text of first selected item from the list element " + element);
			return firstSelectedItemText;
		} finally {
			count = 0;
		}

	}

	/**
	 * This method is used to select a element from a list by index
	 * 
	 * @param element
	 *            Webelement to select
	 * @param index
	 *            of int type value
	 * @return Selecting a item from the List
	 *         SeleniumHelper.selectListItemByIndex(select_States(),3)
	 */
	public static boolean selectListItemByIndex(WebElement element, int index) {
		try {
			Select sel = new Select(element);
			sel.selectByIndex(index);
			log.info(AutomationCore.testName + ": Selected item by index " + index + " from the list element "
					+ element);
			return true;
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectListItemByIndex(staleElement, index);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select item by index " + index
						+ " from the list element " + element);
				TestNGHelper.fail("Failed to select item by index " + index + " from the list element " + element);
				return false;
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				selectListItemByIndex(webElement, index);
				return true;
			} else {
				log.info(AutomationCore.testName + ": Failed to select item by index " + index
						+ " from the list element " + element);
				TestNGHelper.fail("Failed to select item by index " + index + " from the list element " + element);
				return false;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select item by index " + index + " from the list element "
					+ element);
			TestNGHelper.fail("Failed to select item by index " + index + " from the list element " + element);
			return false;
		} finally {
			count = 0;
		}

	}

	/**
	 * This method is used to accept alertmessage
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @return Accepting the alert message Example:
	 *         SeleniumHelper.getAlertMessageAccept(DashBoardpagr)
	 */

	public static String getAlertMessageAccept(WebDriver driver) {
		String alertText = "";
		try {
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
			alert.accept();
			log.info(AutomationCore.testName + ": Successfully retrieved alert text " + alertText
					+ " and accepted the alert");
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve alert text and accept it");
			TestNGHelper.fail("Failed to retrieve alert text and accept it");
		}
		return alertText;
	}

	/**
	 * This method is used to accept alert
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @return Accepting the alert Example:
	 *         SeleniumHelper.accpetAlert(DashBoardpagr)
	 */
	public static boolean accpetAlert(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			log.info(AutomationCore.testName + ": Successfully accepted the alert");
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to accept alert");
			TestNGHelper.fail("Failed to accept alert");
		}
		return false;
	}

	/**
	 * This method is used to select Table Cellelement
	 * 
	 * @param driver
	 *            pageDriver of respective screen
	 * @param tableId
	 *            element locator of webtable of string type
	 * @param columnTextName
	 *            String type value
	 * @param uniqueValue
	 *            String type value
	 * @param headerTagName
	 *            element locator of webtable of of string type
	 * @param rowTagName
	 *            String type value
	 * @param colTagName
	 *            String type value
	 * @param cellElementTagName
	 *            String type value
	 * @param pageNumAttribute
	 *            String type value
	 * @return Table cell Element Example :
	 *         SeleniumHeleper.selectTableCellElementById(DashBoardPage,"td",
	 *         "Delegatename","Delegatecode","a","tr","td","a")
	 */
	public static boolean selectTableCellElementById(WebDriver driver, String tableId, String columnTextName,
			String uniqueValue, String headerTagName, String rowTagName, String colTagName, String cellElementTagName,
			String pageNumAttribute) {
		try {
			int headerInd;
			int rowInd;

			WebElement table = findElement(driver, new String[] { "Id", tableId }, 30, ExplicitConditions.CLICK);

			if (table != null) {
				headerInd = SeleniumHelper.getColumnIndexFromTable(table, headerTagName, columnTextName);
				if (headerInd == -1) {
					return false;
				}
				rowInd = SeleniumHelper.getRowIndexFromTableWithPageNationById(driver, tableId, rowTagName, colTagName,
						uniqueValue, pageNumAttribute);
				if (rowInd == -1) {
					return false;
				}
			} else {
				return false;
			}

			if (!(headerInd == -1 && rowInd == -1)) {
				table = findElement(driver, new String[] { "Id", tableId }, 30, ExplicitConditions.CLICK);
				WebElement LinkObj = SeleniumHelper.getCellElement(table, rowTagName, colTagName, rowInd, (headerInd),
						cellElementTagName);
				if (!LinkObj.equals(null)) {
					clickElement(LinkObj);
					return true;
				}
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select TableCellElementById");
			TestNGHelper.fail("Failed to get row column count of table");
		}

		return false;

	}

	/**
	 * This method is used to getrowcount from a table
	 * 
	 * @param table
	 *            Webtable String type value
	 * @param rowTagName
	 *            String type value
	 * @return Row count Example :
	 *         Seleniumhelper.getTableRowCount(Question_table,"tr")
	 */
	public static int getTableRowCount(WebElement table, String rowTagName) {
		int rowCount = -1;
		try {
			List<WebElement> rows = table.findElements(By.tagName(rowTagName));
			rowCount = rows.size();
			log.info(AutomationCore.testName + ": Table Row count value is " + rowCount);
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get row column count of table " + table);
			TestNGHelper.fail("Failed to get row column count of table " + table);
		}
		return rowCount;
	}

	/**
	 * This method is used get columncount from a row
	 * 
	 * @param rowElement
	 *            locator of rowElement of string type
	 * @param colTagName
	 *            String type value
	 * @return Column count Example :
	 *         Seleniumheleper.getRowCoulmnCount("Delegatename","tr")
	 */
	public static int getRowCoulmnCount(WebElement rowElement, String colTagName) {
		int colCount = -1;
		try {
			List<WebElement> rows = rowElement.findElements(By.tagName(colTagName));
			colCount = rows.size();
			log.info(AutomationCore.testName + ": RowColumn count value is " + colCount);
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get row column count of row element " + rowElement);
			TestNGHelper.fail("Failed to get row column count of row element " + rowElement);
		}
		return colCount;
	}

	public static boolean isAlertExits(WebDriver driver) {
		try {
			if (setExplicitWaitForPresenceOfAlert(currentDriver, 30)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				log.info(AutomationCore.testName + ": Switched to alert" + alert + " and accepted it");
				return true;
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Alert not found");
			TestNGHelper.fail("Alert not found");
		}
		return false;
	}

	public static void AcceptAlertIfExists(WebDriver driver) {
		try {
			if (setExplicitWaitForPresenceOfAlert_NoFail(currentDriver, 30)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				log.info(AutomationCore.testName + ": Switched to alert" + alert + " and accepted it");
			} else {
				log.info(AutomationCore.testName + ": Alert not found");
			}

		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Alert not found");
		}
	}

	public static boolean isAlertExitsIE(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			if (setExplicitWaitForPresenceOfAlert(driver, 30)) {
				pressEnter(driver);
			}
			log.info(AutomationCore.testName + ": Switched to alert" + alert + " and accepted it in IE");
			return true;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Alert not found in IE");
			TestNGHelper.fail("Alert not found in IE");
			return false;
		}
	}

	/**
	 * Newly added methods for Object location with explicit and implicit waits
	 * along with element locator enum
	 */

	/**
	 * This enum helps us in defining the constants for elements locator
	 */
	public enum ElementLocator {

		ID, CLASSNAME, LINKTEXT, PARTIALLINKTEXT, XPATH, CSSSELECTOR, TAGNAME, NAME
	}

	/**
	 * This method is similar to explicit wait but without a condition
	 * 
	 * @param by
	 *            - the element locator with By class
	 * @return WebElement if element found within the timeout
	 */
	public static WebElement waitForElementToPresent(WebDriver driver, final By by) {
		try {
			// threadSleep(5000);
			WebElement element = (new WebDriverWait(driver, synTimeVal))
					.until(ExpectedConditions.presenceOfElementLocated(by));
			log.info(AutomationCore.testName + ": Element with locator " + by + " is present.");
			return element;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator " + by + " is not present.");
			TestNGHelper.fail("Element with locator " + by + " is not present.");
		}
		return null;
	}

	/**
	 * This method is similar to explicit wait but without a condition
	 * 
	 * @param by
	 *            - the element locator with By class
	 * @return WebElement if element found within the timeout
	 */
	public static List<WebElement> waitForElementsToPresent(WebDriver driver, final By by) {
		try {
			// threadSleep(5000);
			List<WebElement> elements = (new WebDriverWait(driver, synTimeVal))
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are not present.");
			return elements;
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are not present.");
			TestNGHelper.fail("List of Elements with locator " + by + " are not present.");
		}
		return null;
	}

	public static WebElement findElement(WebDriver driver, String[] elementProperties) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForElementToPresent(driver, By.id(elementProperties[1]));
			case CLASSNAME:
				return waitForElementToPresent(driver, By.className(elementProperties[1]));
			case LINKTEXT:
				return waitForElementToPresent(driver, By.linkText(elementProperties[1]));
			case PARTIALLINKTEXT:
				return waitForElementToPresent(driver, By.partialLinkText(elementProperties[1]));
			case XPATH:
				return waitForElementToPresent(driver, By.xpath(elementProperties[1]));
			case CSSSELECTOR:
				return waitForElementToPresent(driver, By.cssSelector(elementProperties[1]));
			case NAME:
				return waitForElementToPresent(driver, By.name(elementProperties[1]));
			case TAGNAME:
				return waitForElementToPresent(driver, By.tagName(elementProperties[1]));
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is not found");
			TestNGHelper.fail("Element with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not found");
		}
		return null;
	}

	public static WebElement findStaticElement(WebDriver driver, String[] elementProperties) {
		try {
			WebElement ele;
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				ele = driver.findElement(By.id(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case CLASSNAME:
				ele = driver.findElement(By.className(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case LINKTEXT:
				ele = driver.findElement(By.linkText(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case PARTIALLINKTEXT:
				ele = driver.findElement(By.partialLinkText(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case XPATH:
				ele = driver.findElement(By.xpath(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case CSSSELECTOR:
				ele = driver.findElement(By.cssSelector(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case NAME:
				ele = driver.findElement(By.name(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			case TAGNAME:
				ele = driver.findElement(By.tagName(elementProperties[1]));
				log.info(AutomationCore.testName + ": Element with locator "
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is found");
				return ele;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is not found");
			TestNGHelper.fail("Element with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not found");

		}
		return null;
	}

	public static List<WebElement> findElements(WebDriver driver, String[] elementProperties) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForElementsToPresent(driver, By.id(elementProperties[1]));
			case CLASSNAME:
				return waitForElementsToPresent(driver, By.className(elementProperties[1]));
			case LINKTEXT:
				return waitForElementsToPresent(driver, By.linkText(elementProperties[1]));
			case PARTIALLINKTEXT:
				return waitForElementsToPresent(driver, By.partialLinkText(elementProperties[1]));
			case XPATH:
				return waitForElementsToPresent(driver, By.xpath(elementProperties[1]));
			case CSSSELECTOR:
				return waitForElementsToPresent(driver, By.cssSelector(elementProperties[1]));
			case NAME:
				return waitForElementsToPresent(driver, By.name(elementProperties[1]));
			case TAGNAME:
				return waitForElementsToPresent(driver, By.tagName(elementProperties[1]));
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " are not present.");
			TestNGHelper.fail("List of Elements with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " are not present.");
		}

		return null;
	}

	public enum ExplicitConditions {
		CLICK, VISIBLE, staleness, title, textPresent, INVISIBLE, PRESENCE
	}

	public static WebElement setExplicitWaitForStaleElementToBeRefreshed(WebDriver driver, long timeOutInSeconds,
			String locator, String locatorValue) {
		WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
		try {
			threadSleep(3000);
			switch (locator.toUpperCase()) {
			case "ID":
				return waitDriver.until(
						ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.id(locatorValue))));
			case "CLASS NAME":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.elementToBeClickable(By.className(locatorValue))));
			case "TAG NAME":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.elementToBeClickable(By.tagName(locatorValue))));
			case "NAME":
				return waitDriver.until(
						ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.name(locatorValue))));
			case "XPATH":
				return waitDriver.until(
						ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue))));
			case "CSS SELECTOR":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorValue))));
			case "PARTIAL LINK TEXT":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.elementToBeClickable(By.partialLinkText(locatorValue))));
			case "LINK TEXT":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.elementToBeClickable(By.linkText(locatorValue))));

			}
		} catch (Exception e) {
			log.info("Unable to find element with locator:" + locator + " and locator value: " + locatorValue);
			TestNGHelper.fail("Unable to find element with locator:" + locator + " and locator value: " + locatorValue);
		}
		return null;

	}

	public static List<WebElement> setExplicitWaitForStaleElementsToBeRefreshed(WebDriver driver, long timeOutInSeconds,
			String locator, String locatorValue) {
		WebDriverWait waitDriver = new WebDriverWait(driver, timeOutInSeconds);
		try {
			threadSleep(3000);
			switch (locator.toUpperCase()) {
			case "ID":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(locatorValue))));
			case "CLASS NAME":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(locatorValue))));
			case "TAG NAME":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(locatorValue))));
			case "NAME":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(locatorValue))));
			case "XPATH":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locatorValue))));
			case "CSS SELECTOR":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(locatorValue))));
			case "PARTIAL LINK TEXT":
				return waitDriver.until(ExpectedConditions.refreshed(
						ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(locatorValue))));
			case "LINK TEXT":
				return waitDriver.until(ExpectedConditions
						.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(locatorValue))));

			}
		} catch (Exception e) {
			log.info("Unable to find element with locator:" + locator + " and locator value: " + locatorValue);
			TestNGHelper.fail("Unable to find element with locator:" + locator + " and locator value: " + locatorValue);
		}
		return null;

	}

	public static WebElement findElement(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementToBePresent(driver, By.id(elementProperties[1]), timeOutSeconds, conditions);
			case CLASSNAME:
				return waitForAjaxElementToBePresent(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions);
			case TAGNAME:
				return waitForAjaxElementToBePresent(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions);
			case NAME:
				return waitForAjaxElementToBePresent(driver, By.name(elementProperties[1]), timeOutSeconds, conditions);
			case XPATH:
				return waitForAjaxElementToBePresent(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions);
			case CSSSELECTOR:
				return waitForAjaxElementToBePresent(driver, By.cssSelector(elementProperties[1]), timeOutSeconds,
						conditions);
			case PARTIALLINKTEXT:
				return waitForAjaxElementToBePresent(driver, By.partialLinkText(elementProperties[1]), timeOutSeconds,
						conditions);
			case LINKTEXT:
				return waitForAjaxElementToBePresent(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is not " + conditions
					+ " . Waited " + timeOutSeconds + " seconds for the element");
			TestNGHelper.fail("Element with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not " + conditions + " . Waited " + timeOutSeconds + " seconds for the element");
		}

		return null;
	}

	public static WebElement findElement_NoFail(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementToBePresent_NoFail(driver, By.id(elementProperties[1]), timeOutSeconds,
						conditions);
			case CLASSNAME:
				return waitForAjaxElementToBePresent_NoFail(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions);
			case TAGNAME:
				return waitForAjaxElementToBePresent_NoFail(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions);
			case NAME:
				return waitForAjaxElementToBePresent_NoFail(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions);
			case XPATH:
				return waitForAjaxElementToBePresent_NoFail(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions);
			case CSSSELECTOR:
				return waitForAjaxElementToBePresent_NoFail(driver, By.cssSelector(elementProperties[1]),
						timeOutSeconds, conditions);
			case PARTIALLINKTEXT:
				return waitForAjaxElementToBePresent_NoFail(driver, By.partialLinkText(elementProperties[1]),
						timeOutSeconds, conditions);
			case LINKTEXT:
				return waitForAjaxElementToBePresent_NoFail(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is not " + conditions
					+ " . Waited " + timeOutSeconds + " seconds for the element");
			return null;
		}

		return null;
	}

	public static WebElement findElement_poc(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions, String elementDescription) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementToBePresent_poc(driver, By.id(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case CLASSNAME:
				return waitForAjaxElementToBePresent_poc(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case TAGNAME:
				return waitForAjaxElementToBePresent_poc(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case NAME:
				return waitForAjaxElementToBePresent_poc(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case XPATH:
				return waitForAjaxElementToBePresent_poc(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case CSSSELECTOR:
				return waitForAjaxElementToBePresent_poc(driver, By.cssSelector(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case PARTIALLINKTEXT:
				return waitForAjaxElementToBePresent_poc(driver, By.partialLinkText(elementProperties[1]),
						timeOutSeconds, conditions, elementDescription);
			case LINKTEXT:
				return waitForAjaxElementToBePresent_poc(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			}
		} catch (CucumberException e) {
			System.out.println("Cucumber Exception");
		}

		catch (Exception e) {
			System.out.println("find called");
			log.info(elementDescription + " with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not " + conditions + " . Waited " + timeOutSeconds + " seconds for the element");
			try {
				TestNGHelper.fail("<b>" + elementDescription + "</b>" + " with locator <b>"
						+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + "</b> is not " + conditions
						+ " . Waited " + timeOutSeconds + " seconds for the element");
			} catch (Exception e1) {

			}

		}

		return null;
	}

	public static WebElement findElement(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions, String elementDescription) {
		try {
			threadSleep(5000);
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementToBePresent_poc(driver, By.id(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case CLASSNAME:
				return waitForAjaxElementToBePresent_poc(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case TAGNAME:
				return waitForAjaxElementToBePresent_poc(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case NAME:
				return waitForAjaxElementToBePresent_poc(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case XPATH:
				return waitForAjaxElementToBePresent_poc(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case CSSSELECTOR:
				return waitForAjaxElementToBePresent_poc(driver, By.cssSelector(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			case PARTIALLINKTEXT:
				return waitForAjaxElementToBePresent_poc(driver, By.partialLinkText(elementProperties[1]),
						timeOutSeconds, conditions, elementDescription);
			case LINKTEXT:
				return waitForAjaxElementToBePresent_poc(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions, elementDescription);
			}
		} catch (Exception e) {
			log.info(elementDescription + " with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not " + conditions + " . Waited " + timeOutSeconds + " seconds for the element");
			TestNGHelper.fail("<b>" + elementDescription + "</b>" + " with locator <b>"
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + "</b> is not " + conditions
					+ " . Waited " + timeOutSeconds + " seconds for the element");
		}

		return null;
	}

	public static List<WebElement> findElements(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementsToBePresent(driver, By.id(elementProperties[1]), timeOutSeconds, conditions);
			case CLASSNAME:
				return waitForAjaxElementsToBePresent(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions);
			case TAGNAME:
				return waitForAjaxElementsToBePresent(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions);
			case NAME:
				return waitForAjaxElementsToBePresent(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions);
			case XPATH:
				return waitForAjaxElementsToBePresent(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions);
			case CSSSELECTOR:
				return waitForAjaxElementsToBePresent(driver, By.cssSelector(elementProperties[1]), timeOutSeconds,
						conditions);
			case PARTIALLINKTEXT:
				return waitForAjaxElementsToBePresent(driver, By.partialLinkText(elementProperties[1]), timeOutSeconds,
						conditions);
			case LINKTEXT:
				return waitForAjaxElementsToBePresent(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " are not present. Waited "
					+ timeOutSeconds + " for the elements to be available");
			TestNGHelper.fail("List of Elements with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " are not present. Waited "
					+ timeOutSeconds + " seconds for the elements to be available");
		}

		return null;
	}

	public static List<WebElement> findElements_NoFail(WebDriver driver, String[] elementProperties,
			long timeOutSeconds, ExplicitConditions conditions) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.id(elementProperties[1]), timeOutSeconds,
						conditions);
			case CLASSNAME:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions);
			case TAGNAME:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions);
			case NAME:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions);
			case XPATH:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions);
			case CSSSELECTOR:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.cssSelector(elementProperties[1]),
						timeOutSeconds, conditions);
			case PARTIALLINKTEXT:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.partialLinkText(elementProperties[1]),
						timeOutSeconds, conditions);
			case LINKTEXT:
				return waitForAjaxElementsToBePresent_NoFail(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " are not present. Waited "
					+ timeOutSeconds + " for the elements to be available");
			return null;
		}

		return null;
	}

	// public static WebElement waitForAjaxElementToBePresent(WebDriver driver,
	// By by, long timeOutInSeconds, ExplicitConditions condition)
	// {
	// try
	// {
	// WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
	// switch (condition)
	// {
	// case CLICK:
	// wait.until(ExpectedConditions.elementToBeClickable(by));
	// return driver.findElement(by);
	// case VISIBLE:
	// wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	// return driver.findElement(by);
	// case EXISTS:
	// wait.until(ExpectedConditions.presenceOfElementLocated(by));
	// return driver.findElement(by);
	// default:
	// wait.until(ExpectedConditions.elementToBeClickable(by));
	// return driver.findElement(by);
	// }
	// }
	// catch (Exception e)
	// {
	// e.getMessage();
	// }
	// return null;
	// }
	
	public static void waitForWebElementToBePresent(WebDriver driver, WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public static WebElement waitForAjaxElementToBePresent(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			WebElement ele = null;
			switch (condition) {
			case CLICK:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return ele;
			case VISIBLE:
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is visible");
				return ele;
			case PRESENCE:
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is present");
				return ele;
			default:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return ele;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator " + by + " is not " + condition + " . Waited "
					+ timeOutInSeconds + " for the element");
			TestNGHelper.fail("Element with locator " + by + " is not " + condition + " . Waited " + timeOutInSeconds
					+ " for the element");
		}
		return null;
	}

	public static boolean waitForElementToDisappear(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			String elementDescription) {

		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);

			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(elementProperties[1])));
			case CLASSNAME:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(elementProperties[1])));
			case TAGNAME:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName(elementProperties[1])));
			case NAME:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(elementProperties[1])));
			case XPATH:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementProperties[1])));
			case CSSSELECTOR:
				return wait
						.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(elementProperties[1])));
			case PARTIALLINKTEXT:
				return wait.until(
						ExpectedConditions.invisibilityOfElementLocated(By.partialLinkText(elementProperties[1])));
			case LINKTEXT:
				return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(elementProperties[1])));
			}
		} catch (Exception e) {
			log.info("Element " + elementDescription + " disppear failed. Waited " + timeOutSeconds
					+ " seconds for the element to disappear");
			return false;
		}
		return false;

	}

	public static WebElement waitForAjaxElementToBePresent_NoFail(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			WebElement ele = null;
			switch (condition) {
			case CLICK:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return ele;
			case VISIBLE:
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is visible");
				return ele;
			case PRESENCE:
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is present");
				return ele;
			default:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return ele;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator " + by + " is not " + condition + " . Waited "
					+ timeOutInSeconds + " for the element");
			return null;
		}
	}

	public static WebElement waitForAjaxElementToBePresent_poc(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition, String elementDescription) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			WebElement ele;
			switch (condition) {
			case CLICK:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(elementDescription + " with locator " + by + " is clickable");
				return ele;
			case VISIBLE:
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				log.info(elementDescription + " with locator " + by + " is visible");
				return ele;
			case PRESENCE:
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
				log.info(elementDescription + " with locator " + by + " is present");
				return ele;
			default:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(elementDescription + " with locator " + by + " is clickable");
				return ele;
			}
		} catch (Exception e) {
			System.out.println("wait for called");
			log.info(elementDescription + " with locator " + by + " is not " + condition + " . Waited "
					+ timeOutInSeconds + " seconds for the element");
			TestNGHelper.fail(elementDescription + " with locator " + by + " is not " + condition + " . Waited "
					+ timeOutInSeconds + " seconds for the element");

		}
		return null;
	}

	public static List<WebElement> waitForAjaxElementsToBePresent(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			List<WebElement> list;
			switch (condition) {
			case VISIBLE:
				list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;
			case PRESENCE:
				list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;
			default:
				list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;

			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are not present. Waited "
					+ timeOutInSeconds + " for the elements to be available");
			TestNGHelper.fail("List of Elements with locator " + by + " are not present. Waited " + timeOutInSeconds
					+ " for the elements to be available");
		}
		return null;
	}

	public static List<WebElement> waitForAjaxElementsToBePresent_NoFail(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			List<WebElement> list;
			switch (condition) {
			case VISIBLE:
				list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;
			case PRESENCE:
				list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;
			default:
				list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are present");
				return list;

			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": List of Elements with locator " + by + " are not present. Waited "
					+ timeOutInSeconds + " for the elements to be available");
			return null;
		}
	}

	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		try {
			wait.until(expectation);
		} catch (TimeoutException e) {
			log.info(AutomationCore.testName + ": Timeout (60 seconds) waiting for Page Load Request to complete");
			TestNGHelper.fail("Timeout (60 seconds) waiting for Page Load Request to complete");
		}
	}

	public static void logStepDetails(String stepName) {
		AutomationCore.currentStepName = trimStepName(stepName);
		log.info(stepName);
		Reporter.log("<br>" + stepName);
	}

	public static String trimStepName(String stepName) {
		String tempStepName = stepName.substring(stepName.indexOf(": ") + 2, stepName.length());
		// tempStepName = tempStepName.replace(" ", "");
		return tempStepName;

	}

	public static void logTestSetNameInReport(String Name) {
		log.info("Logging Test Set Name: " + Name);
		String name = Name.trim().replace(" ", "");
		Reporter.log("<br> <h2 id=" + name + "> Test Set Name: </b>" + Name);
	}

	public static void logTestCaseNameInReport(String TestCaseName, String TestSetName) {
		log.info("Logging Test Case Name: " + TestCaseName);
		String tag = TestSetName.trim().replace(" ", "") + TestCaseName;
		Reporter.log("<br> <h2 id=" + tag + "> Test Case Name: </b>" + TestCaseName);

	}

	public static void logStepResult(String stepName, String screenshotStatus) {
		Reporter.log(stepName + "Failed");
		if (screenshotStatus.equalsIgnoreCase("yes")) {
			String seMinVal = AutoCore.formatDateAndTime(AutoCore.getCurrentDateAndTime(), "mmss");
			String path = AutoCore.Environment("screenShotsPath") + "\\" + AutoCore.Environment("projectName") + "\\"
					+ AutomationCore.currentBrowser + "\\" + stepName + "Pass" + seMinVal + ".png";
			log.info(path);
			SeleniumHelper.takeSnapShot(currentDriver, path);
			Reporter.log("<br> <a href=file:" + path + " target='blank'> <img src=" + path
					+ " target='blank' height='300' width='500' /> </a></br>");
		}
	}

	public static String[] getLocator(WebElement element) {
		String[] array = null;
		String element_String = element.toString();
		String ele = element_String.substring(element_String.indexOf(">") + 1, element_String.length() - 1);
		String locator = ele.substring(ele.indexOf(" "), ele.indexOf(":"));
		ele = ele.substring(locator.length(), ele.length());
		String locatorValue = ele.substring(ele.indexOf(" "), ele.length());
		array = new String[] { locator.trim(), locatorValue.trim() };
		return array;
	}

	public static void copyToClipBoard(String filePath) {

		StringSelection stringSelection = new StringSelection(filePath);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public static void uploadFile(String filePath) {
		try {
			Thread.sleep(3000);
			copyToClipBoard(filePath);

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Upload File failed");
			TestNGHelper.fail("Upload file failed");
		}

	}

	public static void downloadFile_SaveAs_IE(String filePath) {
		try {
			copyToClipBoard(filePath);

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_F6);
			robot.keyRelease(KeyEvent.VK_F6);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			threadSleep(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			threadSleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			threadSleep(2000);
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Upload File failed");
			TestNGHelper.fail("Upload file failed");
		}

	}

	public static void editPlatformCheckboxes(List<WebElement> platforms) {
		int size = platforms.size();
		try {
			for (WebElement platform : platforms) {
				if (platform.isSelected() && !(platform.getAttribute("id").equalsIgnoreCase("NDB"))) {
					platform.click();
					log.info(AutomationCore.testName + ": Clicked on element " + platform);
				}
				if (!platform.isSelected() && (platform.getAttribute("id").equalsIgnoreCase("NDB"))) {
					platform.click();
					log.info(AutomationCore.testName + ": Clicked on element " + platform);
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> staleElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				editPlatformCheckboxes(staleElements);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> webElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				editPlatformCheckboxes(webElements);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
			TestNGHelper.fail("Failed to select platforms " + platforms);
		} finally {
			count = 0;
		}

	}

	public static void editPlatformCheckboxes(List<WebElement> platforms, List<String> platformsToBeSelected) {
		int size = platforms.size();
		try {
			for (WebElement platform : platforms) {
				if (platform.isSelected() && !(platformsToBeSelected.contains(platform.getAttribute("id")))) {
					platform.click();
					log.info(AutomationCore.testName + ": Clicked on element " + platform);
				}
				if (!platform.isSelected() && (platformsToBeSelected.contains(platform.getAttribute("id")))) {
					platform.click();
					log.info(AutomationCore.testName + ": Clicked on element " + platform);
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> staleElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				editPlatformCheckboxes(staleElements, platformsToBeSelected);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> webElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				editPlatformCheckboxes(webElements, platformsToBeSelected);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
			TestNGHelper.fail("Failed to select platforms " + platforms);
		} finally {
			count = 0;
		}

	}

	public static void platformDeselectionReason(List<WebElement> platformDeselectionReason, String text) {
		int size = platformDeselectionReason.size();
		try {
			if (platformDeselectionReason.size() > 0) {
				for (WebElement reason : platformDeselectionReason) {
					if (reason.isEnabled()) {
						SeleniumHelper.threadSleep(1000);
						Select sel = new Select(reason);
						sel.selectByVisibleText(text);
						log.info(AutomationCore.testName + ": Selected text " + text + " from the list element "
								+ reason);
					}
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platformDeselectionReason.get(0));
				SeleniumHelper.threadSleep(1000);
				List<WebElement> staleElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				platformDeselectionReason(staleElements, text);
			} else {
				log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
				TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platformDeselectionReason.get(0));
				SeleniumHelper.threadSleep(1000);
				List<WebElement> webElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				platformDeselectionReason(webElements, text);
			} else {
				log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
				TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
			TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
		} finally {
			count = 0;
		}

	}

	public static String getCurrentDateAndTime_CST() {
		try {
			Calendar currentdate = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy H:mm:ss a");
			TimeZone obj = TimeZone.getTimeZone("CST");
			formatter.setTimeZone(obj);
			return formatter.format(currentdate.getTime());
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get Date Time in CST Format");
			TestNGHelper.fail("Failed to get Date Time in CST Format");
			return null;
		}

	}

	public static String getCurrentDateAndTime_Format_HHMM(String timeZone) {
		try {
			Calendar currentdate = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy H:mm a");
			TimeZone obj = TimeZone.getTimeZone(timeZone);
			formatter.setTimeZone(obj);
			return formatter.format(currentdate.getTime());
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get Date Time in CST Format");
			TestNGHelper.fail("Failed to get Date Time in CST Format");
			return null;
		}

	}

	public static String getCurrentDateAndTime_RequiredFormat(String format, String timeZone) {
		try {
			Calendar currentdate = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat(format);
			TimeZone obj = TimeZone.getTimeZone(timeZone);
			formatter.setTimeZone(obj);
			return formatter.format(currentdate.getTime());
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to get Date Time in CST Format");
			TestNGHelper.fail("Failed to get Date Time in CST Format");
			return null;
		}

	}

	public static boolean compareStringDates(String date1, String date2, String compareType, String timeZone) {

		try {
			DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
			TimeZone obj = TimeZone.getTimeZone(timeZone);
			formatter.setTimeZone(obj);

			Date dateOne = formatter.parse(date1);
			Date dateTwo = formatter.parse(date2);

			switch (compareType.toUpperCase()) {
			case "GREATERTHAN":
				return dateOne.after(dateTwo);
			case "LESSTHAN":
				return dateOne.before(dateTwo);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to compare two dates in String format");
			TestNGHelper.fail("Failed to compare two dates in String format");
		}
		return false;
	}

	public static boolean compareStringDates_Format_hhmm(String date1, String date2, String compareType,
			String timeZone) {

		try {
			DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm");
			TimeZone obj = TimeZone.getTimeZone(timeZone);
			formatter.setTimeZone(obj);

			System.out.println("In compare " + date1);
			System.out.println("In compare " + date2);
			Date dateOne = formatter.parse(date1);
			Date dateTwo = formatter.parse(date2);

			switch (compareType.toUpperCase()) {
			case "GREATERTHANOREQUAL":
				if (dateOne.after(dateTwo) || dateOne.equals(dateTwo)) {
					return true;
				} else {
					return false;
				}
			case "LESSTHAN":
				return dateOne.before(dateTwo);
			case "GREATERTHAN":
				return dateOne.after(dateTwo);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to compare two dates in String format");
			TestNGHelper.fail("Failed to compare two dates in String format");
		}
		return false;
	}

	public static String readPR(String submissionMessage) {
		try {
			String message = submissionMessage;
			String prNumber = message.substring(message.indexOf("PR-") + 3, message.length());
			log.info("PR number retrieved from MPP submission message is " + prNumber);
			return prNumber;
		} catch (Exception e) {
			log.info("Unable to retrieve PR from Mpp Submission message");
			TestNGHelper.fail("Unable to retrieve PR from Mpp Submission message");
			return "";
		}
	}

	public static void highlightElement(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
		} catch (Exception e) {
			log.info("Unable to highlight the element " + element);
		}
	}

	public static void scrollWindow(WebDriver driver, int xPixel, int yPixel) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(" + xPixel + "," + yPixel + ")", "");
		} catch (Exception e) {
			log.info("Failed the scroll the window by (" + xPixel + "," + yPixel + ")");
			TestNGHelper.fail("Failed the scroll the window by (" + xPixel + "," + yPixel + ")");
		}
	}

	public static WebElement getRequiredElementFromList(List<WebElement> list, int index) {

		return list.get(index);
	}

	public static WebElement getEnabledElementFromTheList(List<WebElement> list) {
		WebElement element = null;
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isEnabled()) {
					element = list.get(i);
					break;
				}
			}
			return element;
		} catch (Exception e) {
			log.info("Fail to identify an enabled element in the list");
			return element;
		}
	}

	public static WebElement getVisibleElementFromTheList(List<WebElement> list) {
		WebElement element = null;
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isDisplayed()) {
					element = list.get(i);
					break;
				}
			}
			return element;
		} catch (Exception e) {
			log.info("Fail to identify an enabled element in the list");
			return element;
		}
	}

	public static boolean clickElement_JS(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		try {
			executor.executeScript("arguments[0].click();", element);
			log.info("Successfully performed javascript executor click on the element " + element);
			return true;
		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count == 0) {
				count++;
				String[] locator = getLocator(element);
				WebElement webElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
						locator[1]);
				clickElement(webElement);
			} else {
				log.info(AutomationCore.testName + ": Failed to click on the element " + element);
				TestNGHelper.fail("Failed to click on the element " + element);
				return false;
			}
		} catch (Exception e) {
			log.info("Failed to perform javascript executor click on the element " + element);
			TestNGHelper.fail("Failed to perform javascript executor click on the element " + element);
			return false;
		} finally {
			count = 0;
		}

		return false;
	}

	public static void doubleClickCellData(WebElement table, String rowTagName, String colTagName, int rowIndex,
			int colIndex) {
		try {
			List<WebElement> row = table.findElements(By.tagName(rowTagName));
			List<WebElement> cells = (row.get(rowIndex)).findElements(By.tagName(colTagName));

			WebDriver driver = getDriver();
			WebElement element = cells.get(colIndex);
			Actions act = new Actions(driver);

			if (element != null && element.isEnabled()) {
				act.moveToElement(element).build().perform();
				act.doubleClick(element).build().perform();
				log.info(AutomationCore.testName + ": Double Clicked on Pega Row element " + element);
			}

			log.info(AutomationCore.testName + ": Successfully double clicked on Cell data");
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to retrieve cell data from table " + table + " with rowIndex "
					+ rowIndex + " colunIndex " + colIndex);
			TestNGHelper.fail("Failed to retrieve row index from table " + table);
		}
	}

	public static boolean waitUntilDropdownValuePopulated(WebElement element) {

		String[] locator = getLocator(element);
		WebElement staleElement = setExplicitWaitForStaleElementToBeRefreshed(getDriver(), synTimeVal, locator[0],
				locator[1]);

		Select sel = new Select(staleElement);

		String text = sel.getFirstSelectedOption().getText();

		if (text.equalsIgnoreCase("Select") || text.equalsIgnoreCase("")) {

			log.info(AutomationCore.testName
					+ ": Waited until dropdown element get refreshed but the option selected from the list " + element
					+ " is " + text);
			return false;
		} else {
			log.info(AutomationCore.testName + ": Waited until dropdown element get refreshed and verified " + text
					+ " is selected from the list element " + element);
			return true;
		}
	}

	public static void getPageEndSize(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			log.info("Failed to scroll the window by ");
			TestNGHelper.fail("Failed the scroll the window by");
		}
	}

	public static void selectRequiredElementFromListByText(List<WebElement> list, String text) {

		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getText().equalsIgnoreCase(text)) {
					SeleniumHelper.clickElement(list.get(i));
					break;
				}
			}
		} catch (Exception e) {
			log.info("Unable to select element with text " + text + " from the list" + list);
			TestNGHelper.fail("Unable to select element with text " + text + " from the list" + list);
		}
	}

	public static void closeWindow(WebDriver driver) {
		try {
			String currentWindowhandle = driver.getWindowHandle();
			driver.switchTo().window(currentWindowhandle).close();
		} catch (Exception e) {
			log.info("Unable to close current window ");
			TestNGHelper.fail("Unable to close current window ");
		}
	}

	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			} else {
				log.info("Unable to Find the file at " + filePath);
			}
		} catch (Exception e) {
			log.info("Unable to Find the file at " + filePath);
		}

	}

	public static void selectRefreshingListOfCheckboxes(List<WebElement> platforms) {
		int size = platforms.size();
		try {
			for (WebElement platform : platforms) {
				if (!(platform.isSelected())) {
					platform.click();
					log.info(AutomationCore.testName + ": Clicked on element " + platform);
				} else {
					log.info(AutomationCore.testName + ": Element " + platform + "is already selected");
				}

			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> staleElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectRefreshingListOfCheckboxes(staleElements);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platforms.get(0));
				List<WebElement> webElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				selectRefreshingListOfCheckboxes(webElements);
			} else {
				log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
				TestNGHelper.fail("Failed to select platforms " + platforms);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to select platforms " + platforms);
			TestNGHelper.fail("Failed to select platforms " + platforms);
		} finally {
			count = 0;
		}

	}

	public static void selectItemsFromRefreshingList(List<WebElement> platformDeselectionReason, String text) {
		int size = platformDeselectionReason.size();
		try {
			if (platformDeselectionReason.size() > 0) {
				for (WebElement reason : platformDeselectionReason) {
					if (reason.isEnabled()) {
						SeleniumHelper.threadSleep(3000);
						Select sel = new Select(reason);
						sel.selectByVisibleText(text);
						log.info(AutomationCore.testName + ": Selected text " + text + " from the list element "
								+ reason);
					}
				}
			}
		} catch (StaleElementReferenceException se) {
			log.info("In StaleElementReferenceException block");
			se.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platformDeselectionReason.get(0));
				SeleniumHelper.threadSleep(3000);
				List<WebElement> staleElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				platformDeselectionReason(staleElements, text);
			} else {
				log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
				TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
			}

		} catch (WebDriverException we) {
			log.info("In WebDriverException block");
			we.printStackTrace();
			if (count < size) {
				count++;
				String[] locator = getLocator(platformDeselectionReason.get(0));
				SeleniumHelper.threadSleep(3000);
				List<WebElement> webElements = setExplicitWaitForStaleElementsToBeRefreshed(getDriver(), synTimeVal,
						locator[0], locator[1]);
				platformDeselectionReason(webElements, text);
			} else {
				log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
				TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Failed to deselect reasons " + platformDeselectionReason);
			TestNGHelper.fail("Failed to deselect reasons " + platformDeselectionReason);
		} finally {
			count = 0;
		}

	}

	public static String extractTestCaseName(String name) {
		return name.substring(name.lastIndexOf(".") + 1, name.length());
	}

	public static String extractTestName(String name) {
		return name.substring(name.indexOf("_") + 1, name.length());
	}

	/*
	 * public static void quitDriver() { BasePage.logInState = false;
	 * System.out.println("Quit Driver: " + getDriver()); if (getDriver() != null) {
	 * getDriver().quit(); }
	 * 
	 * }
	 */
	public static boolean waitForpopupElementToBePresent(WebDriver driver, By by, long timeOutInSeconds,
			ExplicitConditions condition) {
		try {
			// threadSleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			WebElement ele = null;
			switch (condition) {

			case CLICK:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return true;

			case VISIBLE:
				ele = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is visible");
				return true;
			case PRESENCE:
				ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is present");
				return true;
			default:
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				log.info(AutomationCore.testName + ": Element with locator " + by + " is clickable");
				return true;
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator " + by + " is not " + condition + " . Waited Rao"
					+ timeOutInSeconds + " for the element");
			return false;
		}

	}

	public static boolean findElementpopup(WebDriver driver, String[] elementProperties, long timeOutSeconds,
			ExplicitConditions conditions) {
		try {
			switch (ElementLocator.valueOf(elementProperties[0].toUpperCase())) {
			case ID:
				return waitForpopupElementToBePresent(driver, By.id(elementProperties[1]), timeOutSeconds, conditions);
			case CLASSNAME:
				return waitForpopupElementToBePresent(driver, By.className(elementProperties[1]), timeOutSeconds,
						conditions);
			case TAGNAME:
				return waitForpopupElementToBePresent(driver, By.tagName(elementProperties[1]), timeOutSeconds,
						conditions);
			case NAME:
				return waitForpopupElementToBePresent(driver, By.name(elementProperties[1]), timeOutSeconds,
						conditions);
			case XPATH:
				return waitForpopupElementToBePresent(driver, By.xpath(elementProperties[1]), timeOutSeconds,
						conditions);
			case CSSSELECTOR:
				return waitForpopupElementToBePresent(driver, By.cssSelector(elementProperties[1]), timeOutSeconds,
						conditions);
			case PARTIALLINKTEXT:
				return waitForpopupElementToBePresent(driver, By.partialLinkText(elementProperties[1]), timeOutSeconds,
						conditions);
			case LINKTEXT:
				return waitForpopupElementToBePresent(driver, By.linkText(elementProperties[1]), timeOutSeconds,
						conditions);
			}
		} catch (Exception e) {
			log.info(AutomationCore.testName + ": Element with locator "
					+ ElementLocator.valueOf(elementProperties[0].toUpperCase()) + " is not " + conditions
					+ " . Waited for " + timeOutSeconds + " seconds for the element");
			Assert.fail("Element with locator " + ElementLocator.valueOf(elementProperties[0].toUpperCase())
					+ " is not " + conditions + " . Waited for " + timeOutSeconds + " seconds for the element");
		}
		return false;

	}

	public static void RadioButtonSelection(List<WebElement> RadBtn, String AttributeVal, String IgnoreCaseVal) {
		// Radio button selection
		List<WebElement> RadRPC = RadBtn;
		for (int i = 0; i < RadRPC.size(); i++) {
			WebElement local_radio = RadRPC.get(i);
			String value = local_radio.getAttribute(AttributeVal);
			if (value.equalsIgnoreCase(IgnoreCaseVal)) {
				local_radio.click();
				break;
			}
		}
	}
	
	public static void LinkSelection(List<WebElement> Lnk,String ExpLinkName){
		//Radio button selection
		List<WebElement> LnkColl = Lnk;
		for(int i=0; i<LnkColl.size();i++){
		WebElement local_link=LnkColl.get(i);
		String value = local_link.getText().toLowerCase();
		if(value.equalsIgnoreCase(ExpLinkName.toLowerCase())){		
			local_link.click();
		break;
		}
		}
		}

	public static void ScrollToElement(WebDriver driver, WebElement element) {
		// Create instance of Javascript executor to drag the y-axis so that the
		// link is visible
		JavascriptExecutor je = (JavascriptExecutor) driver;

		// Create an offset constant value.
		final int HEADER_OFFSET = 250;

		int yScrollPosition = element.getLocation().getY() - HEADER_OFFSET;
		System.out.println(yScrollPosition);
		je.executeScript("window.scroll(0, " + yScrollPosition + ");");

	}

	public static void scrollDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	public static void scrollToUP(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-500)", "");
	}

}