package processdata;


import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 


public class HandleExcel {
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	public Iterator<Row> itr;
	HandleExcel (String filename){  
		try{  
			File file = new File(filename);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			this.wb = new XSSFWorkbook(fis);   
			this.sheet = this.wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			this.itr = this.sheet.iterator();    //iterating over excel file   
		}  
		catch(Exception e){  
			e.printStackTrace();  
		}  
	}  
	Iterator<Row> getItr(){
		return this.itr;
	}
	void close() {
		try{
			this.wb.close();
		}
		catch(Exception e) {
			e.printStackTrace(); 
		}
	}
}
