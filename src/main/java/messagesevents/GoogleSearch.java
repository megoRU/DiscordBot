package messagesevents;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GoogleSearch extends ListenerAdapter {

  public String URL = "https://g.zeos.in/?q=";
  public String SEARCH = "!ищи";
  public String SEARCH2 = "ищи";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String[] messages = message.split(" ");

    if(messages[0].matches(SEARCH) || messages[0].matches(SEARCH2)) {
      String messageForReplace = message.replace(" ", "%20");
      int length = messageForReplace.length();
      event.getChannel().sendMessage(URL + messageForReplace.substring(3, length)).queue();
    }
  }
}
