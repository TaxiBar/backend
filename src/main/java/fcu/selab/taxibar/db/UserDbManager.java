package fcu.selab.taxibar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fcu.selab.taxibar.data.User;

public class UserDbManager {

  private static UserDbManager DB_MANAGER = new UserDbManager();

  public static UserDbManager getInstance() {
    return DB_MANAGER;
  }

  private IDatabase database = new MySqlDatabase();

  private UserDbManager() {

  }

  public boolean addUser(User user) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "INSERT INTO User(userName, passWord) VALUES(?, ?)";
    boolean check = false;

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setString(1, user.getUserName());
      preStmt.setString(2, user.getPassword());

      preStmt.executeUpdate();
      preStmt.close();

      check = true;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
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

  public boolean validateUser(String userName, String password) {
    Connection conn = database.getConnection();
    PreparedStatement stmt = null;
    String query = "SELECT * FROM User WHERE userName=? and passWord=?";
    try {
      stmt = conn.prepareStatement(query);
      stmt.setString(1, userName);
      stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery();
      boolean valid = rs.first();
      stmt.close();
      conn.commit();
      return valid;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public User getUserById(int id) {
    User user = new User();
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "SELECT * FROM User WHERE userID=?";

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, id);
      ResultSet rs = preStmt.executeQuery();
      if (rs.next()) {
        String userName = rs.getString("userName");

        user = new User();
        user.setId(id);
        user.setUserName(userName);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        preStmt.close();
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  public List<User> listUsers() {
    List<User> lsUsers = new ArrayList<User>();
    Connection conn = database.getConnection();
    Statement stmt = null;
    String sql = "SELECT * FROM User";

    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int id = rs.getInt("userID");
        String userName = rs.getString("userName");

        User user = new User();
        user.setId(id);
        user.setUserName(userName);

        lsUsers.add(user);
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
    return lsUsers;
  }
}
