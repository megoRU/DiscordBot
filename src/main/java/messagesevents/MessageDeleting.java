package messagesevents;

import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageDeleting extends ListenerAdapter {

  private static final String DELETE_INDEXES = "clear\\s+\\d+";
  private static final String DELETE_INDEXES2 = "clear\\s+\\d+";
  private static final String BOT_CHANNEL_LOGS = "botlog";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      event.getMember().getUser().openPrivateChannel()
          .flatMap(m -> event.getMember().getUser().openPrivateChannel())
          .flatMap(channel -> channel.sendMessage("Bot don\\`t have: `Permission.MESSAGE_WRITE` in this text channel!" + "\n"
              + "Inform the creator of this guild that you need to grant the bot this permission"))
          .queue();
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String prefix = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (prefixCheck.matches("[0-9A-Za-zА-Яа-я]")) {
      prefixCheck = "";
      messageWithOutPrefix = message;
    }

    if (!prefixCheck.equals(prefix) && !message.matches(DELETE_INDEXES2)) {
      return;
    }

    if (messageWithOutPrefix.matches(DELETE_INDEXES)) {
      if (!permCheck(event.getMessage().getMember())) {
        event.getMessage().addReaction("\u26D4").queue();
        EmbedBuilder errorClear = new EmbedBuilder();
        errorClear.setColor(0xff3923);
        errorClear.setTitle("🔴 Error: You are not Admin");
        errorClear
            .setDescription("You need Permission.ADMINISTRATOR." + "\n-> MessageDeleting.java");
        event.getChannel().sendMessage(errorClear.build()).queue();
        errorClear.clear();
        return;
      }

      if (permCheck(event.getMessage().getMember())) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
          event.getChannel().sendMessage("Bot don\\`t have: `Permission.MESSAGE_MANAGE`").queue();
          return;
        }
        String[] commandArray = message.split("\\s+", 2);
        String index = commandArray[1];
        int indexParseInt = Integer.parseInt(index);

        if (indexParseInt >= 2 && indexParseInt <= 100) {
          List<TextChannel> textChannels = event.getGuild()
              .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
          if (textChannels.size() >= 1) {
            deletingLog(event, index);
          }

          try {
            List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
                .complete();
            event.getChannel().deleteMessages(messages).queue();
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0x00FF00);
            error.setTitle(":white_check_mark: Removed: " + indexParseInt + " messages!");
            error.setDescription("This message will be deleted after 5 seconds");
            event.getChannel().sendMessage(error.build()).delay(5, TimeUnit.SECONDS)
                .flatMap(Message::delete).queue();
            error.clear();
          } catch (Exception e) {
            event.getMessage().addReaction("\u26D4").queue();
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xff3923);
            error.setTitle("🔴 Error");
            error.setDescription("Message Id provided was older than 2 weeks.");
            event.getChannel().sendMessage(error.build()).queue();
            error.clear();
          }
        }
        if (indexParseInt < 2 || indexParseInt > 100) {
          event.getMessage().addReaction("\u26D4").queue();
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Error");
          error.setDescription("Between 2-100 messages can be deleted at one time.");
          event.getChannel().sendMessage(error.build()).queue();
          error.clear();
        }
      }
    }
  }

  private void deletingLog(GuildMessageReceivedEvent event, String count) {
    EmbedBuilder delete = new EmbedBuilder();
    String userId = event.getAuthor().getId();
    String channelId = event.getChannel().getId();
    delete.setColor(0x00FF00);
    delete.setTitle(":mega: Someone deleted messages!");
    delete.addField("User", "<@" + userId + ">", false);
    delete.addField("Channel", "<#" + channelId + ">", false);
    delete.addField("Number of deleted messages", "Removed: `" + count + "` messages!", false);
    event.getGuild().getTextChannelsByName("botlog", false)
        .get(0).sendMessage(delete.build()).queue();
    delete.clear();
  }

  public boolean permCheck(Member member) {
    return member.hasPermission(Permission.ADMINISTRATOR);
  }
}