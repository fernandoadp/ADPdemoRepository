package ADP_main;

import ADP_DB.OracleJDBC;
import ADP.codeutils.DataParsing;
import ADP_Streamline.ADP_API_services;
import ADP_Streamline.MatrixReader;
//import ADP_Streamline.WriteReadExcel;

/**
 *
 * @author FernandoRodriguez
 */
public class Main {
    
  public static void main (String[] args) {
      
    //WriteReadExcel wre = new WriteReadExcel();
    MatrixReader mr = new MatrixReader();
    ADP_API_services adp = new ADP_API_services();
    DataParsing dp = new DataParsing();
    
    try {
      
      System.out.println(mr.check());
      System.out.println(adp.check());
      
    } 
    catch (Exception ex) {ex.printStackTrace(System.out);}
  }
}
