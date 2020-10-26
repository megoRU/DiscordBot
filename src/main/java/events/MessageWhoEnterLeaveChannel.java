package events;

import db.DataBase;
import java.sql.SQLException;
import java.util.ArrayList;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST //250699265389625347 - mego
  private final String userIdMeshiva = "310364711587676161";
  private final String userIdMego = "250699265389625347";
  //bottestchannel //botchat
  private final String botChannelLogs = "botchat";
  private final ArrayList<String> listUsersInChannelsForMeshiva = new ArrayList<>();
  private final ArrayList<String> whoLastEnter = new ArrayList<>();

  public Boolean whoLastEnter(@NotNull GuildVoiceJoinEvent event) {
    if (whoLastEnter.size() > 0) {
      String user = whoLastEnter.get(0);
      String idEnterUser = event.getMember().getId();
      return idEnterUser.contains(user);
    }
    return false;
  }

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    try {
    String idEnterUser = event.getMember().getId();
    String nameEnterUser = event.getMember().getUser().getName();
    DataBase dataBase = new DataBase();
    String userFromBD = String.valueOf(dataBase.getUserId(idEnterUser));
    boolean lastWhoEnter = whoLastEnter(event);

    //TODO: Нужно тестировать!
    if (!userFromBD.contains(idEnterUser)) {
      dataBase.createUser(idEnterUser, nameEnterUser);
      dataBase.setCount(idEnterUser);
    }
    if (userFromBD.contains(idEnterUser) && !lastWhoEnter) {
        whoLastEnter.add(0, idEnterUser);
        dataBase.setCount(idEnterUser);
    }

    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    event.getVoiceState().inVoiceChannel();
    event.getMember().getVoiceState();
    event.getGuild().getVoiceChannels()
        .forEach(e -> e.getMembers()
        .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

    for (String listLoop : listUsersInChannelsForMeshiva) {
      if (listLoop.contains(userIdMeshiva)) {
        setInChannelMeshiva(true);
        break;
      }
      if (!listLoop.contains(userIdMeshiva)) {
        setInChannelMeshiva(false);
      }
      //System.out.println(listLoop.matches("753218484455997491") + " " + listLoop);
    }

    if (!user.isBot() && isInChannelMeshiva() && !idEnterUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && isInChannelMeshiva() && idEnterUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **"
              + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
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
    User user = event.getMember().getUser();
    event.getGuild().getVoiceChannels()
        .forEach(e -> e.getMembers()
        .forEach(f -> listUsersInChannelsForMeshiva.add(f.getUser().getId())));

    for (String listLoop : listUsersInChannelsForMeshiva) {
      if (listLoop.contains(userIdMeshiva)) {
        setInChannelMeshiva(true);
        break;
      }
      if (!listLoop.contains(userIdMeshiva)) {
        setInChannelMeshiva(false);
      }
      //System.out.println(listLoop.matches("753218484455997491") + " " + listLoop);
    }

    if (!user.isBot() && idLeaveUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
              + "** вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
              + "** вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
              + "** вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
              + "** вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: **" + nameUserWhoLeave
              + "** вышел в канал: " + nameChannelLeaveUser).queue();
      deleteList();
    }
  }

  public boolean isInChannelMeshiva() {
    return inChannelMeshiva;
  }

  public void setInChannelMeshiva(boolean inChannelMeshiva) {
    this.inChannelMeshiva = inChannelMeshiva;
  }

  public void deleteList() {
    for (int i = 0; i < listUsersInChannelsForMeshiva.size(); i++) {
      listUsersInChannelsForMeshiva.remove(i);
    }
    for (int i = 0; i < whoLastEnter.size(); i++) {
      whoLastEnter.remove(i);
    }
  }

  public int sizeList() {
    return listUsersInChannelsForMeshiva.size();
  }
}