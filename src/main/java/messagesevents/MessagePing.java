package messagesevents;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class MessagePing extends ListenerAdapter {

  public final String PING = "!ping";
  public final String PING_WITH_OUT = "ping";

  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentDisplay().toLowerCase().trim();
    String prefix = PING;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "ping";
    }

    if (message.equals(prefix) || message.equals(PING_WITH_OUT)) {
      MessageChannel channels = event.getChannel();
      long time = System.currentTimeMillis();
      channels.sendMessage("Pong!") /* => RestAction<Message> */
          .queue(response /* => Message */ -> {
            response.editMessageFormat("API Response: %d ms", System.currentTimeMillis() - time).queue();
          });
    }
  }
}


