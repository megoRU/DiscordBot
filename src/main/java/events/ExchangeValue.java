package events;

import java.text.DecimalFormat;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ExchangeValue extends ListenerAdapter {

    public final String RUB_TO_DOLLAR = "\\d+\\sрублей";
    public final String RUB_TO_DOLLAR_DOUBLE = "\\d+\\.\\d+\\sрублей";
    public final String RUB_TO_EURO = "\\d+\\sевро";
    public final String RUB_TO_EURO_DOUBLE = "\\d+\\.\\d+\\sевро";
    public final String DOLLAR_TO_RUB = "\\d+\\sдолларов";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase().trim();
        if (message.matches(RUB_TO_DOLLAR)
            || message.matches(RUB_TO_EURO)
            || message.matches(DOLLAR_TO_RUB)) {
            ExchangeRates exchangeRates = new ExchangeRates();
            exchangeRates.parserSBR();
            exchangeRates.getElements();
            DecimalFormat formatter = new DecimalFormat("#0.00");
            String[] text = exchangeRates.getElements();
            String[] messages = message.split(" ");
            if (message.matches(RUB_TO_DOLLAR) || message.matches(RUB_TO_DOLLAR_DOUBLE)) {
                double format = Double.parseDouble(text[7].replace(",", "."));
                double result = Double.parseDouble(messages[0]) / format;
                event.getChannel().sendMessage(messages[0] + " рублей в долларах: "
                    + "`" + formatter.format(result).replace(",", ".") + "`").queue();
            }
            if (message.matches(DOLLAR_TO_RUB)) {
                double format = Double.parseDouble(text[7].replace(",", "."));
                double result = Double.parseDouble(messages[0]) * format;
                event.getChannel().sendMessage(messages[0] + " долларов в рублях: "
                    + "`" + formatter.format(result).replace(",", ".") + "`").queue();
            }
            if (message.matches(RUB_TO_EURO) || message.matches(RUB_TO_EURO_DOUBLE)) {
                double format = Double.parseDouble(text[10].replace(",", "."));
                double result = Double.parseDouble(messages[0]) * format;
                event.getChannel().sendMessage(messages[0] + " евро в рублях: "
                    + "`" + formatter.format(result).replace(",", ".") + "`").queue();
            }
        }
    }
}
