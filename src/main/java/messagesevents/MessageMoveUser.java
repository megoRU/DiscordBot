package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

import java.util.List;

public class MessageMoveUser extends ListenerAdapter {

    /*
     * TODO: Исправить ошибку с выбрасыванием эксепшина по поводу если юзер в том же канале что и тот кто просит
     *  Либо сделать вообще всех перемещать
     *  Сделать на подобее с !kick
     */
    private static final String MOVE = "move\\s.+";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
            return;
        }
        String message = event.getMessage().getContentRaw().toLowerCase();
        String prefix = "!";
        int length = message.length();

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "move\\s.+";
        }

        if (message.equals("")) {
            return;
        }

        String prefixCheck = message.substring(0, 1);
        String messageWithOutPrefix = message.substring(1, length);

        if (!prefixCheck.equals(prefix)) {
            return;
        }

        if (messageWithOutPrefix.matches(MOVE)) {
            User user = event.getMember().getUser();
            boolean boolPermission = event.getMember().hasPermission(Permission.VOICE_MOVE_OTHERS);

            if (!user.isBot() && boolPermission) {
                if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MOVE_OTHERS)) {
                    event.getChannel().sendMessage("Bot don\\`t have: `Permission.VOICE_MOVE_OTHERS`")
                            .queue();
                    return;
                }
                String[] messages = message.split(" ", 2);
                List<Member> memberId = event.getGuild().getMembersByName(messages[1], true);
                System.out.println(messages[1]);
                if (memberId.size() > 0) {
                    if (memberId.get(0) != null) {
                        event.getGuild()
                                .moveVoiceMember(memberId.get(0),
                                        event.getGuild().getMember(user).getVoiceState().getChannel()).queue();
                        memberId.clear();
                        return;
                    }
                }
                if (memberId.size() == 0) {
                    event.getChannel().sendMessage("User not found in Voice Channels").queue();
                }
            }

            if (!boolPermission) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xff3923);
                error.setTitle("🔴 Error: You cannot move users");
                error.setDescription("You need Permission.VOICE_MOVE_OTHERS"
                        + "\n-> MessageMoveUser.java");
                event.getChannel().sendMessageEmbeds(error.build()).queue();
                error.clear();
            }
        }
    }
}
