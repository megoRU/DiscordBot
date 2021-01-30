package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;
import java.util.ArrayList;
import java.util.List;

public class MessageKick extends ListenerAdapter {

  private static final String KICK = "kick\\s.+\\s.+";
  private static final String KICK2 = "kick\\s.+";
  private static final String BOT_CHANNEL_LOGS = "botlog";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] messages = message.split(" ");
    String prefix = "!";
    String prefix2 = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (!prefixCheck.equals(prefix) || !prefixCheck.equals(prefix2)) {
      return;
    }

    if (messageWithOutPrefix.matches(KICK) || messageWithOutPrefix.matches(KICK2)) {
      if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
          event.getChannel().sendMessage("Bot don\\`t have: `Permission.KICK_MEMBERS`").queue();
          return;
        }
        if (messages.length == 3 || messages.length == 2) {
          List<Member> toKick = new ArrayList<>(1);
          toKick.addAll(event.getMessage().getMentionedMembers());

          if (toKick.size() >= 1 && messages.length == 3) {
            event.getGuild().kick(toKick.get(0)).reason(messages[2]).queue();
            EmbedBuilder kickRsn = new EmbedBuilder();
            kickRsn.setColor(3913034);
            kickRsn.setTitle("Kick success");
            kickRsn
                .setDescription("Kicked member: " + messages[1] + " with reason: " + (messages[2]));
            event.getChannel().sendMessage(kickRsn.build()).queue();
            kickRsn.clear();
            logKickToChat(event, messages[1], "reason: " + messages[2]);
            return;
          }

          if (toKick.size() >= 1 && messages.length == 2) {
            event.getGuild().kick(toKick.get(0)).queue();
            EmbedBuilder kickRsn = new EmbedBuilder();
            kickRsn.setColor(3913034);
            kickRsn.setTitle("Kick success");
            kickRsn.setDescription("Kicked member: " + messages[1]);
            event.getChannel().sendMessage(kickRsn.build()).queue();
            kickRsn.clear();
            logKickToChat(event, messages[1], "without a reason");
            return;
          } else {
            EmbedBuilder kickRsn = new EmbedBuilder();
            kickRsn.setColor(0xFF0000);
            kickRsn.setTitle("Error");
            kickRsn.setDescription("We didn't find a user");
            event.getChannel().sendMessage(kickRsn.build()).queue();
            kickRsn.clear();
          }
        }
      }

      if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
        EmbedBuilder kickRsn = new EmbedBuilder();
        kickRsn.setColor(0xFF0000);
        kickRsn.setTitle("Access denied");
        kickRsn.setDescription("You do not have permission to kick users");
        event.getChannel().sendMessage(kickRsn.build()).queue();
        kickRsn.clear();
      }
    }
  }

  private void logKickToChat(@NotNull GuildMessageReceivedEvent event, String kickedUser, String reason) {
    try {
      List<TextChannel> textChannels = event.getGuild()
          .getTextChannelsByName(BOT_CHANNEL_LOGS, true);
      if (textChannels.size() >= 1) {
        String userId = event.getAuthor().getId();
        String channelId = event.getChannel().getId();
        User user = event.getMember().getUser();
        TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOGS, true)
            .get(0);
        if (!user.isBot() && textChannel != null) {
          EmbedBuilder kickRsn = new EmbedBuilder();
          kickRsn.setColor(3913034);
          kickRsn.setTitle(":mega: Someone kick user!");
          kickRsn.addField("User", "<@" + userId + ">", false);
          kickRsn.addField("Channel", "<#" + channelId + ">", false);
          kickRsn.setDescription("Kicked member: " + kickedUser + " " + reason);
          event.getGuild()
              .getTextChannelsByName("botlog", false)
              .get(0).sendMessage(kickRsn.build()).queue();
          kickRsn.clear();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}