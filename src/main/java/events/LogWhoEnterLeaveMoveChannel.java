package events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class LogWhoEnterLeaveMoveChannel extends ListenerAdapter {

  //bottestchannel //botchat //botlog
  private static String botChannelLogs = "botlog";
  private final ArrayList<String> channelBotlog = new ArrayList<>();
  private static boolean isChannelBotLog = false;

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    event.getGuild().getTextChannels().forEach(textChannel -> channelBotlog.add(textChannel.getName()));

    for (String listLoop : channelBotlog) {
      if (listLoop.contains(botChannelLogs)) {
        isChannelBotLog = true;
        break;
      }
    }

    if (!user.isBot() && isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "User: **" + nameUserWhoEnter
              + "** entered the channel: " + nameChannelEnterUser).queue();
    }

    if (!user.isBot() && !isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getDefaultChannel();
      textChannel.sendMessage("You have not created a channel with this name: `botlog`").queue();
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    String nameChannelMoveUser = event.getChannelLeft().getName();
    String nameUserWhoMove = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    for (String listLoop : channelBotlog) {
      if (listLoop.contains(botChannelLogs)) {
        isChannelBotLog = true;
        break;
      }
    }

    if (!user.isBot() && isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "User: **" + nameUserWhoMove
              + "** moved to channel: " + nameChannelMoveUser).queue();
    }

    if (!user.isBot() && !isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getDefaultChannel();
      textChannel.sendMessage("You have not created a channel with this name: `botlog`").queue();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    for (String listLoop : channelBotlog) {
      if (listLoop.contains(botChannelLogs)) {
        isChannelBotLog = true;
        break;
      }
    }

    if (!user.isBot() && isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "User: **" + nameUserWhoLeave
              + "** left the channel: " + nameChannelLeaveUser).queue();
    }

    if (!user.isBot() && !isChannelBotLog) {
      TextChannel textChannel = event.getGuild().getDefaultChannel();
      textChannel.sendMessage("You have not created a channel with this name: `botlog`").queue();
    }
  }
}
