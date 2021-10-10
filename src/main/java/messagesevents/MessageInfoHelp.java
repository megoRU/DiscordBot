package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

import java.util.concurrent.TimeUnit;

public class MessageInfoHelp extends ListenerAdapter {

    private static final String HELP = "!help";
    private static final String HELP_WITH_OUT = "help";
    private static final String INFO_WITH_OUT = "info";
    private static final String INFO = "!info";
    private static final String INFO_RU = "инфо";
    private static final String MUSIC = "!music";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
            return;
        }
        String message = event.getMessage().getContentRaw().toLowerCase();
        String prefix = HELP;
        String prefix2 = INFO;
        String prefix3 = MUSIC;

        String p = "!";

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "help";
            prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "info";
            prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "music";
            p = BotStart.mapPrefix.get(event.getGuild().getId());
        }

        if (message.equals(prefix3)) {
            EmbedBuilder music = new EmbedBuilder();
            music.setColor(0xa224db);
            event.getChannel().sendMessageEmbeds(music.build()).queue();
            music.clear();
            return;
        }

        if (message.equals(prefix) || message.equals(HELP_WITH_OUT) || message.equals(INFO_WITH_OUT)
                || message.equals(prefix2) || message.equals(INFO_RU)) {
            String avatarUrl = null;
            String avatarFromEvent = event.getMessage().getAuthor().getAvatarUrl();
            if (avatarFromEvent == null) {
                avatarUrl = "https://cdn.discordapp.com/avatars/754093698681274369/dc4b416065569253bc6323efb6296703.png";
            }
            if (avatarFromEvent != null) {
                avatarUrl = avatarFromEvent;
            }
            EmbedBuilder info = new EmbedBuilder();
            info.setColor(0xa224db);
            info.setAuthor(event.getAuthor().getName(), null, avatarUrl);
            info.addField("Prefix:",
                    "`*prefix <symbol>` - Changes the prefix.\n" +
                            "`*prefix reset` - Reset the prefix.", false);

            info.addField("Other functions:",
                    "`" + p + "help` - Information."
                            + "\n`" + p + "poll <text>` - Create a poll."
                            + "\n`" + p + "kick` - Example: " + p + "kick <@user>/" + p + "kick <@user> <reason>"
                            + "\n`" + p + "ban` - Example: " + p + "ban <@user> <days>/" + p
                            + "ban <@user> <days> <reason>"
                            + "\n`" + p + "bitrate <96>` - Changes the channel bitrate to the specified bitrate."
                            + "\n`курс доллара, курс евро, курс, евро, доллар` - Данные от ЦБ к рублю."
                            + "\n`100 долларов в рублях` - Доступны валюты: USD, EUR, RUB"
                            + "\n`" + p + "uptime/uptime` - uptime bot."
                            + "\n`" + p + "ping/ping` - API response."
                            + "\n`" + p + "amount/" + p
                            + "колво]` - How many times have you connected to channels."
                            + "\n`top 3/колво топ` - Top 3 by connection."
                            + "\n`" + p
                            + "clear + <number: >= 2 & <= 100>` - Deletes the specified number of messages."
                            + "\n`" + p + "flip` - flip a coin."
                            + "\n`" + p + "ищи/ищи <текст>` - " + " [g.zeos.in](https://g.zeos.in/) "
                            + "\n`<YouTube link> <minutes> <seconds if present>` - Converts to a short link with time.",
                    false);
            info.addField("Checking the settings",
                    "`?check` - Checks the correct bot setup.", false);
            info.addField("Links:", ":zap: [megoru.ru](https://megoru.ru)\n" +
                            ":robot: [Add me to other guilds](https://discord.com/oauth2/authorize?client_id=754093698681274369&scope=bot&permissions=8)",
                    false);
            info.addField("Bot creator", ":tools: [mego](https://steamcommunity.com/id/megoRU)", false);
            info.addField("License",
                    ":page_facing_up: [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0)",
                    false);
            info.addField("Support Server",
                    ":helmet_with_cross: [Click me](https://discord.com/invite/UrWG3R683d)", false);

            event.getChannel().sendMessage("I sent you a private message").delay(5, TimeUnit.SECONDS)
                    .flatMap(Message::delete).queue();

            event.getMember().getUser().openPrivateChannel()
                    .flatMap(m -> event.getMember().getUser().openPrivateChannel())
                    .flatMap(channel -> channel.sendMessageEmbeds(info.build()))
                    .queue(null, error -> event.getChannel().sendMessage("Failed to send message!").queue());
        }
    }
}
