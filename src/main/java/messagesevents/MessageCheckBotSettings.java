package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageCheckBotSettings extends ListenerAdapter {

  private static final String BOT_CHANNEL_LOGS = "botlog";
  private static final String CHECK = "?check";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().trim();
    String prefix = "!";

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals(CHECK)) {

      boolean hasChannel = event.getGuild()
          .getTextChannels()
          .stream()
          .anyMatch(textChannel -> textChannel.getName().equals(BOT_CHANNEL_LOGS));

      boolean hasPermissionAdministrator = event.getGuild()
          .getSelfMember().hasPermission(Permission.ADMINISTRATOR);

      boolean hasPermissionMessageManage = event.getGuild()
          .getSelfMember().hasPermission(Permission.MESSAGE_MANAGE);

      boolean hasPermissionKickUsers = event.getGuild()
          .getSelfMember().hasPermission(Permission.KICK_MEMBERS);

      boolean hasPermissionBanUsers = event.getGuild()
          .getSelfMember().hasPermission(Permission.BAN_MEMBERS);

      boolean hasPermissionMoveUsers = event.getGuild()
          .getSelfMember().hasPermission(Permission.VOICE_MOVE_OTHERS);

      boolean hasPermissionManageChannel = event.getGuild()
          .getSelfMember().hasPermission(Permission.MANAGE_CHANNEL);

      EmbedBuilder settings = new EmbedBuilder();
      settings.setColor(0x00FF00);
      settings.setTitle("Bot permissions");
      settings.addField("Optional, but recommended:",
          "Bot have Permission.ADMINISTRATOR: " + getStatus(hasPermissionAdministrator)
          , false);

      settings.addField("Required:",
          "Bot have Permission.MESSAGE_MANAGE: " + getStatus(hasPermissionMessageManage)
          , false);

      settings.addField("To expand the functionality:",
          "Bot have Permission.MANAGE_CHANNEL: " + getStatus(hasPermissionManageChannel)
              + "\n" +
              "Bot have Permission.VOICE_MOVE_OTHERS: " + getStatus(hasPermissionMoveUsers)
              + "\n" +
              "Bot have Permission.KICK_MEMBERS: " + getStatus(hasPermissionKickUsers)
              + "\n" +
              "Bot have Permission.BAN_MEMBERS: " + getStatus(hasPermissionBanUsers)
          , false);

      settings.addField("Other information:",
          "Guild have `botlog` text channel: " + getStatus(hasChannel)
              + "\n" +
              "Bot prefix: `" + prefix + "`", false);
      event.getChannel().sendMessage(settings.build()).queue();
      settings.clear();
    }
  }

  private String getStatus(boolean permission) {
    if (permission) {
      return ":white_check_mark:";
    } else {
      return ":no_entry_sign:";
    }
  }
}