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
  public String userIdMeshiva = "310364711587676161";

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();
    event.getVoiceState().inVoiceChannel();
    event.getMember().getVoiceState();
    ArrayList<String> listUsersInChannels = new ArrayList<>();
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

    if (!user.isBot() && isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("bottestchannel", true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
    }

    if (!user.isBot() && !isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("bottestchannel", true)
          .get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();
    ArrayList<String> listUsersInChannels = new ArrayList<>();
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

    if (!user.isBot() && isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("bottestchannel", true).get(0);
      textChannel.sendMessage(
          "Эй <@310364711587676161>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
    }
    if (!user.isBot() && !isInChannel()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("bottestchannel", true).get(0);
      textChannel.sendMessage(
          "Эй <@250699265389625347> и <@335466800793911298>!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
    }
  }

  public boolean isInChannel() {
    return inChannel;
  }

  public void setInChannel(boolean inChannel) {
    this.inChannel = inChannel;
  }
}