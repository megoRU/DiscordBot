package messagesevents;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageDeleting extends ListenerAdapter {

    private static final String DELETE_INDEXES = "clear\\s+\\d+";
    private static final String DELETE_INDEXES2 = "!clear\\s+\\d+";
    private static final String BOT_CHANNEL_LOG = "botchat";

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        try {
            if (message.matches(DELETE_INDEXES) || message.matches(DELETE_INDEXES2)) {
                if (!permCheck(event.getMessage().getMember())) {
                    event.getMessage().addReaction("\u26D4").queue();
                    EmbedBuilder errorClear = new EmbedBuilder();
                    errorClear.setColor(0xff3923);
                    errorClear.setTitle("🔴 Error: You are not Admin");
                    errorClear.setDescription("You need Permission.ADMINISTRATOR." + "\n-> MessageDeleting.java");
                    event.getChannel().sendMessage(errorClear.build()).queue();
                    errorClear.clear();
                    return;
                }
            }

            if ((message.matches(DELETE_INDEXES) || message.matches(DELETE_INDEXES2))) {
                if (permCheck(event.getMessage().getMember())) {
                    String[] commandArray = message.split("\\s+", 2);
                    String index = commandArray[1];
                    int indexParseInt = Integer.parseInt(index);

                    if (indexParseInt >= 2 && indexParseInt <= 100) {
                        deletingLog(event, index);
                        List<Message> messages = event.getChannel()
                                .getHistory().retrievePast(indexParseInt).complete();
                        event.getChannel().deleteMessages(messages).queue();
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0x00FF00);
                        error.setTitle(":white_check_mark: Removed: " + indexParseInt + " messages!");
                        error.setDescription("This message will be deleted after 5 seconds");
                        event.getChannel().sendMessage(error.build()).delay(5, TimeUnit.SECONDS)
                                .flatMap(Message::delete).submit();
                        error.clear();
                    }
                    if (indexParseInt < 2 || indexParseInt > 100) {
                        event.getMessage().addReaction("\u26D4").queue();
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("🔴 Error");
                        error.setDescription("Between 2-100 messages can be deleted at one time.");
                        event.getChannel().sendMessage(error.build()).queue();
                        error.clear();
                    }
                }
            }
        } catch (Exception e) {
            event.getMessage().addReaction("\u26D4").queue();
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xff3923);
            error.setTitle("🔴 Error");
            error.setDescription("Message Id provided was older than 2 weeks.");
            event.getChannel().sendMessage(error.build()).queue();
            error.clear();
        }
    }

    private void deletingLog(GuildMessageReceivedEvent event, String count) {
        List<TextChannel> textChannels = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOG, true);
        if (textChannels.size() >= 1) {
            TextChannel textChannel = event.getGuild().getTextChannelsByName(BOT_CHANNEL_LOG, true).get(0);
            if (textChannel != null) {
                EmbedBuilder delete = new EmbedBuilder();
                String userId = event.getAuthor().getId();
                String channelId = event.getChannel().getId();
                delete.setColor(0x00FF00);
                delete.setTitle(":mega: Someone deleted messages!");
                delete.addField("User", "<@" + userId + ">", false);
                delete.addField("Channel", "<#" + channelId + ">", false);
                delete.addField("Number of deleted messages", "Removed: `" + count + "` messages!", false);
                event.getGuild().getTextChannelsByName("botlog", false)
                        .get(0).sendMessage(delete.build()).queue();
                delete.clear();
            }
        }
    }

    public boolean permCheck(Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }
}