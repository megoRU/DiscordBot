package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotShutDown extends ListenerAdapter {

  public final String SHUTDOWN = "!shutdown";
  public final String SHUTDOWN_WITH_OUT = "shutdown";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    boolean boolPermissionAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);

    if (message.matches(SHUTDOWN) || message.matches(SHUTDOWN_WITH_OUT) & !boolPermissionAdmin) {
      EmbedBuilder errorShutDown = new EmbedBuilder();
      errorShutDown.setColor(0xff3923);
      errorShutDown.setTitle("🔴 Error: You are not Admin");
      errorShutDown.setDescription("You need Permission.ADMINISTRATOR"
          + "\n-> BotShutDown.java");
      event.getChannel().sendMessage(errorShutDown.build()).queue();
    }

    if (message.matches(SHUTDOWN) || message.matches(SHUTDOWN_WITH_OUT) & boolPermissionAdmin) {
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0x00FF00);
      info.setTitle(":white_check_mark: Бот выключен!");
      info.setDescription("Process finished with exit code -1");
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
      event.getJDA().shutdown();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.exit(-1);
    }
  }
}
