package events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class LogWhoEnterLeaveMoveChannel extends ListenerAdapter {

  //bottestchannel //botchat //botlog
  private static final String BOT_CHANNEL_LOGS = "botlog";

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    if (!event.getMember().getUser().isBot()) {
      if (!event.getGuild().getSelfMember().hasPermission(event.getGuild()
          .getTextChannelsByName(BOT_CHANNEL_LOGS, true).get(0), Permission.MESSAGE_WRITE)) {
        event.getMember().getUser().openPrivateChannel()
            .flatMap(m -> event.getMember().getUser().openPrivateChannel())
            .flatMap(channel -> channel.sendMessage("Bot don\\`t have: `Permission.MESSAGE_WRITE` in this text channel!" + "\n"
                + "Inform the creator of this guild that you need to grant the bot this permission"))
            .queue();
        return;
      }
      String nameChannelEnterUser = event.getChannelJoined().getName();
      String nameUserWhoEnter = event.getMember().getUser().getName();
      event.getGuild()
          .getTextChannels()
          .forEach(textChannel -> {
            if (textChannel.getName().equals(BOT_CHANNEL_LOGS)) {
              textChannel.sendMessage(
                  "User: **" + nameUserWhoEnter
                      + "** join the channel: " + nameChannelEnterUser).queue();
            }
          });
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    if (!event.getMember().getUser().isBot()) {
      if (!event.getGuild().getSelfMember().hasPermission(event.getGuild()
          .getTextChannelsByName(BOT_CHANNEL_LOGS, true).get(0), Permission.MESSAGE_WRITE)) {
        event.getMember().getUser().openPrivateChannel()
            .flatMap(m -> event.getMember().getUser().openPrivateChannel())
            .flatMap(channel -> channel.sendMessage("Bot don\\`t have: `Permission.MESSAGE_WRITE` in this text channel!" + "\n"
                + "Inform the creator of this guild that you need to grant the bot this permission"))
            .queue();
        return;
      }
      String nameChannelMoveUser = event.getChannelJoined().getName();
      String nameUserWhoMove = event.getMember().getUser().getName();

      event.getGuild()
          .getTextChannels()
          .forEach(textChannel -> {
            if (textChannel.getName().equals(BOT_CHANNEL_LOGS)) {
              textChannel.sendMessage(
                  "User: **" + nameUserWhoMove
                      + "** moved to channel: " + nameChannelMoveUser).queue();
            }
          });
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    if (!event.getMember().getUser().isBot()) {
      if (!event.getGuild().getSelfMember().hasPermission(event.getGuild()
          .getTextChannelsByName(BOT_CHANNEL_LOGS, true).get(0), Permission.MESSAGE_WRITE)) {
        event.getMember().getUser().openPrivateChannel()
            .flatMap(m -> event.getMember().getUser().openPrivateChannel())
            .flatMap(channel -> channel.sendMessage("Bot don\\`t have: `Permission.MESSAGE_WRITE` in this text channel!" + "\n"
                + "Inform the creator of this guild that you need to grant the bot this permission"))
            .queue();
        return;
      }
      String nameChannelLeaveUser = event.getChannelLeft().getName();
      String nameUserWhoLeave = event.getMember().getUser().getName();

      event.getGuild()
          .getTextChannels()
          .forEach(textChannel -> {
            if (textChannel.getName().equals(BOT_CHANNEL_LOGS)) {
              textChannel.sendMessage("User: **" + nameUserWhoLeave
                  + "** left the channel: " + nameChannelLeaveUser).queue();
            }
          });
    }
  }
}