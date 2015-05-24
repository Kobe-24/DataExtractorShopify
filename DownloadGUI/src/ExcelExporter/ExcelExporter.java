package ExcelExporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import Logger.Logger;

public class ExcelExporter {
	
	private Logger myLogger;
	
	public ExcelExporter(Logger logger)
	{
		this.myLogger = logger;		
	}
	
	public void Export(String outputPath)
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Shopify data");
		//Create a new row in current sheet
		Row row = sheet.createRow(0);
		//Create a new cell in current row
		Cell cell = row.createCell(0);
		//Set value to new value
		cell.setCellValue("Blahblah");
		
		try {
            FileOutputStream out =                 
            		   new FileOutputStream(new File(outputPath));
            workbook.write(out);
            out.close();
            this.myLogger.Log("Excel file written successfully..");
             
        } catch (FileNotFoundException e) {
        	this.myLogger.Log("Error: file not found. " + e.getMessage());
        } catch (IOException e) {
            this.myLogger.Log("Error: " + e.getMessage());
        }
		
	}
}
