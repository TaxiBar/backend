
package fcu.selab.taxibar.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabase implements IDatabase {

  private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_CONNECTION = "jdbc:mysql://140.134.26.71:8880/TaxiBar?relaxAutoCommit=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "taxibar";
  private Connection con = null;

  public Connection getConnection() {
    // TODO Auto-generated method stub
    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }
    try {
      con = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return con;
  }

}
