package messagesevents;

import games.Hangman;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class GameHangmanListener extends ListenerAdapter {
    public final String HG = "!hg";
    public final String HG_REGEX = "!hg\\s+[A-Za-zА-Яа-я]+";
    public final String HG_REGEX_NO = "!hg\\s+[а-я]+";
    public final String HG_STOP = "!hg\\s+stop";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        User user = Objects.requireNonNull(event.getMember()).getUser();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Hangman hangman;

        if (message.equals(HG)) {
            event.getChannel().sendMessage("To start playing write: `!hg [one Russian letter]`" +
                    "\nTo end the game write: `!hg stop`").queue();
            return;
        }

        if (message.equals(HG) || message.matches(HG_REGEX)) {
            String[] messages = message.split(" ", 2);
            hangman = new Hangman();

            if (message.matches(HG_STOP) && !hangman.hasGame(user.getIdLong())) {
                event.getChannel().sendMessage("You are not playing now.\n").queue();
                return;
            }

            if (message.matches(HG_STOP) && hangman.hasGame(user.getIdLong())) {
                hangman.removeGame(user.getIdLong());
                event.getChannel().sendMessage("You have completed the game.\n" +
                        "To start a new game write: `!hg [one Russian letter]`").queue();
                return;
            }
            //Create game if !hangman.hasGame(user.getIdLong())
            if (message.matches(HG_REGEX) && !hangman.hasGame(user.getIdLong()) && message.matches(HG_REGEX_NO)) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
            }
            //Transfers data to the desired instance of the class
            if (hangman.hasGame(user.getIdLong()) && messages.length > 1 && message.matches(HG_REGEX_NO)) {
                hangman = hangman.getGame(user.getIdLong());
                hangman.logic(guild, channel, user, messages[1]);
            }

            if (!message.matches(HG_REGEX_NO)){
                event.getChannel().sendMessage("Only lower case and only one Russian letters!").queue();
            }
        }
    }
}



