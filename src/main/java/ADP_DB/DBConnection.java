/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADP_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author FernandoRodriguez
 */
public class DBConnection {
  private Connection conn = null;
 
  public DBConnection(
    String DriverClass, 
    String JDBCURL, 
    String user, 
    String pass) throws Exception {
    
    try {
      Class.forName(DriverClass);
      conn = DriverManager.getConnection(JDBCURL, user, pass);
    } 
    catch (ClassNotFoundException | SQLException ex) {
      conn.close();
      throw ex;
    }
  }
  
  public ResultSet getResultSet(String SQL) throws Exception {
    Statement stmt = conn.createStatement(); 
    ResultSet rs = stmt.executeQuery(SQL);
    return rs;
  }
  
  public static void closeResultSet(ResultSet rs) throws Exception {
    rs.getStatement().close();
    rs.close();
  }
  
  public void CloseConnection() {
    try {conn.close();} 
    catch (SQLException ex) {}
  } 
}
