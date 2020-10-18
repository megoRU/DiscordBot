package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import messages_events.UsersObjectsForTop;

public class DataBase {

  //CREATE TABLE `Discord` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  private static final String CONN = "jdbc:mysql://95.181.157.159:3306/DiscordBot?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
  private static final String USER = "DiscordBot";
  private static final String TABLE = "Discord";
  private static final String PASS = "";
  private final Connection conn = DriverManager.getConnection(CONN, USER, PASS);
  private final Statement statement = conn.createStatement();

  //userLongId | countConn
  public DataBase() throws SQLException {
  }

  public void setCount(String userLongId) throws SQLException {
    try {
      String query = "UPDATE " + TABLE + " SET countConn = ? WHERE userLongId = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, countConn(userLongId) + 1);
      preparedStmt.setLong(2, Long.parseLong(userLongId));
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public Set<String> topThree() {
    try {
      String query = "SELECT userName, countConn FROM Discord Group By countConn Order By countConn DESC LIMIT 3";
      ResultSet resultSet = statement.executeQuery(query);
      ArrayList<String> data = new ArrayList<>();
      while (resultSet.next()) {
        String name = resultSet.getString("userName");
        String countCon = resultSet.getString("countConn");
        Map<String, UsersObjectsForTop> accounts = IntStream.range(0, 3).boxed().collect(
        Collectors.toMap(String::valueOf, i -> new UsersObjectsForTop(name, countCon)));
        accounts.values()
        .forEach(s -> data.add(s.getNameUser() + " " + s.getCountConnections()));
      }
      return new LinkedHashSet<>(data);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public int countConn(String userLongId) throws SQLException {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT countConn FROM " + TABLE + " WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        return resultSet.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
    return 0;
  }

  public long getUserId(String userLongId) throws SQLException {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT userLongId FROM " + TABLE + " WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        String user = String.valueOf(resultSet.getLong(1));
        return Long.parseLong(user);
      }
    } catch (SQLException | NumberFormatException exception) {
      exception.printStackTrace();
    } finally {
      conn.isClosed();
    }
    return Long.parseLong("0");
  }

  public void createUser(String userLongId, String userName) throws SQLException {
    try {
      String query = "INSERT INTO " + TABLE + "(userLongId, userName, countConn) values (?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setString(1, userLongId);
      preparedStatement.setString(2, userName);
      preparedStatement.setString(3, "0");
      preparedStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public void closeCon() throws SQLException {
    try {
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }
}

