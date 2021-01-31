package messagesevents;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class RepeatMyMessage extends ListenerAdapter {

  public final String REPEAT = "!repeat";
  public final String REPEAT2 = "repeat";
  public final String REPEAT_RU = "повтори";
  public final String REPEAT_RU2 = "!повтори";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      event.getMember().getUser().openPrivateChannel()
          .flatMap(m -> event.getMember().getUser().openPrivateChannel())
          .flatMap(channel -> channel.sendMessage("Bot don\\`t have: `Permission.MESSAGE_WRITE` in this text channel!" + "\n"
              + "Inform the creator of this guild that you need to grant the bot this permission"))
          .queue();
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] split = message.split(" ", 2);
    String prefix = REPEAT;
    String prefix2 = REPEAT_RU2;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "repeat";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "повтори";
    }

    if (split[0].equals(prefix) || split[0].equals(REPEAT2)
        || split[0].equals(REPEAT_RU) || split[0].equals(prefix2)) {
      if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
        event.getChannel().sendMessage("Bot don\\`t have: `Permission.MESSAGE_MANAGE`").queue();
        return;
      }
      event.getMessage().delete().queue();
      event.getChannel().sendMessage(split[1]).queue();
    }
  }
}