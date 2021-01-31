package messagesevents;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageChangeBitrate extends ListenerAdapter {

  private static final String BITRATE = "bitrate\\s[0-9]{1,3}";
  private static final String BITRATE_INFO = "bitrate";

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
    String prefix2 = "!";
    int length = message.length();

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId());
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals("")) {
      return;
    }

    String prefixCheck = message.substring(0, 1);
    String messageWithOutPrefix = message.substring(1, length);

    if (!prefixCheck.equals(prefix) || !prefixCheck.equals(prefix2)) {
      return;
    }

    if (messageWithOutPrefix.matches(BITRATE) || message.equals(BITRATE_INFO)) {
      if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)) {
        event.getChannel().sendMessage("Bot don\\`t have: `Permission.MANAGE_CHANNEL`").queue();
        return;
      }
      try {
        User user = event.getMember().getUser();
        VoiceChannel voiceChannel = event.getGuild().getMember(user).getVoiceState().getChannel();
        String[] messages = message.split(" ", 2);
        int maxBitrate = event.getGuild().getMaxBitrate();

        if (messageWithOutPrefix.matches(BITRATE) && voiceChannel == null) {
          event.getChannel().sendMessage("You must be in a voice channel to change its bitrate!")
              .queue();
          return;
        }

        if (messageWithOutPrefix.equals(BITRATE_INFO)) {
          event.getChannel().sendMessage("!bitrate [64] - without square brackets").queue();
          return;
        }

        if (messageWithOutPrefix.matches(BITRATE)
            && voiceChannel != null
            && Integer.parseInt(messages[1]) < 10) {
          event.getChannel().sendMessage("Bitrate should be more than 10!").queue();
          return;
        }

        if (messageWithOutPrefix.matches(BITRATE)
            && voiceChannel != null
            && Integer.parseInt(messages[1] + "000") > maxBitrate) {
          event.getChannel()
              .sendMessage("Error! The maximum available bitrate for this guild is: " + maxBitrate)
              .queue();
          return;
        }

        if (messageWithOutPrefix.matches(BITRATE)
            && voiceChannel != null
            && Integer.parseInt(messages[1]) >= 10
            && Integer.parseInt(messages[1] + "000") <= maxBitrate) {
          voiceChannel.getManager().setBitrate(Integer.parseInt(messages[1] + "000")).queue();
          event.getChannel()
              .sendMessage("Bitrate channel: `" + voiceChannel.getName() + "` changed to: "
                  + Integer.parseInt(messages[1] + "000") + " kbps").queue();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
