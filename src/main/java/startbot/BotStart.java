package startbot;

import config.Config;
import events.ChangeBitrateChannel;
import events.EventJoinMemberToGuildSetRole;
import events.LogWhoEnterLeaveMoveChannel;
import events.MessageWhenBotLeaveJoinToGuild;
import events.MessageWhoEnterLeaveChannel;
import javax.security.auth.login.LoginException;
import games.GameOfDice;
import messagesevents.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BotStart {

  public static JDA jda;
  private final JDABuilder jdaBuilder = JDABuilder.createDefault(Config.getTOKEN());

  public void startBot() throws InterruptedException, LoginException {
    jdaBuilder.setAutoReconnect(true);
    jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS); // also enable privileged intent
    jdaBuilder.setStatus(OnlineStatus.ONLINE);
    jdaBuilder.setActivity(Activity.playing("—> !help"));
    jdaBuilder.setBulkDeleteSplittingEnabled(false);
    jdaBuilder.addEventListeners(new BotShutDown());
    jdaBuilder.addEventListeners(new MessagePing());
    jdaBuilder.addEventListeners(new MessageDeleting());
    jdaBuilder.addEventListeners(new MessageInfoHelp());
    jdaBuilder.addEventListeners(new MessageMoveUser());
    jdaBuilder.addEventListeners(new MessageUptimeBot());
    jdaBuilder.addEventListeners(new MessageWhenBotLeaveJoinToGuild());
    jdaBuilder.addEventListeners(new MessageWhoEnterLeaveChannel());
    jdaBuilder.addEventListeners(new YoutubeUrlWithTime());
    jdaBuilder.addEventListeners(new ChangeBitrateChannel());
    jdaBuilder.addEventListeners(new GoogleSearch());
    jdaBuilder.addEventListeners(new ExchangeRates());
    jdaBuilder.addEventListeners(new DrinkBoolean());
    jdaBuilder.addEventListeners(new ExchangeValue());
    jdaBuilder.addEventListeners(new EventJoinMemberToGuildSetRole());
    jdaBuilder.addEventListeners(new CountConnectionsEvent());
    jdaBuilder.addEventListeners(new LogWhoEnterLeaveMoveChannel());
    jdaBuilder.addEventListeners(new RepeatMyMessage());
    jdaBuilder.addEventListeners(new SendingMessagesToGuilds());
    jdaBuilder.addEventListeners(new GameOfDice());
    jdaBuilder.addEventListeners(new GameHangmanListener());
    jda = jdaBuilder.build();
    jda.awaitReady();
  }

  public void sendMessage(String channelId, String message) {
    jda.getTextChannelById(channelId).sendMessage(message).queue();
  }

  public JDA getJda() {
    return jda;
  }

  public JDABuilder getJdaBuilder() {
    return jdaBuilder;
  }
}
