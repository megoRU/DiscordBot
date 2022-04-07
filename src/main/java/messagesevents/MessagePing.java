package messagesevents;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class MessagePing extends ListenerAdapter {

    private static final String PING = "!ping";
    private static final String PING_WITH_OUT = "ping";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.isFromGuild()) return;
        if (!event.getGuild().getSelfMember().hasPermission(event.getGuildChannel(), Permission.MESSAGE_SEND)) return;

        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        String prefix = PING;

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "ping";
        }

        if (message.equals(prefix) || message.equals(PING_WITH_OUT)) {
            event.getJDA().getRestPing().queue((time)  ->
                    event.getChannel()
                            .sendMessageFormat("Rest Ping: %d ms\nWebSocket Ping: %d ms", time, event.getJDA().getGatewayPing())
                            .queue());
        }
    }
}


