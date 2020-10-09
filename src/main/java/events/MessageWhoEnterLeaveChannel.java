package events;

import java.util.ArrayList;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

  public boolean inChannel;
  //310364711587676161 - Meshiva //753218484455997491 - megoTEST
  public String userIdMeshiva = "310364711587676161";
  //bottestchannel //botchat
  public String channelNameForBot = "botchat";
  public ArrayList<String> listUsersInChannels = new ArrayList<>();


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
            .forEach(f -> listUsersInChannels.add(f.getUser().getId())));

    System.out.println(isInChannel());
    for (String listlop : listUsersInChannels) {
      if (listlop.contains(userIdMeshiva)) {
        setInChannel(true);
        break;
      }
      if (!listlop.contains(userIdMeshiva)) {
        setInChannel(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }
    System.out.println(isInChannel());
    //&& !idEnterUser.matches("310364711587676161")
    if (!user.isBot() && isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel() && idEnterUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel() && idEnterUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel()) {
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
        .forEach(f -> listUsersInChannels.add(f.getUser().getId())));

    System.out.println(isInChannel());
    for (String listlop : listUsersInChannels) {
      if (listlop.contains(userIdMeshiva)) {
        setInChannel(true);
        break;
      }
      if (!listlop.contains(userIdMeshiva)) {
        setInChannel(false);
      }
      //System.out.println(listlop.matches("753218484455997491") + " " + listlop);
    }
    System.out.println(isInChannel());
    // && !idLeaveUser.matches("310364711587676161")
    if (!user.isBot() && isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel() && idLeaveUser.matches("250699265389625347")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel() && idLeaveUser.matches("335466800793911298")) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true).get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }

    if (!user.isBot() && !isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(channelNameForBot, true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел в канал: " + nameChannelLeaveUser).queue();
      deleteList();
      return;
    }
  }

  public boolean isInChannel() {
    return inChannel;
  }

  public void setInChannel(boolean inChannel) {
    this.inChannel = inChannel;
  }

  public void deleteList() {
    for (int i = 0; i < listUsersInChannels.size(); i++) {
      listUsersInChannels.remove(i);
    }
  }

  public int sizeList() {
    return listUsersInChannels.size();
  }
}