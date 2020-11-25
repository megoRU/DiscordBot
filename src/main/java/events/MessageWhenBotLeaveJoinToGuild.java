package events;

import db.DataBase;
import java.sql.SQLException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class MessageWhenBotLeaveJoinToGuild extends ListenerAdapter {

  //bot join msg
  @Override
  public void onGuildJoin(GuildJoinEvent event) {
    String idGuildJoin = event.getGuild().getId();
    String nameGuild = event.getGuild().getName();
    String regionGuild = event.getGuild().getRegion().getName();

    event.getGuild().getDefaultChannel()
        .sendMessage("Thanks for adding " + "**mego**" + " to " + event.getGuild().getName() + "!"
            + "\nUse **!help/help/info** for a list of commands."
            + "\nCreate text channel with name: `botlog`"
            ).queue();
//+ "\nIf you need to give users a Role when they join, make a Role with this name: `DefaultRole`"
    EmbedBuilder botAddToGuild = new EmbedBuilder();
    botAddToGuild.setColor(0xa224db);
    botAddToGuild.setTitle("Guild added a bot");
    botAddToGuild.setDescription(
            "Guild id: " + idGuildJoin
            + "\nGuild name: " + nameGuild
            + "\nGuild region: " + regionGuild);
    BotStart.jda.getGuildById("779317239722672128").getTextChannelById("779321076424376350").sendMessage(botAddToGuild.build()).queue();
    botAddToGuild.clear();

    try {
      DataBase dataBase = new DataBase();
      dataBase.Guild(idGuildJoin);
      dataBase.createTableVoiceWhenBotJoinGuild(idGuildJoin);
      dataBase.createDefaultUserInVoice("1", "1111111111111", idGuildJoin);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }
}