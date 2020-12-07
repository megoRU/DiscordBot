package messagesevents;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessageChangeBitrate extends ListenerAdapter {

    private static final String BITRATE = "!bitrate\\s[0-9]{1,3}";
    private static final String BITRATE_INFO = "!bitrate";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay().trim();
        String prefix = BITRATE;
        String prefix2 = BITRATE_INFO;

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "bitrate\\s[0-9]{1,3}";
            prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "bitrate";
        }

        if (message.matches(prefix) || message.equals(prefix2)) {
            try {
                User user = event.getMember().getUser();
                VoiceChannel voiceChannel = event.getGuild().getMember(user).getVoiceState().getChannel();
                String[] messages = message.split(" ", 2);
                int maxBitrate = event.getGuild().getMaxBitrate();

                if (message.matches(prefix) && voiceChannel == null) {
                    event.getChannel().sendMessage("You must be in a voice channel to change its bitrate!").queue();
                    return;
                }

                if (message.equals(prefix2)) {
                    event.getChannel().sendMessage("!bitrate [64] - without square brackets").queue();
                    return;
                }

                if (message.matches(prefix)
                        && voiceChannel != null
                        && Integer.parseInt(messages[1]) < 10) {
                    event.getChannel().sendMessage("Bitrate should be more than 10!").queue();
                    return;
                }

                if (message.matches(prefix)
                        && voiceChannel != null
                        && Integer.parseInt(messages[1] + "000") > maxBitrate) {
                    event.getChannel().sendMessage("Error! The maximum available bitrate for this guild is: " + maxBitrate).queue();
                    return;
                }

                if (message.matches(prefix)
                        && voiceChannel != null
                        && Integer.parseInt(messages[1]) >= 10
                        && Integer.parseInt(messages[1] + "000") <= maxBitrate) {
                    voiceChannel.getManager().setBitrate(Integer.parseInt(messages[1] + "000")).queue();
                    event.getChannel().sendMessage("Bitrate channel: `" + voiceChannel.getName() + "` changed to: "
                            + Integer.parseInt(messages[1] + "000") + " kbps").queue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
