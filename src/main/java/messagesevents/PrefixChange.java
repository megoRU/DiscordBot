package messagesevents;

import db.DataBase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class PrefixChange extends ListenerAdapter {

  private static final String PREFIX = "\\*prefix\\s.";
  private static final String PREFIX_RESET = "*prefix reset";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] messages = message.split(" ", 2);

    if ((message.equals(PREFIX_RESET) || message.matches(PREFIX)) && !event.getMember()
        .hasPermission(Permission.MANAGE_SERVER)) {
      event.getChannel()
          .sendMessage("You cannot change the prefix. You must have permission: `MANAGE_SERVER`")
          .queue();
      return;
    }

    if (message.matches(PREFIX) && event.getMember().hasPermission(Permission.MANAGE_SERVER)
        && BotStart.mapPrefix.get(event.getMessage().getGuild().getId()) != null) {
      BotStart.mapPrefix.put(event.getGuild().getId(), messages[1]);

      DataBase.getInstance().removeDB(event.getGuild().getId());
      DataBase.getInstance().addDB(event.getGuild().getId(), messages[1]);

      event.getChannel().sendMessage("The prefix is now: `" + messages[1] + "`").queue();
      return;
    }

    if (message.matches(PREFIX) && event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
      BotStart.mapPrefix.put(event.getGuild().getId(), messages[1]);

      DataBase.getInstance().addDB(event.getGuild().getId(), messages[1]);

      event.getChannel().sendMessage("The prefix is now: `" + messages[1] + "`").queue();
      return;
    }

    if (message.equals(PREFIX_RESET) && event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
      BotStart.mapPrefix.remove(event.getGuild().getId());

      DataBase.getInstance().removeDB(event.getGuild().getId());

      event.getChannel().sendMessage("The prefix is now standard: `!`").queue();
    }
  }
}
