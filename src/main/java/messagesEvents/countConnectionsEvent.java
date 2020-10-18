package messagesEvents;

import db.DataBase;
import java.sql.SQLException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class countConnectionsEvent extends ListenerAdapter {

  public final String COUNT = "!колличество";
  public final String COUNT2 = "колличество";
  public final String COUNT3 = "колво";

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String idUser = event.getMember().getUser().getId();
    String idUserName = event.getMember().getUser().getName();
    try {
      DataBase dataBase = new DataBase();
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