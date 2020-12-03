package messagesevents;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class FlipCoin extends ListenerAdapter {

  private static final Random random = new Random();
  public final String FLIP_WITH = "!flip";
  public final String FLIP = "flip";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String prefix = FLIP_WITH;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + FLIP;
    }

    if (message.equals(prefix) || message.equals(FLIP)) {
      String avatarUrl = null;
      String avatarFromEvent = event.getMessage().getAuthor().getAvatarUrl();
      if (avatarFromEvent == null) {
        avatarUrl = "https://cdn.discordapp.com/avatars/754093698681274369/a6b1eb1cdcc29e0bd7e14228b17a28aa.png";
      }
      if (avatarFromEvent != null) {
        avatarUrl = avatarFromEvent;
      }
      boolean result = flipCoin();
      if (result) {
        EmbedBuilder flipCoin = new EmbedBuilder();
        flipCoin.setAuthor(event.getAuthor().getName(), null, avatarUrl);
        flipCoin.setColor(0x00FF00);
        flipCoin.setTitle("it's Heads");
        event.getChannel().sendMessage(flipCoin.build()).queue();
        flipCoin.clear();
      }
      if (!result) {
        EmbedBuilder flipCoin = new EmbedBuilder();
        flipCoin.setAuthor(event.getAuthor().getName(), null, avatarUrl);
        flipCoin.setColor(0x0BB2BA);
        flipCoin.setTitle("it's Tails");
        event.getChannel().sendMessage(flipCoin.build()).queue();
        flipCoin.clear();
      }
    }
  }

  public boolean flipCoin() {
    return random.nextBoolean();
  }
}
