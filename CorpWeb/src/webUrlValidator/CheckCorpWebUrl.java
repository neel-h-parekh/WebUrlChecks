package webUrlValidator;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.logging.Level;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.apache.poi.ss.usermodel.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.awt.Color;

import webUrlValidator.InputTestFile;

public class CheckCorpWebUrl implements Runnable{

//	XSSFWorkbook workbook;
//	
//	WebDriver driver;
//
//	public void ValidateUrl (int testFlag, int UrlColumnNo, int ExpectedTitleColumnNo, int Actual, int Result, String ChromeDriver, String URLFileName)
//	{
//		try
//		{
//
////			System.setProperty("webdriver.chrome.driver","C:\\eclipse\\chromedriver_win32_v2.23\\chromedriver.exe");
//			
//			System.setProperty("webdriver.chrome.driver",ChromeDriver);
//			
//			driver = new ChromeDriver();
//			
//			LoggingPreferences loggingprefs = new LoggingPreferences();
//			
//			loggingprefs.enable(LogType.BROWSER, Level.ALL);
//			
//			FileInputStream file = new FileInputStream(new File(URLFileName));
//				
//			workbook = new XSSFWorkbook(file);
//			
//			XSSFSheet sheet = workbook.getSheetAt(0);
//						
//			String appUrl;//This is input from the file..
//			
//			String title_expected;//This is input from the file.
//			
//			String title_actual = null;//Will get on performing the Url.
//			
//			int row_num;
//			
//			int cell_no;
//			
//			int put_result_cell_no = Result;
//			
//			int put_actual_cell_no = Actual;
//			
//			String test_result = null;
//			
//			String Test_Flag;
//			
//			int pass_cnt = 0;
//			
//			int fail_cnt = 0;
//			
//			int cnt_inactive = 0;
//			
//			int row_cnt = 0;
//
//			for (Row row : sheet) 
//			{
//				
//				row_cnt++;
//				
//				if ( row.getRowNum() == 0 ) System.out.println("Asuming The First Row Is Header So Ignoring...");
//				
//				row_num = row.getRowNum();
//				
//				Test_Flag = row.getCell(testFlag).getStringCellValue();
//				
//				if (Test_Flag.equalsIgnoreCase("N")) cnt_inactive++;
//				
//				if ( row_num > 0 && Test_Flag.equalsIgnoreCase("Y"))
//				{
//					Cell BrowserError = row.createCell(7);
//					for (Cell cell : row) 
//					{
//						cell_no = cell.getColumnIndex();
//						
//						DataFormatter objDefaultFormat = new DataFormatter();
//						
//						if ( cell_no == UrlColumnNo )//Perform Url
//						{
//							appUrl = cell.getStringCellValue();
//							
//							System.out.println("Checking URL\t: " + appUrl);
//							driver.get(appUrl);
//							title_actual = driver.getTitle();
//							LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
//
//							StringBuilder SB_BrowserLogMessage = new StringBuilder();
//							
//							for (LogEntry entry : logEntries) 
//							{
//								String BrowserLogMessage=entry.toString();
////					            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
//								SB_BrowserLogMessage.append(BrowserLogMessage + "\n");
//							}
//							System.out.println(SB_BrowserLogMessage.toString());
//							
//							BrowserError.setCellValue(SB_BrowserLogMessage.toString());
//
//						}
//
//						if ( cell_no == ExpectedTitleColumnNo )//Check Expected & Actual Title
//						{
//							title_expected = cell.getStringCellValue();
//							
//							System.out.println("Actual Title\t: " + title_actual);
//														
//							System.out.println("Expected Title\t: " + title_expected);
//							
//							if ( title_expected.equalsIgnoreCase(title_actual) )
//							{
//								System.out.println("Title Match\t: Pass");
//								test_result = "Pass";
//								pass_cnt++;
//							}
//							else
//							{
//								System.out.println("Title Matching\t: Fail");
//								test_result = "Fail";
//								fail_cnt++;
//							}
//							
//						}
//											
//					}
//					
//					Cell actual_cell = row.createCell(put_actual_cell_no);
//					actual_cell.setCellValue(title_actual);
//					
//					Cell result_cell = row.createCell(put_result_cell_no);
//					result_cell.setCellValue(test_result);
//					
//				}
//				
//			}
//						
//			XSSFSheet sheet1 = workbook.getSheetAt(1);
//			String email_flag = sheet1.getRow(0).getCell(1).toString();
//			String email_subject = sheet1.getRow(1).getCell(1).toString();
//			String from = sheet1.getRow(2).getCell(1).toString();
//			String to = sheet1.getRow(3).getCell(1).toString();
//			
//			StringTokenizer to_st = new StringTokenizer(to,";");
//			
//			file.close();
//
//	    	FileOutputStream out = new FileOutputStream(new File(URLFileName));
//	    	workbook.write(out);
//			out.flush();
//			out.close();
//			
//			row_cnt=row_cnt-1;
//			String msg1 = "\tUrl's Test Summary";
//			String msg2 = "No of Url's in the file\t\t" +row_cnt;
//			String msg3 = "No of Url's Available\t\t" +pass_cnt;
//			String msg4 = "No of Url's Failed\t\t" +fail_cnt;
//			String msg5 = "No of Test Inactive\t\t" +cnt_inactive;
//			
//			System.out.println(msg1 +"\n" +msg2 +"\n" +msg3 +"\n" +msg4 +"\n" +msg5);
//			
//			String host = "webmail.sterlinginfosystems.com";
//			Properties properties = System.getProperties();
//			properties.setProperty("mail.smtp.host", host);
//			Session session = Session.getDefaultInstance(properties);
//			
//			MimeMessage message = new MimeMessage(session);
//			
//			message.setFrom(new InternetAddress(from));
//			
//			while(to_st.hasMoreTokens())
//			{
//				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to_st.nextToken()));
//			}
//
//			message.setSubject(email_subject);
//			message.setText(msg1 +"\n" +msg2 +"\n" +msg3 +"\n" +msg4 +"\n" +msg5);
//			
//			
//			if (email_flag.equalsIgnoreCase("Y"))
//			{
//				System.out.println("Sending Email...");
//				System.out.println("To - " +to);
//				System.out.println("From - " +from);
//				Transport.send(message);
//			}
//			
//		}
//	
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//	}
//
//	public void showBrowserLogErrors()
//	{
//		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
//		
//		if (logEntries.equals(null)) System.out.println("log entry is null");
//		
//		for (LogEntry entry : logEntries) 
//		{
//            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
//		}
//	}
	
