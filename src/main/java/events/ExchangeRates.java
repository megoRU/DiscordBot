package events;

import java.io.IOException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExchangeRates extends ListenerAdapter {

  private final static String URL = "https://www.cbr.ru/key-indicators/";
  public final String COURSE_DOLLAR = "курс доллара";
  public final String COURSE_EURO = "курс евро";
  public final String COURSE = "курс";
  public final String EURO_RU = "евро";
  public final String DOLLAR = "доллар";

  @Override
  public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    if (message.matches(COURSE_DOLLAR)
        || message.matches(COURSE_EURO)
        || message.matches(COURSE)
        || message.matches(EURO_RU)
        || message.matches(DOLLAR))
    {
      Document doc = null;
      try {
        doc = Jsoup.connect(URL)
            .maxBodySize(0)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.53 Safari/537.36")
            .referrer("https://www.yandex.com/")
            .get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      assert doc != null;
      Elements namesOfStations = doc.getElementsByClass("key-indicator_table");
      String[] text = namesOfStations.text().split(" ");
      String usd =
          text[5] + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
          + text[6] + "⠀⠀⠀⠀⠀⠀⠀"
          + text[7];
      String euro =
          text[9] + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
              + text[10] + "⠀⠀⠀⠀⠀⠀⠀"
              + text[11];
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xf45642);
      info.setTitle("Курсы рубля к иностранным валютам | ЦБ");
      info.setDescription("Код валюты⠀⠀ ⠀⠀⠀⠀Покупка⠀⠀ ⠀⠀⠀⠀Продажа\n"
          + "" + usd + "\n"
          + "" + euro);
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}
