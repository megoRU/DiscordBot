package messagesevents;

import db.DataBase;
import java.util.ArrayList;
import java.util.Set;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CountConnectionsEvent extends ListenerAdapter {

  public final String COUNT_RU = "!колличество";
  public final String COUNT2_RU = "колличество";
  public final String COUNT3 = "колво";
  public final String COUNT4 = "колво топ";
  public final String COUNT_TOP_THREE = "top 3";
  public final String COUNT_EN = "!amount";

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();

    if (message.equals(COUNT_RU)
        || message.equals(COUNT2_RU)
        || message.equals(COUNT3)
        || message.equals(COUNT4)
        || message.equals(COUNT_TOP_THREE)
        || message.equals(COUNT_EN)) {
      String idUser = event.getMember().getUser().getId();
      String idUserName = event.getMember().getUser().getName();
      String idGuild = event.getGuild().getId();
      event.getChannel().sendTyping().queue();
      try {
        DataBase dataBase = new DataBase();
        Set<String> topThreeUsers = dataBase.topThree(idGuild);
        ArrayList<String> data = new ArrayList<>(topThreeUsers);
        if (!message.equals(COUNT4) && !message.equals(COUNT_TOP_THREE)) {
          int value = dataBase.countConn(idUser, idGuild);
          EmbedBuilder info = new EmbedBuilder();
          info.setColor(0x00FF00);
          info.setDescription(idUserName + " подключался в канал: " + value + getEndingWord(value));
          event.getChannel().sendMessage(info.build()).queue();
          info.clear();
        }

        if ((message.equals(COUNT4) || message.equals(COUNT_TOP_THREE)) && data.size() < 3) {
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
          String[] first = data.get(0).split(" ");
          String[] second = data.get(1).split(" ");
          String[] third = data.get(2).split(" ");
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
          dataBase.deleteList();
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