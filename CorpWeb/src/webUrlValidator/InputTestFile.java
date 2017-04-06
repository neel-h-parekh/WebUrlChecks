package webUrlValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class InputTestFile
{
	static FileInputStream Input_File;
	static XSSFWorkbook Test_Workbook;
	static XSSFSheet Test_Sheet;
	static int Test_Row_No;
	static int Last_Row_No;
	static Row Test_Record_Row;

	public static void SetInputFile(String Filename)
	{
		try 
		{
			Input_File = new FileInputStream(new File(Filename));
			Test_Workbook = new XSSFWorkbook(Input_File);
			Test_Sheet = Test_Workbook.getSheetAt(0);
			Last_Row_No = Test_Sheet.getLastRowNum();
		}
		catch (Exception e) 
		{
			Test_Row_No=-1;
			e.printStackTrace();
		}
	}
	
	public static synchronized Row GetRowRecord()
	{
		try
		{
			if (!Input_File.equals(null))
			{
//				System.out.println("Getting row..."+Test_Row_No);
//				System.out.println("Last row no is..."+Test_Sheet.getLastRowNum());
				
				if(Test_Row_No<Last_Row_No)
				{
					Test_Record_Row = Test_Sheet.getRow(Test_Row_No);
					Test_Row_No++;
				}
				else
				{
					Test_Row_No=-1;
				}
			}
		}
		catch (Exception e)
		{
			Test_Row_No=-1;
			e.printStackTrace();
		}
		return Test_Record_Row;
	}
}