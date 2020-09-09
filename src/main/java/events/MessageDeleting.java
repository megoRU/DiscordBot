package events;

import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDeleting extends ListenerAdapter {

  public final String DELETE_INDEXES = "clear\\s+\\d+";
  public final String DELETE_INDEXES2 = "move\\s+\\d+";
  //public final String ALL_NUMBERS = "^[0-9]+$";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    if (message.matches(DELETE_INDEXES2)) {
      event.getGuild().moveVoiceMember(event.getMember(),
          event.getGuild().getVoiceChannels().get(1))
          .queue();
      event.getGuild().moveVoiceMember(event.getMember(),
          event.getGuild().getVoiceChannels().get(0))
          .queue();
    }

    if (message.matches(DELETE_INDEXES)) {
      String[] commandArray = message.split("\\s+", 2);
      String index = commandArray[1];
      int indexParseInt = Integer.parseInt(index);
      if (indexParseInt == 1) {
        EmbedBuilder error = new EmbedBuilder();
        error.setColor(0xff3923);
        error.setTitle("🔴 Index cannot be 0-1");
        error.setDescription("Between 0-1 index can be deleted.");
        event.getChannel().sendMessage(error.build()).queue();
      }
      try {
        if (indexParseInt > 1) {
          List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
              .complete();
          event.getChannel().deleteMessages(messages).queue();
        }
      } catch (IllegalArgumentException e) {
        if (e.toString().startsWith(
            "java.lang.IllegalArgumentException: Message retrieval")) {
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Too many messages selected");
          error.setDescription("Between 1-100 messages can be deleted at one time.");
          event.getChannel().sendMessage(error.build()).queue();
        } else {
          // Messages too old
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Selected messages are older than 2 weeks");
          error.setDescription("Messages older than 2 weeks cannot be deleted.");
          event.getChannel().sendMessage(error.build()).queue();
        }
      }
    }
  }


}