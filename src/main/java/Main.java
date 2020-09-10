import config.Config;
import events.MessageWhoEnterChannel;
import events.MessageDeleting;
import events.MoveUserToChannel;
import events.YoutubeUrlWithTime;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

  public static void main(String[] args) throws Exception  {
    JDABuilder builder = JDABuilder.createDefault(Config.getTOKEN()); //The token was changed after opening the repository!
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new MessageWhoEnterChannel());
    builder.addEventListeners(new MessageDeleting());
    builder.addEventListeners(new MoveUserToChannel());
    builder.addEventListeners(new YoutubeUrlWithTime());

    builder.setActivity(Activity.listening("как орёт мамка Юры"));
    builder.build();
  }
}
