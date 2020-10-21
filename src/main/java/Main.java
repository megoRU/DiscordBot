import config.Config;
import messages.events.BotShutDown;
import events.ChangeBitrateChannel;
import messages.events.ExchangeRates;
import messages.events.ExchangeValue;
import messages.events.GoogleSearch;
import messages.events.MessagePing;
import messages.events.MessageUptimeBot;
import messages.events.MessageDeleting;
import messages.events.MessageInfoHelp;
import messages.events.MessageMoveUser;
import events.MessageWhenBotLeaveJoinToGuild;
import events.MessageWhoEnterLeaveChannel;
import messages.events.DrinkBoolean;
import events.EventJoinMemberToGuildSetRole;
import messages.events.YoutubeUrlWithTime;
import messages.events.CountConnectionsEvent;
import events.LogWhoEnterLeaveMoveChannel;
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
    builder.addEventListeners(new CountConnectionsEvent());
    builder.addEventListeners(new LogWhoEnterLeaveMoveChannel());

    builder.build();
  }
}
