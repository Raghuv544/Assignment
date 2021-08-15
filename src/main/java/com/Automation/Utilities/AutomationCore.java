package com.Automation.Utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import junit.framework.Assert;






import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class AutomationCore
{
	public static String currentBrowser=null;
	public static String envName=null;
	public static String appURL=null;
	public static String appURLForManager=null;
	public static String platform=null;
	//private static boolean fileStatus=false;
	private static String resultFile=null;
	static Logger log = Logger.getLogger(AutomationCore.class);
	public static String testName = "";
	public static String testSetName = "";
	public static String testFileAttachmentPath = "";
	public static String psiEnvironment= "";
	public static String appType="";
	public static String psiWindowTitle="";
	public static String currentTime_PSI="Feb 24, 2017 01:18 PM";
	public static String exportDataFileDownloadPath="";
	public static String psiImportDataFilePath="";
	public static String psiReimportDataFilePath="";
	public static String trueDefectFilePath="";
	public static List<String> exportData=new ArrayList<String>();
	public static List<String> transaction1ExportData=new ArrayList<String>();
	public static List<String> transaction2ExportData=new ArrayList<String>();
	public static HashMap<String, String> errorSheetData = new HashMap<String, String>();
	public static String currentStepName="";
	
	
	/**
	 * This method Loads the config.properties file
	 */
	public Properties loadProperties()
	{
		InputStream instream;
		try {
			String currentDirectory = System.getProperty("user.dir");
		    System.out.println("The current working directory is " + currentDirectory+"\\resources\\config.properties");
			instream = new FileInputStream(currentDirectory+"//resources//config.properties");
		//getClass().getClassLoader().getResourceAsStream("config.properties");
		if(instream!=null)
		{
			Properties prop = new Properties();
			try 
			{
				prop.load(instream);
				return prop;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			return null;	
	}
	
	/**
	 * This method is used close all opened browsers
	 */
	public void closeAllOpenedBrowsers()
	{
		try
		{
			String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
					SeleniumHelper.threadSleep(500);
					Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");		
					SeleniumHelper.threadSleep(500);
					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
					SeleniumHelper.threadSleep(500);
					//Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}	
	}
	
	public void closeAllOpenedDrivers()
	{
		try
		{
			String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
					
					Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}	
	}
	/**
	 * This method is used to kill the excel process
	 */
	public void killExcel()
	{
		try
		{
			String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM EXCEL.EXE");
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}	
	}
	
	/**
	 * This method is used to get the property value from config.properties file.
	 * 
	 * @param propertyName : The name of the property for which user need to retrieve the value
	 * @return the property value for specified property name
	 * 
	 * Ex: Environment("browserName"):- it returns the value of browserName property from config.properties file
	 */
	public String Environment(String propertyName)
	{
		Properties propValue = loadProperties();
		//System.out.println("Property Name: "+propValue);
		return propValue.getProperty(propertyName);
	}
	
	/**
	 * 
	 * @param encodedString
	 * @return decodeString
	 */
	public static String PasswordDecoder(String encodedString)
	{
		Base64.Decoder decoder = Base64.getDecoder();  
	    // Decoding string  
	    String dStr = new String(decoder.decode(encodedString));  
		return dStr; 
	}
	
	/**
	 * This method is used to read the object properties for each element of specified screen
	 * 
	 * @param screenName The screen name/page name on which the required element/exists is exists
	 * @param objPropName The Property name / property value of the control/element for which selenium is searching for
	 *<p>
	 *Example for calling function :- 
	 *readORProperties("LoginaPgae","userNameXpath"), which returns the xpath value of username filed that is saved in
	 *objectRepositry.xml document
	 */
//	public String readORProperties(String screenName, String objPropName)
//	{
//		try {		
//			 	File fXmlFile = new File(Environment("objectRepoPath"));
//				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//				Document doc = dBuilder.parse(fXmlFile);		 
//				doc.getDocumentElement().normalize();					 
//				NodeList nList = doc.getElementsByTagName(screenName);
//				for (int temp = 0; temp < nList.getLength(); temp++)
//				{			 
//					Node nNode = nList.item(temp);
//					
//					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
//					{			 
//						Element eElement = (Element) nNode;
//						return eElement.getElementsByTagName(objPropName).item(0).getTextContent();					
//			 
//					}
//				}
//		    } 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//				return null;
//	}
	
	/**
	 * This method is used to read the object properties for each element of specified screen
	 * 
	 * @param screenName The screen name/page name on which the required element/exists is exists
	 * @param objPropName The Property name / property value of the control/element for which selenium is searching for
	 *<p>
	 *Example for calling function :- 
	 *readORProperties("LoginaPgae","userNameXpath"), which returns the xpath value of username filed that is saved in
	 *objectRepositry.xml document
	 */
	public String[] readORProperties(String screenName, String objectName)
	{
		try {	
				String[] elementProperties = new String[2];
			 	File fXmlFile = new File(Environment("objectRepoPath"));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);		 
				doc.getDocumentElement().normalize();					 
				NodeList nList = doc.getElementsByTagName(screenName);
				for (int temp = 0; temp < nList.getLength(); temp++)
				{			 
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{			 
						Element eElement = (Element) nNode;
						elementProperties[0]=eElement.getElementsByTagName(objectName).item(0).getAttributes().
								getNamedItem("locatorName").getTextContent();
						elementProperties[1]= eElement.getElementsByTagName(objectName).item(0).getTextContent();					
						return elementProperties;
					}
				}
		    } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				return null;
	}
	
	public String readTestData(String screenName, String objectName)
	{
		try {	
			System.out.println("In test data method");
				String elementProperties = "";
			 	File fXmlFile = new File(Environment("testDataPath"));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);		 
				doc.getDocumentElement().normalize();					 
				NodeList nList = doc.getElementsByTagName(screenName);
				for (int temp = 0; temp < nList.getLength(); temp++)
				{			 
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{			 
						Element eElement = (Element) nNode;						
						elementProperties= eElement.getElementsByTagName(objectName).item(0).getTextContent();
						System.out.println(elementProperties);						
						return elementProperties;
					}
				}
		    } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				return null;
	}
	
//	public WebElement getPageObject(String objectName)
//	{
//		MasterStep step = new MasterStep();
//		switch(objectName)
//		{
//			case "LoginPage":
//				step.getLoginPage();
//				return (WebElement) objectName;
//				break;
//		}
//	}
	
	public String readORProperties_Window(String screenName, String objectName)
	{
		try {	
				String elementProperties = null;
			 	File fXmlFile = new File(Environment("objectRepoPath"));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);		 
				doc.getDocumentElement().normalize();					 
				NodeList nList = doc.getElementsByTagName(screenName);
				for (int temp = 0; temp < nList.getLength(); temp++)
				{			 
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{			 
						Element eElement = (Element) nNode;						
						elementProperties= eElement.getElementsByTagName(objectName).item(0).getTextContent();					
						return elementProperties;
					}
				}
		    } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				return null;
	}
	
	
	
	/**
	 * This method is used to format the date type value into required date format in string 
	 * @param dateValue The date value that is in other format
	 * @param format Required format type to be formatted
	 * @return Returns the string type date value
	 * <p>
	 * Example:-
	 * <p>
	 * formatDateAndTime(13 dec 2015, "MM_dd_yyyy");
	 */
	public String formatDateAndTime(Date  dateValue, String format)
	{		
		SimpleDateFormat ft =  new SimpleDateFormat(format);	
		String formatedDateValue= ft.format(dateValue);
		return formatedDateValue;
	}
	
	public String formatDateAndTime(String  dateValue, String inputformat, String outputFormat)
	{		
		SimpleDateFormat inputDateFormat =  new SimpleDateFormat(inputformat);	
		SimpleDateFormat outputDateFormat =  new SimpleDateFormat(outputFormat);	
		String formatedDateValue = null;
		Date inputDate=null;
		try 
		{
			inputDate=inputDateFormat.parse(dateValue);			
			formatedDateValue = outputDateFormat.format(inputDate);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatedDateValue;
	}
	
	/**
	 * This method is used to change the current Date format to expected date format
	 * @param dateValue The current date value that is supposed to change the format
	 * @param currentFormat The current format of date value
	 * @param expFormat The expected format that data is supposed to be
	 * @return Returns the a string type date value in expected format
	 * 
	 * <p>
	 * Example :- convertDateFromString(13 dec 2015, "dd MM yyyy"
	 */
	public String convertDateFromString(String  dateValue,String currentFormat, String expFormat)
	{	
		try
		{
			//Locale.setDefault(Locale.US);
			DateFormat ft =  new SimpleDateFormat(currentFormat);	
			DateFormat dft = new SimpleDateFormat(expFormat);
			Date formatedDateValue= (Date)ft.parse(dateValue);
			Date finalDate = (Date)dft.parse(formatedDateValue.toString());
			
			return finalDate.toString();
			
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return null;
		
	}
	
	/**
	 * This method is used to get the current time stamp
	 * @return Current Date 
	 */
	public Date getCurrentDateAndTime()
	{
		Date dNow = new Date( );
		return dNow;
	}
	
	public Date getWeekDayCurrentDateAndTime()
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_WEEK);
			if(day==Calendar.SUNDAY)
			{
				cal.add(Calendar.DATE, -2);
				return cal.getTime();
			}
			else if(day==Calendar.SATURDAY)
			{
				cal.add(Calendar.DATE, -1);
				return cal.getTime();
			}
			else
			{
				return getCurrentDateAndTime();
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		return null;
	}
	
	/**
	 * This method is used to get the difference between time values
	 * @param time1 The start time value
	 * @param time2 The end time value
	 * @param formatValue Format type of time to return 
	 * @return Returns the long type time value
	 */
	public long timeDiffInSeconds(String time1, String time2,String formatValue)
	{
		try
		{
			SimpleDateFormat format =  new SimpleDateFormat(formatValue);
			Date date1 = format.parse(time1);
			Date date2 = format.parse(time2);
			long difference = date2.getTime() - date1.getTime();
			difference = (difference) /(1000);  
			return difference;
		}
		catch(Exception e)
		{
			e.getMessage();
			return 0;
		}		
	}
	

	/**
	 * This method is used to create the Results file
	 * @param fileTypeExtn of String type value
	 * @param direcotryPath of String type value
	 * @return Creates a File 
	 */
	
	public File createFile(String fileTypeExtn, String direcotryPath)
	{
		File file =null;
		String fileName="";
		 
		try
		{	
			Date currentDate = getCurrentDateAndTime();
			String formatedCurrentDate =formatDateAndTime(currentDate,Environment("resultDateFormat"));
			Thread.sleep(1000);
			switch (fileTypeExtn) 
			{
			case "xls":
				fileName = direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".xls";
				break;
			case "txt":
				fileName =direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".txt";
				break;
			default:
				fileName = direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".xls";
				break;
			}
			file = new File(fileName);
			if(file.exists())
			{				
				return file;
			}
			else
			{
				 file.createNewFile();
				 return file;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return file;
	}
	
	/**
	 * This method is used to get Path of the file 
	 * @param file of String type value
	 * @return It returns path of the file 
	 * Example : Seleniumhelper.getAbolutePathofFile(automation.xml)
	 */
	public String getAbolutePathofFile(File file)
	{
		try
		{
			return file.getAbsolutePath();
		}
		catch(Exception e)
		{
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * This method is used to send text into a file 
	 * @param file String type value
	 * @param textToWrite String type value
	 * @return text sent into a file 
	 * Example : Seleniumhelper.writeAndSaveTextFile(Automation.xls,"automation")
	 */
	public boolean writeAndSaveTextFile(File file, String textToWrite)
	{
		try
		{
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(textToWrite);
			output.close();
			return true;
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return false;
		
	}
	

	/**
	 * This method is used to get the machine name
	 * @return Machine name of string type
	 */
	public String getHostName()
	{
		String hostName="Not found";
		try
		{
			hostName= InetAddress.getLocalHost().getHostName().toString();
			return hostName;
		}
		catch(Exception e)
		{
			
		}
		return hostName;
	}
	
	/**
	 * This method is used to get username 
	 * @return Username of string type
	 */
	public String getUserName()
	{
		String userName="Not found";
		try
		{
			userName = System.getProperty("user.name");
			return userName;
		}
		catch(Exception e)
		{
			
		}
		return userName;
	}
	/**
	 * This method is used to generate random string
	 * @param length of Int type
	 * @param strType of string type
	 * @return Generate random string according to length and type 
	 * Example: Seleniumhelper.generateRandomString(4,"AB")
	 */
	public String generateRandomString(int length, String strType)
	{
		StringBuffer buffer = new StringBuffer();
		String characters = "";

		if(strType.equalsIgnoreCase("AB"))
		{
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		else if(strType.equalsIgnoreCase("N"))
		{
			characters = "123456789";
		}
		else if(strType.equalsIgnoreCase("AN"))
		{
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		}
		
		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) 
		{
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString().toUpperCase();
	}
	
	/**
	 * This method is used to generate random integer
	 * @param minRange of Int type
	 * @param maxRange of Int type
	 * @return Generate random Int according to minRange and maxRange 
	 * Example: Seleniumhelper.generateRandomInteger(1,5)
	 */
	public double generateRandomInteger(int minRange, int maxRange)
	{
		int num=-1;
		try
		{
			num=maxRange-minRange;
			return minRange+(Math.random()*num);
		}
		catch (Exception e)
		{
			e.getMessage();
		}
			return -1;
	}
	
	public int generateRandomInteger(int maxRange)
	{
		int num=-1;		
		try
		{			
			num=maxRange;			
			return new Double((Math.floor(Math.random()*maxRange))).intValue();
		}
		catch (Exception e)
		{
			e.getMessage();
		}
			return -1;
	}

	/**
	 * This method is get JVM version
	 * @return JVMversion  of Int type
	 */
//	public static String getJVMVersion()
//	{
//		String jvmBitVersion="";
//		try
//		{
//			jvmBitVersion=System.getProperty("sun.arch.data.model");
//			//System.out.println(jvmBitVersion);
//		}
//		catch(Exception e)
//		{
//			e.getMessage();
//		}
//		return jvmBitVersion;
//	}
	
	public static String getDriverPath()
	{
		File f;
		try
		{
			switch (currentBrowser.toLowerCase()) 
			{
			case "ie":
				f = new File("Drivers", "IEDriverServer.exe");
				System.out.println(f.exists());
				//return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "chrome":
				f = new File("Drivers", "chromedriver_win32/chromedriver.exe");
				//return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");
				return f.getAbsolutePath();

			default:
				f = new File("Drivers", "IEDriverServer.exe");
				//return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");
				return f.getAbsolutePath();
			}
			
		}
		catch(Exception e)
		{
			return null;
		}
	}	
	
	/**
	 * This method is get JVM version
	 * @return JVMversion  of Int type
	 */
	public static String getJVMVersion()
	{
		String jvmBitVersion="";
		try
		{
			jvmBitVersion=System.getProperty("sun.arch.data.model");
			//System.out.println(jvmBitVersion);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return jvmBitVersion;
	}
	    
	public static List<String> retrieveDataFromTextFile(String textFilePath) {
		try {
			List<String> data = new ArrayList<String>();
			String s;
			BufferedReader in = new BufferedReader(
					new FileReader(
							textFilePath));

			while ((s = in.readLine()) != null) {
				if (s.contains("PT-")) {
					String temp = s.substring(s.indexOf("PT-"));
					data.add(temp.substring(temp.indexOf("PT-"), temp.indexOf(" ")));
				}
			}
			
			in.close();
			Collections.sort(data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void retrieveDataFromTextFile(String textFilePath, String transaction1Name, String transaction2Name) {
		try {
			List<String> PtList = new ArrayList<String>();
			List<String> transactionName = new ArrayList<String>();
			String s;			 
			String temp;
			BufferedReader in = new BufferedReader(
					new FileReader(
							textFilePath));

			while ((s = in.readLine()) != null) {				
				System.out.println(s);
				if (s.contains("PT-")) {
					temp = s.substring(s.indexOf("PT-"));
					PtList.add(temp.substring(temp.indexOf("PT-"), temp.indexOf(" ")));
				}
				if(s.contains(transaction1Name))
				{
					transactionName.add(transaction1Name);
				}
				if(s.contains(transaction2Name))
				{
					transactionName.add(transaction2Name);
				}				
			}
			
			if(PtList.size()==transactionName.size())
			{
				for(int i=0; i<transactionName.size();i++)
				{
					if(transactionName.get(i).equalsIgnoreCase(transaction1Name))
					{
						transaction1ExportData.add(PtList.get(i));
					}
					else if(transactionName.get(i).equalsIgnoreCase(transaction2Name))
					{
						transaction2ExportData.add(PtList.get(i));
					}
				}
			}
			else
			{
				Assert.fail("Pt List size not equal to transaction name list size");
			}
			
			in.close();
			Collections.sort(transaction1ExportData);
			Collections.sort(transaction2ExportData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	public void LogErrorSheetData(String testName, String errorMessage)
	{
		String message = errorMessage.substring(errorMessage.indexOf(":")+1, errorMessage.length());
		errorSheetData.put(testName, message);
	}
	
//	public static void writeErrorSheet()
//	{
//		try
//		{
//			PrintWriter writer = new PrintWriter(new FileOutputStream("C://Javeed//TrueDefect.txt", false));
//			
//			writer.println("Test Case Name \t Error Message \t Error Type");
//			writer.println();
//			for(Map.Entry<String, String> entry : errorSheetData.entrySet())
//			{
//				String errorCategory = categorizeErrors(entry.getKey(), entry.getValue());
//				writer.println(entry.getKey()+"\t"+entry.getValue()+"\t"+errorCategory);
//			}
//			
//			writer.close();
//			
//		}
//		catch(Exception e)
//		{
//			log.info("Unable to write data to True Defect Analysis file");
//		}
//	}
	
	
	
	public static String categorizeErrors(String errorMessage)
	{
		try
		{
			if(errorMessage.contains("UserName textbox in Login page with locator By.id: txtUserI is not PRESENCE"))
			{
				return "Application Down issue";
			}
			else if(errorMessage.contains("NPI MPIN details table with locator By.xpath: //table[@id='ViewTable']/tbody is not CLICK"))
			{
				return "NDB connectivity issue";
			}
			else
			{
				return "Sync issue";
			}
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
			return null;
		}
	}
}
