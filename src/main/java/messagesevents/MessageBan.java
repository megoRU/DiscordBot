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

public class MessageBan extends ListenerAdapter {

  private static final String BAN = "ban\\s.+\\s[0-9]+";
  private static final String BAN2 = "ban\\s.+";
  private static final String UN_BAN = "unban\\s.+";
  private static final String BOT_CHANNEL_LOGS = "botlog";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().toLowerCase().trim();
    String[] messages = message.split(" ", 4);
    String prefix = "!";
    String prefix2 = "!";
    String prefix3 = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId());
      prefix3 = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (!prefixCheck.equals(prefix) || !prefixCheck.equals(prefix2) || !prefixCheck
        .equals(prefix3)) {
      return;
    }

    if (messageWithOutPrefix.matches(UN_BAN)) {
      if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
          event.getChannel().sendMessage("Bot don\\`t have: `Permission.BAN_MEMBERS`").queue();
          return;
        }
        String result = message
            .replaceAll("!unban <@!", "")
            .replaceAll(">", "");
        try {
          event.getGuild().unban(result).queue();
        } catch (Exception e) {
          event.getChannel().sendMessage("It seems like a ban does not exist").queue();
        }
        EmbedBuilder unban = new EmbedBuilder();
        unban.setColor(3913034);
        unban.setTitle("Unban success");
        unban.setDescription("Unbanned user: " + messages[1]);
        event.getChannel().sendMessage(unban.build()).queue();
        unban.clear();
        //logKickToChat(event, messages[1], );
        return;
      }
      return;
    }

    if (messageWithOutPrefix.matches(BAN) || messageWithOutPrefix.matches(BAN2)) {
      if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
          event.getChannel().sendMessage("Bot don\\`t have: `Permission.BAN_MEMBERS`").queue();
          return;
        }
        if (messages.length == 4 || messages.length == 3) {
          List<Member> toBan = new ArrayList<>(1);
          toBan.addAll(event.getMessage().getMentionedMembers());

          if (toBan.size() >= 1 && messages.length == 4) {
            event.getGuild().ban(toBan.get(0), Integer.parseInt(messages[2]), messages[3]).queue();
            EmbedBuilder kickRsn = new EmbedBuilder();
            kickRsn.setColor(3913034);
            kickRsn.setTitle("Ban success");
            kickRsn
                .setDescription("Banned member: " + messages[1] + " with reason: " + (messages[3]));
            event.getChannel().sendMessage(kickRsn.build()).queue();
            kickRsn.clear();
            logBanToChat(event, messages[1], "reason: " + messages[3]);
            return;
          }

          if (toBan.size() >= 1 && messages.length == 3) {
            event.getGuild().ban(toBan.get(0), Integer.parseInt(messages[2])).queue();
            EmbedBuilder ban = new EmbedBuilder();
            ban.setColor(3913034);
            ban.setTitle("Ban success");
            ban.setDescription("Banned member: " + messages[1]);
            event.getChannel().sendMessage(ban.build()).queue();
            ban.clear();
            logBanToChat(event, messages[1], "without a reason");
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
      if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
        EmbedBuilder kickRsn = new EmbedBuilder();
        kickRsn.setColor(0xFF0000);
        kickRsn.setTitle("Access denied");
        kickRsn.setDescription("You do not have permission to ban users");
        event.getChannel().sendMessage(kickRsn.build()).queue();
        kickRsn.clear();
      }
    }
  }

  private void logBanToChat(@NotNull GuildMessageReceivedEvent event, String banUser, String reason) {
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
          kickRsn.setTitle(":mega: Someone ban user!");
          kickRsn.addField("User", "<@" + userId + ">", false);
          kickRsn.addField("Channel", "<#" + channelId + ">", false);
          kickRsn.setDescription("Banned member: " + banUser + " " + reason);
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