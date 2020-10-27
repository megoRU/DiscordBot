package messagesevents;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDeleting extends ListenerAdapter {

  public final String DELETE_INDEXES = "clear\\s+\\d+";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    boolean boolPermissionAdmin = Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR);
    if (message.matches(DELETE_INDEXES) & !boolPermissionAdmin) {
      event.getMessage().addReaction("\u26D4").queue();
      EmbedBuilder errorClear = new EmbedBuilder();
      errorClear.setColor(0xff3923);
      errorClear.setTitle("🔴 Error: You are not Admin");
      errorClear.setDescription("You need Permission.ADMINISTRATOR." + "\n-> MessageDeleting.java");
      event.getChannel().sendMessage(errorClear.build()).queue();
      errorClear.clear();
    }
    try {
      if (message.matches(DELETE_INDEXES) & boolPermissionAdmin) {
        String[] commandArray = message.split("\\s+", 2);
        String index = commandArray[1];
        int indexParseInt = Integer.parseInt(index);

        if (indexParseInt >= 2 && indexParseInt <= 100) {
          event.getMessage().addReaction("\u2705").queue();
          List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
              .complete();
          event.getChannel().deleteMessages(messages).queue();

          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0x00FF00);
          error.setTitle(":white_check_mark: Удалено: " + indexParseInt + " сообщений!");
          error.setDescription("Это сообщение удалится через 5 секунд");
          event.getChannel().sendMessage(error.build()).delay(5, TimeUnit.SECONDS)
              .flatMap(Message::delete).submit();
          error.clear();
        }
        if (indexParseInt < 2 || indexParseInt > 100) {
          event.getMessage().addReaction("\u26D4").queue();
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Error");
          error.setDescription("Between 2-100 messages can be deleted at one time.");
          event.getChannel().sendMessage(error.build()).queue();
          error.clear();
        }
      }
    } catch (Exception e) {
      event.getMessage().addReaction("\u26D4").queue();
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error");
      error.setDescription("The error is not related to the index!");
      event.getChannel().sendMessage(error.build()).queue();
      error.clear();
    }
  }
}