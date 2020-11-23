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
    public final String HG_ONE_LETTER = "[а-я]";


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim().toLowerCase();

        if (message.matches(HG_ONE_LETTER) && message.length() == 1) {
            User user = Objects.requireNonNull(event.getMember()).getUser();
            TextChannel channel = event.getChannel();
            Guild guild = event.getGuild();
            Hangman hangman;
            hangman = new Hangman();

            if (!hangman.hasGame(user.getIdLong())) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
            }
            if (hangman.hasGame(user.getIdLong())) {

                hangman = hangman.getGame(user.getIdLong());
                hangman.logic(guild, channel, user, message);
            }

            return;
        }

        if (message.equals(HG)) {
            User user = Objects.requireNonNull(event.getMember()).getUser();
            TextChannel channel = event.getChannel();
            Guild guild = event.getGuild();
            Hangman hangman;
            hangman = new Hangman();

            if (message.matches(HG_STOP) && !hangman.hasGame(user.getIdLong())) {
                event.getChannel().sendMessage("You are not playing now.\n").queue();
                return;
            }

            if (message.matches(HG_STOP) && hangman.hasGame(user.getIdLong())) {
                hangman.removeGame(user.getIdLong());
                event.getChannel().sendMessage("You have completed the game.\n" +
                        "To start a new game write: `!hg`\n" +
                        "When send Russian letters to chat").queue();
                return;
            }

            if (!hangman.hasGame(user.getIdLong()) && message.matches(HG)) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
                hangman = hangman.getGame(user.getIdLong());
                hangman.startGame(guild, channel, user);
            return;
            }

            if (!message.matches(HG_ONE_LETTER) && hangman.hasGame(user.getIdLong())){
                event.getChannel().sendMessage("Only lower case and only one Russian letters!").queue();
            }
        }
    }
}


