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
    public final String PLAY_STOP = "!play\\s+[stop]+";


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        long idUser = event.getMember().getUser().getIdLong();
        User user = event.getMember().getUser();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Hangman hangman;
        if (message.matches(PLAY)) {
            event.getChannel().sendMessage("To start playing write: `!play [one Russian letter]`" +
                    "\n To end the game write: `!play stop`").queue();
            return;
        }

        if (message.matches(PLAY) || message.matches(PLAY_REGEX)) {
            String[] messages = message.split(" ", 2);
            hangman = new Hangman();

            if (message.matches(PLAY_STOP) && hangman.hasGame(user.getIdLong())) {
                hangman.removeGame(user.getIdLong());
                event.getChannel().sendMessage("You have completed the game.\n" +
                        "To start a new game write: `!play [one Russian letter]`").queue();
                return;
            }
            //Create game if !hangman.hasGame(user.getIdLong())
            if (message.matches(PLAY_REGEX) && !hangman.hasGame(user.getIdLong())) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
            }
            //Transfers data to the desired instance of the class
            if (hangman.hasGame(user.getIdLong()) && messages.length > 1) {
                hangman = hangman.getGame(user.getIdLong());
                hangman.logic(guild, channel, user, messages[1]);
            }
        }
    }
}



