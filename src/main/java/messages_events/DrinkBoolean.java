package messages_events;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DrinkBoolean extends ListenerAdapter {

  private static final Random random = new Random();
  public final String DRINK = "бухать";
  public final String DRINK2 = "бухнем";
  public final String DRINK3 = "бухаем";
  public final String DRINK4 = "бухаем\\?";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    if (message.matches(DRINK)
        || message.matches(DRINK2)
        || message.matches(DRINK3)
        || message.matches(DRINK4)) {
      boolean drink = drink();
      if (drink) {
        EmbedBuilder drinks = new EmbedBuilder();
        drinks.setColor(0x00FF00);
        drinks.setTitle(":white_check_mark: Сегодня мы бухаем!");
        event.getChannel().sendMessage(drinks.build()).queue();
        drinks.clear();
      }
      if (!drink) {
        EmbedBuilder drinks = new EmbedBuilder();
        drinks.setColor(0xff3923);
        drinks.setTitle(":x: Сегодня мы не бухаем!");
        event.getChannel().sendMessage(drinks.build()).queue();
        drinks.clear();
      }
    }
  }

  public boolean drink() {
    return random.nextBoolean();
  }
}
