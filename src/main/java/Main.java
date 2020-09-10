import config.Config;
import events.BotJoinChannel;
import events.MessageWhoEnterLeaveChannel;
import events.MessageDeleting;
import events.MessageMoveUser;
import events.MoveUserToChannel;
import events.YoutubeUrlWithTime;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

  public static void main(String[] args) throws Exception  {
    JDABuilder builder = JDABuilder.createDefault(Config.getTOKEN()); //The token was changed after opening the repository!
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new MessageWhoEnterLeaveChannel());
    builder.addEventListeners(new MessageDeleting());
    builder.addEventListeners(new MoveUserToChannel());
    builder.addEventListeners(new YoutubeUrlWithTime());
    builder.addEventListeners(new MessageMoveUser());
    builder.addEventListeners(new BotJoinChannel());

    builder.setActivity(Activity.playing("—> !help"));
    builder.build();
  }
}
