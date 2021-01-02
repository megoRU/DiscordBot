package messagesevents;

import java.util.Calendar;

public class ClassWithThread extends Thread {

  @Override
  public void run() {
    while (true) {
      Calendar calendar = Calendar.getInstance();
      int year = calendar.get(Calendar.YEAR);
      int sec = calendar.get(Calendar.SECOND);
      System.out.println(sec);
      try {
        ClassWithThread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        ClassWithThread.currentThread().interrupt();
      }
//      if (year == 2021) {
//        BotStart.jda.getGuildById("250700478520885248")
//            .getDefaultChannel()
//            .sendMessage("**С новым 2021 годом!** \uD83C\uDF84\u200B :sparkles: \n" +
//                "Теперь добби свободен." + "\n@everyone").queue();
//        break;
//      }
    }
  }
}