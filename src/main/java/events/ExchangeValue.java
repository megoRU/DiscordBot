package events;

import java.text.DecimalFormat;
import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ExchangeValue extends ListenerAdapter {
    private final String[] RUB = {"рублей", "рубль", "рублях"};
    private final String[] USD = {"долларах", "долларов", "доллоров"};
    private final String[] EUR = {"евро"};

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        String[] messages = message.split(" ");
        if (message.contains(RUB[0])
            || message.contains(RUB[1])
            || message.contains(USD[0])
            || message.contains(USD[1])
            || message.contains(USD[2])
            || message.contains(EUR[0])) {
            event.getChannel().sendTyping().queue();
            ExchangeRates exchangeRates = new ExchangeRates();
            exchangeRates.parserSBR();
            exchangeRates.getElements();
            DecimalFormat formatter = new DecimalFormat("#0.00");
            String[] text = exchangeRates.getElements();
            int size = messages.length - 1;
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
                    event.getChannel().sendMessage(messages[0] + " рублей в долларах: "
                        + "`" + formatter.format(result).replace(",", ".") + "`").queue();
                    return;
                }

                if (containsUSDFirst && containsRUBLast) {
                    double format = Double.parseDouble(text[7].replace(",", "."));
                    double result = Double.parseDouble(messages[0]) * format;
                    event.getChannel().sendMessage(messages[0] + " долларов в рублях: "
                        + "`" + formatter.format(result).replace(",", ".") + "`").queue();
                    return;
                }

                if (containsRUBFirst && containsEURLast) {
                    double format = Double.parseDouble(text[10].replace(",", "."));
                    double result = Double.parseDouble(messages[0]) / format;
                    event.getChannel().sendMessage(messages[0] + " рублей в евро: "
                        + "`" + formatter.format(result).replace(",", ".") + "`").queue();
                    return;
                }

                if (containsEURFirst && containsRUBLast) {
                    double format = Double.parseDouble(text[10].replace(",", "."));
                    double result = Double.parseDouble(messages[0]) * format;
                    event.getChannel().sendMessage(messages[0] + " евро в рублях: "
                        + "`" + formatter.format(result).replace(",", ".") + "`").queue();
                    return;
                }

            } catch (Exception exception) {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xff3923);
                error.setTitle("🔴 Error: Incorrect syntax");
                error.setDescription("Пример:" + "\n" + "10 долларов в рублях" + "\n" + "1000 рублей в евро");
                event.getChannel().sendMessage(error.build()).queue();
                error.clear();
                exception.printStackTrace();
                return;
            }
        }
    }
}
