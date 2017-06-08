package fcu.selab.taxibar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fcu.selab.taxibar.data.Rank;

public class RankDbManager {

  private IDatabase database = new MySqlDatabase();

  private static RankDbManager DB_MANAGER = new RankDbManager();

  public static RankDbManager getInstance() {
    return DB_MANAGER;
  }

  private RankDbManager() {

  }

  private DriverDbManager driverDbManager = DriverDbManager.getInstance();

  public boolean updateRank(Rank rank) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "UPDATE Rank SET score=? WHERE driverID=?";
    boolean check = false;

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setFloat(1, rank.getScore());
      preStmt.setInt(2, rank.getDriverId());
      preStmt.executeUpdate();
      preStmt.close();

      check = true;
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

  public boolean addRank(Rank rank) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "INSERT INTO Rank (driverID, score) VALUES(?, ?)";
    boolean check = false;

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, rank.getDriverId());
      preStmt.setFloat(2, rank.getScore());
      preStmt.executeUpdate();
      preStmt.close();

      check = true;
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

  public boolean isEmpty(int driverId) {
    boolean check = false;

    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT *FROM Rank WHERE driverID=?";
    Rank rank = new Rank();

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driverId);
      ResultSet rs = preStmt.executeQuery();
      if (rs.next()) {
        int score = rs.getInt("score");

        rank.setDriverId(driverId);
        rank.setScore(score);

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

  public Rank getRankByDriverId(int driverId) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT *FROM Rank WHERE driverID=?";
    Rank rank = new Rank();

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driverId);
      ResultSet rs = preStmt.executeQuery();
      if (rs.next()) {
        int score = rs.getInt("score");

        rank.setDriverId(driverId);
        rank.setScore(score);
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
    return rank;
  }

  public Rank getRankByPlateNumber(String plateNumber) {

    int driverId = driverDbManager.getDriverByPlateNumber(plateNumber).getId();

    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT * FROM Rank WHERE driverID=?";
    Rank rank = new Rank();

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driverId);
      ResultSet rs = preStmt.executeQuery();
      if (rs.next()) {
        int score = rs.getInt("score");

        rank.setDriverId(driverId);
        rank.setScore(score);
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
    return rank;
  }

  public List<Rank> listRanks() {
    List<Rank> lsRanks = new ArrayList<Rank>();
    Connection conn = database.getConnection();
    Statement stmt = null;
    String sql = "SELECT * FROM Rank";

    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        int driverId = rs.getInt("driverID");
        int score = rs.getInt("score");

        Rank rank = new Rank();
        rank.setDriverId(driverId);
        rank.setScore(score);

        lsRanks.add(rank);
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
    return lsRanks;
  }
}
