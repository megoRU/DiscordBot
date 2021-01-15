package messagesevents;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExchangeRates extends ListenerAdapter {

  private static final String URL = "https://www.cbr.ru/key-indicators/";
  private static final String[] values = {"курс доллара", "курс доллора", "курс евро", "курс",
      "евро", "доллар"};
  private static String[] elements;

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentRaw().toLowerCase();
    boolean contains = Arrays.asList(values).contains(message);
    if (contains) {
      event.getMessage().addReaction("\u2705").queue();
      event.getChannel().sendTyping().queue();
      parserSBR();
      DecimalFormat formatter = new DecimalFormat("#0.00");

      String usd =
          elements[5] + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
              + formatter.format(Double.parseDouble(elements[6].replace(",", "."))) + "⠀⠀⠀⠀⠀⠀⠀"
              + formatter.format(Double.parseDouble(elements[7].replace(",", ".")));
      String euro =
          elements[9] + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
              + formatter.format(Double.parseDouble(elements[10].replace(",", "."))) + "⠀⠀⠀⠀⠀⠀⠀"
              + formatter.format(Double.parseDouble(elements[11].replace(",", ".")));
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xf45642);
      info.setTitle("Курс рубля к иностранным валютам | ЦБ");
      info.setDescription("Код валюты⠀⠀|⠀⠀Покупка⠀⠀|⠀⠀Продажа\n"
          + usd + "\n"
          + euro);
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }

  private static void parserSBR() {
    Document doc = null;
    try {
      doc = Jsoup.connect(URL)
          .maxBodySize(0)
          .userAgent(
              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36")
          .referrer("https://www.yandex.com/")
          .get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert doc != null;
    Elements values = doc.getElementsByClass("key-indicator_table");
    elements = values.text().split(" ");
  }

  public static void getParser() {
    parserSBR();
  }

  public static String[] getElements() {
    return elements;
  }
}
