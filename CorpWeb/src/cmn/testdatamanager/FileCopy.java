package cmn.testdatamanager;

import java.io.File;

import java.nio.file.Files;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/

public class FileCopy
{

//Static variables and methods for handling the input test file
	
	public static File SourceFile;
	public static File SourceFileCopy;
	

	//Create Copy of Source File
		public static File copySourceFile(String FileName)
		{
			try
			{
				getSourceFile(FileName);
				System.out.println("Creating a copy of input file - "+SourceFile.getName());
				
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
				SourceFileCopy = new File(dateFormat.format(new Date())+"_"+SourceFile.getName());//Creates empty file and assigns file name (Copy)
				Files.copy(SourceFile.toPath(), SourceFileCopy.toPath());//Copies Original file to the above created Copy file
				fileExists(SourceFile,SourceFileCopy);
			}
			catch(Exception E)
			{
				E.printStackTrace();
			}
			return SourceFileCopy;
		}	

//Get Input|Source File
	static void getSourceFile(String FileName)
	{
		try
		{
			System.out.println("Getting input file - "+FileName);
			SourceFile = new File(FileName);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}



	static void showFileDetails(File SrcFile, File SrcFileCpy)
	{
		System.out.println("Input File Is: "+SrcFile.getAbsolutePath());
		System.out.println("Copy of Input File Is: "+SrcFileCpy.getAbsolutePath());
	}
	
	static boolean fileExists(File SrcFile, File SrcFileCpy)
	{
		if (SrcFile.exists() && SrcFile.exists())
		{
//			showFileDetails(SourceFile,SourceFileCopy);
			return true;
		}
		else
		{
//			System.out.println("Getting/Coping File Failed");
			return false;
		}
	}
//	public static void main(String[] args)
//	{
//		System.out.println("Starting Main");
//		getSourceFile("C:\\Sts\\SiteCore\\STCorp_au\\Sitemap\\STCorpWebUrl_vFirst\\STCorpWebUrl_CSS_Update.xlsx");
//	}
}
