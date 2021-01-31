package messagesevents;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;
import time.UptimeBot;

public class MessageUptimeBot extends ListenerAdapter {

  public final String UPTIME_WITH_OUT = "uptime";

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

    String prefix = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (prefixCheck.matches("[0-9A-Za-zА-Яа-я]")) {
      prefixCheck = "";
    }

    if (!prefixCheck.equals(prefix) && !message.equals(UPTIME_WITH_OUT)) {
      return;
    }

    if (messageWithOutPrefix.equals(UPTIME_WITH_OUT) || message.equals(UPTIME_WITH_OUT)) {
      TextChannel textChannel = event.getChannel();

      String[] messageFromHandle = UptimeBot.uptimeBot().split(" ");

      if (Integer.parseInt(messageFromHandle[0]) > 0) {
        textChannel.sendMessage("Bot duration: `"
            + messageFromHandle[0] + " d., "
            + messageFromHandle[1] + " h., "
            + messageFromHandle[2] + " min., "
            + messageFromHandle[3] + " sec.`").queue();
        return;
      }

      if (Integer.parseInt(messageFromHandle[1]) > 0) {
        textChannel.sendMessage("Bot duration: `"
            + messageFromHandle[1] + " h., "
            + messageFromHandle[2] + " min., "
            + messageFromHandle[3] + " sec.`").queue();
        return;
      }

      if (Integer.parseInt(messageFromHandle[2]) > 0) {
        textChannel.sendMessage("Bot duration: `"
            + messageFromHandle[2] + " min., "
            + messageFromHandle[3] + " sec.`").queue();
        return;
      }

      if (Integer.parseInt(messageFromHandle[3]) > 0) {
        textChannel.sendMessage("Bot duration: `"
            + messageFromHandle[3] + " sec.`").queue();
      }
    }
  }
}
