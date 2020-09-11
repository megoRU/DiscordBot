package events;

import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class YoutubeUrlWithTime extends ListenerAdapter {

  public final String YOUTUBE_LINKS = "(?:https:\\/\\/)?(?:www\\.)youtube\\.com\\/watch\\?v=[a-zA-z-0-9]+\\s+[0-9]+"; // https://www.youtube.com/watch?v=o5a8ZCUVQ40
  public final String YOUTUBE_MINI = "(?:https:\\/\\/)?(?:)youtu\\.be\\/[a-zA-z-0-9]+\\s+[0-9]+"; // https://youtu.be/ALtLujDc1xw

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();

    try {
      if (message.matches(YOUTUBE_LINKS)) {
        String[] text = message.split(" ");
        int indexFirst = text[0].lastIndexOf("=");
        int length = text[0].length();
        int timeMinutes = Integer.parseInt(text[1]);
        int results = timeMinutes * 60;
        String resultsUrl = text[0].substring(indexFirst + 1, length);
        String lastMessage = event.getChannel().getLatestMessageId();
        event.getChannel().deleteMessageById(lastMessage).queue();
        event.getChannel().sendMessage("<@" + idUser + ">! " + "прислал сообщение:" + "\n"
            + "https://youtu.be/" + resultsUrl + "?t=" + results).queue();
      }
      if (message.matches(YOUTUBE_MINI)) {
        String[] text = message.split(" ");
        int indexFirst = text[0].lastIndexOf("/");
        int length = text[0].length();
        int timeMinutes = Integer.parseInt(text[1]);
        int results = timeMinutes * 60;
        String resultsUrl = text[0].substring(indexFirst + 1, length);
        String lastMessage = event.getChannel().getLatestMessageId();
        event.getChannel().deleteMessageById(lastMessage).queue();
        event.getChannel().sendMessage("<@" + idUser + ">! " + "прислал сообщение:" + "\n"
            + "https://youtu.be/" + resultsUrl + "?t=" + results).queue();
      }
    } catch (Exception exception) {
      EmbedBuilder errorYoutube = new EmbedBuilder();
      errorYoutube.setColor(0xff3923);
      errorYoutube.setTitle("🔴 Error: Произошла ошибка №хуй");
      errorYoutube.setDescription("-> YoutubeUrlWithTime.java");
      event.getChannel().sendMessage(errorYoutube.build()).queue();
      errorYoutube.clear();
    }
  }
}