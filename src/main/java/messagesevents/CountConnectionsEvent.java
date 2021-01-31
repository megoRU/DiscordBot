package messagesevents;

import db.DataBase;
import java.util.Map;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class CountConnectionsEvent extends ListenerAdapter {

  private static final String COUNT_RU = "!колличество";
  private static final String COUNT2_RU = "колличество";
  private static final String COUNT3 = "колво";
  private static final String COUNT4 = "колво топ";
  private static final String COUNT_TOP_THREE = "!top 3";
  private static final String COUNT_EN = "!amount";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String prefix = COUNT_RU;
    String prefix2 = COUNT_EN;
    String prefix3 = COUNT_TOP_THREE;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "колличество";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "amount";
      prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "top 3";
    }

    if (message.equals(prefix)
        || message.equals(COUNT2_RU)
        || message.equals(COUNT3)
        || message.equals(COUNT4)
        || message.equals(COUNT_TOP_THREE)
        || message.equals(prefix2)
        || message.equals(prefix3)) {
      String idUser = event.getMember().getUser().getId();
      String idUserName = event.getMember().getUser().getName();
      String idGuild = event.getGuild().getId();
      event.getChannel().sendTyping().queue();
      try {
        DataBase dataBase = new DataBase();
        Map<Integer, String> topThreeUsers = dataBase.topThree(idGuild);
        if (!message.equals(COUNT4) && !message.equals(COUNT_TOP_THREE)) {
          int value = dataBase.getCountConn(idUser, idGuild);
          EmbedBuilder info = new EmbedBuilder();
          info.setColor(0x00FF00);
          info.setDescription(
              idUserName + " connected to the channel: " + value + getEndingWord(value));
          event.getChannel().sendMessage(info.build()).queue();
          info.clear();
        }

        if ((message.equals(COUNT4) || message.equals(COUNT_TOP_THREE))
            && topThreeUsers.size() < 3) {
          event.getMessage().addReaction("\u26D4").queue();
          EmbedBuilder info = new EmbedBuilder();
          info.setColor(0x00FF00);
          info.setDescription("Not enough data!");
          event.getChannel().sendMessage(info.build()).queue();
          info.clear();
          return;
        }

        //TODO Сделать в нормальном виде //Сделать проверку на пустой список!
        if (message.equals(COUNT_TOP_THREE) || message.equals(COUNT4)) {
          String[] first = topThreeUsers.get(0).split(" ");
          String[] second = topThreeUsers.get(1).split(" ");
          String[] third = topThreeUsers.get(2).split(" ");
          EmbedBuilder info = new EmbedBuilder();
          info.setColor(0x00FF00);
          info.setTitle("Top 3 by connection");
          info.setDescription(
              ":first_place: First place: " + first[0] + " | " + first[1] + getEndingWord(
                  Integer.parseInt(first[1]))
                  + "\n :second_place: Second place: " + second[0] + " | " + second[1]
                  + getEndingWord(Integer.parseInt(second[1]))
                  + "\n :third_place: Third place: " + third[0] + " | " + third[1] + getEndingWord(
                  Integer.parseInt(third[1])));
          event.getChannel().sendMessage(info.build()).queue();
          info.clear();
        }
      } catch (Exception exception) {
        event.getMessage().addReaction("\u26D4").queue();
        exception.printStackTrace();
      }
    }
  }

  private String getEndingWord(int num) {
    int preLastDigit = num % 100 / 10;
    if (preLastDigit == 1) {
      return " time!";
    }
    switch (num % 10) {
      case 2:
      case 3:
      case 4:
        return " times!";
      default:
        return " time!";
    }
  }
}