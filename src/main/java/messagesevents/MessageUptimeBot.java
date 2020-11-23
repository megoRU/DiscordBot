package messagesevents;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import time.UptimeBot;

public class MessageUptimeBot extends ListenerAdapter {

  public final String UPTIME = "!uptime";
  public final String UPTIME_WITH_OUT = "uptime";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    User user = event.getMember().getUser();

    if (message.matches(UPTIME) || message.matches(UPTIME_WITH_OUT) && !user.isBot()) {
      TextChannel textChannel = event.getChannel();
      UptimeBot ub = new UptimeBot();
      String[] messageFromHandle = ub.uptimeBot().split(" ");

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
