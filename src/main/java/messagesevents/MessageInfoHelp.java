package messagesevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import startbot.BotStart;

public class MessageInfoHelp extends ListenerAdapter {

  public final String HELP = "!help";
  public final String HELP_WITH_OUT = "help";
  public final String INFO_WITH_OUT = "info";
  public final String INFO = "!info";
  public final String INFO_RU = "инфо";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw().toLowerCase();
    String prefix = HELP;
    String prefix2 = INFO;
    String p = "!";

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "help";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "info";
      p = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals(prefix) || message.equals(HELP_WITH_OUT) || message.equals(INFO_WITH_OUT)
            || message.equals(prefix2) || message.equals(INFO_RU)) {
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
          + "\n`[*prefix <symbol>/*prefix reset]` - Changes the prefix."
          + "\n`[" + p + "help/" + p + "info/help/info]` - Information."
          + "\n`[" + p + "roll]` - The Game of Dice."
          + "\n`[" + p + "poll <text>]` - Create a poll."
          + "\n`[" + p + "hg]` - Game: Hangman/Виселица."
          + "\n`[" + p + "kick]` - Example: " + p + "kick <@user>/" + p + "kick <@user> <reason>"
          + "\n`[" + p + "ban]` - Example: " + p + "ban <@user> <days>/" + p + "ban <@user> <days> <reason>"
          + "\n`[" + p + "bitrate]` - Change the channel bitrate. Example: " + p + "bitrate 96"
          + "\n`[курс доллара, курс евро, курс, евро, доллар]` - Данные от ЦБ к рублю."
          + "\n`[100 долларов в рублях]` - Переведет по курсу. Доступны валюты: USD, EUR, RUB"
          + "\n`[" + p +"uptime/uptime]` - uptime bot."
          + "\n`[" + p +"shutdown/shutdown/sd]` - The bot is shutting down on the Linux server. (Only the bot creator can use!)"
          + "\n`[" + p +"ping/ping]` - API response."
          + "\n`[" + p +"amount/" + p + "колличество/колво/колличество]` - How many times have you connected to channels."
          + "\n`[top 3/колво топ]` - Top 3 by connection."
          + "\n`" + p + "clear + <number: >= 2 & <= 100>` - Deletes messages."
          + "\n`[" + p + "flip/flip]` - flip a coin."
          + "\n`[" + p + "ищи/ищи]` - " + p + "ищи + пробел + какой-то запрос в google [g.zeos.in](https://g.zeos.in/) "
          + "\n`YouTube link + space + minutes + space + seconds if present` - converts to a short link with time"
      );
      info.addField("Links:", ":zap: [megolox.ru](https://megolox.ru)\n" +
      ":robot: [Add me to other guilds](https://discord.com/oauth2/authorize?client_id=754093698681274369&scope=bot&permissions=8)", false);
      info.addField("Bot creator", ":tools: [mego](https://steamcommunity.com/id/megoRU)", false);
      info.addField("License", ":page_facing_up: [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0)", false);
      info.addField("Support Server", ":helmet_with_cross: [Click me](https://discord.com/invite/UrWG3R683d)", false);

      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}
