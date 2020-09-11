package events;

import java.util.List;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberLeaveJoin extends ListenerAdapter {

  public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
    final List<TextChannel> dontDoThis = event.getGuild()
        .getTextChannelsByName("bottestchannel", true);

    if (dontDoThis.isEmpty()) {
      return;
    }

    final TextChannel pleaseDontDoThisAtAll = dontDoThis.get(0);
    final String useGualSpec = String
        .format("Welcome %s to %s", event.getMember().getUser().getAsTag(),
            event.getGuild().getName());

    pleaseDontDoThisAtAll.sendMessage(useGualSpec).queue();
  }

  public void onGuildMemberLeaveEvent(@Nonnull GuildMemberLeaveEvent event) {
    final List<TextChannel> dontDoThis = event.getGuild()
        .getTextChannelsByName("bottestchannel", true);

    if (dontDoThis.isEmpty()) {
      return;
    }

    final TextChannel pleaseDontDoThisAtAll = dontDoThis.get(0);
    final String useGualSpec = String
        .format("leave %s to %s", event.getMember().getUser().getAsTag(),
            event.getGuild().getName());

    pleaseDontDoThisAtAll.sendMessage(useGualSpec).queue();
  }
}