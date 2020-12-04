package db;

import config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataBase {

  //CREATE TABLE `Discord` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  //CREATE TABLE IF NOT EXISTS VOICE_772388035944906793 (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  private final Connection conn = DriverManager.getConnection(Config.getCONN(), Config.getUSER(), Config.getPASS());
  private final Statement statement = conn.createStatement();
  private final Map<Integer, String> topThree = new HashMap<>();
  private final Map<Integer, String> whoLast = new HashMap<>();
  private Integer count = -1;

  //userLongId | countConn
  public DataBase() throws SQLException {
  }

  public void setCount(String userLongId, String guildId) {
    try {
      String query = "UPDATE `GUILD_" + guildId + "` SET countConn = ? WHERE userLongId = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, getCountConn(userLongId, guildId) + 1);
      preparedStmt.setLong(2, Long.parseLong(userLongId));
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Map<Integer, String> topThree(String guildId) {
    try {
      String query = "SELECT userName, countConn FROM `GUILD_" + guildId + "` Group By countConn Order By countConn DESC LIMIT 3";
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        count++;
        String name = resultSet.getString("userName");
        String countCon = resultSet.getString("countConn");
        topThree.put(count, name + " " + countCon);
      }
      return topThree;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  public int getCountConn(String userLongId, String guildId) {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT countConn FROM `GUILD_" + guildId + "` WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        return resultSet.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public long getUserId(String userLongId, String guildId) {
    try {
      ResultSet resultSet = statement
          .executeQuery("SELECT userLongId FROM `GUILD_" + guildId + "` WHERE userLongId = " + userLongId);
      if (resultSet.next()) {
        String user = String.valueOf(resultSet.getLong(1));
        return Long.parseLong(user);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Long.parseLong("0");
  }

  public void setWhoLastEnter(int id, String voiceGuild, String userLongId) {
    try {
      String query = "UPDATE `VOICE_" + voiceGuild + "` SET userLongId = ? WHERE id = ?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setInt(2, id);
      preparedStatement.setString(1, userLongId);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Map<Integer, String> whoLastEnter(String voiceGuild) {
    try {
      String query = "SELECT userLongId FROM `VOICE_" + voiceGuild + "`";
      ResultSet resultSet = statement.executeQuery(query);
      while (resultSet.next()) {
        String userLongId = resultSet.getString("userLongId");
        whoLast.put(0, userLongId);
      }
      return whoLast;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void createTableGuildWhenBotJoinGuild(String guildIdLong) {
    try {
      String query = "CREATE TABLE IF NOT EXISTS `GUILD_" + guildIdLong + "` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createTableVoiceWhenBotJoinGuild(String guildIdLong) {
    try {
      String query = "CREATE TABLE IF NOT EXISTS `VOICE_" + guildIdLong + "` (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createDefaultUserInVoice(String id, String userLongId, String guildId) {
    try {
      String query = "INSERT IGNORE INTO `VOICE_" + guildId + "` (id, userLongId) values (?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setInt(1, Integer.parseInt(id));
      preparedStatement.setString(2, userLongId);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createDefaultUserInGuild(String userLongId, String userName, String guildId) {
    try {
      String query = "INSERT IGNORE INTO `GUILD_" + guildId + "` (userLongId, userName, countConn) values (?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setString(1, userLongId);
      preparedStatement.setString(2, userName);
      preparedStatement.setString(3, "0");
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Добавление префекса
  public void addDB(String serverId, String prefix) {
    try {
      String sql = "INSERT INTO prefixs (serverId, prefix) VALUES (?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, serverId);
      preparedStatement.setString(2, prefix);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //Удаление префекса
  public void removeDB(String serverId) {
    try {
      String sql = "DELETE FROM prefixs WHERE serverId='" + serverId + "'";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeCon() {
    try {
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

