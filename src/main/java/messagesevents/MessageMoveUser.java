package messagesevents;

import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageMoveUser extends ListenerAdapter {

    public final String MOVE = "!move\\s.+";

    //TODO: Исправить ошибку с выбрасыванием эксепшина по поводу если юзер в том же канале что и тот кто просит
    // Либо сделать вообще всех перемещать
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw().toLowerCase();
        String idUser = Objects.requireNonNull(event.getMember()).getUser().getId();
        User user = event.getMember().getUser();
        if (!user.isBot()) {
            boolean boolPermission = event.getMember().hasPermission(Permission.VOICE_MOVE_OTHERS);

            if (message.matches(MOVE) && !user.isBot() && boolPermission) {
                String[] messages = message.split(" ", 2);
                List<Member> memberId = event.getGuild().getMembersByName(messages[1], true);
                System.out.println(messages[1]);
                if (memberId.size() > 0) {
                    if (memberId.get(0) != null) {
                        event.getGuild().moveVoiceMember(memberId.get(0),
                                Objects.requireNonNull(
                                        Objects.requireNonNull(
                                                event.getGuild().getMember(user)).getVoiceState()).getChannel()).queue();
                        memberId.clear();
                        return;
                    }
                }
                if (memberId.size() == 0) {
                    event.getChannel().sendMessage("User not found in Voice Channels").queue();
                }
            }

            if (message.matches(MOVE) & !boolPermission) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xff3923);
                error.setTitle("🔴 Error: You cannot move users");
                error.setDescription("You need Permission.VOICE_MOVE_OTHERS"
                        + "\n-> MessageMoveUser.java");
                event.getChannel().sendMessage(error.build()).queue();
                error.clear();
            }
        }
    }
}
