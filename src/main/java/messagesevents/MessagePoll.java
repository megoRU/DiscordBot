package messagesevents;

import db.DataBase;
import java.sql.SQLException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessagePoll extends ListenerAdapter {

  private static final String POLL = "poll\\s.+";
  private static final String emojiYes = "\uD83D\uDC4D";
  private static final String emojiNo = "\uD83D\uDC4E";
  private static final String emojiIdk = "\uD83E\uDD37";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
      return;
    }

    String message = event.getMessage().getContentRaw().trim();
    String prefix = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (!prefixCheck.equals(prefix)) {
      return;
    }

    if (messageWithOutPrefix.matches(POLL)) {
      if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
        event.getChannel().sendMessage("Bot don\\`t have: `Permission.MESSAGE_MANAGE`").queue();
        return;
      }
      String[] messages = message.split(" ", 2);
      EmbedBuilder emb = new EmbedBuilder();
      emb.setColor(event.getMessage().getMember().getColor());
      emb.setFooter("Poll by: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());
      emb.setTitle("**" + messages[1] + "**");
      event.getChannel().sendMessage(emb.build()).queue(m -> {
        m.addReaction(emojiYes).queue();
        m.addReaction(emojiNo).queue();
        m.addReaction(emojiIdk).queue();
        BotStart.idMessagesWithPollEmoji.put(m.getId(), m.getId());
        try {
          DataBase dataBase = new DataBase();
          dataBase.insertIdMessagesWithPollEmoji(m.getId());
        } catch (SQLException troubles) {
          troubles.printStackTrace();
        }
      });
    }
  }

  @Override
  public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
    try {
      if (event.getUser().isBot()) {
        return;
      }
      if (!event.getReactionEmote().isEmoji()) {
        event.getReaction().removeReaction(event.getUser()).queue();
        return;
      }

      String emoji = event.getReactionEmote().getEmoji();

      if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
        event.getChannel().sendMessage("Bot don\\`t have: `Permission.MESSAGE_MANAGE`").queue();
        return;
      }

      if (!(emoji.equals(emojiYes) || emoji.equals(emojiNo) || emoji.equals(emojiIdk))
          && BotStart.idMessagesWithPollEmoji.get(event.getMessageId()) != null) {
        event.getReaction().removeReaction(event.getUser()).queue();
      }
    } catch (IllegalStateException e) {
      BotStart.jda.getGuildById("779317239722672128")
          .getTextChannelById("779321076424376350")
          .sendMessage(event.getReaction().toString() + "\n"
              + event.getReactionEmote().isEmoji()).queue();
    }
  }
}