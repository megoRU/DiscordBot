import config.Config;
import messagesEvents.BotShutDown;
import events.ChangeBitrateChannel;
import messagesEvents.ExchangeRates;
import messagesEvents.ExchangeValue;
import messagesEvents.GoogleSearch;
import messagesEvents.MessagePing;
import messagesEvents.MessageUptimeBot;
import messagesEvents.MessageDeleting;
import messagesEvents.MessageInfoHelp;
import messagesEvents.MessageMoveUser;
import events.MessageWhenBotLeaveJoinToGuild;
import events.MessageWhoEnterLeaveChannel;
import messagesEvents.DrinkBoolean;
import events.EventJoinMemberToGuildSetRole;
import messagesEvents.YoutubeUrlWithTime;
import messagesEvents.countConnectionsEvent;
import events.logWhoEnterLeaveMoveChannel;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

  public static void main(String[] args) throws LoginException {
    JDABuilder builder = JDABuilder.createDefault(Config.getTOKEN()); //The token was changed after opening the repository!
    builder.setAutoReconnect(true);
    builder.enableIntents(GatewayIntent.GUILD_MEMBERS); // also enable privileged intent
    builder.setStatus(OnlineStatus.ONLINE);
    builder.setActivity(Activity.playing("—> !help"));
    builder.setBulkDeleteSplittingEnabled(false);
    builder.addEventListeners(new BotShutDown());
    builder.addEventListeners(new MessagePing());
    builder.addEventListeners(new MessageDeleting());
    builder.addEventListeners(new MessageInfoHelp());
    builder.addEventListeners(new MessageMoveUser());
    builder.addEventListeners(new MessageUptimeBot());
    builder.addEventListeners(new MessageWhenBotLeaveJoinToGuild());
    builder.addEventListeners(new MessageWhoEnterLeaveChannel());
    builder.addEventListeners(new YoutubeUrlWithTime());
    builder.addEventListeners(new ChangeBitrateChannel());
    builder.addEventListeners(new GoogleSearch());
    builder.addEventListeners(new ExchangeRates());
    builder.addEventListeners(new DrinkBoolean());
    builder.addEventListeners(new ExchangeValue());
    builder.addEventListeners(new EventJoinMemberToGuildSetRole());
    builder.addEventListeners(new countConnectionsEvent());
    builder.addEventListeners(new logWhoEnterLeaveMoveChannel());

    builder.build();
  }
}
