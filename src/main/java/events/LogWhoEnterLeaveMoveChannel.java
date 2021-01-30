package events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LogWhoEnterLeaveMoveChannel extends ListenerAdapter {

  //bottestchannel //botchat //botlog
  private static final String BOT_CHANNEL_LOGS = "botlog";

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    if (!event.getMember().getUser().isBot()) {
      String nameChannelEnterUser = event.getChannelJoined().getName();
      String nameUserWhoEnter = event.getMember().getUser().getName();
      try {
        List<TextChannel> textChannels = event.getGuild()
            .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
        if (textChannels.size() >= 1) {
          User user = event.getMember().getUser();
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          if (!user.isBot() && textChannel != null) {
            textChannel.sendMessage(
                "User: **" + nameUserWhoEnter
                    + "** join the channel: " + nameChannelEnterUser).queue();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    if (!event.getMember().getUser().isBot()) {
      String nameChannelMoveUser = event.getChannelJoined().getName();
      String nameUserWhoMove = event.getMember().getUser().getName();
      try {
        List<TextChannel> textChannels = event.getGuild()
            .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
        if (textChannels.size() >= 1) {
          User user = event.getMember().getUser();
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          if (!user.isBot() && textChannel != null) {
            textChannel.sendMessage(
                "User: **" + nameUserWhoMove
                    + "** moved to channel: " + nameChannelMoveUser).queue();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    if (!event.getMember().getUser().isBot()) {
      String nameChannelLeaveUser = event.getChannelLeft().getName();
      String nameUserWhoLeave = event.getMember().getUser().getName();
      try {
        User user = event.getMember().getUser();
        List<TextChannel> textChannels = event.getGuild()
            .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
        if (textChannels.size() >= 1) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          if (!user.isBot() && textChannel != null) {
            textChannel.sendMessage(
                "User: **" + nameUserWhoLeave
                    + "** left the channel: " + nameChannelLeaveUser).queue();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}