package messagesevents;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class MessagePing extends ListenerAdapter {

  private static final String PING = "!ping";
  private static final String PING_WITH_OUT = "ping";

  @Override
  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String prefix = PING;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "ping";
    }

    if (message.equals(prefix) || message.equals(PING_WITH_OUT)) {
      MessageChannel channels = event.getChannel();
      long time = System.currentTimeMillis();
      channels.sendMessage("Pong!") /* => RestAction<Message> */
          .queue(response /* => Message */ -> {
            response.editMessageFormat("API Response: %d ms", System.currentTimeMillis() - time)
                .queue();
          });
    }
  }
}


