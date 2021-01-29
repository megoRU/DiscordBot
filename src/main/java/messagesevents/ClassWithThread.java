package messagesevents;

import net.dv8tion.jda.api.entities.Activity;
import startbot.BotStart;

public class ClassWithThread extends Thread {

  @Override
  public void run() {
    while (true) {
      try {
        ClassWithThread.sleep(80000);
        BotStart.jda.getPresence().setActivity(Activity.playing("—> ?check"));
        ClassWithThread.sleep(20000);
        BotStart.jda.getPresence().setActivity(Activity.playing("—> !music | !help"));
      } catch (InterruptedException e) {
        ClassWithThread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
  }
}