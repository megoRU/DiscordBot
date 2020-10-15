package events;

import static time.UptimeBot.uptimeBot;

import java.util.Objects;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUptimeBot extends ListenerAdapter {

  public final String UPTIME = "!uptime";
  public final String UPTIME_WITH_OUT = "uptime";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    User user = Objects.requireNonNull(event.getMember()).getUser();

    if (message.matches(UPTIME) || message.matches(UPTIME_WITH_OUT) && !user.isBot()) {
      TextChannel textChannel = event.getChannel();
      String[] messageFromhandle = uptimeBot().split(" ");
      textChannel.sendMessage("Бот работает непрерывно: `"
          + messageFromhandle[0] + " ч., "
          + messageFromhandle[1] + " мин., "
          + messageFromhandle[2] + " сек.`").queue();
    }
  }
}
