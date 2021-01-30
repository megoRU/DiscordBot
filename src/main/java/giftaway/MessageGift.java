package giftaway;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageGift extends ListenerAdapter {

  private static final String GIFT = "!gift";
  private static final String GIFT_START = "!gift start";
  private static final String GIFT_STOP = "!gift stop";
  private static final String GIFT_STOP_COUNT = "gift stop\\s[0-9]+";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    try {
      String message = event.getMessage().getContentRaw().trim().toLowerCase();
      if (message.equals("")) {
        return;
      }

      String[] messageSplit = message.split(" ");
      int length = message.length();
      String messageWithOutPrefix = message.substring(1, length);

      String prefix = GIFT;
      String prefix2 = GIFT_START;
      String prefix3 = GIFT_STOP;

      if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
        prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift";
        prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift start";
        prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "gift stop";
      }

      if ((message.equals(prefix)
          || message.equals(prefix2)
          || message.equals(prefix3)
          || messageWithOutPrefix.matches(GIFT_STOP_COUNT))) {

        if (message.equals(prefix)) {
          long guild = event.getGuild().getIdLong();
          Gift gift;
          gift = new Gift();

          if (!gift.hasGift(guild)) {
            return;
          }

          //Исключает повторных
          if (gift.hasGift(guild)) {
            gift = gift.getGift(event.getGuild().getIdLong());
            if (gift.getListUsersHash(event.getAuthor().getId()) == null) {
              gift.addUserToPoll(event.getMember().getUser(), event.getGuild(), prefix, prefix3,
                  event.getChannel());
              return;
            }
          }
          return;
        }

        if ((message.equals(prefix2)
            || message.equals(prefix3)
            || messageWithOutPrefix.matches(GIFT_STOP_COUNT))) {
          long guild = event.getGuild().getIdLong();
          Gift gift;
          gift = new Gift();

          if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage("You are not Admin").queue();
            return;
          }

          if (message.equals(prefix2) && !gift.hasGift(guild)) {
            gift.setGift(guild, new Gift(event.getGuild()));
            gift.startGift(event.getGuild(), event.getChannel(), prefix, prefix3);

          }

          if ((message.equals(prefix3)
              || messageWithOutPrefix.matches(GIFT_STOP_COUNT))
              && gift.hasGift(guild)) {
            gift = gift.getGift(event.getGuild().getIdLong());

            if (messageSplit.length == 3) {
              gift.stopGift(event.getGuild(), event.getChannel(),
                  Integer.parseInt(messageSplit[messageSplit.length - 1]));
              return;
            }
            gift.stopGift(event.getGuild(), event.getChannel(), Integer.parseInt("1"));
          }
        }
      }
    } catch (Exception e) {
      BotStart.jda.getGuildById("779317239722672128")
          .getTextChannelById("805025970544967701")
          .sendMessage("Ошибка: в классе MessageGift\n" +
              "Из-за сообщения: " + event.getMessage().getContentDisplay()).queue();
    }
  }
}