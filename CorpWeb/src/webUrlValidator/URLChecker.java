package webUrlValidator;

import cmn.testdatamanager.*;
import java.io.File;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class URLChecker implements Runnable
{
	static Logger logger;
	//Calculate Execution Time
	static Date startDT;
	static Date endDT;
	
	//Class Variables
	static String browser;
	static String browserDriver;
	static String inputFile;
	static String threads;
	static String testSheetName;
	static Integer noOfThreads;
	static File testFile;
	static int threadFinishCounter=1;
	
	//Instance Variables
	Thread t;
	String threadNo;
	WebDriver driver;
	LoggingPreferences loggingPrefs;
	LogEntries logEntries;
	XSSFRow testRow;
	int colNo_TCaseNo=0; String tCaseNo;
	int colNo_Flag=1; String flag;
	int colNo_Desc=2; String desc;
	int colNo_URL=3; String URL;
	int colNo_actualURL=4; String actualURL;
	int colNo_actualTitle=5; String actualTitle;
	int colNo_actualLogs=6; StringBuilder sb_logEntries;
	
	static void validateInputs(String S1, String S2, String S3, String S4)
	{
		if (S1.isEmpty() || S2.isEmpty() || S3.isEmpty() || S4.isEmpty())
		{
			logger.info("Input string are empty");
			logger.info("Browser "+S1);
			logger.info("Browser Driver "+S2);
			logger.info("File name "+S3);
			logger.info("Threads "+S4);
			System.exit(0);
		}
	}
	
	URLChecker(int no)
	{
		logger.info("Starting Thread "+no);
		setBrowser(browser,browserDriver);
		threadNo = Integer.toString(no);
		t=new Thread(this,threadNo);
		t.setName(threadNo);
		t.start();
	}

	void setBrowser(String S1_Browser, String S2_BrowserDriver)
	{
		if (S1_Browser.equalsIgnoreCase("CHROME"))
		{
			System.setProperty("webdriver.chrome.driver",S2_BrowserDriver);
			driver = new ChromeDriver();
		}
		
		if (S1_Browser.equalsIgnoreCase("FIREFOX"))
		{
			
		}
	}
	
	@Override
	public void run() 
	{
		while (ExcelTestdata.counterStatus()==true)
		{
			test();
		}
		this.closeDriver();
		finishLine();
	}
	
	void test()
	{
		try
		{
			getRowValues();//get test record values
			if (flag.equalsIgnoreCase("Y"))
			{
				sb_logEntries = new StringBuilder();
				driver.get(URL);
				actualTitle = driver.getTitle();
				actualURL = driver.getCurrentUrl();
				logEntries = driver.manage().logs().get(LogType.BROWSER);
		        for (LogEntry entry : logEntries)
		        {
		        	sb_logEntries.append(entry.toString());
		        	sb_logEntries.append("\n");
		        }
		        logger.info(testRow.getRowNum()+" - "+t.getName()+" - "+tCaseNo+" - "+driver.getCurrentUrl()+"\n"+sb_logEntries.toString());//uncomment for test
		        setTestResult();
			}
		}
		catch(Exception E)
		{
			logger.info("Exception in test() "+E);
			sb_logEntries.append(E);
			logger.error("Exception:",E);
			setTestResult();
		}

	}
	
	void getRowValues()
	{
		testRow=ExcelTestdata.getRow();
		tCaseNo=testRow.getCell(colNo_TCaseNo).toString();
		flag=testRow.getCell(colNo_Flag).toString();
		desc=testRow.getCell(colNo_Desc).toString();
		URL=testRow.getCell(colNo_URL).toString();
		/*
		logger.info(tCaseNo);
		logger.info(flag);
		logger.info(desc);
		logger.info(URL);
		*/
	}
	
	void setTestResult()
	{
		ExcelTestdata.updateCell(testRow, colNo_actualURL, actualURL);
		ExcelTestdata.updateCell(testRow, colNo_actualTitle, actualTitle);
		ExcelTestdata.updateCell(testRow, colNo_actualLogs, sb_logEntries.toString());
	}
	
	void closeDriver()
	{
		driver.close();
	}
	
	static synchronized void finishLine()
	{
		if (noOfThreads.intValue()==threadFinishCounter)
		{
			ExcelTestdata.closeFile();//Close input file
			
			logger.info("Time Taken : ");
			endDT = new Date();
			long diff = endDT.getTime() - startDT.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			logger.info(diffDays + " days, ");
			logger.info(diffHours + " hours, ");
			logger.info(diffMinutes + " minutes, ");
			logger.info(diffSeconds + " seconds."+"\n");
		}
		threadFinishCounter++;
	}
	
	public static void main(String[] args)
	{
		logger = LogManager.getLogger("com.webUrlValidator.URLChecker");
		startDT = new Date();
		logger.info(startDT);
		logger.info("inside main");
		logger.info("expected arguments - Browser, Browser Driver File, filename, sheetname, No of Threads");
		logger.info("Eg - Browser (Chrome or Firefox), Browser Driver File (C:\\\\chromedriver_v2.29_win32.exe), Filename (Test.xlsx), Sheetname (Sheet1), No of Threads (4)");
		try
		{
			browser = args[0];
			browserDriver = args[1];
			inputFile = args[2];
			testSheetName = args[3];
			threads = args[4];

			logger.info("actual inputs :");
			logger.info("Browser - "+browser);
			logger.info("Browser Driver - "+browserDriver);
			logger.info("Workbook - "+inputFile);
			logger.info("Testdata Sheet - "+testSheetName);
			logger.info("No of threads to run - "+threads);
			
			validateInputs(browser,browserDriver,inputFile,threads);//Check if input is valid, exit if any error.

			testFile=FileCopy.copySourceFile(inputFile);//Create copy of input file
			
			logger.info("Is file available: "+FileCopy.SourceFileCopy.canRead());//Check if file is available
			
			ExcelTestdata.setTestdata(testFile, testSheetName);//Set the testdata sheet
			ExcelTestdata.showDetails();//Show no of records present in the testdata
			
			noOfThreads = Integer.parseInt(threads);//set int value of no of threads, used for creating threads in the below for loops.
		}
		catch (Exception E)
		{
			logger.error("Exception:",E);
			System.exit(0);
		}
		
		int i;
		for(i=0;i<noOfThreads;i++)//creates threads
		{
			new URLChecker(i);//one-instance-one-thread
		}
	}
}
