package generics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel 
{
//	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException
//	{
//		int rowNum = shiftingRowsDown("./data/inputs.xlsx", "trial", 1);
//		setExcelData("./data/inputs.xlsx", "trial", rowNum, 0, "dfghdfg");
//		setExcelData("./data/inputs.xlsx", "trial", rowNum, 1, "dfg");
//	}
	
	public static int getRowCount(String path,String sheet)
	{
		int r=0;
		try
		{
			r=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getLastRowNum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return r;
	}
	
	public static String getCellValue(String path,String sheet,int r,int c)
	{
		String v="";
		try
		{	
			CellType cs=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(r).getCell(c).getCellTypeEnum();
			v=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(r).getCell(c).toString();
			if(cs == CellType.NUMERIC)
			{
				//System.out.println("V:::"+ v);
				v = v.split("\\.")[0];       //Extension Removal
				//System.out.println("V:::"+ v);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v;	
	}
	
	public static void setExcelData(String path, String sheet, int rowNum, int colNum,String data) 
			throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheet);
	
		Row row = sh.getRow(rowNum);
		Cell cel = row.createCell(colNum);
		cel.setCellValue(data);

		
	    FileOutputStream fos = new FileOutputStream(path);
	    wb.write(fos);
	    wb.close();
	}
	
	public static String shiftingRowsUp(String path,String sheet,int r) 
	{
	
		try
		{	
			FileInputStream fis=new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet(sheet);
			int lastRowNum = sh.getLastRowNum();
		    if (r >= 0 && r < lastRowNum) {
		        sh.shiftRows(r + 1, lastRowNum, -1);
		    }
		    if (r == lastRowNum) {
		        Row removingRow = sh.getRow(r);
		        if (removingRow != null) {
		            sh.removeRow(removingRow);
		        }
		    }
			
		
		    FileOutputStream fos = new FileOutputStream(path);
		    wb.write(fos);
		    fos.flush();
		    fos.close();
	      
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sheet;
		
		
	}
	
	public static String shiftingRowsDown(String path,String sheet,int r)
	{

		try
		{	
			FileInputStream fis=new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet(sheet);
			int lastRowNum = sh.getLastRowNum();
			sh.shiftRows(r, lastRowNum, 1);
			sh.createRow(2);	
		    FileOutputStream fos = new FileOutputStream(path);
		    wb.write(fos);
		    fos.flush();
		    fos.close();
	      
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sheet;
	
	}
	
}



