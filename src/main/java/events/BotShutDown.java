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
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error: You not Admin");
      error.setDescription("You need Permission.ADMINISTRATOR");
      event.getChannel().sendMessage(error.build()).queue();
    }

    if (message.matches(SHUTDOWN) || message.matches(SHUTDOWN_WITH_OUT) & boolPermissionAdmin) {
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xf45642);
      info.setTitle("Бот выключен!");
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
      event.getJDA().shutdown();
    }
  }
}
