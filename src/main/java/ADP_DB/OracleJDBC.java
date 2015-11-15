/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_DB;

import ADP_Streamline.CURL;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author frodri1
 */
public class OracleJDBC {
 
  public String check(String rights, String roles) throws Exception {
      
    int queryiteration = 0;  
    
    Random random = new Random();
    
    String query ="";
    String RoleID = "";
    String UM_MST_ATTRIBUTE_TYPE_ID = "";
    String VALUE = "";
    String UM_FCT_ASSIGNED_ROLE_ID = "";
    String UM_MST_USER_ID = "";
    	
    Connection P_SL_MSTORM1;
    Connection OPR_BUSINESS_DATA;
    Connection SPC_PRODUCTION_LINE;
    Connection SPC_USER_MANAGEMENT;
    Connection SPC_PRODUCTION_SYSTEM_V11CFG;
    
    Statement stmt = null;
    Statement stmtquery = null;
    ResultSet rs;
    ResultSet rsquery = null;
    
    OracleJDBC ora = new OracleJDBC(); 
    
    try {
        
      /*P_SL_MSTORM1 = DriverManager.getConnection(
      "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "P_SL_MSTORM1", "Bordeaux_1");    
        
      OPR_BUSINESS_DATA = DriverManager.getConnection(
      "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "OPR_BUSINESS_DATA_V11CFG", "Bordeaux_1");  
      
      SPC_PRODUCTION_LINE = DriverManager.getConnection(
      "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "SPC_PRODUCTION_LINE", "Bordeaux_1"); 
      
      SPC_USER_MANAGEMENT = DriverManager.getConnection(
      "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "SPC_USER_MANAGEMENT_V11CFG", "Bordeaux_1");
      
      SPC_PRODUCTION_SYSTEM_V11CFG = DriverManager.getConnection(
      "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "SPC_PRODUCTION_SYSTEM_V11CFG", "Bordeaux_1");
 
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return "fail connection";
    }
    
    if (P_SL_MSTORM1 != null) {
      System.out.println("connection stablished!");
    } else {
      System.out.println("P_SL_MSTORM1 Failed to make connection!");
    }*/
    
    
      OPR_BUSINESS_DATA = DriverManager.getConnection(
        "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "OPR_BUSINESS_DATA_V11CFG", "Bordeaux_1");  
    
      SPC_USER_MANAGEMENT = DriverManager.getConnection(
        "jdbc:oracle:thin:@mbudb06dev-dc1.esi.adp.com:12002:PHXBPMR1", "SPC_USER_MANAGEMENT_V11CFG", "Bordeaux_1");
    
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return "fail connection";
    }
    
    if (OPR_BUSINESS_DATA != null) {
      System.out.println("connection stablished!");
    } else {
      System.out.println("OPR_BUSINESS_DATA Failed to make connection!");
    }
    
    if (SPC_USER_MANAGEMENT != null) {
      System.out.println("connection stablished!");
    } else {
      System.out.println("SPC_USER_MANAGEMENT Failed to make connection!");
    }
    
    /*if (SPC_PRODUCTION_LINE != null) {
      System.out.println("connection stablished!");
    } else {
      System.out.println("SPC_PRODUCTION_LINE Failed to make connection!");
    }
    
    if (SPC_PRODUCTION_SYSTEM_V11CFG != null) {
      System.out.println("connection stablished!");
    } else {
      System.out.println("SPC_PRODUCTION_SYSTEM_V11CFG Failed to make connection!");
    }*/
      
    try {
        
    // Delete Users
        
       query = "select u.ID from SPC_USER_MANAGEMENT_V11CFG.UM_MST_USER u \n" +
               "where u.USER_NAME like '%Test_role%'";   
        
       stmt = SPC_USER_MANAGEMENT.createStatement();
       rs = stmt.executeQuery(query);
       
       while (rs.next()) {
           
         UM_MST_USER_ID = rs.getString("ID"); 
         
         query = "select ua.UM_MST_USER_ID from SPC_USER_MANAGEMENT_V11CFG.UM_FCT_USER_ATTRIBUTE ua\n" +
                 "where ua.UM_MST_USER_ID = '"+UM_MST_USER_ID+"'";
         
         if(!"".equals(ora.SelectQuery(query, stmtquery, rsquery, SPC_USER_MANAGEMENT))) {
             
           query = "delete from SPC_USER_MANAGEMENT_V11CFG.UM_FCT_USER_ATTRIBUTE ua\n" +
                   "where ua.UM_MST_USER_ID = '"+UM_MST_USER_ID+"'";
           
           ora.Insert_DeleteQuery(query, stmtquery, rsquery, SPC_USER_MANAGEMENT);
         }
         
         query = "select ar.UM_MST_USER_ID from SPC_USER_MANAGEMENT_V11CFG.UM_FCT_ASSIGNED_ROLE ar\n" +
                  "where ar.UM_MST_USER_ID = '"+UM_MST_USER_ID+"'";
         
         if(!"".equals(ora.SelectQuery(query, stmtquery, rsquery, SPC_USER_MANAGEMENT))) {
             
           query = "delete from SPC_USER_MANAGEMENT_V11CFG.UM_FCT_ASSIGNED_ROLE ar\n" +
                   "where ar.UM_MST_USER_ID = '"+UM_MST_USER_ID+"'";
           
           ora.Insert_DeleteQuery(query, stmtquery, rsquery, SPC_USER_MANAGEMENT);
         }
       }  
        
       query = "delete from SPC_USER_MANAGEMENT_V11CFG.UM_MST_USER u \n" +
               "where u.USER_NAME like '%Test_role%'";
       
       stmt.executeQuery(query);
       
           
    // Create new User    
        
      String USER_ID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
      String USER_NAME = "Test_role-" + RandomStringUtils.randomAlphanumeric(4).toUpperCase();
      String EMAIL = "test.test.str@adp.com";
    
      query = "INSERT INTO SPC_USER_MANAGEMENT_V11CFG.UM_MST_USER (ID, USER_NAME, EMAIL, UM_MST_SIDE_ID, DT_LAST_BPM_ACCESS)\n" +
              "VALUES ('"+USER_ID+"', '"+USER_NAME+"', '"+EMAIL+"', '2', null)";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);  
      
    
    // Define User Scope  
      
      query = "select c.GUID_CLIENT\n" +
              "from SPC_USER_MANAGEMENT_V11CFG.UM_FCT_USER_ATTRIBUTE ua\n" +
              "join OPR_BUSINESS_DATA_V11CFG.MST_CLIENT c on c.GUID_CLIENT = ua.\"VALUE\"";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next() && (queryiteration < 1)) {
        VALUE = rs.getString("GUID_CLIENT"); 
        queryiteration++;
      }  
        
      query = "select aty.ID\n" +
              "from SPC_USER_MANAGEMENT_V11CFG.UM_MST_ATTRIBUTE_TYPE aty\n" +
              "where aty.DE_NAME = 'MEMBER OF Client'";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        UM_MST_ATTRIBUTE_TYPE_ID = rs.getString("ID"); 
      }
      
