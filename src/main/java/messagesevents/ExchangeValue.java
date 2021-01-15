package messagesevents;

import java.text.DecimalFormat;
import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ExchangeValue extends ListenerAdapter {

  private static final String[] RUB = {"рублей", "рубль", "рублях"};
  private static final String[] USD = {"долларах", "долларов", "доллоров", "доллар"};
  private static final String[] EUR = {"евро"};

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
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
            event.getChannel().sendMessage(messages[0] + " рублей в евро: "
                + "`" + addCommasToNumericString(formatter.format(result).replace(",", ".")) + "`")
                .queue();
            return;
          }

          if (containsEURFirst && containsRUBLast) {
            double format = Double.parseDouble(text[10].replace(",", "."));
            double result = Double.parseDouble(messages[0]) * format;
            event.getChannel().sendMessage(messages[0] + " евро в рублях: "
                + "`" + addCommasToNumericString(formatter.format(result).replace(",", ".")) + "`")
                .queue();
          }

        } catch (Exception exception) {
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Error: Incorrect syntax");
          error.setDescription(
              "Пример:" + "\n" + "10 долларов в рублях" + "\n" + "1000 рублей в евро");
          event.getChannel().sendMessage(error.build()).queue();
          error.clear();
          exception.printStackTrace();
        }
      }
    }
  }

  public String addCommasToNumericString(String digits) {
    String result = "";
    for (int i = 1; i <= digits.length(); ++i) {
      char ch = digits.charAt(digits.length() - i);
      result = ch + result;
      if (i % 3 == 0 && i > 3) {
        result = "," + result;
      }
    }
    return result;
  }

  private String getEndingWord(double num, String whatCurrency) {
    int intValue = (int) num;

    int preLastDigit = intValue % 100 / 10;
    if (preLastDigit == 1) {
      return whatCurrency.equals("USD") ? " долларов" : " рублей";
    }
    switch (intValue % 10) {
      case 1:
        return whatCurrency.equals("USD") ? " доллар" : " рубль";
      case 2:
      case 3:
      case 4:
        return whatCurrency.equals("USD") ? " доллара" : " рубля";
      case 5:
      default:
        return whatCurrency.equals("USD") ? " долларов" : " рублей";
    }
  }
}