package events;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDeleting extends ListenerAdapter {

  public final String DELETE_INDEXES = "clear\\s+\\d+";
  public final String MOVE = "move";
  public final String HELP = "!help";
  public final String HELP_WITH_OUT = "help";
  public final String INFO = "info";

  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    String idUser = event.getMember().getUser().getId();
    boolean boolPermissionAdmin = event.getMember().hasPermission(Permission.ADMINISTRATOR);

    if (message.matches(HELP) || message.matches(HELP_WITH_OUT) || message.matches(INFO)) {
      EmbedBuilder info = new EmbedBuilder();
      info.setColor(0xf45642);
      info.setTitle("Информация");
      info.setDescription("Команды: " + "\n"
          + "[!help/help/info] -> Информация" + "\n"
          + "clear + число" + " -> Удаляет сообщения. Нужно быть админом" + "\n"
          + "Ютуб ссылка + пробел + время в минутах! -> конвертирует в короткую ссылку со временем"
      );
      info.addField("Ссылки:", "Сайт: [megolox.ru](https://megolox.ru)" + "\n"
              + "TS3 Сервер: ` ts3.megolox.ru`" + "\n"
              + "CS:GO Сервер: `176.96.238.167:27015`" + "\n"
              + "",
          false);
      info.addField("Создатель бота", "[mego](https://steamcommunity.com/id/megoRU)", false);
      event.getChannel().sendMessage(info.build()).queue();
      info.clear();
    }

    if (message.matches(MOVE) & !event.getMember().getUser().isBot() & idUser
        .equals("310364711587676161") & boolPermissionAdmin) {
      List<Member> memberList = new ArrayList<>();
      memberList.add(event.getMember());

      event.getGuild().moveVoiceMember(memberList.get(0),
          event.getGuild().getVoiceChannels().get(1))
          .queue();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      event.getGuild().moveVoiceMember(memberList.get(0),
          event.getGuild().getVoiceChannels().get(0))
          .queue();
    }

    if (message.matches(MOVE) & !boolPermissionAdmin) {
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error: You not Admin");
      error.setDescription("You need Permission.ADMINISTRATOR");
      event.getChannel().sendMessage(error.build()).queue();
    }

    if (message.matches(DELETE_INDEXES) & !boolPermissionAdmin) {
      EmbedBuilder error = new EmbedBuilder();
      error.setColor(0xff3923);
      error.setTitle("🔴 Error: You not Admin");
      error.setDescription("You need Permission.ADMINISTRATOR");
      event.getChannel().sendMessage(error.build()).queue();
    }

    if (message.matches(DELETE_INDEXES) & boolPermissionAdmin) {
      String[] commandArray = message.split("\\s+", 2);
      String index = commandArray[1];
      int indexParseInt = Integer.parseInt(index);
      if (indexParseInt == 1) {
        EmbedBuilder error = new EmbedBuilder();
        error.setColor(0xff3923);
        error.setTitle("🔴 Index cannot be 0-1");
        error.setDescription("Between 0-1 index can be deleted.");
        event.getChannel().sendMessage(error.build()).queue();
      }
      try {
        if (indexParseInt > 1) {
          List<Message> messages = event.getChannel().getHistory().retrievePast(indexParseInt)
              .complete();
          event.getChannel().deleteMessages(messages).queue();
        }
      } catch (IllegalArgumentException e) {
        if (e.toString().startsWith(
            "java.lang.IllegalArgumentException: Message retrieval")) {
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Too many messages selected");
          error.setDescription("Between 1-100 messages can be deleted at one time.");
          event.getChannel().sendMessage(error.build()).queue();
        } else {
          // Messages too old
          EmbedBuilder error = new EmbedBuilder();
          error.setColor(0xff3923);
          error.setTitle("🔴 Selected messages are older than 2 weeks");
          error.setDescription("Messages older than 2 weeks cannot be deleted.");
          event.getChannel().sendMessage(error.build()).queue();
        }
      }
    }
  }
}