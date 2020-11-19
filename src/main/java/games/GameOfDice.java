package games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

//TODO: It would be better if no one saw this.
//
public class GameOfDice extends ListenerAdapter {

    private static final ArrayList<String> playerList = new ArrayList<>();
    private static final ArrayList<Long> guild = new ArrayList<>();
    private static final ArrayList<Long> chat = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay().trim();
        String playerTag = event.getMessage().getAuthor().getId();
        String idPlayer = event.getMessage().getAuthor().getName() + "_" + playerTag;

        if (message.contains("!roll")) {
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

                int diceFirstPlayer = 1 + (int) (Math.random() * 6);
                int diceSecondPlayer = 1 + (int) (Math.random() * 6);
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
                // event.getChannel().sendMessage(gameOfDice.build()).queue();
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

                int diceFirstPlayer = 1 + (int) (Math.random() * 6);
                int diceSecondPlayer = 1 + (int) (Math.random() * 6);
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
                event.getJDA().getGuildById(guild.get(0)).getTextChannelById(chat.get(0)).sendMessage(gameOfDice.build()).queue();
                event.getGuild().getTextChannelById(idChannel).sendMessage(gameOfDice.build()).queue();
                // event.getChannel().sendMessage(gameOfDice.build()).queue();
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
        String first = String.valueOf(diceFirstPlayer);
        String second = String.valueOf(diceSecondPlayer);

        //1
        if (first.equals("1")) {

            if (second.equals("1")) {
                return "https://megolox.ru/disImages/1-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/1-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/1-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/1-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/1-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/1-6.png";
            }
        }

        //2

        if (first.equals("2")) {

            if (second.equals("1")) {
                return "https://megolox.ru/disImages/2-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/2-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/2-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/2-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/2-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/2-6.png";
            }
        }

        //3
        if (first.equals("3")) {


            if (first.equals("3") && second.equals("1")) {
                return "https://megolox.ru/disImages/3-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/3-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/3-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/3-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/3-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/3-6.png";
            }
        }

        //4
        if (first.equals("4")) {

            if (second.equals("1")) {
                return "https://megolox.ru/disImages/4-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/4-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/4-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/4-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/4-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/4-6.png";
            }
        }

        //
        if (first.equals("5")) {

            if (second.equals("1")) {
                return "https://megolox.ru/disImages/5-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/5-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/5-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/5-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/5-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/5-6.png";
            }
        }

        //
        if (first.equals("6")) {

            if (second.equals("1")) {
                return "https://megolox.ru/disImages/6-1.png";
            }

            if (second.equals("2")) {
                return "https://megolox.ru/disImages/6-2.png";
            }

            if (second.equals("3")) {
                return "https://megolox.ru/disImages/6-3.png";
            }

            if (second.equals("4")) {
                return "https://megolox.ru/disImages/6-4.png";
            }

            if (second.equals("5")) {
                return "https://megolox.ru/disImages/6-5.png";
            }

            if (second.equals("6")) {
                return "https://megolox.ru/disImages/6-6.png";
            }
        }

        return "https://megolox.ru/dice.png";
    }

}
