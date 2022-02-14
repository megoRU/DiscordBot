package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class YoutubeUrlWithTime extends ListenerAdapter {

    // https://www.youtube.com/watch?v=o5a8ZCUVQ40 22 2
    public final String YOUTUBE_LINKS_MIN_SEC = "(?:https:\\/\\/)?(?:)www.youtube.com\\/[watch?v=]+[A-Za-z0-9_-]+\\s+[0-9]+\\s+[0-9]+";
    // https://www.youtube.com/watch?v=o5a8ZCUVQ40 22
    public final String YOUTUBE_LINKS_MIN = "(?:https:\\/\\/)?(?:)www.youtube.com\\/[watch?v=]+[A-Za-z0-9_-]+\\s+[0-9]+";
    // https://youtu.be/ALtLujDc1xw 22 2
    public final String YOUTUBE_MINI_MIN_SEC = "(https:\\/\\/)?(?:)youtu\\.be\\/.+\\s+[0-9]+\\s+[0-9]+";
    // https://youtu.be/ALtLujDc1xw 2
    public final String YOUTUBE_MINI_MIN = "(https:\\/\\/)?(?:)youtu\\.be\\/.+\\s+[0-9]+";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.isFromGuild()) return;
        if (!event.getGuild().getSelfMember().hasPermission(event.getGuildChannel(), Permission.MESSAGE_SEND)) return;

        String message = event.getMessage().getContentDisplay().trim();

        if (message.matches(YOUTUBE_LINKS_MIN_SEC)
                || message.matches(YOUTUBE_MINI_MIN_SEC)
                || message.matches(YOUTUBE_MINI_MIN)
                || message.matches(YOUTUBE_LINKS_MIN)) {
            String idUser = event.getMember().getUser().getId();
            try {
                if (message.matches(YOUTUBE_LINKS_MIN_SEC)) {
                    youtubeLinks(event, idUser, message, "2", "=");
                    return;
                }
                if (message.matches(YOUTUBE_LINKS_MIN)) {
                    youtubeLinks(event, idUser, message, "1", "=");
                    return;
                }
                if (message.matches(YOUTUBE_MINI_MIN)) {
                    youtubeLinks(event, idUser, message, "1", "/");
                    return;
                }
                if (message.matches(YOUTUBE_MINI_MIN_SEC)) {
                    youtubeLinks(event, idUser, message, "2", "/");
                }
            } catch (Exception exception) {
                EmbedBuilder errorYoutube = new EmbedBuilder();
                errorYoutube.setColor(0xff3923);
                errorYoutube.setTitle("🔴 Error: unexpected error");
                errorYoutube.setDescription("-> YoutubeUrlWithTime.java");
                event.getChannel().sendMessageEmbeds(errorYoutube.build()).queue();
                errorYoutube.clear();
            }
        }
    }

    public String youtubeLinks(@NotNull MessageReceivedEvent event, String idUser, String message, String count, String argument) {
        String[] text = message.split(" ");
        String slash = "/";
        String equal = "=";
        int indexFirst = 0;
        if (slash.equals(argument)) {
            indexFirst = text[0].lastIndexOf(slash);
        }
        if (equal.equals(argument)) {
            indexFirst = text[0].lastIndexOf(equal);
        }
        int length = text[0].length();
        int timeMinutes = Integer.parseInt(text[1]);
        int results = 0;
        String one = "1";
        String two = "2";
        if (one.equals(count)) {
            results = timeMinutes * 60;
        }
        if (two.equals(count)) {
            results = (timeMinutes * 60) + Integer.parseInt(text[2]);
        }
        String resultsUrl = text[0].substring(indexFirst + 1, length);
        String lastMessage = event.getChannel().getLatestMessageId();
        event.getChannel().deleteMessageById(lastMessage).queue();
        event.getChannel().sendMessage("<@" + idUser + ">! " + "sent a message:" + "\n"
                + "https://youtu.be/" + resultsUrl + "?t=" + results).queue();
        return null;
    }
}