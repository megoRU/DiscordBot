package games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import startbot.BotStart;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameOfDice extends ListenerAdapter {

  private static final ArrayList<String> playerList = new ArrayList<>();
  private static final ArrayList<Long> guild = new ArrayList<>();
  private static final ArrayList<Long> chat = new ArrayList<>();
  private static final String ROLL = "!roll";
  private static final Random random = new Random();

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentDisplay().trim();
    String prefix = ROLL;

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "roll";
    }

    if (message.equals(prefix)) {
      String playerTag = event.getMessage().getAuthor().getId();
      String idPlayer = event.getMessage().getAuthor().getName() + "_" + playerTag;
      playerList.add(idPlayer);
      guild.add(event.getGuild().getIdLong());
      chat.add(event.getMessage().getChannel().getIdLong());

      if (playerList.size() == 2 && guild.get(0).equals(guild.get(1))) {

        if (playerList.get(0).equals(playerList.get(1))) {
          guild.remove(1);
          chat.remove(1);
          playerList.remove(1);
          event.getMessage().addReaction("\u26D4").queue();
          event.getChannel().sendMessage("You are already on the list!").queue();
          return;
        }

        int diceFirstPlayer = random.nextInt(6) + 1;
        int diceSecondPlayer = random.nextInt(6) + 1;
        long idChannel = event.getChannel().getIdLong();
        int index = playerList.get(0).indexOf("_");
        int index2 = playerList.get(1).indexOf("_");

        EmbedBuilder gameOfDice = new EmbedBuilder();
        gameOfDice.setTitle("Game of Dice");
        gameOfDice.setDescription("Player 1: **" + diceFirstPlayer
            + "**" + "\nPlayer 2: **" + diceSecondPlayer +
            "\n\n**Winner: **" +
            whoWin(diceFirstPlayer, playerList.get(0),
                diceSecondPlayer, playerList.get(1))
            + "**"
            + "\n\nRolled by: **" + playerList.get(0).substring(0, index)
            + "**" + " & " + "**" + playerList.get(1).substring(0, index2)
            + "**");
        gameOfDice.setColor(Color.WHITE);
        gameOfDice.setThumbnail(choiceOfSides(diceFirstPlayer, diceSecondPlayer));
        event.getGuild().getTextChannelById(idChannel).sendMessage(gameOfDice.build()).queue();
        playerList.clear();
        guild.clear();
        chat.clear();
        return;
      }

      if (playerList.size() == 2 && !guild.get(0).equals(guild.get(1))) {

        if (playerList.get(0).equals(playerList.get(1))) {
          guild.remove(1);
          chat.remove(1);
          playerList.remove(1);
          event.getMessage().addReaction("\u26D4").queue();
          event.getChannel().sendMessage("You are already on the list!").queue();
          return;
        }

        int diceFirstPlayer = random.nextInt(6) + 1;
        int diceSecondPlayer = random.nextInt(6) + 1;
        long idChannel = event.getChannel().getIdLong();
        int index = playerList.get(0).indexOf("_");
        int index2 = playerList.get(1).indexOf("_");

        EmbedBuilder gameOfDice = new EmbedBuilder();
        gameOfDice.setTitle("Game of Dice");
        gameOfDice.setDescription("Player 1: **" + diceFirstPlayer
            + "**" + "\nPlayer 2: **" + diceSecondPlayer +
            "\n\n**Winner: **" +
            whoWin(diceFirstPlayer, playerList.get(0),
                diceSecondPlayer, playerList.get(1))
            + "**"
            + "\n\nRolled by: **" + playerList.get(0).substring(0, index)
            + "**" + " & " + "**" + playerList.get(1).substring(0, index2)
            + "**");
        gameOfDice.setColor(Color.WHITE);
        gameOfDice.setThumbnail(choiceOfSides(diceFirstPlayer, diceSecondPlayer));
        event.getJDA().getGuildById(guild.get(0)).getTextChannelById(chat.get(0))
            .sendMessage(gameOfDice.build()).queue();
        event.getGuild().getTextChannelById(idChannel).sendMessage(gameOfDice.build()).queue();
        playerList.clear();
        guild.clear();
        chat.clear();
        return;
      }

      if (playerList.size() == 1) {
        event.getChannel().sendMessage("Waiting for one more player!").queue();
      }
    }
  }

  private String whoWin(Integer first, String firstId, Integer second, String secondId) {
    if (first > second) {
      int index = firstId.indexOf("_");
      return firstId.substring(0, index);
    }
    if (second > first) {
      int index = secondId.indexOf("_");
      return secondId.substring(0, index);
    }
    return "Draw!";
  }

  private String choiceOfSides(int diceFirstPlayer, int diceSecondPlayer) {
    return "https://megoru.ru/disImages/" + diceFirstPlayer + "-" + diceSecondPlayer + ".png";
  }

}