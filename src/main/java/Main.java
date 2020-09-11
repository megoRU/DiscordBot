import config.Config;
import events.BotJoinChannel;
import events.BotShutDown;
import events.MessagePing;
import events.MessageUptimeBot;
import events.MessageDeleting;
import events.MessageInfoHelp;
import events.MessageMoveUser;
import events.MessageWhenBotLeaveJoinToGuild;
import events.MessageWhoEnterLeaveChannel;
import events.MoveUserToChannel;
import events.YoutubeUrlWithTime;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

  public static void main(String[] args) throws LoginException {
    JDABuilder builder = JDABuilder.createDefault(Config.getTOKEN()); //The token was changed after opening the repository!
    builder.setAutoReconnect(true);
    builder.setStatus(OnlineStatus.ONLINE);
    builder.setActivity(Activity.playing("—> !help"));
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new BotJoinChannel());
    builder.addEventListeners(new BotShutDown());
    builder.addEventListeners(new MessagePing());
    builder.addEventListeners(new MessageDeleting());
    builder.addEventListeners(new MessageInfoHelp());
    builder.addEventListeners(new MessageMoveUser());
    builder.addEventListeners(new MessageUptimeBot());
    builder.addEventListeners(new MessageWhenBotLeaveJoinToGuild());
    builder.addEventListeners(new MessageWhoEnterLeaveChannel());
    builder.addEventListeners(new MoveUserToChannel());
    builder.addEventListeners(new YoutubeUrlWithTime());

    builder.build();
  }
}
