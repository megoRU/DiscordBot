package events;

import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class YoutubeUrlWithTime extends ListenerAdapter {

  public final String YOUTUBE_LINKS = "(?:https:\\/\\/)?(?:www\\.)youtube\\.com\\/watch\\?v=[a-zA-z-0-9]+"; // https://www.youtube.com/watch?v=o5a8ZCUVQ40
  public final String YOUTUBE_MINI = "(?:https:\\/\\/)?(?:)youtu\\.be\\/[a-zA-z-0-9]+"; // https://youtu.be/ALtLujDc1xw

  //&t=1476s

  /**
   * https://youtu.be/o5a8ZCUVQ40?t=2 /** https://youtu.be/ALtLujDc1xw
   */

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String[] message = event.getMessage().getContentRaw().split(" ");
    String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();

    try {
      if (message[0].matches(YOUTUBE_LINKS)) {
        int indexFirst = message[0].lastIndexOf("=");
        int length = message[0].length();
        int timeMinutes = Integer.parseInt(message[1]);
        int results = timeMinutes * 60;
        String resultsUrl = message[0].substring(indexFirst + 1, length);
        String lastMessage = event.getChannel().getLatestMessageId();
        event.getChannel().deleteMessageById(lastMessage).queue();
        event.getChannel().sendMessage("<@" + idUser + ">! " + "прислал сообщение:" + "\n"
            + "https://youtu.be/" + resultsUrl + "?t=" + results).queue();
      }
      if (message[0].matches(YOUTUBE_MINI)) {
        int indexFirst = message[0].lastIndexOf("/");
        int length = message[0].length();
        int timeMinutes = Integer.parseInt(message[1]);
        int results = timeMinutes * 60;
        String resultsUrl = message[0].substring(indexFirst + 1, length);
        String lastMessage = event.getChannel().getLatestMessageId();
        event.getChannel().deleteMessageById(lastMessage).queue();
        event.getChannel().sendMessage("<@" + idUser + ">! " + "прислал сообщение:" + "\n"
            + "https://youtu.be/" + resultsUrl + "?t=" + results).queue();

      }
    } catch (Exception exception) {
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Какая-то ошибка №хуй");
      error.setDescription("Где пиздец случился: public class YoutubeUrlWithTime extends ListenerAdapter");
      event.getChannel().sendMessage(error.build()).queue();
    }
  }
}