package fcu.selab.taxibar.db;

import java.sql.Connection;

public interface IDatabase {
  
  Connection getConnection();

}
