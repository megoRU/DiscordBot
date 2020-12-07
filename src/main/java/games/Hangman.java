package games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import startbot.BotStart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Hangman {

    private String WORD = null;
    private final String[] ALL_WORDS = {"копирайтер", "деятельность", "любопытность", "",
            "всласть", "лесопромышленность", "психология", "скоросшиватель", "толерантность", "эксгумация",
            "астрономия", "либерализм", "экспонат", "пышность", "бодибилдинг", "шаловливость",
            "экспозиция", "индульгенция", "контрацептив", "безмятежность", "барбекю",
            "кулинария", "энцефалопатия", "парашютист", "сущность", "поэтапность", "напыщенность",
            "возвышенность", "интерпретация", "термометр", "градусник"};
    private char[] strToArray;
    private String WORD_HIDDEN = "";
    private final ArrayList<String> wordList = new ArrayList<>();
    private final ArrayList<Integer> index = new ArrayList<>();
    private final ArrayList<String> usedLetters = new ArrayList<>();
    private boolean isLetterPresent;
    private Integer count = 0;
    private Integer count2 = 1;
    private static final HashMap<Long, Hangman> games = new HashMap<>();
    private User user;
    private Guild guild;
    private TextChannel channel;
    private final Random random = new Random();

    public Hangman(Guild guild, TextChannel channel, User user) {
        this.guild = guild;
        this.channel = channel;
        this.user = user;
    }

    public Hangman() {
    }

    public void startGame(Guild guild, TextChannel channel, User user) {
        if (WORD == null) {
            WORD = ALL_WORDS[random.nextInt(ALL_WORDS.length)];
            strToArray = WORD.toCharArray(); // Преобразуем строку str в массив символов (char)
            hideWord(WORD.length());
        }
        EmbedBuilder start = new EmbedBuilder();
        start.setColor(0x00FF00);
        start.setTitle("Виселица");
        start.setDescription("Игра началась!\n"
                + "Теперь просто отправлять по одной букве в чат\n **без**" + " `!` " + "и любых других символов"
                + getDescription(count2)
                + "Текущее слово: `" + hideWord(WORD.length()) + "`"
                + "\nИгрок: <@" + user.getIdLong() + ">");

        BotStart.jda.getGuildById(guild.getId())
                .getTextChannelById(channel.getId())
                .sendMessage(start.build()).queue();
        start.clear();
    }

    public void logic(Guild guild, TextChannel channel, User user, String inputs) {
        if (WORD == null) {
            int randomWord = (int) Math.floor(Math.random() * ALL_WORDS.length);
            WORD = ALL_WORDS[randomWord];
            strToArray = WORD.toCharArray(); // Преобразуем строку str в массив символов (char)
            hideWord(WORD.length());
        }

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

            if (inputs.length() == 1) {

                if (isIsLetterPresent()) {

                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Вы уже использовали эту букву!\n"
                            + "У вас попыток: `" + (6 - count2) + "`\n"
                            + getDescription(count2)
                            + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`"
                            + "\nИгрок: <@" + user.getIdLong() + ">");

                    BotStart.jda.getGuildById(guild.getId())
                            .getTextChannelById(channel.getId())
                            .sendMessage(info.build()).queue();
                    info.clear();
                    return;
                }

                if (!isIsLetterPresent()) {
                    usedLetters.add(inputs);
                }

                if (!isIsLetterPresent() && WORD.contains(inputs)) {
                    char c = inputs.charAt(0);
                    checkMethod(strToArray, c);
                    String result = replacementLetters(WORD.indexOf(inputs));

                    if (!wordList.get(wordList.size() - 1).contains("_")) {
                        EmbedBuilder infof = new EmbedBuilder();
                        infof.setColor(0x00FF00);
                        infof.setTitle("Виселица");
                        infof.setDescription("Игра завершена, вы победили!\n"
                                + getDescription(count2)
                                + "Текущее слово: `" + result + "`"
                                + "\nИгрок: <@" + user.getIdLong() + ">");

                        BotStart.jda.getGuildById(guild.getId())
                                .getTextChannelById(channel.getId())
                                .sendMessage(infof.build()).queue();
                        infof.clear();
                        WORD = null;
                        removeGame(user.getIdLong());
                        return;
                    }

                    EmbedBuilder info = new EmbedBuilder();
                    info.setColor(0x00FF00);
                    info.setTitle("Виселица");
                    info.setDescription("Вы угадали букву!\n"
                            + "У вас попыток: `" + (6 - count2) + "`\n"
                            + getDescription(count2)
                            + "Текущее слово: `" + result + "`"
                            + "\nИгрок: <@" + user.getIdLong() + ">");

                    BotStart.jda.getGuildById(guild.getId())
                            .getTextChannelById(channel.getId())
                            .sendMessage(info.build()).queue();
                    info.clear();
                    return;
                }

                if (!WORD.contains(inputs)) {
                    count2++;

                    if (count2 > 5) {
                        EmbedBuilder info = new EmbedBuilder();
                        info.setColor(0x00FF00);
                        info.setTitle("Виселица");
                        info.setDescription("Вы проиграли!\n"
                                + getDescription(count2)
                                + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`"
                                + "\n Слово которое было: `" + WORD + "`"
                                + "\nИгрок: <@" + user.getIdLong() + ">");

                        BotStart.jda.getGuildById(guild.getId())
                                .getTextChannelById(channel.getId())
                                .sendMessage(info.build()).queue();
                        info.clear();
                        WORD = null;
                        removeGame(user.getIdLong());
                        return;
                    }

                    if (count2 <= 5) {
                        EmbedBuilder wordNotFound = new EmbedBuilder();
                        wordNotFound.setColor(0x00FF00);
                        wordNotFound.setTitle("Виселица");
                        wordNotFound.setDescription("Такой буквы нет!\n"
                                + "Осталось попыток: `" + (6 - count2) + "`\n"
                                + getDescription(count2)
                                + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`"
                                + "\nИгрок: <@" + user.getIdLong() + ">");

                        BotStart.jda.getGuildById(guild.getId())
                                .getTextChannelById(channel.getId())
                                .sendMessage(wordNotFound.build()).queue();
                        wordNotFound.clear();
                        return;
                    }
                    // System.out.println("Такой буквы нет!");
                }
                return;
            }
        }
        if (inputs.length() > 1) {
            EmbedBuilder info = new EmbedBuilder();
            info.setColor(0x00FF00);
            info.setTitle("Виселица");
            info.setDescription("Нужна 1 буква!\n"
                    + "Осталось попыток: `" + (6 - count2) + "`\n"
                    + getDescription(count2)
                    + "Текущее слово: `" + replacementLetters(WORD.indexOf(inputs)) + "`"
                    + "\nИгрок: <@" + user.getIdLong() + ">");

            BotStart.jda.getGuildById(guild.getId())
                    .getTextChannelById(channel.getId())
                    .sendMessage(info.build()).queue();
            info.clear();
            //  System.out.println("Нужна 1 буква!");
        }
    }

    private String getDescription(int count) {
        return "```"
                + (count > 0 ? "|‾‾‾‾‾‾|      " : " ")
                + "   \n|     "
                + (count > 1 ? "🎩" : " ")
                + "   \n|     "
                + (count > 2 ? "\uD83E\uDD75" : " ")
                + "   \n|   "
                + (count > 3 ? "👌👕\uD83E\uDD19" : " ")
                + "   \n|     "
                + (count > 4 ? "🩳" : " ")
                + "   \n|    "
                + (count > 5 ? "👞👞" : " ")
                + "   \n|     \n|__________\n\n"
                + "```";
    }

    //Создает скрытую линию из длины слова
    private String hideWord(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append('_');
        }
        return WORD_HIDDEN = sb.toString();
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

    private boolean isIsLetterPresent() {
        return isLetterPresent;
    }
}