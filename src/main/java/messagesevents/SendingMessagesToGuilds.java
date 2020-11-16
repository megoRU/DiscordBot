package messagesevents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class SendingMessagesToGuilds extends ListenerAdapter {

  private static final ArrayList<String> listGuilds = new ArrayList<>();
  private static final ArrayList<String> listGuilds2 = new ArrayList<>();
  protected final String MAIN_GUILD_ID = "250700478520885248";
  protected final String MAIN_USER_ID = "250699265389625347";
  protected final String MSG = "!msg";

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().trim();
    String[] messages = message.split(" ", 2);
    String idGuild = event.getGuild().getId();
    String idUser = event.getAuthor().getId();

    if (messages[0].contains(MSG) && !idGuild.contains(MAIN_GUILD_ID) && !idUser.contains(MAIN_USER_ID)) {
      EmbedBuilder errorShutDown = new EmbedBuilder();
      errorShutDown.setColor(0xff3923);
      errorShutDown.setTitle("🔴 Error: Your guild has no access");
      errorShutDown.setDescription("Your guild does not have access to shutdown the bot on the linux server\n-> SendingMessagesToGuilds.java");
      event.getChannel().sendMessage(errorShutDown.build()).queue();
      errorShutDown.clear();
      return;
    }

    if (messages[0].contains(MSG) && idGuild.contains(MAIN_GUILD_ID) && !idUser.contains(MAIN_USER_ID)) {
      EmbedBuilder errorShutDown = new EmbedBuilder();
      errorShutDown.setColor(0xff3923);
      errorShutDown.setTitle("🔴 Error: This command is not available to you!");
      errorShutDown.setDescription("You are not <@250699265389625347>!\n-> SendingMessagesToGuilds.java");
      event.getChannel().sendMessage(errorShutDown.build()).queue();
      errorShutDown.clear();
      return;
    }

    if (messages[0].contains(MSG) && idGuild.contains(MAIN_GUILD_ID) && idUser.contains(MAIN_USER_ID)) {
      BotStart.jda.getGuilds().forEach(guild -> listGuilds.add(guild.getId()));
      HashSet<String> integerHashSet = new HashSet<>(listGuilds);
      listGuilds2.addAll(integerHashSet);
      for (int i = 0; i < listGuilds2.size(); i++) {
        Objects.requireNonNull(Objects.requireNonNull(
            BotStart.jda.getGuildById(listGuilds2.get(i)))
            .getDefaultChannel()).sendMessage(messages[1]).queue();
      }
    }
  }
}