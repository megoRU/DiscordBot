import javax.security.auth.login.LoginException;
import startbot.BotStart;

public class Main {

  public static void main(String[] args) throws LoginException, InterruptedException {

    BotStart botStart = new BotStart();
    botStart.startBot();
    //runBot.sendMessage("753624591594946678", "Бот запущен!");
  }
}
