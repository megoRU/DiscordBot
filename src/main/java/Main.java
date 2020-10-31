import javax.security.auth.login.LoginException;

public class Main {

  public static void main(String[] args) throws LoginException, InterruptedException {
    RunBot runBot = new RunBot();
    runBot.startBot();
    //runBot.sendMessage("753624591594946678", "Бот запущен!");
  }
}
