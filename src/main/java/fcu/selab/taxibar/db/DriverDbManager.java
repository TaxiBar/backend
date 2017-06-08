package fcu.selab.taxibar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fcu.selab.taxibar.data.Driver;

public class DriverDbManager {

  private static DriverDbManager DB_MANAGER = new DriverDbManager();

  private IDatabase database = new MySqlDatabase();

  public static DriverDbManager getInstance() {
    return DB_MANAGER;
  }

  private DriverDbManager() {

  }

  public boolean addDriver(Driver driver) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "INSERT INTO Driver(driverID, plateNumber) VALUES(?, ?)";
    boolean check = false;

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driver.getId());
      preStmt.setString(2, driver.getPlateNumber());

      preStmt.executeUpdate();
      preStmt.close();

      check = true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return check;
  }

  public Driver getDriverById(int driverId) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT *FROM Driver WHERE driverID=?";
    Driver driver = new Driver();
    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driverId);
      ResultSet rs = preStmt.executeQuery();
      while (rs.next()) {
        String plateNumber = rs.getString("plateNumber");
        driver.setId(driverId);
        driver.setPlateNumber(plateNumber);
      }
      preStmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return driver;
  }

  public Driver getDriverByPlateNumber(String plateNumber) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT *FROM Driver WHERE plateNumber=?";
    Driver driver = new Driver();
    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setString(1, plateNumber);
      ResultSet rs = preStmt.executeQuery();
      while (rs.next()) {
        String plate = plateNumber;
        int driverId = rs.getInt("driverID");
        driver.setId(driverId);
        driver.setPlateNumber(plate);
      }
      preStmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return driver;
  }

  public boolean isEmpty(String plateNumber) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT *FROM Driver WHERE plateNumber=?";
    Driver driver = new Driver();
    boolean check = false;
    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setString(1, plateNumber);
      ResultSet rs = preStmt.executeQuery();
      while (rs.next()) {
        String plate = plateNumber;
        int driverId = rs.getInt("driverID");
        driver.setId(driverId);
        driver.setPlateNumber(plate);

        check = true;
      }
      preStmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return check;
  }

  public List<Driver> listDrivers() {
    List<Driver> lsDrivers = new ArrayList<Driver>();
    Connection conn = database.getConnection();
    Statement stmt = null;
    String sql = "SELECT * FROM Driver";

    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        int id = rs.getInt("driverID");
        String plateNumber = rs.getString("plateNumber");

        Driver driver = new Driver();
        driver.setId(id);
        driver.setPlateNumber(plateNumber);

        lsDrivers.add(driver);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return lsDrivers;
  }
}
