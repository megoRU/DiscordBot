package games;

import net.dv8tion.jda.api.Permission;
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
    if (event.getAuthor().isBot()) {
      return;
    }
    if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
      return;
    }
    String message = event.getMessage().getContentRaw().trim().toLowerCase();

    String prefix = HG;
    String prefix2 = HG_STOP;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "hg";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "hg stop";
    }

    long userIdLong = event.getAuthor().getIdLong();

    if (message.length() == 1) {
      if (!HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        return;
      }

      if (message.matches(HG_ONE_LETTER_ENG) && HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        event.getChannel().sendMessage("Поддерживается только кириллица!").queue();
        return;
      }

      if (HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        HangmanRegistry.getInstance().getActiveHangman().get(userIdLong).logic(message);
      }
      return;
    }

    if (message.equals(prefix) || message.matches(HG_ONE_LETTER) || message
        .matches(HG_ONE_LETTER_ENG) || message.equals(prefix2)) {

      if (message.equals(prefix) && HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        event.getChannel().sendMessage("Сейчас вы играете.\nНужно прислать одну букву в чат.").queue();
        return;
      }

      if (message.equals(prefix2) && HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        HangmanRegistry.getInstance().getActiveHangman().remove(userIdLong);
        event.getChannel().sendMessage("Вы завершили игру.\n" +
            "Чтобы начать новую игру напишите: `" + prefix + "`\n" +
            "Далее присылайте в чат по одной букве.").queue();
        return;
      }

      if (message.equals(prefix2) && !HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        event.getChannel().sendMessage("Вы сейчас не играете.").queue();
        return;
      }

      if (!HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        HangmanRegistry.getInstance().setHangman(userIdLong, new Hangman(event.getGuild(), event.getChannel(), event.getMember().getUser()));
      }

      if (HangmanRegistry.getInstance().hasHangman(userIdLong)) {
        HangmanRegistry.getInstance().getActiveHangman().get(userIdLong).startGame(event.getChannel(), event.getMember().getUser());
      }
    }
  }
}