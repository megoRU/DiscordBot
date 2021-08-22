package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ExchangeValue extends ListenerAdapter {

    private static final String[] RUB = {"рублей", "рубль", "рублях"};
    private static final String[] USD = {"долларах", "долларов", "доллоров", "доллар", "доллор"};
    private static final String[] EUR = {"евро"};

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
            return;
        }
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        String[] messages = message.split(" ");
        boolean isBot = event.getAuthor().isBot();

        if (messages.length > 3 && !isBot) {
            if (messages[1].equals(RUB[0])
                    || messages[1].equals(RUB[1])
                    || messages[1].equals(RUB[2])
                    || messages[1].equals(USD[0])
                    || messages[1].equals(USD[1])
                    || messages[1].equals(USD[2])
                    || messages[1].equals(USD[3])
                    || messages[1].equals(EUR[0])) {
                event.getMessage().addReaction("\u2705").queue();
                event.getChannel().sendTyping().queue();
                ExchangeRates.getParser();
                DecimalFormat formatter = new DecimalFormat("#0.00");
                String[] text = ExchangeRates.getElements();
                int size = messages.length - 1;
                //TODO: Может быть сделать это выше IF и тогда будет 3 проверки вместо 7
                boolean containsRUBFirst = Arrays.asList(RUB).contains(messages[1]);
                boolean containsEURFirst = Arrays.asList(EUR).contains(messages[1]);
                boolean containsUSDFirst = Arrays.asList(USD).contains(messages[1]);
                boolean containsUSDLast = Arrays.asList(USD).contains(messages[size]);
                boolean containsEURLast = Arrays.asList(EUR).contains(messages[size]);
                boolean containsRUBLast = Arrays.asList(RUB).contains(messages[size]);

                try {
                    if (containsRUBFirst && containsUSDLast) {
                        double format = Double.parseDouble(text[7].replace(",", "."));
                        double result = Double.parseDouble(messages[0]) / format;
                        event.getChannel().sendMessage(
                                messages[0] + getEndingWord(Integer.parseInt(messages[0]), "RUB") + " в долларах: "
                                        + "`" + addCommasToNumericString(formatter.format(result).replace(",", "."))
                                        + "`").queue();
                        return;
                    }

                    if (containsUSDFirst && containsRUBLast) {
                        double format = Double.parseDouble(text[7].replace(",", "."));
                        double result = Double.parseDouble(messages[0]) * format;
                        event.getChannel().sendMessage(
                                messages[0] + getEndingWord(Integer.parseInt(messages[0]), "USD") + " в рублях: "
                                        + "`" + addCommasToNumericString(formatter.format(result).replace(",", "."))
                                        + "`").queue();
                        return;
                    }

                    if (containsRUBFirst && containsEURLast) {
                        double format = Double.parseDouble(text[10].replace(",", "."));
                        double result = Double.parseDouble(messages[0]) / format;
                        event.getChannel().sendMessage(
                                        messages[0] + getEndingWord(Integer.parseInt(messages[0]), "RUB") + " в евро: "
                                                + "`" + addCommasToNumericString(formatter.format(result).replace(",", ".")) + "`")
                                .queue();
                        return;
                    }

                    if (containsEURFirst && containsRUBLast) {
                        double format = Double.parseDouble(text[10].replace(",", "."));
                        double result = Double.parseDouble(messages[0]) * format;
                        event.getChannel().sendMessage(
                                        messages[0] + getEndingWord(Integer.parseInt(messages[0]), "EUR") + " в рублях: "
                                                + "`" + addCommasToNumericString(formatter.format(result).replace(",", ".")) + "`")
                                .queue();
                    }

                } catch (Exception exception) {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(0xff3923);
                    error.setTitle("🔴 Error: Incorrect syntax");
                    error.setDescription(
                            "Пример:" + "\n" + "10 долларов в рублях" + "\n" + "1000 рублей в евро");
                    event.getChannel().sendMessageEmbeds(error.build()).queue();
                    error.clear();
                    exception.printStackTrace();
                }
            }
        }
    }

    public String addCommasToNumericString(String digits) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= digits.length(); ++i) {
            char ch = digits.charAt(digits.length() - i);
            result.insert(0, ch);
            if (i % 3 == 0 && i > 3 && i != digits.length()) {
                result.insert(0, " ");
            }
        }
        return result.toString();
    }

    private String getEndingWord(int num, String whatCurrency) {

        int preLastDigit = num % 100 / 10;
        if (preLastDigit == 1) {
            switch (whatCurrency) {
                case "USD":
                    return " долларов";
                case "RUB":
                    return " рублей";
                case "EUR":
                    return " евро";
            }
        }
        switch (num % 10) {
            case 1:
                switch (whatCurrency) {
                    case "USD":
                        return " доллар";
                    case "RUB":
                        return " рубль";
                    case "EUR":
                        return " евро";
                }
            case 2:
            case 3:
            case 4:
                switch (whatCurrency) {
                    case "USD":
                        return " доллара";
                    case "RUB":
                        return " рубля";
                    case "EUR":
                        return " евро";
                }
            case 5:
            default:
                switch (whatCurrency) {
                    case "USD":
                        return " долларов";
                    case "RUB":
                        return " рублей";
                    case "EUR":
                        return " евро";
                }
                return "Error: send to: https://discord.gg/UrWG3R683d";
        }
    }
}