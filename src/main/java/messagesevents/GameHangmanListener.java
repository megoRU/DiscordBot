package messagesevents;

import games.Hangman;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GameHangmanListener extends ListenerAdapter {
    public final String PLAY = "!play";
    public final String PLAY_REGEX = "!play\\s+[A-Za-zА-Яа-я]+";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        long idUser = event.getMember().getUser().getIdLong();
        User user = event.getMember().getUser();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Hangman hangman;
        if (message.matches(PLAY)) {
            event.getChannel().sendMessage("To start playing write: `!play [Russian letter]`").queue();

            return;
        }
        if (message.matches(PLAY) || message.matches(PLAY_REGEX)) {
            String[] messages = message.split(" ", 2);
            hangman = new Hangman();
            if (message.matches(PLAY_REGEX) && !hangman.hasGame(user.getIdLong())) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
            }
            if (hangman.hasGame(user.getIdLong()) && messages.length > 1) {
                hangman = hangman.getGame(user.getIdLong());
                hangman.logic(guild, channel, user, messages[1]);
            }
        }
    }
}



