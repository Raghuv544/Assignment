package com.UAT.SAMUI.core.generic.utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.UAT.SAMUI.automation.steps.MasterStep;


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
		InputStream instream = getClass().getClassLoader().getResourceAsStream("config.properties");
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
	 * This method returns the 2 dimensional array object data from "inputData.xls" sheet based on table name
	 * @param tableName It holds the data table name from which the data should be retrieved.
	 * @return an Array[][] object data with rows and columns of data table from input sheet
	 * 
	 * <p>
	 * 
	 * Example: ReadDataFromExcel("TC_01")
	 * <p>
	 * Here TC_01 is table name that is defined in input data sheet
	 */
	
	public Object[][] ReadDataFromExcel(String tableName)
	{
		System.out.println("Read Data From Excel");
		 ArrayList<String> cellDataList = new ArrayList<String>();
		 ArrayList<String> newCellDataList = new ArrayList<String>();
		 ArrayList<Integer> ignoredRows = new ArrayList<Integer>();
		 int RowCount=0;
		 int columnCount=0;
		 int itr=0;
		// boolean str=false;
		 //boolean num=false;
		 Object[][] data=null;
		 //int firstRowNum=0;
		 int statuscol=0;
		 Cell c=null;
		 int lastCellIndex = 0;
		 int nItr=0;
		 boolean recordsFOund=false;
		
		try
		{
			FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + File.separator + Environment("testDataFilePath"));
			System.out.println(System.getProperty("user.dir") + File.separator + Environment("testDataFilePath"));
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			Workbook workBook = WorkbookFactory.create(fileInputStream);
			int namedCellIdx = workBook.getNameIndex(tableName);			
		    org.apache.poi.ss.usermodel.Name aNamedCell = workBook.getNameAt(namedCellIdx);
			AreaReference area = new AreaReference(aNamedCell.getRefersToFormula());
			CellReference[] cellrefs = area.getAllReferencedCells();
			Sheet s = workBook.getSheet(aNamedCell.getSheetName());
			for(int i=0;i<cellrefs.length;i++)
			{				
				Row r = s.getRow(cellrefs[i].getRow());
				/*if(i<r.getLastCellNum() && statuscol==0 )
				{				
					 c= r.getCell(cellrefs[i].getCol());
					 if(c.getStringCellValue().equals("Status"))
					 {
						 statuscol=(c.getColumnIndex())-(r.getFirstCellNum());
						 lastCellIndex= statuscol;
					 }
					 if(i+1==r.getLastCellNum())
						{
							firstRowNum=(r.getLastCellNum()-r.getFirstCellNum());
							i= firstRowNum-1;
						}
					continue;
				}*/
				
				if(statuscol==0)
				{
					statuscol=(r.getLastCellNum()-r.getFirstCellNum())-1;
					lastCellIndex= statuscol;
				}
				c= r.getCell(cellrefs[i].getCol());
				
				switch (c.getCellType()) 
				{
					case XSSFCell.CELL_TYPE_STRING:
						//str =true;
						cellDataList.add(c.getStringCellValue().toString());
						if(c.getStringCellValue().toString().trim().equalsIgnoreCase("No")&&c.getColumnIndex()-r.getFirstCellNum()==statuscol)
						{				
							
							ignoredRows.add(c.getRowIndex());
						}	
					break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if(DateUtil.isCellDateFormatted(c))
						{
							cellDataList.add(c.getDateCellValue().toString());
							break;
						}
						c.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellDataList.add(c.getStringCellValue().toString());
						//num=true;
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						c.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellDataList.add(c.getStringCellValue().toString());
						break;
						
					case XSSFCell.CELL_TYPE_FORMULA:
						try
						{
							//str =true;							
							cellDataList.add(c.getStringCellValue().toString());
							if(c.getStringCellValue().toString().trim().equals("No")&&c.getColumnIndex()==statuscol)
							{				
								
								ignoredRows.add(c.getRowIndex());
							}
						}
						catch(Exception e)
						{						
							if(DateUtil.isCellDateFormatted(c))
							{
								cellDataList.add(c.getDateCellValue().toString());
							}
							else if(e.getMessage().contains("Cannot get a text value from a numeric formula cell"))
							{
								c.setCellType(HSSFCell.CELL_TYPE_STRING);
								cellDataList.add(c.getStringCellValue().toString());
							}
						}	
				}					
				
				if(i==cellrefs.length-1)
				{					
					columnCount=r.getPhysicalNumberOfCells();
					RowCount=(((i+1)/columnCount)-(ignoredRows.size()));
					//RowCount=RowCount-1;
				}				
			}
				workBook.close();
				recordsFOund=true;
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.info("Your test name and table name are not maching:"+tableName);
		}
		
		if(recordsFOund)
		{
			try
			{
				if(cellDataList.size()!=0)
				{
					
					while(itr<cellDataList.size())
					{
						
						if(cellDataList.get(statuscol).equals("Yes"))
						{			
							while(itr<=statuscol)
							{
								if(itr!=statuscol)
								{
									newCellDataList.add(cellDataList.get(itr));
								}
													
								itr++;
							}			
							
						}
						else
						{
							itr=(itr+1)+lastCellIndex;
						}
						
						statuscol=(statuscol+lastCellIndex)+1;
						
					}
				}
				
					if(newCellDataList.size()!=0)
					{
						while(nItr<newCellDataList.size())
						{
							data= new Object[RowCount][columnCount-1];
							for(int i=0;i<RowCount;i++)
							{	
								for (int j=0;j<columnCount-1;j++)
								{
									data[i][j]= newCellDataList.get(nItr);
									nItr++;
								}
							}
						}
					}
					else
					{
						log.info("No records are marked as 'Yes' in given table:"+ tableName);
					}		
			}		
			
			catch(Exception e)
			{
				e.printStackTrace();
				data=null;			
			}			
		}
		else
		{
			log.info("No records avialble in given table:"+ tableName);
		}	
			
			return data;
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
	 */
	public String  CreateResultsFile()
	{
		
		if(resultFile!=null)
		{
			return resultFile;
		}
		//String newFile=null;
		 
		try
		{	
			Date currentDate = getCurrentDateAndTime();
			String formatedCurrentDate =formatDateAndTime(currentDate,Environment("resultDateFormat"));
			Thread.sleep(1000);
			resultFile = "Results\\"+Environment("projectName")+"_ResultsNew_"+formatedCurrentDate+".xlsm";
			File f = new File(resultFile);
			if(f.exists())
			{		
				//resultFile=newFile;
				return resultFile;
			}			
			FileInputStream fileInputStream = new FileInputStream(Environment("resultTemplatePath"));			
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);			    
			FileOutputStream fileOut = new FileOutputStream(resultFile);
			workbook.write(fileOut);
			workbook.close();
			fileInputStream.close();
			fileOut.close();
			return resultFile;
			
		}
		catch (Exception e)
		{
			resultFile =null;
		}
		
		return resultFile;
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
	 * This method is used to Report the result of each test
	 * @param testID Test script name/Test script ID of string type
	 * @param testName Test scenario objective of string type
	 * @param testDesc Test description objective of string type
	 * @param resultValue Test script result status
	 * @param startTime start time value of test script
	 * @param endTime end time value of test script
	 * @param responseTime response time of total script
	 * <p>
	 * Example:- ReportTestSummary("TC_01","Verify login functionality",
	 * "Verifying login functionality with valid credentials","PASS","12/12/15 10:15","12/12/16 10:30",
	 * 20)
	 */
	public void ReportTestSummary(String testID,String testName,String testDesc,String resultValue,
			String startTime, String endTime, long responseTime)
	{
		String resultFileName="";
		//int itr=0;
		ArrayList<String> columnTexts = new ArrayList<String>();
		try
		{
			resultFileName= CreateResultsFile();			
			FileInputStream fileInputStream = new FileInputStream(resultFileName);
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("Summary");
			int rowCount = sheet.getPhysicalNumberOfRows();			
			XSSFRow mainrow = sheet.getRow(2);
			int lastColumNumber = mainrow.getLastCellNum();
			for(int i=0; i<lastColumNumber;i++)
			{
				columnTexts.add(mainrow.getCell(i).getStringCellValue());
			}
			XSSFRow newRow = sheet.createRow(rowCount);
			for(int j=0;j<columnTexts.size();j++)
			{
				//Need to add switch cases once java is upgraded to jre1.7 since jre1.6 doesnt support switch type
				if(columnTexts.get(j).toString().equals(Environment("ReportTempField1")))
				{
					newRow.createCell(j).setCellValue(testID);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField2")))
				{
					newRow.createCell(j).setCellValue(testName);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField3")))
				{
					newRow.createCell(j).setCellValue(testDesc);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField4")))
				{
					CellStyle style = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();	
				    if(resultValue.equals("PASS"))
					{
						style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						
						font = workbook.createFont();				       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(resultValue);
					}
					else
					{
						style.setFillForegroundColor(IndexedColors.RED.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(resultValue);
					}
				}
					
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField5")))
				{
					newRow.createCell(j).setCellValue(startTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField6")))
				{
					newRow.createCell(j).setCellValue(endTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField7")))
				{
					newRow.createCell(j).setCellValue(responseTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField8")))
				{
					newRow.createCell(j).setCellValue(getHostName());
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField9")))
				{
					newRow.createCell(j).setCellValue(envName);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField10")))
				{
					try
					{
						CellStyle hlink_style = workbook.createCellStyle();
				        XSSFFont hlink_font = workbook.createFont();			       
				        hlink_font.setColor(IndexedColors.BLUE.getIndex());
				        hlink_font.setBold(true);
				        hlink_font.setItalic(true);			        
				        hlink_style.setFont(hlink_font);
				        CreationHelper createHelper = workbook.getCreationHelper();
				        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
				        link.setAddress(appURL);
				        newRow.createCell(j).setCellStyle(hlink_style);			        
				        newRow.getCell(j).setCellValue(appURL);
				        newRow.getCell(j).setHyperlink(link);
					}
					catch(Exception e)
					{
						e.getMessage();
						newRow.createCell(j).setCellValue(appURL);
					}
					
			        //newRow.getCell(j).getStringCellValue().
					//newRow.createCell(j).setCellValue(Environment("appURL"));
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField11")))
				{
					newRow.createCell(j).setCellValue(currentBrowser);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField12")))
				{
					newRow.createCell(j).setCellValue(getUserName());
				}
			}
			FileOutputStream fileOutPutStream = new FileOutputStream(resultFileName);
			workbook.write(fileOutPutStream);
			workbook.close();
			fileInputStream.close();
			fileOutPutStream.close();				
		}
		catch (Exception e)
		{
			e.getMessage();
			
		}		
	}
	
	/**
	 * This  method is used to report the each test step result details
	 * @param testCaseNumber The test Id of string type
	 * @param rowIterationNumber The rowIteration Number of string type
	 * @param stepNumber The step number of string type
	 * @param stepName The step name of string type
	 * @param actualValue The actual result of test step 
	 * @param statusValue The status value of test step 
	 * @param screenShotStatus The screenshot status (Yes/No) if screenshot need Yes otherwise No
	 * @param Driver Page driver of webDriver type	 * 
	 * <p>
	 * Example :- ReportStepDetails("TC_01",0,1,"Login to home page",
	 * "Login is successful","PASS","Yes",loginPageDriver)
	 */
	public void ReportStepDetails(String testCaseNumber, int rowIterationNumber,int stepNumber,
			String stepName,String stepDetails,String expValue, String actualValue,
			String statusValue,String screenShotStatus,WebDriver Driver)
	{
		String resultFileName="";
		//int itr=0;
		ArrayList<String> columnTexts = new ArrayList<String>();
		try
		{
			resultFileName= CreateResultsFile();			
			FileInputStream fileInputStream = new FileInputStream(resultFileName);
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("Steps");
			int rowCount = sheet.getPhysicalNumberOfRows();			
			XSSFRow mainrow = sheet.getRow(0);
			int lastColumNumber = mainrow.getLastCellNum();
			for(int i=0; i<lastColumNumber;i++)
			{
				columnTexts.add(mainrow.getCell(i).getStringCellValue());
			}
			XSSFRow newRow = sheet.createRow(rowCount);
			for(int j=0;j<columnTexts.size();j++)
			{
				
				//Need to add switch cases once java is upgraded to jre1.7 since jre1.6 doesnt support switch type
				if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField1")))
				{
					newRow.createCell(j).setCellValue(testCaseNumber);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField2")))
				{
					newRow.createCell(j).setCellValue(rowIterationNumber);					
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField3")))
				{
					newRow.createCell(j).setCellValue(stepNumber);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField4")))
				{
					newRow.createCell(j).setCellValue(stepName);
				}					
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField5")))
				{
					newRow.createCell(j).setCellValue(stepDetails);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField6")))
				{
					newRow.createCell(j).setCellValue(expValue);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField7")))
				{
					newRow.createCell(j).setCellValue(actualValue);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField8")))
				{
					CellStyle style = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();	
				    if(statusValue.equalsIgnoreCase("PASS"))
					{
						style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						
						font = workbook.createFont();				       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(statusValue);
					}
					else
					{
						style.setFillForegroundColor(IndexedColors.RED.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(statusValue);
					}
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField9")))
				{
					try
					{						
						if(screenShotStatus.equalsIgnoreCase("Yes"))
						{
							String seMinVal=formatDateAndTime(getCurrentDateAndTime(), "mmss");
							SeleniumHelper.takeSnapShot(Driver,Environment("screenShotsPath")+"\\"+Environment("projectName")+"\\"+
						currentBrowser+"\\"+stepName+statusValue+seMinVal+".png");
							CellStyle hlink_style = workbook.createCellStyle();
					        XSSFFont hlink_font = workbook.createFont();			       
					        hlink_font.setColor(IndexedColors.BLUE.getIndex());
					        hlink_font.setBold(true);
					        hlink_font.setItalic(true);			        
					        hlink_style.setFont(hlink_font);					        
					        newRow.createCell(j).setCellStyle(hlink_style);
					        newRow.getCell(j).setCellValue("file:"+(Environment("screenShotsPath")+"\\"+Environment("projectName")+"\\"+
					        currentBrowser+"\\"+stepName+statusValue+seMinVal+".png"));
					    }													        
				}
				catch (Exception e)
				{
					e.getMessage();					 
				}
			}							
		}//ENd for
			FileOutputStream fileOutPutStream = new FileOutputStream(resultFileName);
			workbook.write(fileOutPutStream);
			workbook.close();
			fileInputStream.close();
			fileOutPutStream.close();
		}
		catch (Exception e)
		{
			e.getMessage();
			
		}		
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
			characters = "1234567890";
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
				f = new File("Drivers", "IEDriverServer_Win32_2.47.0/IEDriverServer.exe");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "chrome":
				f = new File("Drivers", "chromedriver_win32/chromedriver.exe");
				return f.getAbsolutePath();

			default:
				f = new File("Drivers", "IEDriverServer_Win32_2.47.0/IEDriverServer.exe");
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
	
	public static void copySheetDataForPSI_Reimport(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.Sheet mySheet, List<String> exportData, CellStyle dateCellStyle)
	  {
		try
		{
			
			
			Row myRow = null;
		      Cell myCell = null;
		      int iRow = 0;
		      int iCell = 0;
		      int listIndex = 0;
		      
			  for (Row r : sheet) {
				  myRow = mySheet.createRow(iRow);
				  iCell = 0;
			    for (Cell c : r) {
			      System.out.println(c.getStringCellValue());
			      myCell = myRow.createCell(iCell);
			      myCell.setCellType(c.getCellType());
			      
			      switch (c.getCellType()) {
		          case XSSFCell.CELL_TYPE_BLANK:
		              myCell.setCellValue("");
		              break;

		          case XSSFCell.CELL_TYPE_BOOLEAN:
		              myCell.setCellValue(c.getBooleanCellValue());
		              break;

		          case XSSFCell.CELL_TYPE_ERROR:
		              myCell.setCellErrorValue(c.getErrorCellValue());
		              break;

		          case XSSFCell.CELL_TYPE_FORMULA:
		              myCell.setCellFormula(c.getCellFormula());
		              break;

		          case XSSFCell.CELL_TYPE_NUMERIC:
		        	  if(DateUtil.isCellDateFormatted(c))
		        	  {
		        		  myCell.setCellStyle(dateCellStyle);
		        		  myCell.setCellValue(c.getDateCellValue());
		        		  break;
		        	  }
		        	  else
		        	  {
		        		  myCell.setCellValue(c.getNumericCellValue());
		        		  break;
		        	  }

						case XSSFCell.CELL_TYPE_STRING:
							if (c.getStringCellValue().contains("PT-")) {
								myCell.setCellValue(exportData.get(listIndex)
										.toString());
								listIndex++;
								break;

							} else {
								myCell.setCellValue(c.getStringCellValue());
								break;
							}          	                   
		              
		          default:
		              myCell.setCellFormula(c.getCellFormula());
		          }
			      System.out.println("iCell value:" +iCell);
			      iCell++;
			    }
			    iRow++;
			  }
		}
	     catch(Exception e)
	     {
	    	 log.info("Unable to copy data from sheet "+sheet.getSheetName()+"to new sheet");
			 Assert.fail("Unable to copy data from sheet "+sheet.getSheetName()+"to new sheet");
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
	
	public static void writeErrorSheet()
	{		
		try
		{
			String headers[] = new String[]{"Test Case Name","Error Message","Error Category"};
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("True Defect");
			XSSFRow row;
			XSSFCell cell;
			
			int rowCount=0;
			row = sheet.createRow(rowCount);
			for(int k=0; k<headers.length;k++)
			{				
				cell = row.createCell(k);
				cell.setCellValue(headers[k]);
			}
			
			for(Map.Entry<String, String> entry : errorSheetData.entrySet())
			{
				row = sheet.createRow(++rowCount);
				String errorCategory = categorizeErrors(entry.getValue());
				
				for(int i=0;i<3;i++)
				{
					cell = row.createCell(i);
					switch(i)
					{
					case 0: 
						cell.setCellValue(entry.getKey());
						break;
					case 1:
						cell.setCellValue(entry.getValue());
						break;
					case 2:
						cell.setCellValue(errorCategory);
						break;
					}
				}				
			}
			
			FileOutputStream fileOutPutStream = new FileOutputStream(trueDefectFilePath);
			workbook.write(fileOutPutStream);
			workbook.close();
			fileOutPutStream.close();			
		}
		catch(Exception e)
		{
			log.info("Unable to write data to True Defect Analysis file");
		}
	}
	
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
	
	public static String getFileLocation(String fileLocation)
	{
		File f;
		try
		{
			switch (fileLocation.toLowerCase()) 
			{
			case "census":
				f = new File("censusTestData", "Census.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "censusfortwo":
				f = new File("censusTestData", "CensusforTwo.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "censusfortwowithdep":
				f = new File("censusTestData", "CensusforTwoWithDep.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();	
			case "censusforfour":
				f = new File("censusTestData", "CensusforFour.xls");
				return f.getAbsolutePath();
			case "censusforten":
				f = new File("censusTestData", "Censusforten.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "censusforthirty":
				f = new File("censusTestData", "censusforThirty.xls");
				return f.getAbsolutePath();	
			case "censusfortwo_dependentabove115years":
				f = new File("censusTestData", "CensusforTwo_DependentAbove115Years.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "censusfortwo_dependentbelow1year.xls":
				f = new File("censusTestData", "CensusforTwo_DependentBelow1Year.xls");
				return f.getAbsolutePath();
			case "censusnolife":
				f = new File("censusTestData", "CensusNOlife.xls");
				System.out.println(f.exists());
				System.out.println(f.getAbsolutePath());
				return f.getAbsolutePath();
			case "dummy":
				f = new File("censusTestData", "Dummy.xls");
				return f.getAbsolutePath();	
			case "censusnolifefortwo":
				f = new File("censusTestData", "CensusNOlifeforTwo.xls");
				return f.getAbsolutePath();	
			case "extent":
				f = new File("test-output", "extent");
				return f.getAbsolutePath();		
			default:
				f = new File("censusTestData", "CensusforTwo.xls");
				return f.getAbsolutePath();
			}
			
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
