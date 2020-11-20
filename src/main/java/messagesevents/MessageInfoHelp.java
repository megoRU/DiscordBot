package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageInfoHelp extends ListenerAdapter {

  public final String HELP = "!help";
  public final String HELP_WITH_OUT = "help";
  public final String INFO_WITH_OUT = "info";
  public final String INFO = "!info";

  public final String INFO_RU = "инфо";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();

    if (message.matches(HELP) || message.matches(HELP_WITH_OUT) || message.matches(INFO_WITH_OUT)
            || message.matches(INFO) || message.matches(INFO_RU)) {
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
      info.setDescription("Commands:"
          + "\n`[!help/!info/help/info]` - Information."
          + "\n`[!roll]` - The Game of Dice. "
          + "\n`[!play]` - Game: Hangman/Виселица. "
          + "\n`[курс доллара, курс евро, курс, евро, доллар]` - Данные от ЦБ."
          + "\n`[!uptime/uptime]` - uptime bot."
          + "\n`[!shutdown/shutdown/sd]` - The bot is shutting down on the Linux server. (Only the bot creator can use!)"
          + "\n`[!ping/ping]` - API response."
          + "\n`[!колличество/колво/колличество]` - How many times have you connected to channels."
          + "\n`[колво топ]` - Top 3 by connection."
          + "\n`!clear + number: >= 2 & <= 100` - Deletes messages."
          + "\n`[бухать/бухнем/бухаем/бухаем?]` - Бот точно решит за Вас!"
          + "\n`[!ищи/ищи]` - !ищи + пробел + какой-то запрос в google [g.zeos.in](https://g.zeos.in/) "
          + "\n`YouTube link + space + minutes + space + seconds if present` - converts to a short link with time"
      );
      info.addField("Links:", "[megolox.ru](https://megolox.ru)", false);
      info.addField("Bot creator", "[mego](https://steamcommunity.com/id/megoRU)", false);
      info.addField("License", "[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0)", false);
      info.addField("Support Server", "[Click me](https://discord.com/invite/UrWG3R683d)", false);

      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}