      query = "INSERT INTO SPC_USER_MANAGEMENT_V11CFG.UM_FCT_USER_ATTRIBUTE (ID, VALUE, UM_MST_USER_ID, UM_MST_ATTRIBUTE_TYPE_ID, UM_MST_SIDE_ID, DT_ACTION, ACTION_USER)\n" +
              "VALUES ('"+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+"', '"+VALUE+"', '"+USER_ID+"', '"+UM_MST_ATTRIBUTE_TYPE_ID+"', '2', null, null)";

      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      
      
      query = "select aty.ID\n" +
              "from SPC_USER_MANAGEMENT_V11CFG.UM_MST_ATTRIBUTE_TYPE aty\n" +
              "where aty.DE_NAME = 'Alive'";
      
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        UM_MST_ATTRIBUTE_TYPE_ID = rs.getString("ID"); 
      }
      
      query = "INSERT INTO SPC_USER_MANAGEMENT_V11CFG.UM_FCT_USER_ATTRIBUTE (ID, VALUE, UM_MST_USER_ID, UM_MST_ATTRIBUTE_TYPE_ID, UM_MST_SIDE_ID, DT_ACTION, ACTION_USER)\n" +
              "VALUES ('"+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+"', '1', '"+USER_ID+"', '"+UM_MST_ATTRIBUTE_TYPE_ID+"', '2', null, null)";

      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      

    // Assign a Role TO The User (by role name from the excel matrix "roles")
      
      query = "select * from SPC_USER_MANAGEMENT_V11CFG.UM_MST_ROLE ro\n" +
              "where ro.\"NAME\" = '"+roles+"'\n" +
              "and ro.UM_MST_SIDE_ID = '2'";
      
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        RoleID = rs.getString("ID"); 
      }
      
      UM_FCT_ASSIGNED_ROLE_ID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
      
      query = "INSERT INTO SPC_USER_MANAGEMENT_V11CFG.UM_FCT_ASSIGNED_ROLE (ID, UM_MST_USER_ID, DT_EFFECTIVE, DT_EXPIRATION, UM_MST_ROLE_ID)\n" +
              "VALUES ('"+UM_FCT_ASSIGNED_ROLE_ID+"', '"+USER_ID+"', current_timestamp, null, '"+RoleID+"')";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      
      
    //Define Scope for a certain role 
      
      query = "select aty.ID \n" +
              "from SPC_USER_MANAGEMENT_V11CFG.UM_MST_ATTRIBUTE_TYPE aty\n" +
              "where aty.DE_NAME = 'SCOPE All Client Paygroups'";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        UM_MST_ATTRIBUTE_TYPE_ID = rs.getString("ID"); 
      }
      
      String user = "Fernando-" + RandomStringUtils.randomAlphanumeric(4).toUpperCase();
      
      query = "INSERT INTO SPC_USER_MANAGEMENT_V11CFG.UM_FCT_ASSIGNMENT_ATTRIBUTE (ID, VALUE, UM_MST_ATTRIBUTE_TYPE_ID, UM_FCT_ASSIGNED_ROLE_ID, DT_ACTION, ACTION_USER)\n" +
              "VALUES ('"+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+"', '"+VALUE+"', '"+UM_MST_ATTRIBUTE_TYPE_ID+"', '"+UM_FCT_ASSIGNED_ROLE_ID+"', current_timestamp, '"+user+"')";
      
      stmt = SPC_USER_MANAGEMENT.createStatement();
      rs = stmt.executeQuery(query);
      
      
      int y = 0;
      
    } catch (SQLException e ) {
        System.out.print(e);
    //} finally {
        //if (stmt != null) { stmt.close(); }
    }
    
    return "";
  }
  
  public String SelectQuery(String query, Statement stmtquery, ResultSet rsquery, Connection SPC_USER_MANAGEMENT) throws Exception {
     
    String queryResult = ""; 
    stmtquery = SPC_USER_MANAGEMENT.createStatement();
    rsquery = stmtquery.executeQuery(query);
    
    while (rsquery.next()) {
      queryResult = rsquery.getString("UM_MST_USER_ID");
    } 
    return queryResult;
  }
  
  
  public void Insert_DeleteQuery(String query, Statement stmtquery, ResultSet rsquery, Connection SPC_USER_MANAGEMENT) throws Exception {
     
    String queryResult = ""; 
    stmtquery = SPC_USER_MANAGEMENT.createStatement();
    rsquery = stmtquery.executeQuery(query);
  }
  
}
