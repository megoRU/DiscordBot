package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageInfoHelp extends ListenerAdapter {

  public final String HELP = "!help";
  public final String HELP_WITH_OUT = "help";
  public final String INFO = "info";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    String idUser = event.getMember().getUser().getId();

    if (message.matches(HELP) || message.matches(HELP_WITH_OUT) || message.matches(INFO)) {
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xf45642);
      info.setTitle("Информация");
      info.setDescription("Команды: "
          + "\n[!help/help/info] -> Информация"
          + "\nclear + число" + " -> Удаляет сообщения. Нужно быть админом"
          + "\nЮтуб ссылка + пробел + время в минутах! -> конвертирует в короткую ссылку со временем"
      );
      info.addField("Ссылки:", "Сайт: [megolox.ru](https://megolox.ru)"
              + "\nTS3 Сервер: ` ts3.megolox.ru`"
              + "\nCS:GO Сервер: `176.96.238.167:27015`", false);
      info.addField("Создатель бота", "[mego](https://steamcommunity.com/id/megoRU)", false);
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}
