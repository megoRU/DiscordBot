package messagesevents;

import java.util.List;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class RepeatMyMessage extends ListenerAdapter {

  public final String REPEAT = "!repeat";
  public final String REPEAT2 = "repeat";
  public final String REPEAT_RU = "повтори";
  public final String REPEAT_RU2 = "!повтори";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] split = message.split(" ", 2);

    if (split[0].matches(REPEAT)
        || split[0].matches(REPEAT2)
        || split[0].matches(REPEAT_RU)
        || split[0].matches(REPEAT_RU2)) {
      event.getChannel().sendMessage("✅").queue();

      try {
      Thread.sleep(250);
      List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();

      event.getChannel().deleteMessages(messages).queue();
      event.getChannel().sendMessage(split[1]).queue();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
  }
}
