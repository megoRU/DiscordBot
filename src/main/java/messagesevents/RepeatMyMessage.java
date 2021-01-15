package messagesevents;

import java.util.List;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class RepeatMyMessage extends ListenerAdapter {

  public final String REPEAT = "!repeat";
  public final String REPEAT2 = "repeat";
  public final String REPEAT_RU = "повтори";
  public final String REPEAT_RU2 = "!повтори";
  public final String N = "\n";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] split = message.split(" ", 2);
    String prefix = REPEAT;
    String prefix2 = REPEAT_RU2;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "repeat";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "повтори";
    }

    if ((split[0].equals(prefix)
        || split[0].equals(REPEAT2)
        || split[0].equals(REPEAT_RU)
        || split[0].equals(prefix2))
        && message.contains(N)) {
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
