package startbot;

import config.Config;
import events.*;
import games.GameOfDice;
import messagesevents.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.discordbots.api.client.DiscordBotListAPI;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BotStart {

    public static JDA jda;
    private final JDABuilder jdaBuilder = JDABuilder.createDefault(Config.getTOKEN());
    public static final Map<String, String> mapPrefix = new HashMap<>();
    public static final Map<String, String> idMessagesWithPollEmoji = new HashMap<>();
    public static DiscordBotListAPI TOP_GG_API;

    public void startBot() throws Exception {
        jdaBuilder.setAutoReconnect(true);
//    jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS); // also enable privileged intent
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("!help"));
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
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
        jdaBuilder.addEventListeners(new UserJoinToGuild());
        jdaBuilder.addEventListeners(new CountConnectionsEvent());
        jdaBuilder.addEventListeners(new LogWhoEnterLeaveMoveChannel());
        jdaBuilder.addEventListeners(new RepeatMyMessage());
        jdaBuilder.addEventListeners(new SendingMessagesToGuilds());
        jdaBuilder.addEventListeners(new GameOfDice());
        jdaBuilder.addEventListeners(new MessageChangeBitrate());
        jdaBuilder.addEventListeners(new MessageKick());
        jdaBuilder.addEventListeners(new MessageBan());
        jdaBuilder.addEventListeners(new MessagePoll());
        jdaBuilder.addEventListeners(new PrefixChange());
        jdaBuilder.addEventListeners(new MessageCheckBotSettings());

        jda = jdaBuilder.build();
        jda.awaitReady();

        try {
            Connection conn = DriverManager.getConnection(Config.getCONN(), Config.getUSER(), Config.getPASS());
            Statement statement = conn.createStatement();
            String sql = "select * from prefixs";
            ResultSet rs = statement.executeQuery(sql);
            Statement statementSecond = conn.createStatement();

            String sqlIdMessagesWithPollEmoji = "select * from idMessagesWithPollEmoji";
            ResultSet rsIdMessages = statementSecond.executeQuery(sqlIdMessagesWithPollEmoji);


            while (rs.next()) {
                mapPrefix.put(rs.getString("serverId"), rs.getString("prefix"));
            }

            while (rsIdMessages.next()) {
                idMessagesWithPollEmoji.put(rsIdMessages.getString("idMessagesWithPollEmoji")
                        , rsIdMessages.getString("idMessagesWithPollEmoji"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        TOP_GG_API = new DiscordBotListAPI.Builder()
                .token(Config.getTopGgApiToken())
                .botId(Config.getBotId())
                .build();
        int serverCount = (int) jda.getGuildCache().size();
        TOP_GG_API.setStats(serverCount);


    }

    public JDA getJda() {
        return jda;
    }
}