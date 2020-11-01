package events;

import db.DataBase;
import java.sql.SQLException;
import java.util.Objects;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageWhenBotLeaveJoinToGuild extends ListenerAdapter {

  //bot join msg
  @Override
  public void onGuildJoin(GuildJoinEvent event) {
    String idGuild = event.getGuild().getId();
    Objects.requireNonNull(event.getGuild().getDefaultChannel())
        .sendMessage("Thanks for adding " + "**mego**" + " to " + event.getGuild().getName() + "!"
            + "\nUse **!help** for a list of commands."
            + "\nCreate text channel with name: `botlog`").queue();
    try {
      DataBase dataBase = new DataBase();
      dataBase.createTableWhenBotJoinGuild(idGuild);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}