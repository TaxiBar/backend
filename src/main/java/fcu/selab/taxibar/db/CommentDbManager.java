package fcu.selab.taxibar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import fcu.selab.taxibar.data.Comment;

public class CommentDbManager {

  private IDatabase database = new MySqlDatabase();

  private static CommentDbManager DB_MANAGER = new CommentDbManager();

  public static CommentDbManager

      getInstance() {
    return DB_MANAGER;
  }

  private CommentDbManager() {

  }

  public boolean addComment(Comment comment) {
    Connection conn = database.getConnection();
    PreparedStatement preStmt = null;
    String sql = "INSERT INTO Comment(userID, driverID, title, comment, score, commentTime)  VALUES(?, ?, ?, ?, ?, ?)";
    boolean check = false;

    try {
      TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
      SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date current = new Date();

      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, comment.getUserId());
      preStmt.setInt(2, comment.getDriverId());
      preStmt.setString(3, comment.getTitle());
      preStmt.setString(4, comment.getComment());
      preStmt.setFloat(5, comment.getScore());
      preStmt.setString(6, sdFormat.format(current));
      preStmt.executeUpdate();
      preStmt.close();

      check = true;
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
    return check;
  }

  public List<Comment> getCommentByDriverId(int driverId) {
    List<Comment> lsComments = new ArrayList<Comment>();
    Connection conn = database.getConnection();
    String sql = "SELECT * FROM Comment WHERE driverID=?";
    PreparedStatement preStmt = null;

    try {
      preStmt = conn.prepareStatement(sql);
      preStmt.setInt(1, driverId);
      ResultSet rs = preStmt.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        int userId = rs.getInt("userID");
        String title = rs.getString("title");
        String content = rs.getString("comment");
        float score = rs.getFloat("score");
        String commentTime = rs.getString("commentTime");

        Comment comment = new Comment();
        comment.setId(id);
        comment.setUserId(userId);
        comment.setDriverId(driverId);
        comment.setTitle(title);
        comment.setComment(content);
        comment.setScore(score);
        comment.setCommentTime(commentTime);

        lsComments.add(comment);
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
    return lsComments;
  }

  public List<Comment> listComments() {
    List<Comment> lsComments = new ArrayList<Comment>();

    Connection conn = database.getConnection();
    String sql = "SELECT * FROM Comment";
    Statement stmt = null;

    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int id = rs.getInt("id");
        int userId = rs.getInt("userID");
        int driverId = rs.getInt("driverID");
        String title = rs.getString("title");
        String content = rs.getString("comment");
        float score = rs.getFloat("score");
        String commentTime = rs.getString("commentTime");

        Comment comment = new Comment();
        comment.setId(id);
        comment.setUserId(userId);
        comment.setDriverId(driverId);
        comment.setTitle(title);
        comment.setComment(content);
        comment.setScore(score);
        comment.setCommentTime(commentTime);

        lsComments.add(comment);
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
    return lsComments;
  }
}
