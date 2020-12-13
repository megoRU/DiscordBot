package games;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;

public class GameHangmanListener extends ListenerAdapter {
    private static final String HG = "!hg";
    private static final String HG_STOP = "!hg stop";
    private static final String HG_ONE_LETTER = "[А-Яа-я]";
    private static final String HG_ONE_LETTER_ENG = "[A-Za-z]";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim().toLowerCase();

        String prefix = HG;
        String prefix2 = HG_STOP;

        if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
            prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "hg";
            prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "hg stop";
        }

        if (message.length() == 1) {
            User user = event.getMember().getUser();
            TextChannel channel = event.getChannel();
            Guild guild = event.getGuild();
            Hangman hangman;
            hangman = new Hangman();

            if (!hangman.hasGame(user.getIdLong())) {
                return;
            }

            if (message.matches(HG_ONE_LETTER_ENG) && hangman.hasGame(user.getIdLong())) {
                event.getChannel().sendMessage("Поддерживается только кириллица!").queue();
                return;
            }

            if (hangman.hasGame(user.getIdLong())) {
                hangman = hangman.getGame(user.getIdLong());
                hangman.logic(guild, channel, user, message);
            }
            return;
        }

        if (message.equals(prefix) || message.matches(HG_ONE_LETTER) || message.matches(HG_ONE_LETTER_ENG) || message.equals(prefix2)) {
            User user = event.getMember().getUser();
            TextChannel channel = event.getChannel();
            Guild guild = event.getGuild();
            Hangman hangman;
            hangman = new Hangman();

            if (message.equals(prefix) && hangman.hasGame(user.getIdLong())) {
                event.getChannel().sendMessage("Сейчас вы играете.\nНужно прислать одну букву в чат.").queue();
                return;
            }

            if (message.equals(prefix2) && hangman.hasGame(user.getIdLong())) {
                hangman.removeGame(user.getIdLong());
                event.getChannel().sendMessage("Вы завершили игру.\n" +
                        "Чтобы начать новую игру напишите: `" + prefix + "`\n" +
                        "Далее присылайте в чат по одной букве.").queue();
                return;
            }

            if (message.equals(prefix2) && !hangman.hasGame(user.getIdLong())) {
                event.getChannel().sendMessage("Вы сейчас не играете.\n").queue();
                return;
            }

            if (!hangman.hasGame(user.getIdLong())) {
                hangman.setGame(user.getIdLong(), new Hangman(guild, channel, user));
            }

            if (hangman.hasGame(user.getIdLong())) {
                hangman = hangman.getGame(user.getIdLong());
                try {
                    hangman.startGame(guild, channel, user);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}