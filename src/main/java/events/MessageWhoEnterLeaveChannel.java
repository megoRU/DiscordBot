package events;

import java.util.ArrayList;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

  private boolean inChannelMeshiva;
  private boolean inChannelMego;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST
  private String userIdMeshiva = "310364711587676161";
  private String userIdMego = "250699265389625347";
  //bottestchannel //botchat
  private String channelNameForBot = "botchat";
  private ArrayList<String> listUsersInChannelsForMeshive = new ArrayList<>();
  private ArrayList<String> listUsersInChannelsForMego = new ArrayList<>();

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String idEnterUser = event.getMember().getId();
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();
    event.getVoiceState().inVoiceChannel();
    event.getMember().getVoiceState();
    event.getGuild().getVoiceChannels().stream()
        .forEach(e -> e.getMembers().stream()
        .forEach(f -> listUsersInChannelsForMeshive.add(f.getUser().getId())));
    event.getGuild().getVoiceChannels().stream()
        .forEach(e -> e.getMembers().stream()
            .forEach(f -> listUsersInChannelsForMego.add(f.getUser().getId())));

    for (String listlop : listUsersInChannelsForMeshive) {
      if (listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(true);
        break;
      }
      if (!listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }

    for (String listlop : listUsersInChannelsForMego) {
      if (listlop.contains(userIdMego)) {
        setInChannelMego(true);
        break;
      }
      if (!listlop.contains(userIdMego)) {
        setInChannelMego(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }
    if (!user.isBot() && isInChannelMeshiva() && !idEnterUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && isInChannelMeshiva() && idEnterUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idEnterUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: "
              + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String idLeaveUser = event.getMember().getId();
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();
    event.getGuild().getVoiceChannels().stream()
        .forEach(e -> e.getMembers().stream()
            .forEach(f -> listUsersInChannelsForMeshive.add(f.getUser().getId())));
    event.getGuild().getVoiceChannels().stream()
        .forEach(e -> e.getMembers().stream()
            .forEach(f -> listUsersInChannelsForMego.add(f.getUser().getId())));

    for (String listlop : listUsersInChannelsForMeshive) {
      if (listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(true);
        break;
      }
      if (!listlop.contains(userIdMeshiva)) {
        setInChannelMeshiva(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }

    for (String listlop : listUsersInChannelsForMego) {
      if (listlop.contains(userIdMego)) {
        setInChannelMego(true);
        break;
      }
      if (!listlop.contains(userIdMego)) {
        setInChannelMego(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }
    if (!user.isBot() && idLeaveUser.matches(userIdMeshiva)) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva() && idLeaveUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannelMeshiva()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел в канал: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }
  }

  public boolean isInChannelMego() {
    return inChannelMego;
  }

  public void setInChannelMego(boolean inChannelMego) {
    this.inChannelMego = inChannelMego;
  }

  public boolean isInChannelMeshiva() {
    return inChannelMeshiva;
  }

  public void setInChannelMeshiva(boolean inChannelMeshiva) {
    this.inChannelMeshiva = inChannelMeshiva;
  }

  public void deleteList() {
    for (int i = 0; i < listUsersInChannelsForMeshive.size(); i++) {
      listUsersInChannelsForMeshive.remove(i);
    }
    for (int i = 0; i < listUsersInChannelsForMego.size(); i++) {
      listUsersInChannelsForMego.remove(i);
    }
  }

  public int sizeList() {
    return listUsersInChannelsForMeshive.size();
  }
}