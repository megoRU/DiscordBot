package events;

import java.util.List;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDeleting extends ListenerAdapter {

  public final String DELETE_INDEXES = "clear\\s+\\d+";
  //public final String ALL_NUMBERS = "^[0-9]+$";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    if (message.matches(DELETE_INDEXES)) {
      String[] commandArray = message.split("\\s+", 2);
      String index = commandArray[1];
      int indexParseInt = Integer.parseInt(index);
      try {
        if (indexParseInt == 1) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName("botchat", true).get(0);
          textChannel.sendMessage("Error: index can`t be 1").queue();
        }
        if (indexParseInt > 1) {
          List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
              .complete();
          event.getChannel().deleteMessages(messages).queue();
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
}