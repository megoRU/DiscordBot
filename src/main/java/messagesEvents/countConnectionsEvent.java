package messagesEvents;

import db.DataBase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class countConnectionsEvent extends ListenerAdapter {

  public final String COUNT = "!колличество";
  public final String COUNT2 = "колличество";
  public final String COUNT3 = "колво";
  public final String COUNT4 = "колво топ";

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String idUser = event.getMember().getUser().getId();
    String idUserName = event.getMember().getUser().getName();
    try {
      DataBase dataBase = new DataBase();
      Set<String> topThreeUsers = dataBase.topThree();
      ArrayList<String> data = new ArrayList<>(topThreeUsers);

      //TODO Сделать в нормальном виде
      if (message.matches(COUNT4)) {
        String[] first = data.get(0).split(" ");
        String[] second = data.get(1).split(" ");
        String[] third = data.get(2).split(" ");

        EmbedBuilder info = new EmbedBuilder();
        info.setColor(0x00FF00);
        info.setDescription(":first_place: Первое место: " + first[0] + " | " + first[1] + GetEndingWord(Integer.parseInt(first[1]))
        + "\n :second_place: Второе место: " + second[0] + " | " + second[1] + GetEndingWord(Integer.parseInt(second[1]))
        + "\n :third_place: Третье место: " + third[0] + " | " + third[1] + GetEndingWord(Integer.parseInt(third[1])));
        event.getChannel().sendMessage(info.build()).queue();
        info.clear();
        return;
      }

      if (message.matches(COUNT) || message.matches(COUNT2) || message.matches(COUNT3)) {
        int value = dataBase.countConn(idUser);
        EmbedBuilder info = new EmbedBuilder();
        info.setColor(0x00FF00);
       // info.setTitle("У — " + idUserName);
        info.setDescription(idUserName + " подключался в канал: " + value + GetEndingWord(value));
        event.getChannel().sendMessage(info.build()).queue();
        info.clear();
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  private String GetEndingWord(int num) {
    int preLastDigit = num % 100 / 10;
    if (preLastDigit == 1) {
      return " раз!";
    }
    switch (num % 10) {
      case 2:
      case 3:
      case 4:
        return " раза!";
      default:
        return " раз!";
    }
  }
}