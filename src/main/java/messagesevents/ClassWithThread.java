package messagesevents;

import config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import startbot.BotStart;

public class ClassWithThread extends Thread {

  private static final String ID_GUILD = "250700478520885248";
  private static final String ID_CHAT = "800380002767208518";

  @Override
  public void run() {
    while (true) {

      final IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
      request.setHost(Config.getIpServerForTracert());
      final IcmpPingResponse response = IcmpPingUtil.executePingRequest(request);
      final String formattedResponse = IcmpPingUtil.formatResponse(response);

      String[] args = formattedResponse.split(" ");

      String replace = args[4]
          .replaceAll("time=", "")
          .replaceAll("ms", "");
      System.out.println(replace);
      if (Integer.parseInt(replace) > 50) {
        try {
          EmbedBuilder ping = new EmbedBuilder();
          ping.setColor(0xff3923);
          ping.setTitle("Проблема с пингом на Linux сервере!");
          ping.setDescription("Пинг сейчас: `" + replace + "ms`");
          BotStart.jda.getGuildById(ID_GUILD)
              .getTextChannelById(ID_CHAT)
              .sendMessage(ping.build()).queue();
          ping.clear();
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
        try {
          ClassWithThread.sleep(3540000);
        } catch (InterruptedException e) {
          ClassWithThread.currentThread().interrupt();
          e.printStackTrace();
        }
      }

      try {
        ClassWithThread.sleep(12000);
      } catch (InterruptedException e) {
        ClassWithThread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
  }
}