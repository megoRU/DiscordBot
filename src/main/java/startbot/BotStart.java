package startbot;

import config.Config;
import events.ChangeBitrateChannel;
import events.EventJoinMemberToGuildSetRole;
import events.LogWhoEnterLeaveMoveChannel;
import events.MessageWhenBotLeaveJoinToGuild;
import events.MessageWhoEnterLeaveChannel;
import javax.security.auth.login.LoginException;
import games.GameHangmanListener;
import games.GameOfDice;
import giftaway.MessageGift;
import lavaplayer.PlayerControl;
import messagesevents.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.discordbots.api.client.DiscordBotListAPI;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BotStart {

  public static JDA jda;
  private final JDABuilder jdaBuilder = JDABuilder.createDefault(Config.getTOKEN());
  public static Map<String, String> mapPrefix = new HashMap<>();
  public static DiscordBotListAPI TOP_GG_API;

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
    jdaBuilder.addEventListeners(new FlipCoin());
    jdaBuilder.addEventListeners(new ExchangeValue());
    jdaBuilder.addEventListeners(new EventJoinMemberToGuildSetRole());
    jdaBuilder.addEventListeners(new CountConnectionsEvent());
    jdaBuilder.addEventListeners(new LogWhoEnterLeaveMoveChannel());
    jdaBuilder.addEventListeners(new RepeatMyMessage());
    jdaBuilder.addEventListeners(new SendingMessagesToGuilds());
    jdaBuilder.addEventListeners(new GameOfDice());
    jdaBuilder.addEventListeners(new GameHangmanListener());
    jdaBuilder.addEventListeners(new MessageChangeBitrate());
    jdaBuilder.addEventListeners(new MessageKick());
    jdaBuilder.addEventListeners(new MessageBan());
    jdaBuilder.addEventListeners(new MessagePoll());
    jdaBuilder.addEventListeners(new PrefixChange());
    jdaBuilder.addEventListeners(new MessageGift());
    jdaBuilder.addEventListeners(new MessageCheckBotSettings());
    jdaBuilder.addEventListeners(new PlayerControl());

    jda = jdaBuilder.build();
    jda.awaitReady();

    try {
      Connection conn = DriverManager.getConnection(Config.getCONN(), Config.getUSER(), Config.getPASS());
      Statement statement = conn.createStatement();
      String sql = "select * from prefixs";
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        mapPrefix.put(rs.getString("serverId"), rs.getString("prefix"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

//    TOP_GG_API = new DiscordBotListAPI.Builder()
//            .token(Config.getTopGgApiToken())
//            .botId(Config.getBotId())
//            .build();
//    int serverCount = (int) jda.getGuildCache().size();
//    TOP_GG_API.setStats(serverCount);

  }

  public void sendMessage(String channelId, String message) {
    Objects.requireNonNull(jda.getTextChannelById(channelId)).sendMessage(message).queue();
  }

  public JDA getJda() {
    return jda;
  }

  public JDABuilder getJdaBuilder() {
    return jdaBuilder;
  }
}