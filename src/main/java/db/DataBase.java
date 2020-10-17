package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

  // CREATE TABLE `Discord` (`userLongId` bigint(30) NOT NULL,`countConn` bigint(30) NOT NULL,PRIMARY KEY (`userLongId`),UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  private static final String CONN = "jdbc:mysql://95.181.157.159:3306/DiscordBot?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
  private static final String USER = "DiscordBot";
  private static final String TABLE = "Discord";
  private static final String PASS = "";
  private final Connection conn = DriverManager.getConnection(CONN, USER, PASS);

  //userLongId | countConn
  public DataBase() throws SQLException {
  }

  public void setCount(String userLongId) throws SQLException {
    String query = "update " + TABLE + " SET countConn = ? WHERE userLongId = ?";
    PreparedStatement preparedStmt = conn.prepareStatement(query);
    preparedStmt.setInt(1, countConn(userLongId) + 1);
    preparedStmt.setLong(2, Long.parseLong(userLongId));
    preparedStmt.executeUpdate();
  }

  public int countConn(String userLongId) {
    try {
      Statement statement = conn.createStatement();
      ResultSet resultSet = statement
          .executeQuery("SELECT countConn FROM " + TABLE + " WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        return resultSet.getInt(1);
      }
      //   conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public long getUserId(String userLongId) {
    try {
      Statement statement = conn.createStatement();
      ResultSet resultSet = statement
          .executeQuery("SELECT userLongId FROM " + TABLE + " WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        String user = String.valueOf(resultSet.getLong(1));
        return Long.parseLong(user);
      }
    } catch (SQLException | NumberFormatException exception) {
      exception.printStackTrace();
    }
    return Long.parseLong("0");
  }

  public void createUser(String userLongId) throws SQLException {
    String query = "INSERT INTO " + TABLE + "(userLongId, countConn) values (" + userLongId + ", 0)";
    PreparedStatement preparedStatement = conn.prepareStatement(query);
    preparedStatement.executeUpdate();
  }

  public void closeCon() throws SQLException {
    conn.close();
  }
}

