package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessagePoll extends ListenerAdapter {

  private static final String POLL = "poll\\s.+";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentDisplay().trim();
    String prefix = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (!prefixCheck.equals(prefix)) {
      return;
    }

    if (messageWithOutPrefix.matches(POLL)) {
      String[] messages = message.split(" ", 2);
      EmbedBuilder emb = new EmbedBuilder();
      emb.setColor(
          event.getGuild().getMemberById(event.getMessage().getAuthor().getIdLong()).getColor());
      emb.setFooter("Poll by: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());
      emb.setTitle("**" + messages[1] + "**");
      event.getChannel().sendMessage(emb.build()).queue(m -> {
        m.addReaction("\ud83d\udc4d").queue();
        m.addReaction("\uD83D\uDC4E").queue();
        m.addReaction("\uD83E\uDD37").queue();
      });
    }
  }
}
