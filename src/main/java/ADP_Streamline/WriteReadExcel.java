/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_Streamline;

import ADP_DB.OracleJDBC;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;
import static oracle.net.aso.b.c;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author frodri1
 */
public class WriteReadExcel {
      
  public void WriteExcel() throws Exception {
        
    //Blank workbook
    XSSFWorkbook workbook = new XSSFWorkbook();
         
    //Create a blank sheet
    XSSFSheet sheet = workbook.createSheet("Employee Data");
          
    //This data needs to be written (Object[])
    Map<String, Object[]> data = new TreeMap<>();
    data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
    data.put("2", new Object[] {1, "Amit", "Shukla"});
    data.put("3", new Object[] {2, "Lokesh", "Gupta"});
    data.put("4", new Object[] {3, "John", "Adwards"});
    data.put("5", new Object[] {4, "Brian", "Schultz"});
          
    //Iterate over data and write to sheet
    Set<String> keyset = data.keySet();
    int rownum = 0;
    for (String key : keyset){
      
      Row row = sheet.createRow(rownum++);
      Object [] objArr = data.get(key);
      int cellnum = 0;
    
      for (Object obj : objArr){
        
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellnum++);
        if(obj instanceof String)
          cell.setCellValue((String)obj);
        else if(obj instanceof Integer)
          cell.setCellValue((Integer)obj);
      }
    }
  
    try {
        try ( //Write the workbook in file system
          FileOutputStream out = new FileOutputStream(new File("C:\\Users\\frodri1\\Documents\\Demo.xlsx"))) {
          workbook.write(out);
        }
      System.out.println("Demo.xlsx written successfully on disk.");
    }
    catch (Exception e){e.printStackTrace();}
  }  
  
  
  public void ReadExcel() throws Exception {
      
    List<String> execFlowList = new ArrayList<>();
    List<String> TestCase = new ArrayList<>();
    
    String[] exec;
    String str = "";
    
    Boolean executionFlow = false;
    Boolean rowToRead = false;
    Boolean TC = false;
    
    Double cellNumericValue;
    Double id;
    
    int cellCount = 0;
    
    Iterator<Row> rowIterator;
    Row row;
    Iterator<Cell> cellIterator;
    Cell cell;
      
    try {
      FileInputStream file = new FileInputStream(new File("C:\\Users\\frodri1\\Documents\\Demo.xlsx"));
 
      //Create Workbook instance holding reference to .xlsx file
      XSSFWorkbook workbook = new XSSFWorkbook(file);
 
      //Get first/desired sheet from the workbook
      XSSFSheet sheet = workbook.getSheetAt(0);
 
      //Iterate through each rows one by one
      rowIterator = sheet.iterator();
      while (rowIterator.hasNext()){
        row = rowIterator.next();
        
        //For each row, iterate through all the columns
        cellIterator = row.cellIterator();        
        while (cellIterator.hasNext()){
          cell = cellIterator.next();
          
          //Check the cell type and format accordingly
          if(cell.getCellType() == 1) {
                
            if(executionFlow) {     
              execFlowList = Arrays.asList(cell.getStringCellValue().split(","));
              cellCount++;
            }
              
            if(cell.getStringCellValue().contentEquals("Execution Flow")) {  
              executionFlow = true;    
            }  
          }
          if(cellCount == 1) break;
        }
        if(cellCount == 1) break;
      }
      
      
      for(int i=0; i < execFlowList.size(); i++){
          
        rowToRead = false;
        TC = false;
        TestCase.clear(); 
        cellCount = 0;
              
        rowIterator = sheet.iterator();
        while (rowIterator.hasNext()){
          row = rowIterator.next();
          
          cellIterator = row.cellIterator();        
          while (cellIterator.hasNext()){
            cell = cellIterator.next();
                
            // --Copy values--  
            if(cell.getCellType() == 0) {
                
              if(rowToRead) {    
                Double dou = cell.getNumericCellValue();   
                TestCase.add(Integer.toString(dou.intValue()));
                cellCount++;
              }
            }  
            
            if(cell.getCellType() == 1) {
                
              if(rowToRead) { 
                TestCase.add(cell.getStringCellValue());
                cellCount++;
              }
            }  
            // --Copy values--
            
            if(cell.getCellType() == 1) {
                 
              if( cell.getStringCellValue().equals(execFlowList.get(i))) {  
                rowToRead = true;
              }
            }
            
            if(!rowToRead) break;
          }
          
          if(row.getLastCellNum() == cellCount + 1 && rowToRead) break;
        }
      }
    }
    catch (Exception e){e.printStackTrace();}     
  }
  
 
  public void ReadRolesRights() throws Exception {
      
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