	String threadName;
	Thread t;
	Row r;
	
	CheckCorpWebUrl(String tname)
	{
		threadName = tname;
		System.out.println("Creating..." +  threadName);
	}
	
	public void start () 
	{
		System.out.println("Starting thread..." + threadName);
		if (t == null)
		{
			t = new Thread (this, threadName);
			t.start ();
		}
	}

	public void run()
	{
		while (!(webUrlValidator.InputTestFile.Test_Row_No==-1))
		{
			r=webUrlValidator.InputTestFile.GetRowRecord();
			for (Cell c : r)
			{
				System.out.println(threadName + "-" + c.getRowIndex() + "-" + c.getCellType());
				switch (c.getCellType())
				{
				case 0:
					System.out.println(threadName + "-" + c.getRowIndex() + "-" + c.getNumericCellValue());
					break;
				default:
					System.out.println(threadName + "-" + c.getRowIndex() + "-" + c.getStringCellValue());
					break;
				}
			}
		}
	}
	public static void main(String[] args) 
	{
		
		try 
		{
//	        String ChromeWebDriver = args[0];
//			String filename = args[1];
//	        
////			CheckCorpWebUrl ccwu = new CheckCorpWebUrl();		
////			ccwu.ValidateUrl(1,3,4,5,6,"C:\\eclipse\\chromedriver_win32_v2.23\\chromedriver.exe","C:\\Sts\\SiteCore\\STCorp_au\\STCorpWebUrl.xlsx");
////			ccwu.ValidateUrl(1,3,4,5,6,"C:\\eclipse\\chromedriver_win32\\chromedriver.exe","C:\\Sts\\SiteCore\\STCorp_au\\STCorpWebUrl.xlsx");
			
//			ccwu.ValidateUrl(1,3,4,5,6,ChromeWebDriver,filename);
//			ccwu.driver.quit();
//			InputTestFile i =new InputTestFile();
			
			webUrlValidator.InputTestFile.SetInputFile("C:\\Sts\\SiteCore\\STCorp_au\\Sitemap\\STCorpWebUrl.xlsx");
			System.out.println(webUrlValidator.InputTestFile.Test_Sheet.getSheetName());
			CheckCorpWebUrl T1 = new CheckCorpWebUrl("Thread-1");
			T1.start();
//			
			CheckCorpWebUrl T2 = new CheckCorpWebUrl("Thread-2");
			T2.start();
			
			CheckCorpWebUrl T3 = new CheckCorpWebUrl("Thread-3");
			T3.start();
//			for (int i=0;i<10;i++)
//			{
//				T1.run();
//			}

			
	    }
		
	    catch (Exception e)
		{
	        System.out.println("Exception in main");
	        System.out.println(e);
	    }

	}
	

}
