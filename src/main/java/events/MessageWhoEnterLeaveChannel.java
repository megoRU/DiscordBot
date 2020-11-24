package events;

import db.DataBase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST //250699265389625347 - mego
  private static final String USER_ID_MESHIVA = "310364711587676161";
  //private static final String userIdMego = "250699265389625347";
  private static final String MAIN_GUILD_ID = "250700478520885248";
  //bottestchannel //botchat
  private static final String BOT_CHANNEL_LOGS = "botchat";
  private final ArrayList<String> listUsersInChannelsForMeshiva = new ArrayList<>();

  //TODO: Сделать ООП
  private Boolean whoLastEnter(String idUser, String idGuild) throws SQLException {
    DataBase dataBase = new DataBase();
    Set<String> whoLast = dataBase.whoLastEnter(idGuild);
    ArrayList<String> dataFrom = new ArrayList<>(whoLast);
    dataBase.deleteListWhoLast();
    if (dataFrom.isEmpty()) {
      return false;
    }
    whoLast.clear();
    String id = dataFrom.get(0);
    return id.equals(idUser);
  }

  //TODO: Исправить баг когда бывают случаи, что он не меняет битрейт
  @Override
   public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    try {
      String idEnterUser = event.getMember().getId();
      String nameEnterUser = event.getMember().getUser().getName();
      String idGuild = event.getGuild().getId();
      DataBase dataBase = new DataBase();
      String userFromBD = String.valueOf(dataBase.getUserId(idEnterUser, idGuild));
      boolean lastWhoEnter = whoLastEnter(idEnterUser, idGuild);

      if (!userFromBD.equals(idEnterUser)) {
        dataBase.createTableForGuild(idEnterUser, nameEnterUser, idGuild);
        dataBase.setCount(idEnterUser, idGuild);
      }
      if (userFromBD.equals(idEnterUser) && !lastWhoEnter) {
        dataBase.setWhoLastEnter(1, idGuild, idEnterUser);
        dataBase.setCount(idEnterUser, idGuild);
      }

      String nameChannelEnterUser = event.getChannelJoined().getName();
      String nameUserWhoEnter = event.getMember().getUser().getName();
      User user = event.getMember().getUser();

      event.getGuild().getVoiceChannels()
          .forEach(e -> e.getMembers()
              .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

      for (String listLoop : listUsersInChannelsForMeshiva) {
        if (listLoop.equals(USER_ID_MESHIVA)) {
          inChannelMeshiva = true;
          break;
        }
        inChannelMeshiva = false;
      }

      if (idGuild.equals(MAIN_GUILD_ID)) {
        if (!user.isBot() && isInChannelMeshiva() && !idEnterUser.equals(USER_ID_MESHIVA)) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          textChannel.sendMessage(
              "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                  + "** зашёл в канал: " + nameChannelEnterUser).queue();
          deleteList();
          return;
        }

        if (!user.isBot() && isInChannelMeshiva() && idEnterUser.equals(USER_ID_MESHIVA)) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          textChannel.sendMessage(
              "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                  + "** зашёл в канал: " + nameChannelEnterUser).queue();
          deleteList();
          return;
        }

        if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.equals("250699265389625347")) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          textChannel.sendMessage(
              "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                  + "** зашёл в канал: " + nameChannelEnterUser).queue();
          deleteList();
          return;
        }

        if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.equals("335466800793911298")) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          textChannel.sendMessage(
              "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
                  + "** зашёл в канал: " + nameChannelEnterUser).queue();
          deleteList();
          return;
        }

        if (!user.isBot() && !isInChannelMeshiva()) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          textChannel.sendMessage(
              "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **"
                  + nameUserWhoEnter
                  + "** зашёл в канал: " + nameChannelEnterUser).queue();
          deleteList();
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String idLeaveUser = event.getMember().getId();
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    String idGuild = event.getGuild().getId();
    User user = event.getMember().getUser();
    event.getGuild().getVoiceChannels()
        .forEach(e -> e.getMembers()
            .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

    for (String listLoop : listUsersInChannelsForMeshiva) {
      if (listLoop.contains(USER_ID_MESHIVA)) {
        inChannelMeshiva = true;
        break;
      }
      inChannelMeshiva = false;
    }

    if (idGuild.equals(MAIN_GUILD_ID)) {
      if (!user.isBot() && idLeaveUser.equals(USER_ID_MESHIVA)) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        textChannel.sendMessage(
            "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                + "** вышел из канала: " + nameChannelLeaveUser).queue();
        deleteList();
        return;
      }

      if (!user.isBot() && isInChannelMeshiva()) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        textChannel.sendMessage(
            "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                + "** вышел из канала: " + nameChannelLeaveUser).queue();
        deleteList();
        return;
      }

      if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.equals("250699265389625347")) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        textChannel.sendMessage(
            "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                + "** вышел из канала: " + nameChannelLeaveUser).queue();
        deleteList();
        return;
      }

      if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.equals("335466800793911298")) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        textChannel.sendMessage(
            "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
                + "** вышел из канала: " + nameChannelLeaveUser).queue();
        deleteList();
        return;
      }

      if (!user.isBot() && !isInChannelMeshiva()) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        textChannel.sendMessage(
            "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **"
                + nameUserWhoLeave
                + "** вышел в канал: " + nameChannelLeaveUser).queue();
        deleteList();
      }
    }
  }

  private boolean isInChannelMeshiva() {
    return inChannelMeshiva;
  }

  private void deleteList() {
    listUsersInChannelsForMeshiva.clear();
  }
}