package games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import startbot.BotStart;

import java.util.ArrayList;
import java.util.HashMap;

public class Hangman {

    private String WORD = null;
    private final String[] ALL_WORDS = {"копирайтер", "деятельность", "любопытность", "выхохолощенный",
            "йогурт", "галлюциноген", "перпендикуляр", "скоросшиватель", "толерантность", "эксгумация",
            "чехословакия", "либерализм", "экспонат", "пышность", "скабрёзность", "шаловливость",
            "экспозиция", "индульгенция", "контрацептив", "шкворень", "эпиграф", "эпитафия", "барбекю",
            "жульен", "энцефалопатия", "парашютист", "импозантность", "индифферент", "демультипликатор",
            "педикулёз", "выхухоль", "россомаха", "сущность", "поэтапность", "напыщенность", "возвышенность"};
    private char[] strToArray;
    private String WORD_HIDDEN = "";
    protected ArrayList<String> wordList = new ArrayList<>();
    protected ArrayList<Integer> index = new ArrayList<>();
    protected ArrayList<String> usedLetters = new ArrayList<>();
    private boolean isLetterPresent;
    private Integer count = 0;
    private Integer count2 = 0;
    private static final HashMap<Long, Hangman> games = new HashMap<>();
    private User user;
    private Guild guild;
    private TextChannel channel;

    public Hangman(Guild guild, TextChannel channel, User user) {
        this.guild = guild;
        this.channel = channel;
        this.user = user;
    }

    public Hangman() {
    }

    public void logic(Guild guild, TextChannel channel, User user, String inputs) {
        System.out.println(WORD);
        if(WORD == null) {
            int randomWord = (int) Math.floor(Math.random() * ALL_WORDS.length);
            WORD = ALL_WORDS[randomWord];
            System.out.println(WORD);
            strToArray = WORD.toCharArray(); // Преобразуем строку str в массив символов (char)
            hideWord(WORD.length());
        }
        System.out.println(WORD);

        if (WORD_HIDDEN.contains("_")) {

            for (String listLoop : usedLetters) {
                if (listLoop.contains(inputs)) {
                    isLetterPresent = true;
                    break;
                }
                if (!listLoop.contains(inputs)) {
                    isLetterPresent = false;
                }
            }
            //System.out.println();

            if (inputs.length() == 1) {

                if (count2 > 5) {
                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Вы проиграли!\n"
                            + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                    BotStart.jda.getGuildById(guild.getId())
                            .getTextChannelById(channel.getId())
                            .sendMessage(info.build()).queue();
                    info.clear();
                    WORD = null;
                    removeGame(user.getIdLong());

                    return;
                }

                if (isIsLetterPresent()) {

                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Вы уже использовали эту букву!\n"
                            + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                    BotStart.jda.getGuildById(getGuild().getId())
                            .getTextChannelById(getChannel().getId())
                            .sendMessage(info.build()).queue();
                    info.clear();

                    //  System.out.println("Вы уже использовали эту букву!");
                }

                if (!isIsLetterPresent()) {
                    usedLetters.add(inputs);
                }

                if (!isIsLetterPresent() && WORD.contains(inputs)) {
                    char c = inputs.charAt(0);
                    checkMethod(strToArray, c);

                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Вы угадали букву!\n"
                            + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                    BotStart.jda.getGuildById(getGuild().getId())
                            .getTextChannelById(getChannel().getId())
                            .sendMessage(info.build()).queue();
                    info.clear();

                    if (!wordList.get(wordList.size() - 1).contains("_")) {

                        EmbedBuilder infof = new EmbedBuilder();
                        infof.setColor(0x00FF00);
                        infof.setTitle("Виселица");
                        infof.setDescription("Игра завершена, вы победили!\n"
                                + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                        BotStart.jda.getGuildById(getGuild().getId())
                                .getTextChannelById(getChannel().getId())
                                .sendMessage(infof.build()).queue();
                        infof.clear();
                        //System.out.println("Игра завершена, вы победили!");
                        wordList.clear();
                        WORD = null;
                        removeGame(user.getIdLong());
                        return;
                    }
                    return;
                }

                if (!WORD.contains(inputs)) {
                    count2++;
                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Такой буквы нет!\n"
                            + "Осталось попыток: `" + (6 - count2) + "`\n"
                            + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                    BotStart.jda.getGuildById(getGuild().getId())
                            .getTextChannelById(getChannel().getId())
                            .sendMessage(info.build()).queue();
                    info.clear();
                    return;
                    // System.out.println("Такой буквы нет!");
                }
            }
            if (inputs.length() > 1) {

                EmbedBuilder info = new EmbedBuilder();
                info.setColor(0x00FF00);
                info.setTitle("Виселица");
                info.setDescription("Нужна 1 буква!\n"
                        + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`");

                BotStart.jda.getGuildById(getGuild().getId())
                        .getTextChannelById(getChannel().getId())
                        .sendMessage(info.build()).queue();
                info.clear();

                //  System.out.println("Нужна 1 буква!");
            }
        }
    }

    protected HashMap<Long, Hangman> getGames() {
        return games;
    }

    public void setGame(long userId, Hangman game) {
        games.put(userId, game);
    }

    public boolean hasGame(long userId) {
        return games.containsKey(userId);
    }

    public Hangman getGame(long userId) {
        return games.get(userId);
    }

    public void removeGame(long userId) {
        games.remove(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public TextChannel getChannel() {
        return channel;
    }

    //Создает скрытую линию из длины слова
    private void hideWord(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append('_');
        }
        WORD_HIDDEN = sb.toString();
    }

    //заменяет "_" на букву которая есть в слове
    private String replacementLetters(int length) {
        if (count < 1) {
            wordList.add(WORD_HIDDEN);
            count++;
        }
        int size = wordList.size() - 1;
        StringBuilder sb = new StringBuilder(wordList.get(size));
        for (int i = 0; i < index.size(); i++) {
            sb.replace(index.get(i), index.get(i) + 1, String.valueOf(strToArray[length]));
        }
        wordList.add(sb.toString());
        index.clear();
        return sb.toString();
    }

    //Ищет все одинаковые буквы и записывает в коллекцию
    private void checkMethod(char[] checkArray, char letter) {
        for (int i = 0; i < checkArray.length; i++) {
            if (checkArray[i] == letter) {
                checkArray[i] = letter;
                index.add(i);
                //System.out.println(checkArray[i]);
            }
        }
    }

    private boolean isIsLetterPresent() {
        return isLetterPresent;
    }
}