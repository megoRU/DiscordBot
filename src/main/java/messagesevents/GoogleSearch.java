package messagesevents;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GoogleSearch extends ListenerAdapter {

  public String URL = "https://g.zeos.in/?q=";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String[] messages = message.split(" ");

    if(messages[0].matches("!ищи")) {
      String messageForReplase = message.replace(" ", "%20");
      int length = messageForReplase.length();
      event.getChannel().sendMessage(URL + messageForReplase.substring(4, length)).queue();
    }
    if(messages[0].matches("ищи")) {
      String messageForReplase = message.replace(" ", "%20");
      int length = messageForReplase.length();
      event.getChannel().sendMessage(URL + messageForReplase.substring(3, length)).queue();
    }
  }
}
