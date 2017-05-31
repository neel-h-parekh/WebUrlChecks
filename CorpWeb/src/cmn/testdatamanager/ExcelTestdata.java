package cmn.testdatamanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTestdata 
{
	static File fileInput;
	static FileInputStream fileInputStream;
	static FileOutputStream fileOutputStream;
	static XSSFWorkbook workbook;
	static XSSFSheet testDataSheet;
	static XSSFRow testRow;
	static int recordsCount;
	static int counter=1;//This will allow to ignore Header Row...
	
	public static void setTestdata(File fileName, String sheetName)
	{
		try
		{
			fileInput = fileName;
			fileInputStream = new FileInputStream(fileInput);
			workbook = new XSSFWorkbook(fileInputStream);
			testDataSheet=workbook.getSheet(sheetName);
			recordsCount = testDataSheet.getPhysicalNumberOfRows();
		}
		catch (Exception E)
		{
			System.out.println(E);
			E.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void showDetails()
	{
		System.out.println("Total no of records are : "+recordsCount);
	}
	
	public synchronized static XSSFRow getRow()
	{
		if (counter<=recordsCount)
		{
			testRow=testDataSheet.getRow(counter++);
			return testRow;
		}
		return null;
	}
	
	public synchronized static boolean counterStatus()
	{
		if (counter<recordsCount)
		{
			return true;
		}
		else
			return false;
	}
	
	public synchronized static void updateCell(XSSFRow row, int cellNo, String msg)
	{
		try
		{
			XSSFCell c = row.createCell(cellNo);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			c.setCellStyle(cellStyle);
			c.setCellValue(msg);
			fileOutputStream = new FileOutputStream(fileInput);
	    	workbook.write(fileOutputStream);
	    	fileOutputStream.flush();
	    	fileOutputStream.close();
		}
		catch(Exception E)
		{
			System.out.println("Exception while updating the workbook"+E);
			E.printStackTrace();
		}
	}
	
	public static void closeFile()
	{
		try
		{
			workbook.close();
			fileInputStream.close();
		}
		catch (Exception E)
		{
			E.printStackTrace();
		}
	}
}
