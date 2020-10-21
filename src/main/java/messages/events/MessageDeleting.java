package messages.events;

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
    String message = event.getMessage().getContentRaw().toLowerCase();
    boolean boolPermissionAdmin = Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR);

    if (message.matches(DELETE_INDEXES) & !boolPermissionAdmin) {
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
        if (indexParseInt == 1 || indexParseInt == 0) {
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Error: Index cannot be 0-1");
          error.setDescription("Between 0-1 index can be deleted.");
          event.getChannel().sendMessage(error.build()).queue();
          error.clear();
        }
        if (indexParseInt > 1 & indexParseInt <= 99) {
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
        if (indexParseInt < 1 || indexParseInt >= 100) {
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Error: Too many messages selected");
          error.setDescription("Between 1-100 messages can be deleted at one time.");
          event.getChannel().sendMessage(error.build()).queue();
          error.clear();
        }
      }
    } catch (Exception e) {
      // Messages too old
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error: Selected messages are older than 2 weeks");
      error.setDescription("Messages older than 2 weeks cannot be deleted.");
      event.getChannel().sendMessage(error.build()).queue();
      error.clear();
    }
  }
}