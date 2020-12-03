package messagesevents;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class GoogleSearch extends ListenerAdapter {

  public String URL = "https://g.zeos.in/?q=";
  public String SEARCH = "!ищи";
  public String SEARCH2 = "ищи";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String[] messages = message.split(" ");
    String prefix = SEARCH;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "ищи";
    }

    if (messages[0].equals(prefix)) {
      String messageForReplase = message.replace(" ", "%20");
      int length = messageForReplase.length();
      event.getChannel().sendMessage(URL + messageForReplase.substring(4, length)).queue();
    }
    if (messages[0].equals(SEARCH2)) {
      String messageForReplase = message.replace(" ", "%20");
      int length = messageForReplase.length();
      event.getChannel().sendMessage(URL + messageForReplase.substring(3, length)).queue();
    }
  }
}
