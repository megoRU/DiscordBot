package db;

import config.Config;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBase {

    //CREATE TABLE `Discord` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    //CREATE TABLE IF NOT EXISTS VOICE_772388035944906793 (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    private static volatile Connection connection;
    private static volatile DataBase instance;
    private final Map<Integer, String> topThree = new HashMap<>();
    private final Map<String, String> whoLast = new HashMap<>();
    private Integer count = -1;

    private DataBase() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DataBase.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(
                            Config.getCONN(),
                            Config.getUSER(),
                            Config.getPASS());
                }
            }
        }
        return connection;
    }

    public static DataBase getInstance() {
        DataBase localInstance = instance;
        if (localInstance == null) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBase();
                }
            }
        }
        return localInstance;
    }

    public void setCount(String userLongId, String guildId) {
        try {
            String query = "UPDATE `GUILD_" + guildId + "` SET countConn = ? WHERE userLongId = ?";
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);
            preparedStmt.setInt(1, getCountConn(userLongId, guildId) + 1);
            preparedStmt.setLong(2, Long.parseLong(userLongId));
            preparedStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertIdMessagesWithPollEmoji(String messageId) {
        try {
            String query = "INSERT IGNORE INTO `idMessagesWithPollEmoji` (idMessagesWithPollEmoji) values (?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, messageId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> topThree(String guildId) {
        try {
            String query = "SELECT userName, countConn FROM `GUILD_" + guildId
                    + "` Group By countConn Order By countConn DESC LIMIT 3";
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
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
            ResultSet resultSet = getConnection()
                    .createStatement().executeQuery(
                            "SELECT countConn FROM `GUILD_" + guildId + "` WHERE userLongId = " + userLongId);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getUserId(String userLongId, String guildId) {
        try {
            ResultSet resultSet = getConnection()
                    .createStatement().executeQuery(
                            "SELECT userLongId FROM `GUILD_" + guildId + "` WHERE userLongId = " + userLongId);
            if (resultSet.next()) {
                return String.valueOf(resultSet.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public void setWhoLastEnter(String guildLongId, String userLongId) {
        try {
            String query = "UPDATE `VOICE_" + guildLongId + "` SET userLongId = ? WHERE id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, userLongId);
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getWhoLastEnter(String guildLongId) {
        try {
            String query = "SELECT userLongId FROM `VOICE_" + guildLongId + "`";
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                String userLongId = resultSet.getString("userLongId");
                whoLast.put(guildLongId, userLongId);
            }
            return whoLast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTableGuildWhenBotJoinGuild(String guildLongId) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS `GUILD_" + guildLongId
                    + "` (`userLongId` bigint(30) NOT NULL, `userName` varchar(255) NOT NULL, `countConn` bigint(30) NOT NULL, PRIMARY KEY (`userLongId`), UNIQUE KEY `userLongId` (`userLongId`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTableVoiceWhenBotJoinGuild(String guildLongId) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS `VOICE_" + guildLongId
                    + "` (`id` bigint(5) NOT NULL, `userLongId` bigint(30) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDefaultUserInVoice(String id, String userLongId, String guildLongId) {
        try {
            String query = "INSERT IGNORE INTO `VOICE_" + guildLongId + "` (id, userLongId) values (?, ?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, userLongId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDefaultUserInGuild(String userLongId, String userName, String guildLongId) {
        try {
            String query = "INSERT IGNORE INTO `GUILD_" + guildLongId
                    + "` (userLongId, userName, countConn) values (?, ?, ?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
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
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
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
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeCon() {
        try {
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

