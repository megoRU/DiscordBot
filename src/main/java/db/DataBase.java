package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataBase {

  //CREATE TABLE `Discord` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  //CREATE TABLE IF NOT EXISTS VOICE_772388035944906793 (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  private static final String CONN = "jdbc:mysql://95.181.157.159:3306/DiscordBot?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
  private static final String USER = "DiscordBot";
  private static final String PASS = "";
  private final Connection conn = DriverManager.getConnection(CONN, USER, PASS);
  private final Statement statement = conn.createStatement();
  private final ArrayList<String> data = new ArrayList<>();
  private final ArrayList<String> data2 = new ArrayList<>();

  //userLongId | countConn
  public DataBase() throws SQLException {
  }

  public void setCount(String userLongId, String guildId) throws SQLException {
    try {
      String query = "UPDATE GUILD_" + guildId + " SET countConn = ? WHERE userLongId = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, countConn(userLongId, guildId) + 1);
      preparedStmt.setLong(2, Long.parseLong(userLongId));
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public void createTableWhenBotJoinGuild(String guildIdLong) throws SQLException {
    try {
      String query = "CREATE TABLE IF NOT EXISTS `GUILD_" + guildIdLong + "` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public Set<String> topThree(String guildId) {
    try {
      String query = "SELECT userName, countConn FROM GUILD_" + guildId + " Group By countConn Order By countConn DESC LIMIT 3";
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        String name = resultSet.getString("userName");
        String countCon = resultSet.getString("countConn");
        data.add(name + " " + countCon);
      }
      return new LinkedHashSet<>(data);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public int countConn(String userLongId, String guildId) throws SQLException {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT countConn FROM GUILD_" + guildId + " WHERE userLongId = " + userLongId);
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

  public long getUserId(String userLongId, String guildId) throws SQLException {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT userLongId FROM GUILD_" + guildId + " WHERE userLongId = " + userLongId);
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

  public void setWhoLastEnter(int id, String voiceGuild, String userLongId) throws SQLException {
    try {
      String query = "UPDATE VOICE_" + voiceGuild + " SET userLongId = ? WHERE id = ?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setInt(2, id);
      preparedStatement.setString(1, userLongId);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public Set<String> whoLastEnter(String voiceGuild) throws SQLException {
    try {
      String query = "SELECT userLongId FROM VOICE_" + voiceGuild;
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        String userLongId = resultSet.getString("userLongId");
        data2.add(userLongId);
      }
      return new LinkedHashSet<>(data2);
    } finally {
      conn.isClosed();
    }
  }

  public void createTableVoiceWhenBotJoinGuild(String guildIdLong) throws SQLException {
    try {
      String query = "CREATE TABLE IF NOT EXISTS VOICE_" + guildIdLong + " (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public void createTableForVoice(String id, String userLongId, String guildId) throws SQLException {
    try {
      String query = "INSERT INTO VOICE_" + guildId + " (id, userLongId) values (?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setInt(1, Integer.parseInt(id));
      preparedStatement.setString(2, userLongId);
      preparedStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.isClosed();
    }
  }

  public void createTableForGuild(String userLongId, String userName, String guildId) throws SQLException {
    try {
      String query = "INSERT INTO VOICE_" + guildId + " (userLongId, userName, countConn) values (?, ?, ?)";
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

  public void deleteList() {
    for (int i = 0; i < data.size(); i++) {
      data.remove(i);
    }
  }
}

