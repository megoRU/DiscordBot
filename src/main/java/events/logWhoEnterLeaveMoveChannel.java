package events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class logWhoEnterLeaveMoveChannel extends ListenerAdapter {

  //bottestchannel //botchat //botlog
  private final String botChannelLogs = "botlog";

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    if (!user.isBot()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true).get(0);
      textChannel.sendMessage(
          "Пользователь: **" + nameUserWhoEnter
              + "** зашёл в канал: " + nameChannelEnterUser).queue();
    }
  }

  @Override
  public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
    String nameChannelMoveUser = event.getChannelLeft().getName();
    String nameUserWhoMove = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    if (!user.isBot()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Пользователь: **" + nameUserWhoMove
              + "** переместился в канал: " + nameChannelMoveUser).queue();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    User user = event.getMember().getUser();

    if (!user.isBot()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName(botChannelLogs, true)
          .get(0);
      textChannel.sendMessage(
          "Пользователь: **" + nameUserWhoLeave
              + "** вышел из канала: " + nameChannelLeaveUser).queue();
    }
  }
}
