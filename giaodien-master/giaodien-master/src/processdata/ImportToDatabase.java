package processdata;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ImportToDatabase {
        public int codeStat = 0;
	public ImportToDatabase(String filename){
		HandleExcel he = new HandleExcel(filename);
		String sanGD = null;
		int idx = filename.indexOf("HNX");
		if (idx > 0) {
			sanGD = "HNX";
		}else {
			idx = filename.indexOf("HSX");
			if(idx > 0) {
				sanGD = "HSX";
			}else {
				idx = filename.indexOf("UPCOM");
				if(idx > 0) {
					sanGD = "UPCOM";
				}else {
					idx = filename.indexOf("INDEX");
					if(idx > 0) {
						sanGD = "INDEX";
					}
					else {
                                                this.codeStat = -1;
						return;
					}
				}
				
			}
		}
		ConnectDatabase db = new ConnectDatabase();
		int i = 0;
		Iterator <Row> itr = he.getItr();
		try {
		while (itr.hasNext()){  
			Row row = itr.next();  
			if (i == 0) {i++;continue;}
			Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
			String MaCP = null;
			double NgayGD = 0,open = 0,high = 0,low = 0,close = 0,weight = 0;
			while (cellIterator.hasNext()){  
				Cell cell = cellIterator.next();
				switch (cell.getCellType()){  
					case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
					MaCP = cell.getStringCellValue(); 
					break;  
					case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type 
						switch(cell.getAddress().toString().charAt(0)) {
							case 'B':
								NgayGD = cell.getNumericCellValue();
								break;
							case 'C':
								open = cell.getNumericCellValue();
								break;
							case 'D':
								high = cell.getNumericCellValue();
								break;
							case 'E':
								low = cell.getNumericCellValue();
								break;
							case 'F':
								close = cell.getNumericCellValue();
								break;
							case 'G':
								weight = cell.getNumericCellValue();
								break;	
						}	  
						break;  
					default:  
				}  
			}
			//puts in database
			if(MaCP == null)continue;
			String query = "insert into So_Lieu_Giao_Dich values ('" 
					 + MaCP + "','" + String.valueOf((long)NgayGD)
					 + "','" + Double.toString(open)
					 + "','" + Double.toString(high)
					 + "','" + Double.toString(low)
					 + "','" + Double.toString(close)
					 + "','" + String.valueOf((long)weight)
					 + "','" + sanGD
					 + "')";
			db.executeQueryInsert(query);
			
		}
		he.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
                this.codeStat = 0;
		return;
	}
}
