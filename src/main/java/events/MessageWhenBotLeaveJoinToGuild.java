package events;

import java.util.Objects;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageWhenBotLeaveJoinToGuild extends ListenerAdapter {

  //bot join msg
  @Override
  public void onGuildJoin(GuildJoinEvent event) {
    Objects.requireNonNull(event.getGuild().getDefaultChannel())
        .sendMessage("Thanks for adding " + "**mego BOT**" + " to " + event.getGuild().getName()
            + "!\nUse **help** for a list of commands.").queue();
  }
}