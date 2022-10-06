package startbot;

import config.Config;
import events.*;
import games.GameOfDice;
import messagesevents.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.sql.*;
import java.util.*;

public class BotStart {

    public static JDA jda;
    private final JDABuilder jdaBuilder = JDABuilder.createDefault(Config.getTOKEN());
    public static final Map<String, String> mapPrefix = new HashMap<>();
    public static final Map<String, String> idMessagesWithPollEmoji = new HashMap<>();

    public void startBot() throws Exception {

        List<GatewayIntent> intents = new ArrayList<>(
                Arrays.asList(
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING));

        jdaBuilder.disableCache(
                CacheFlag.ROLE_TAGS,
                CacheFlag.ACTIVITY,
                CacheFlag.MEMBER_OVERRIDES);

        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.enableIntents(intents);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("!help"));
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
        jdaBuilder.addEventListeners(new MessagePing());
        jdaBuilder.addEventListeners(new MessageInfoHelp());
        jdaBuilder.addEventListeners(new MessageWhenBotLeaveJoinToGuild());
        jdaBuilder.addEventListeners(new MessageWhoEnterLeaveChannel());
        jdaBuilder.addEventListeners(new YoutubeUrlWithTime());
        jdaBuilder.addEventListeners(new ChangeBitrateChannel());
        jdaBuilder.addEventListeners(new GoogleSearch());
        jdaBuilder.addEventListeners(new UserJoinToGuild());
        jdaBuilder.addEventListeners(new CountConnectionsEvent());
        jdaBuilder.addEventListeners(new LogWhoEnterLeaveMoveChannel());
        jdaBuilder.addEventListeners(new GameOfDice());
        jdaBuilder.addEventListeners(new MessageChangeBitrate());
        jdaBuilder.addEventListeners(new PrefixChange());
        jdaBuilder.addEventListeners(new MessageCheckBotSettings());

        jda = jdaBuilder.build();
        jda.awaitReady();

        Message.suppressContentIntentWarning();

        jda.getPresence().setActivity(Activity.playing("!help " + jda.getGuilds().size() + " guilds"));

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
    }

    public JDA getJda() {
        return jda;
    }
}