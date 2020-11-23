package messagesevents;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessagePing extends ListenerAdapter {

  public final String PING = "!ping";
  public final String PING_WITH_OUT = "ping";

  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentDisplay().toLowerCase().trim();
    if (message.matches(PING) || message.matches(PING_WITH_OUT)) {
      MessageChannel channels = event.getChannel();
      long time = System.currentTimeMillis();
      channels.sendMessage("Pong!") /* => RestAction<Message> */
          .queue(response /* => Message */ -> {
            response.editMessageFormat("API Response: %d ms", System.currentTimeMillis() - time).queue();
          });
    }
  }
}


