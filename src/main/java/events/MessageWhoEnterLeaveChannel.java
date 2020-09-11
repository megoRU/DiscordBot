package events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageWhoEnterLeaveChannel extends ListenerAdapter {

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    String nameChannelEnterUser = event.getChannelJoined().getName();
    String nameUserWhoEnter = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();

    if (!user.isBot()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("botchat", true).get(0);
      textChannel.sendMessage(
          "Эй @here!" + "\n" + "Пользователь: " + nameUserWhoEnter
              + " зашёл в канал: " + nameChannelEnterUser).queue();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    String nameChannelLeaveUser = event.getChannelLeft().getName();
    String nameUserWhoLeave = event.getMember().getUser().getName();
    User user = event.getMember().getUser();
    String channel = event.getMember().getUser().getId();

    if (!user.isBot()) {
      TextChannel textChannel = event.getGuild().getTextChannelsByName("botchat", true).get(0);
      textChannel.sendMessage(
          "Эй @here!" + "\n" + "Пользователь: " + nameUserWhoLeave
              + " вышел из канала: " + nameChannelLeaveUser).queue();
    }
  }
}