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
  public final String MUSIC = "!music";

  @Override
  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }

    String message = event.getMessage().getContentRaw().toLowerCase();
    String prefix = HELP;
    String prefix2 = INFO;
    String prefix3 = MUSIC;

    String p = "!";

    if (BotStart.mapPrefix.containsKey(event.getGuild().getId())) {
      prefix = BotStart.mapPrefix.get(event.getGuild().getId()) + "help";
      prefix2 = BotStart.mapPrefix.get(event.getGuild().getId()) + "info";
      prefix3 = BotStart.mapPrefix.get(event.getGuild().getId()) + "music";
      p = BotStart.mapPrefix.get(event.getGuild().getId());
    }

    if (message.equals(prefix3)) {
      EmbedBuilder music = new EmbedBuilder();
      music.setColor(0xa224db);
      music.addField("Music:",
          "`" + p + "play <YouTube url>` - The bot will play audio from the video. \n" +
              "`" + p + "pplay <YouTube url playlist>` - Generally this is for playlists.\n" +
              "`" + p + "stop` - Stops music and clears.\n" +
              "`" + p + "skip` - Skip track.\n" +
              "`" + p + "pause` - Pause track.\n" +
              "`" + p + "leave` - Bot leave from voice channel.\n" +
              "`" + p + "list` - Will print all the tracks in the list.\n" +
              "`" + p + "restart` - Restarts the playing track.\n" +
              "`" + p + "repeat` - Repeat endlessly track.\n" +
              "`" + p + "reset` - Resets the player.\n" +
              "`" + p + "np` - Sends a list of tracks currently on the list.\n", false);
      event.getChannel().sendMessage(music.build()).queue();
      music.clear();
      return;
    }

    if (message.equals(prefix) || message.equals(HELP_WITH_OUT) || message.equals(INFO_WITH_OUT)
        || message.equals(prefix2) || message.equals(INFO_RU)) {
      String avatarUrl = null;
      String avatarFromEvent = event.getMessage().getAuthor().getAvatarUrl();
      if (avatarFromEvent == null) {
        avatarUrl = "https://cdn.discordapp.com/avatars/754093698681274369/dc4b416065569253bc6323efb6296703.png";
      }
      if (avatarFromEvent != null) {
        avatarUrl = avatarFromEvent;
      }
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xa224db);
      info.setAuthor(event.getAuthor().getName(), null, avatarUrl);
      info.addField("Prefix:",
          "`*prefix <symbol>` - Changes the prefix.\n" +
              "`*prefix reset` - Reset the prefix.", false);

      info.addField("Giveaway:", "`" + p + "gift start` - Run Giveaway \n`" +
          p + "gift stop` - Stop Giveaway.", false);
      info.addField("Music:",
          "`" + p + "play <YouTube url>` - The bot will play audio from the video. \n" +
              "`" + p + "pplay <YouTube url playlist>` - Generally this is for playlists.\n" +
              "`" + p + "stop` - Stops music and clears.\n" +
              "`" + p + "skip` - Skip track.\n" +
              "`" + p + "pause` - Pause track.\n" +
              "`" + p + "leave` - Bot leave from voice channel.\n" +
              "`" + p + "list` - Will print all the tracks in the list.\n" +
              "`" + p + "restart` - Restarts the playing track.\n" +
              "`" + p + "repeat` - Repeat endlessly track.\n" +
              "`" + p + "reset` - Resets the player.\n" +
              "`" + p + "np` - Sends a list of tracks currently on the list.\n", false);

      info.addField("Games:",
          "`" + p + "roll` - The Game of Dice.\n" +
              "`" + p + "hg` - Hangman/Виселица.", false);

      info.addField("Other functions:",
          "`" + p + "help` - Information."
              + "\n`" + p + "poll <text>` - Create a poll."
              + "\n`" + p + "kick` - Example: " + p + "kick <@user>/" + p + "kick <@user> <reason>"
              + "\n`" + p + "ban` - Example: " + p + "ban <@user> <days>/" + p
              + "ban <@user> <days> <reason>"
              + "\n`" + p + "bitrate <96>` - Changes the channel bitrate to the specified bitrate."
              + "\n`курс доллара, курс евро, курс, евро, доллар` - Данные от ЦБ к рублю."
              + "\n`100 долларов в рублях` - Доступны валюты: USD, EUR, RUB"
              + "\n`" + p + "uptime/uptime` - uptime bot."
              + "\n`" + p + "ping/ping` - API response."
              + "\n`" + p + "amount/" + p
              + "колво]` - How many times have you connected to channels."
              + "\n`top 3/колво топ` - Top 3 by connection."
              + "\n`" + p
              + "clear + <number: >= 2 & <= 100>` - Deletes the specified number of messages."
              + "\n`" + p + "flip` - flip a coin."
              + "\n`" + p + "ищи/ищи <текст>` - " + " [g.zeos.in](https://g.zeos.in/) "
              + "\n`<YouTube link> <minutes> <seconds if present>` - Converts to a short link with time.",
          false);
      info.addField("Checking the settings",
          "`?check` - Checks the correct bot setup.", false);
      info.addField("Links:", ":zap: [megoru.ru](https://megoru.ru)\n" +
              ":robot: [Add me to other guilds](https://discord.com/oauth2/authorize?client_id=754093698681274369&scope=bot&permissions=8)",
          false);
      info.addField("Bot creator", ":tools: [mego](https://steamcommunity.com/id/megoRU)", false);
      info.addField("License",
          ":page_facing_up: [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0)",
          false);
      info.addField("Support Server",
          ":helmet_with_cross: [Click me](https://discord.com/invite/UrWG3R683d)", false);

      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }
  }
}