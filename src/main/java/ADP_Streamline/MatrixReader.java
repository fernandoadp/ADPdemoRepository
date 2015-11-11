/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_Streamline;

import ADP_DB.OracleJDBC;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author frodri1
 */
public class MatrixReader {
    
  public String check() throws Exception {
      
    List<String> ClientRoles = new ArrayList<>();
    List<String> PartnerRoles = new ArrayList<>();
    List<String> ADPRoles = new ArrayList<>();
    List<String> Rights = new ArrayList<>();
    
    String rightscolumn = "";
    String rights = "";
    String roles = "";
    String cellwithx;
    
    int righstrow = 0;
    int columncount;
    int rowcount;
    int roleslenght;
    int rightslenght = 0;
    
    Boolean client = false;
    Boolean partner = false;
    Boolean adp;
    Boolean rightstart = false;
    
    Iterator<Row> rowIterator;
    Iterator<Cell> cellIterator;
    
    Row row;
    Cell cell;
    
    OracleJDBC oracle = new OracleJDBC(); 
      
    try {
          
      FileInputStream file = new FileInputStream(new File("C:\\Users\\frodri1\\Documents\\SPM 1.2_RoleMatrix_Demo.xlsx"));
  
      //Create Workbook instance holding reference to .xlsx file
      XSSFWorkbook workbook = new XSSFWorkbook(file);
 
      //Get first/desired sheet from the workbook
      XSSFSheet sheet = workbook.getSheetAt(0);
      
      //Iterate through each rows one by one
      rowIterator = sheet.iterator();
      
      rowcount = 2;
      columncount = 0;
      
      while (rowIterator.hasNext()){
           
        row = rowIterator.next();
        //if(adp) {break;}
        adp = false;
        
        //For each row, iterate through all the columns
        cellIterator = row.cellIterator();        
        
        while (cellIterator.hasNext()){
            
          cell = cellIterator.next();
          
          if(cell.getStringCellValue().contentEquals("Right"))  {
            rightscolumn = CellReference.convertNumToColString(cell.getColumnIndex());
            righstrow = cell.getRowIndex();
            rightstart = true;
          }  
          
          if(cell.getStringCellValue().contentEquals("Client"))  {
            client = true;
          }    
          
          if(cell.getStringCellValue().contentEquals("Partner"))  {
            partner = true;
            client = false;
          }  
          
          if(cell.getStringCellValue().contentEquals("ADP"))  {
            partner = false;
            client = false;
            adp = true;
          }  
          
          if(client) {ClientRoles.add(CellIteration(sheet, CellReference.convertNumToColString(cell.getColumnIndex()), righstrow, columncount, 0).trim());}
          
          if(partner) {PartnerRoles.add(CellIteration(sheet, CellReference.convertNumToColString(cell.getColumnIndex()), righstrow, columncount, 0).trim());}
          
          if(adp) {ADPRoles.add(CellIteration(sheet, CellReference.convertNumToColString(cell.getColumnIndex()), righstrow, columncount, 0).trim());}
        }
        
        if(rightstart) {
              
          rights = CellIteration(sheet, rightscolumn, righstrow, columncount, rowcount);
          
          if(!"".equals(rights)) {
              
            Rights.add(rights.trim());
            rightslenght++;  
            rowcount++;
          } else break;
        }
      } 
      
      roleslenght = ClientRoles.size() + PartnerRoles.size() + ADPRoles.size();
      
      for( int i = 0; i < rightslenght; i++ ) {
         
        for( int l = 0; l < roleslenght; l++ ) {
              
          cellwithx = CellIteration(sheet, rightscolumn, righstrow, l + 1, i + 2); 
          if("x".equals(cellwithx)) {
              
            if(l < ClientRoles.size()) {
                
              rights = Rights.get(i);
              roles = ClientRoles.get(l);
              
              oracle.check(rights, roles);
            }
            
            if(l >= ClientRoles.size() && l < (ClientRoles.size() + PartnerRoles.size())) {
                  
              rights = Rights.get(i);
              roles = PartnerRoles.get(l - ClientRoles.size());   
              
            }
            
            if(l >= ClientRoles.size() + PartnerRoles.size()) {
              
              rights = Rights.get(i);
              roles = ADPRoles.get(l - (ClientRoles.size() + PartnerRoles.size())); 
              
            }
          }
        } 
      }
    }
    catch (Exception e){e.printStackTrace();}
    
    return "";
  }
  
  
   public String CellIteration(XSSFSheet sheet, String columnletter, int rownum, int columncount, int rowcount) throws Exception {
      
    if(columncount > 0) {  
      int column = (int) columnletter.charAt(0) + columncount;
      columnletter = Character.toString((char) column);
    }
    
    CellReference cr = new CellReference(columnletter + (rownum + rowcount));
    Row row = sheet.getRow(cr.getRow());
    String Roles = row.getCell(cr.getCol()).getStringCellValue();  
      
    return Roles;    
  }    
    
}
