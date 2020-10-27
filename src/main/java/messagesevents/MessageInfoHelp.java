package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageInfoHelp extends ListenerAdapter {

  public final String HELP = "!help";
  public final String HELP_WITH_OUT = "help";
  public final String INFO = "info";
  public final String INFO_RU = "инфо";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();

    if (message.matches(HELP) || message.matches(HELP_WITH_OUT) || message.matches(INFO) || message.matches(INFO_RU)) {
      String avatarUrl = null;
      String avatarFromEvent = event.getMessage().getAuthor().getAvatarUrl();
      if (avatarFromEvent == null) {
        avatarUrl = "https://cdn.discordapp.com/avatars/754093698681274369/a6b1eb1cdcc29e0bd7e14228b17a28aa.png";
      }
      if (avatarFromEvent != null) {
        avatarUrl = avatarFromEvent;
      }
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xa224db);
      info.setAuthor(event.getAuthor().getName(), null, avatarUrl);
      info.setDescription("Команды: "
          + "\n`[!help/help/info]` - Информация."
          + "\n`[курс доллара, курс евро, курс, евро, доллар]` - Данные от ЦБ."
          + "\n`[!uptime/uptime]` - uptime bot."
          + "\n`[!shutdown/shutdown]` - Бот выключается на Linux сервере."
          + "\n`[!ping/ping]` - время затраченное на выполнение команды."
          + "\n`[!колличество/колво/колво топ/колличество]` - Топ 3 по подключению."
          + "\n`clear + число: >= 2 и <= 100` - Удаляет сообщения."
          + "\n`[бухать/бухнем/бухаем/бухаем?]` - Бот точно решит за Вас!"
          + "\n`[!ищи/ищи]` - !ищи + пробел + какой-то запрос в google [g.zeos.in](https://g.zeos.in/) "
          + "\nЮтуб ссылка + пробел + минуты + пробел + секунды если есть - конвертирует в короткую ссылку со временем"
      );
      info.addField("Ссылки:", "Сайт: [megolox.ru](https://megolox.ru)"
              + "\nTS3 Сервер: `ts3.megolox.ru`"
              + "\nCS:GO Сервер: `176.96.238.167:27015`", false);
      info.addField("Создатель бота", "[mego](https://steamcommunity.com/id/megoRU)", false);
      info.addField("Лицензия", "[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0)", false);
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}
