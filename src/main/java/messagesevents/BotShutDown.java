package messagesevents;

import db.DataBase;
import java.sql.SQLException;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotShutDown extends ListenerAdapter {

  public final String SHUTDOWN = "!shutdown";
  public final String SHUTDOWN_WITH_OUT = "shutdown";
  public final String SD = "sd";
  public final String MAIN_GUILD_ID = "250700478520885248";
  public final String MAIN_USER_ID = "250699265389625347";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();

    if (message.matches(SHUTDOWN) || message.matches(SHUTDOWN_WITH_OUT) || message.matches(SD)) {
      boolean boolPermissionAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);
      String idGuild = event.getGuild().getId();
      String idUser = event.getAuthor().getId();
      if (!idGuild.contains(MAIN_GUILD_ID)) {
        EmbedBuilder errorShutDown = new EmbedBuilder();
        errorShutDown.setColor(0xff3923);
        errorShutDown.setTitle("🔴 Error: Your guild has no access");
        errorShutDown.setDescription("Your guild does not have access to shutdown the bot on the linux server\n-> BotShutDown.java");
        event.getChannel().sendMessage(errorShutDown.build()).queue();
        errorShutDown.clear();
        return;
      }

      if (!boolPermissionAdmin && !idUser.contains(MAIN_USER_ID) && (!idGuild.contains(MAIN_GUILD_ID) || idGuild.contains(MAIN_GUILD_ID))) {
        EmbedBuilder errorShutDown = new EmbedBuilder();
        errorShutDown.setColor(0xff3923);
        errorShutDown.setTitle("🔴 Error: This command is not available to you!");
        errorShutDown.setDescription("You are not <@250699265389625347>!\n-> BotShutDown.java");
        event.getChannel().sendMessage(errorShutDown.build()).queue();
        errorShutDown.clear();
        return;
      }

      if (idGuild.contains(MAIN_GUILD_ID) && boolPermissionAdmin && idUser.contains(MAIN_USER_ID)) {
        EmbedBuilder info = new EmbedBuilder();
        info.setColor(0x00FF00);
        info.setTitle(":white_check_mark: Бот выключен!");
        info.setDescription("Process finished with exit code -1");
        event.getChannel().sendMessage(info.build()).queue();
        info.clear();
        event.getJDA().shutdown();
        try {
          DataBase dataBase = new DataBase();
          dataBase.closeCon();
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        } catch (SQLException exception) {
          exception.printStackTrace();
        }
        System.exit(-1);
      }
    }
  }
}