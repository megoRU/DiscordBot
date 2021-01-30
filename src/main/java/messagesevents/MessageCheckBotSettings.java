package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;
import java.util.List;

public class MessageCheckBotSettings extends ListenerAdapter {

  private static final String BOT_CHANNEL_LOGS = "botlog";
  private static final String CHECK = "?check";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentDisplay().trim();
    boolean botPermission = event.getGuild().getSelfMember()
        .hasPermission(Permission.ADMINISTRATOR);

    String prefix = "!";

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals(CHECK)) {
      try {
        List<TextChannel> textChannels = event.getGuild()
            .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
        if (textChannels.size() >= 1) {
          TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
              .get(0);
          if (textChannel != null && botPermission) {
            EmbedBuilder settings = new EmbedBuilder();
            settings.setColor(0x00FF00);
            settings.setTitle("Bot settings");
            settings.setDescription("Bot Administrator. :white_check_mark:\n" +
                "Guild have `botlog` text chat. :white_check_mark:\n" +
                "Bot prefix: `" + prefix + "`");
            event.getChannel().sendMessage(settings.build()).queue();
            settings.clear();
            return;
          }

          if (textChannel != null && !botPermission) {
            EmbedBuilder settings = new EmbedBuilder();
            settings.setColor(0x00FF00);
            settings.setTitle("Bot settings");
            settings.setDescription("Bot Administrator. :no_entry_sign:\n" +
                "Guild have `botlog` text chat. :white_check_mark:\n" +
                "Bot prefix: `" + prefix + "`");
            event.getChannel().sendMessage(settings.build()).queue();
            settings.clear();
            return;
          }
        }

        if (textChannels.size() == 0 && botPermission) {
          EmbedBuilder settings = new EmbedBuilder();
          settings.setColor(0x00FF00);
          settings.setTitle("Bot settings");
          settings.setDescription("Bot Administrator. :white_check_mark:\n" +
              "Guild have `botlog` text chat. :no_entry_sign:\n" +
              "Bot prefix: `" + prefix + "`");
          event.getChannel().sendMessage(settings.build()).queue();
          settings.clear();
        } else {
          EmbedBuilder settings = new EmbedBuilder();
          settings.setColor(0x00FF00);
          settings.setTitle("Bot settings");
          settings.setDescription("Bot Administrator. :no_entry_sign:\n" +
              "Guild have `botlog` text chat. :no_entry_sign:\n" +
              "Bot prefix: `" + prefix + "`");
          event.getChannel().sendMessage(settings.build()).queue();
          settings.clear();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
