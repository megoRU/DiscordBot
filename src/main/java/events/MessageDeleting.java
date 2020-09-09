package events;

import java.util.List;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDeleting extends ListenerAdapter {

  public final String DELETE_INDEXES = "add\\s+\\d+\\s+.+";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String[] message = event.getMessage().getContentRaw().split("\\s+", 2);
    String text = message[0];
    String index = message[1];
    int indexParseInt = Integer.parseInt(index);

    if (!text.equalsIgnoreCase("clear")) {
      //Todo error
      System.out.println("Ничего не делать");
    }
    if (text.equalsIgnoreCase("clear")) {
      try {
        List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
            .complete();
        event.getChannel().deleteMessages(messages).queue();
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
}
