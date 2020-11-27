package messagesevents;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import time.UptimeBot;

public class MessageUptimeBot extends ListenerAdapter {

  public final String UPTIME = "!uptime";
  public final String UPTIME_WITH_OUT = "uptime";

  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();

    if (message.equals(UPTIME) || message.equals(UPTIME_WITH_OUT)) {
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
